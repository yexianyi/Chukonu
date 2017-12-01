package com.yxy.chukonu.spark.streaming;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

/**
 * Hello world!
 *
 */
public class Server 
{
    public static void main( String[] args ) {
    	// Create a local StreamingContext with two working thread and batch interval of 1 second
    	SparkConf conf = new SparkConf().setAppName("NetworkWordCount");
    	conf.setMaster("local[2]") ;
    	
    	//Retreving Streaming Context from Spark Conf
    	JavaStreamingContext streamCtx = new JavaStreamingContext(conf, Durations.seconds(2));
    	
    	//specify Stream type, here we use TCP socket as text stream
    	//setup listener on specific machine, receive data and store them into RAM,
    	//if out of RAM capacity, the data will be stored to HD.
    	//then, data will be transformed to DStream
    	JavaReceiverInputDStream<String> lines = streamCtx.socketTextStream("localhost", 9087, StorageLevel.MEMORY_AND_DISK_SER_2()) ;
    	
    	//map and merge 
    	JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>(){
			@Override
			public Iterator<String> call(String x) throws Exception {
				return Arrays.asList(x.split(" ")).iterator();
			}
    		
    	}) ;
    	
    	
    	JavaPairDStream<String,Integer> pairs = words.mapToPair(new PairFunction<String,String,Integer>(){
			@Override
			public Tuple2<String, Integer> call(String s) throws Exception {
				return new Tuple2<String,Integer>(s, 1);
			}
    		
    	}) ;
    	
    	
    	JavaPairDStream<String,Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>(){
			@Override
			public Integer call(Integer i1, Integer i2) throws Exception {
				return i1+i2 ;
			}
    	}) ;
    	
    	
    	wordCounts.print(10);
    	
    	//init streaming context
    	streamCtx.start();
    	
    	try {
			streamCtx.awaitTermination();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
