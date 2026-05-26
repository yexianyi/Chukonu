package com.yxy.chukonu.opcua.server;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.milo.opcua.sdk.core.AccessLevel;
import org.eclipse.milo.opcua.sdk.server.Lifecycle;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.ManagedNamespaceWithLifecycle;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulationNamespace extends ManagedNamespaceWithLifecycle {

    private static final Logger log = LoggerFactory.getLogger(SimulationNamespace.class);
    public static final String NAMESPACE_URI = "urn:yxy:chukonu:opcua:simulation";

    private Timer timer;
    private final Random random = new Random();
    
    private UaVariableNode temperatureNode;
    private UaVariableNode humidityNode;

    public SimulationNamespace(OpcUaServer server) {
        super(server, NAMESPACE_URI);

        getLifecycleManager().addLifecycle(new Lifecycle() {
            @Override
            public void startup() {
                log.info(">>>> [Server Namespace] 收到基础设施就绪通知，开始创建地址空间节点...");
                // 1. 初始化节点树
                initializeNodes();
                // 2. 顺正拉起仿真定时造数器
                startSimulation();
            }

            @Override
            public void shutdown() {
                log.info(">>>> [Server Namespace] 正在停止仿真造数定时器...");
                stopSimulation();
            }
        });
    }

    private void initializeNodes() {
        // 创建 Devices 文件夹
        NodeId folderNodeId = newNodeId("Devices");
        UaFolderNode folderNode = new UaFolderNode(
            getNodeContext(),
            folderNodeId,
            new QualifiedName(getNamespaceIndex(), "Devices"),
            LocalizedText.english("Simulation Devices")
        );
        getNodeManager().addNode(folderNode);

        try {
            UaNode objectsFolder = getServer().getAddressSpaceManager().getManagedNode(Identifiers.ObjectsFolder).orElse(null);
            if (objectsFolder instanceof UaFolderNode) {
                ((UaFolderNode) objectsFolder).addOrganizes(folderNode);
            }
        } catch (Exception e) {
            log.error("Failed to bind custom folder to OPC UA ObjectsFolder", e);
        }

        // 创建温湿度变量节点
        temperatureNode = createVariableNode("Devices.Temperature", "Temperature", Identifiers.Double, 25.0);
        folderNode.addOrganizes(temperatureNode);

        humidityNode = createVariableNode("Devices.Humidity", "Humidity", Identifiers.Int32, 60);
        folderNode.addOrganizes(humidityNode);
    }

    private void startSimulation() {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (temperatureNode == null || humidityNode == null) return;

                double newTemp = 20.0 + random.nextDouble() * 15.0;
                int newHumid = 50 + random.nextInt(30);

                // 更新值并触发数据突变事件
                temperatureNode.setValue(new DataValue(new Variant(newTemp)));
                humidityNode.setValue(new DataValue(new Variant(newHumid)));

                log.info(">>>> [Server 仿真造数中] 实时温度: {} ℃ | 实时湿度: {} %", 
                    String.format("%.2f", newTemp), newHumid);
            }
        }, 1000, 1000);
        log.info("OPC UA Simulation data generator started.");
    }

    private void stopSimulation() {
        if (timer != null) {
            timer.cancel();
        }
        log.info("OPC UA Simulation data generator stopped.");
    }

    private UaVariableNode createVariableNode(String id, String name, NodeId dataType, Object initialValue) {
        UaVariableNode node = new UaVariableNode.UaVariableNodeBuilder(getNodeContext())
            .setNodeId(newNodeId(id))
            .setAccessLevel(AccessLevel.READ_WRITE)
            .setBrowseName(new QualifiedName(getNamespaceIndex(), name))
            .setDisplayName(LocalizedText.english(name))
            .setDataType(dataType)
            .setTypeDefinition(Identifiers.BaseDataVariableType)
            .build();

        node.setValue(new DataValue(new Variant(initialValue)));
        getNodeManager().addNode(node);
        return node;
    }

    @Override public void onDataItemsCreated(List<DataItem> dataItems) { log.info("Client subscribed successfully, items count: {}", dataItems.size()); }
    @Override public void onDataItemsModified(List<DataItem> dataItems) {}
    @Override public void onDataItemsDeleted(List<DataItem> dataItems) {}
    @Override public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {}
}