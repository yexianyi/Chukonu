/**
 * Copyright (c) 2016, Xianyi Ye
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
package com.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Test {

	public static void main(String[] args) throws IOException {
		String path = "/F://Java//FinancialService//target//FinancialService-1.0-SNAPSHOT.jar" ;
//		String path = "/F:/Java/FinancialService/target/classes/WEB-INF/realm.properties" ;
//		String path = "/F:/Java/FinancialService/target/FinancialService-1.0-SNAPSHOT.jar" ;
		String folder=System.getProperty("java.io.tmpdir");
//		path = path.substring(0, path.lastIndexOf('/')) ;
		
		JarFile jarFile = new JarFile(path) ;
		JarEntry entry = jarFile.getJarEntry("realm.properties") ;
		System.out.println(folder);
		File file = new File(folder) ;
		if(file.exists()){
			System.out.println("Exist");
		}else{
			System.out.println("Not Exist");
		}
		
//		 JarFile jarFile = null;
//         try {
//             jarFile = new JarFile(path);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         
//         Enumeration<JarEntry> entries = jarFile.entries();
//         while (entries.hasMoreElements()) 
//         {
//             String element = entries.nextElement().getName();
//             URL templateUrl = Test.class.getClassLoader().getResource(element);
//             System.out.println(templateUrl.getPath());
//         }
//		
		
		
		

	}

}
