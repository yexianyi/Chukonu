package net.chukonu.spring.boot.kafka.test;
import org.apache.kafka.clients.consumer.ConsumerRecord;  
import org.apache.kafka.clients.consumer.ConsumerRecords;  
import org.apache.kafka.clients.consumer.KafkaConsumer;  
import org.apache.kafka.common.TopicPartition;
  
import java.util.Arrays;  
import java.util.Properties;  
  
public class Consumer {  
    public static void main(String[] args) {  
    	String topic = "test";
    	Properties props = new Properties();  
    	props.put("bootstrap.servers", " localhost:32775");  
    	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
    	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
    	props.setProperty("group.id", "0");  
    	props.setProperty("enable.auto.commit", "true");  
    	props.setProperty("auto.offset.reset", "earliest");  
    	  
    	KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);  
    	consumer.subscribe(Arrays.asList(topic));  
    	  
    	for (int i = 0; i < 2; i++) {  
    	    ConsumerRecords<String, String> records = consumer.poll(1000);  
    	    System.out.println(records.count());  
    	    for (ConsumerRecord<String, String> record : records) {  
    	        System.out.println(record);  
//    	        consumer.seekToBeginning(new TopicPartition(record.topic(), record.partition()));
    	    }  
    	}
    }
}