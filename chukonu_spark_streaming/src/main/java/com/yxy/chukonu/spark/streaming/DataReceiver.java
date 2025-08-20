/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.spark.streaming;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.Partition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Hello world!
 *
 */
public class DataReceiver 
{
	
    public static void main( String[] args ) {

    	System.setProperty("hadoop.home.dir", DataReceiver.class.getClassLoader().getResource("").getPath());
    	Logger.getLogger("org.apache.spark").setLevel(Level.ERROR) ;
    	
    	// Create a local StreamingContext with two working thread and batch interval of 1 second
    	SparkConf conf = new SparkConf().setAppName("NetworkWordCount");
    	conf.setMaster("local[2]") ;

    	
    	//Retreving Streaming Context from Spark Conf
    	JavaStreamingContext streamCtx = new JavaStreamingContext(conf, Durations.milliseconds(500));
    	
    	//specify Stream type, here we use TCP socket as text stream
    	//setup listener on specific machine, receive data and store them into RAM,
    	//if out of RAM capacity, the data will be stored to HD.
    	//then, data will be transformed to DStream
    	JavaReceiverInputDStream<String> lines = streamCtx.socketTextStream("localhost", 9087, StorageLevel.MEMORY_AND_DISK_SER_2()) ;
    	
    	lines.foreachRDD(new VoidFunction<JavaRDD<String>>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void call(JavaRDD<String> rdd) throws Exception {
				for(String str : rdd.collect()){
					System.out.println(str+ " ");
				}
				System.out.println("---------------------------------");
			}
    		
    	});
    	
    	
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
