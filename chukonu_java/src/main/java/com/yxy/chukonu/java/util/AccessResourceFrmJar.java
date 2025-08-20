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
package com.yxy.chukonu.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AccessResourceFrmJar{
	public List<InputStream> loadWFTempInputStream(String templateDir) {
	       
        List<InputStream> list = new ArrayList<InputStream>(); 
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation(); 
        
        String currPath = null;
        
        try {
            currPath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        if (currPath.endsWith(".jar")==true) //jar path
        {
            System.out.println("<Load following WF template from Jar file>");
            
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(currPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) 
            {
                String element = entries.nextElement().getName();
                URL templateUrl = getClass().getClassLoader().getResource(element);
               
                if (templateUrl.getPath().endsWith(".xml") && templateUrl.getPath().contains(templateDir)) 
                {
                    InputStream is = getClass().getClassLoader().getResourceAsStream(element); 
                    list.add(is);
                    System.out.println(templateUrl.getPath());
                }
            }
        }
        
        if (currPath.endsWith(".jar")==false) //class path
        {
            System.out.println("<Load following WF template from class folder>");
            URL dir = this.getClass().getResource(templateDir) ;
            String tempDir = dir.getPath();
            
            File folder = new File(tempDir);
            File[] files = folder.listFiles();
          
            for(File template:files) 
            {
                System.out.println(template.getAbsolutePath());
                InputStream is = null;
                
                try {
                    is = new FileInputStream(template.getPath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                
                list.add(is) ;
            }
        }   
     
        return list;
    }

}

