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
package com.yxy.chukonu.java.hadoop.hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


public class Writing {
	
	

	public static void main(String[] args) throws Exception {
		System.out.print("Begin:"+new Date());
		//Source file in the local file system
//		String localSrc = "/Users/xianyiye/Documents/workspace/Redshift-jdbc/customer.tbl.2";
		String localSrc = "/Users/xianyiye/Downloads/dump2/address.txt";
		//Destination file in HDFS
//		String dst = "hdfs://quickstart.cloudera:8020/user/hive/warehouse/address.txt";
		String dst = "hdfs://172.23.5.144:8020/user/hive/warehouse/address";
		 
		//Input stream for the file in local file system to be written to HDFS
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		 
		//Get configuration of Hadoop system
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.set("dfs.client.use.datanode.hostname", "true");
		
		System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
		 
		//Destination file in HDFS
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
//		OutputStream out = fs.create(new Path(dst));
		OutputStream out = fs.create(new Path(dst), (short)1) ;
//		fs.delete(new Path(dst), true) ;
		
		//Copy file from local to HDFS
		IOUtils.copyBytes(in, out, 4096, true);
		 
		System.out.println(dst + " copied to HDFS");
		System.out.print("Finished:"+new Date());
		 
		}


}
