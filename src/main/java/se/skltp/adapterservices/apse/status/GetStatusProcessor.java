package se.skltp.adapterservices.apse.status;

import io.netty.buffer.PooledByteBufAllocatorMetric;
import org.apache.camel.*;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import se.skltp.adapterservices.apse.config.EndpointConfig;
import se.skltp.adapterservices.apse.constants.HttpHeaders;
import se.skltp.adapterservices.apse.utils.MemoryUtil;

import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GetStatusProcessor implements Processor {

    public static final String KEY_APP_NAME = "Name";
    public static final String KEY_APP_VERSION = "Version";
    public static final String KEY_APP_BUILD_TIME = "BuildTime";
    public static final String KEY_SERVICE_STATUS = "ServiceStatus";
    public static final String KEY_UPTIME = "Uptime";
    public static final String KEY_MANAGEMENT_NAME = "ManagementName";
    public static final String KEY_JAVA_VERSION = "JavaVersion";
    public static final String KEY_CAMEL_VERSION = "CamelVersion";
    public static final String KEY_JVM_TOTAL_MEMORY = "JvmTotalMemory";
    public static final String KEY_JVM_FREE_MEMORY = "JvmFreeMemory";
    public static final String KEY_JVM_USED_MEMORY = "JvmUsedMemory";
    public static final String KEY_JVM_MAX_MEMORY = "JvmMaxMemory";
    public static final String KEY_DIRECT_MEMORY = "DirectMemBufferPool";
    public static final String KEY_NON_HEAP_MEMORY = "NonHeapMemory";
    public static final String KEY_VM_MAX_DIRECT_MEMORY = "MaxDirectMemory";
    public static final String KEY_NETTY_DIRECT_MEMORY = "NettyDirectMemory";
    public static final String KEY_ENDPOINTS = "Endpoints";

    @Value("${apse.status.url}")
    private String statusUrl;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private EndpointConfig endpointConfig;

    @Autowired
    BuildProperties buildProperties;

    @Override
    public void process(Exchange exchange) {
        boolean showNettyMemory = exchange.getIn().getHeaders().containsKey("netty");
        boolean showExtendedMemory = exchange.getIn().getHeaders().containsKey("memory");
        if (showNettyMemory) {
            exchange.getIn().setBody(MemoryUtil.getNettyMemoryJsonString());
        } else {
            JSONObject jsonObject = new JSONObject(registerInfo(showExtendedMemory));
            try {
                exchange.getIn().setBody(jsonObject.toString(2).replace("\\/", "/"));
            } catch (JSONException e) {
                exchange.getIn().setBody(jsonObject.toString());
            }
        }
        exchange.getIn().getHeaders().put(HttpHeaders.HEADER_CONTENT_TYPE, "application/json");
    }

    private Map<String, Object> registerInfo(boolean showMemory) {
        OrderedMap map = new ListOrderedMap();

        map.put(KEY_APP_NAME, buildProperties.getName());
        map.put(KEY_APP_VERSION, buildProperties.getVersion());
        map.put(KEY_APP_BUILD_TIME, buildProperties.getTime());

        ServiceStatus serviceStatus = camelContext.getStatus();
        map.put(KEY_SERVICE_STATUS, "" + serviceStatus);
        map.put(KEY_UPTIME, camelContext.getUptime());
        map.put(KEY_MANAGEMENT_NAME, camelContext.getManagementName());
        map.put(KEY_JAVA_VERSION, System.getProperties().get("java.version"));
        map.put(KEY_CAMEL_VERSION, camelContext.getVersion());

        Runtime instance = Runtime.getRuntime();
        map.put(KEY_JVM_TOTAL_MEMORY, "" + MemoryUtil.bytesReadable(instance.totalMemory()));
        map.put(KEY_JVM_FREE_MEMORY, "" + MemoryUtil.bytesReadable(instance.freeMemory()));
        map.put(KEY_JVM_USED_MEMORY,
                "" + MemoryUtil.bytesReadable((instance.totalMemory() - instance.freeMemory())));
        map.put(KEY_JVM_MAX_MEMORY, "" + MemoryUtil.bytesReadable(instance.maxMemory()));
        if (showMemory) {
            map.put(KEY_DIRECT_MEMORY, "" + getDirectMemoryString());
            map.put(KEY_VM_MAX_DIRECT_MEMORY, "" + MemoryUtil.getVMMaxMemory());
            map.put(KEY_NON_HEAP_MEMORY, "" + getNonHeapMemory());
            map.put(KEY_NETTY_DIRECT_MEMORY, "" + getNettyDirectMemory());
        }
        map.put(KEY_ENDPOINTS, getEndpointInfo());
        return map;
    }

    private String getNonHeapMemory() {
        MemoryUsage nonHeapMemoryUsage = MemoryUtil.getNonHeapMemoryUsage();

        return String.format("Init: %s Used: %s, Committed: %s, Max: %s",
                MemoryUtil.bytesReadable(nonHeapMemoryUsage.getInit()),
                MemoryUtil.bytesReadable(nonHeapMemoryUsage.getUsed()),
                MemoryUtil.bytesReadable(nonHeapMemoryUsage.getCommitted()),
                MemoryUtil.bytesReadable(nonHeapMemoryUsage.getMax()));
    }

    private String getNettyDirectMemory() {
        PooledByteBufAllocatorMetric nettyMetrics = MemoryUtil.getNettyPooledByteBufMetrics();
        String usedDirectMem = MemoryUtil.bytesReadable(nettyMetrics.usedDirectMemory());
        String usedHeapMem = MemoryUtil.bytesReadable(nettyMetrics.usedHeapMemory());

        return String.format("Direct: %s(Arenas:%d), Heap: %s(Arenas:%d), ThreadCaches: %d",
                usedDirectMem,
                nettyMetrics.numDirectArenas(),
                usedHeapMem,
                nettyMetrics.numHeapArenas(),
                nettyMetrics.numThreadLocalCaches());
    }

    private String getDirectMemoryString() {
        return String.format("Used: %s, Count: %d, Max Capacity: %s",
                MemoryUtil.getMemoryUsed(),
                MemoryUtil.getCount(),
                MemoryUtil.getTotalCapacity());
    }

    private Map<String, Map<String, Object>> getEndpointInfo() {
        Map<String, Map<String, Object>> endpoints = new ListOrderedMap();
        List<Route> routes = camelContext.getRoutes();

        for (Route route : routes) {

            String endpoint = route.getEndpoint().getEndpointKey();
            if (endpoint.startsWith("http")) {
                AtomicReference<String> name = new AtomicReference<>("");
                endpointConfig.getInbound().forEach( (k, v) -> {
                        if (v.equals(endpoint)){
                            name.set(k);
                        }
                    }
                );
                Map<String, Object> endpointMap = new ListOrderedMap();
                endpointMap.put("url", endpoint);
                if (!name.get().equals("")){
                    endpointMap.put("outbound", endpointConfig.getOutbound().get(name.get()));
                } else if (endpoint.equals(statusUrl)) {
                    endpointMap.put("name", "ApSe adapter status");
                }
                endpoints.put(name.get(), endpointMap);
            }
        }
        return endpoints;
    }
}
