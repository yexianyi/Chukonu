package com.yxy.chukonu.opcua.client;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpcUaClientService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OpcUaClientService.class);

    @Value("${opcua.client.endpoint-url}")
    private String endpointUrl;

    private OpcUaClient client;

    @Override
    public void run(String... args) {
        log.info(">>>> [Client] 正在连接 OPC UA 服务端: {}", endpointUrl);

        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointUrl).get();
            EndpointDescription endpoint = endpoints.stream()
                .filter(e -> e.getSecurityPolicyUri().equals("http://opcfoundation.org/UA/SecurityPolicy#None"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No unencrypted endpoint found"));

            OpcUaClientConfigBuilder config = new OpcUaClientConfigBuilder();
            config.setEndpoint(endpoint);

            client = OpcUaClient.create(config.build());
            client.connect().get();
            log.info(">>>> [Client] 成功连接上服务器地址空间！");

            // 创建订阅组
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

            int nsIndex = 2; 
            NodeId tempNodeId = new NodeId(nsIndex, "Devices.Temperature");
            NodeId humidNodeId = new NodeId(nsIndex, "Devices.Humidity");

            List<MonitoredItemCreateRequest> requests = new ArrayList<>();
            requests.add(createReadRequest(tempNodeId, 1L));
            requests.add(createReadRequest(humidNodeId, 2L));

            // 绑定异步消费通知
            subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                requests,
                (item, id) -> item.setValueConsumer(this::onDataChangeNotification)
            ).get();

            log.info(">>>> [Client] 数据数采通道监听就绪，开始实时接收突变数据...");

        } catch (Exception e) {
            log.error(">>>> [Client] 数采引擎初始化异常: ", e);
        }
    }

    private MonitoredItemCreateRequest createReadRequest(NodeId nodeId, long clientHandle) {
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);
        MonitoringParameters parameters = new MonitoringParameters(
            Unsigned.uint(clientHandle), 1000.0, null, Unsigned.uint(10), true
        );
        return new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);
    }

    private void onDataChangeNotification(UaMonitoredItem item, org.eclipse.milo.opcua.stack.core.types.builtin.DataValue value) {
        String nodeIdStr = item.getReadValueId().getNodeId().getIdentifier().toString();
        Object rawValue = value.getValue().getValue();
        
        // 数采结果高亮输出
        log.info("------------------------------------------------------------------------------------------------");
        log.info("[DATA INGESTION SUCCESS] -> NodeId: {} | Metric Value: {} | Device SourceTime: {}", 
            nodeIdStr, rawValue, value.getSourceTime());
        log.info("------------------------------------------------------------------------------------------------");
    }

    @PreDestroy
    public void closeClient() {
        if (client != null) {
            try {
                client.disconnect().get();
                log.info("OPC UA Data Collector Client shutdown safely...");
            } catch (Exception e) {
                log.error("Error during client disconnect", e);
            }
        }
    }
}