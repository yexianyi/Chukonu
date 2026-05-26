package com.yxy.chukonu.opcua.server;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxy.chukonu.opcua.config.OpcPoint;

/**
 * OPC UA 服务端数据交互单体服务
 */
@Service
public class OpcUaServerService {

    private static final Logger log = LoggerFactory.getLogger(OpcUaServerService.class);

    private final OpcUaServer opcUaServer;
    
    // 核心修复：将底层 namespaceIndex 的类型从 int 改为 UShort，彻底解决编译期 Type mismatch 错误
    private UShort namespaceIndex = null;

    @Autowired
    public OpcUaServerService(OpcUaServer opcUaServer) {
        this.opcUaServer = opcUaServer;
        // 动态解析自定义仿真命名空间的 UShort 索引
        this.namespaceIndex = opcUaServer.getNamespaceTable().getIndex(SimulationNamespace.NAMESPACE_URI);
    }

    /**
     * 高级拓展：直接支持传入点位枚举来更新数据
     *
     * @param point OpcPoint点位枚举对象对象 (如 OpcPoint.TEMPERATURE)
     * @param value 需要写入的物理值值
     * @return 是否写入成功
     */
    public boolean writeNodeValue(OpcPoint point, Object value) {
        if (point == null) return false;
        // 点位里定义的 nodeId 是 "Devices.Temperature" 或形如 "ns=2;s=Temperature"
        String identifier = point.getNodeId();
        if (identifier.contains("s=")) {
            identifier = identifier.substring(identifier.indexOf("s=") + 2);
        }
        return writeNodeValue(identifier, value);
    }

    /**
     * 向指定的 OPC UA 变量节点动态写入/更新数据
     *
     * @param nodeIdString 节点String类型的ID (例如: "Devices.Temperature")
     * @param value        需要写入的最新物理值
     * @return 是否写入成功
     */
    public boolean writeNodeValue(String nodeIdString, Object value) {
        try {
            Optional<UaVariableNode> nodeOpt = getVariableNode(nodeIdString);
            if (nodeOpt.isPresent()) {
                UaVariableNode variableNode = nodeOpt.get();
                // 将标准 Java 数据类型包装为 OPC UA 专用的 Variant 变体，并安全写入节点
                variableNode.setValue(new DataValue(new Variant(value)));
                log.debug("Successfully write value [{}] to NodeId: {}", value, nodeIdString);
                return true;
            } else {
                log.warn("NodeId [{}] not found in address space.", nodeIdString);
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to write value to NodeId: " + nodeIdString, e);
            return false;
        }
    }

    /**
     * 根据节点String ID获取底层的变量节点实例
     *
     * @param nodeIdString 节点String类型的ID
     * @return 变量节点包装类
     */
    public Optional<UaVariableNode> getVariableNode(String nodeIdString) {
        if (namespaceIndex == null) {
            this.namespaceIndex = opcUaServer.getNamespaceTable().getIndex(SimulationNamespace.NAMESPACE_URI);
        }
        
        // 核心修复：直接使用 UShort 类型的 namespaceIndex 构建标准 NodeId 对象
        NodeId nodeId = new NodeId(namespaceIndex, nodeIdString);
        
        try {
            // 从 Milo 核心服务器地址空间管理器中检索托管的物理节点
            Optional<UaNode> nodeOpt = opcUaServer.getAddressSpaceManager().getManagedNode(nodeId);
            
            // 筛选并转换为标准的 UaVariableNode 变量节点返回
            return nodeOpt.filter(uaNode -> uaNode instanceof UaVariableNode)
                          .map(uaNode -> (UaVariableNode) uaNode);
                          
        } catch (Exception e) {
            log.error("Error occurred while fetching node: " + nodeIdString, e);
            return Optional.empty();
        }
    }

    /**
     * 获取当前业务命名空间的索引(UShort 包装)
     *
     * @return 业务命名空间不带符号短整型索引值
     */
    public UShort getSimulationNamespaceIndex() {
        if (namespaceIndex == null) {
            this.namespaceIndex = opcUaServer.getNamespaceTable().getIndex(SimulationNamespace.NAMESPACE_URI);
        }
        return this.namespaceIndex;
    }

    /**
     * 兼容性拓展：获取 int 类型的 Namespace 索引（通过无符号转换）
     * * @return int类型的命名空间索引
     */
    public int getSimulationNamespaceIndexAsInt() {
        UShort current = getSimulationNamespaceIndex();
        return current != null ? current.intValue() : -1;
    }
}