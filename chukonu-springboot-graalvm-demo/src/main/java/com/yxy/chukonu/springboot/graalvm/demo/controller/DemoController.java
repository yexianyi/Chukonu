package com.yxy.chukonu.springboot.graalvm.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class DemoController {

    // 模拟高密度的业务数据对象
    static class LogRecord {
        long id;
        String userId;
        String action;
        long timestamp;
        String rawPayload;

        public LogRecord(long id, String userId, String action, long timestamp) {
            this.id = id;
            this.userId = userId;
            this.action = action;
            this.timestamp = timestamp;
            // 故意生成一段较长且不重复的垃圾字符串，用来疯狂占满年轻代堆空间
            this.rawPayload = "PAYLOAD_DATA_BLOCK_UUID_" + UUID.randomUUID().toString() 
                            + "_USER_" + userId + "_ACTION_" + action;
        }

        public String getUserId() { return userId; }
        public String getAction() { return action; }
    }

    @GetMapping("/api/process-data")
    public Map<String, Object> processLargeData() {
        long startTime = System.currentTimeMillis();
        System.out.println("====== [Start to process big data] ======");

        int recordCount = 500_000; // 模拟 50 万条数据
        List<LogRecord> records = new ArrayList<>(recordCount);

        // 1. 内存分配密集阶段：疯狂创建 50 万个大对象，强行触发频繁的 GC
        String[] actions = {"LOGIN", "VIEW_ITEM", "ADD_TO_CART", "BUY", "LOGOUT"};
        Random random = new Random();
        for (int i = 0; i < recordCount; i++) {
            records.add(new LogRecord(
                i, 
                "USER_" + random.nextInt(50000), 
                actions[random.nextInt(actions.length)], 
                System.currentTimeMillis() - random.nextInt(100000)
            ));
        }

        // 2. CPU 计算密集阶段：使用并行流（ParallelStream）进行重度 CPU 计算
        // 包括：过滤、分组、高阶排序、字符串聚合，强行将多核 CPU 的使用率瞬间拉满
        Map<String, Long> actionCounts = records.parallelStream()
                .filter(r -> r.getUserId().hashCode() % 2 == 0) // 过滤掉一半数据
                .collect(Collectors.groupingBy(
                        LogRecord::getAction, 
                        Collectors.counting()
                ));

        // 3. 找出最活跃的前 100 个用户（重度排序）
        List<String> topUsers = records.parallelStream()
                .collect(Collectors.groupingBy(LogRecord::getUserId, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(100)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("====== [Finish] Total time: " + costTime + " ms ======");

        // 返回加工后的统计结果
        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("processedRecords", recordCount);
        result.put("costTimeMs", costTime);
        result.put("actionStatistics", actionCounts);
        result.put("top100UsersCount", topUsers.size());
        return result;
    }
}