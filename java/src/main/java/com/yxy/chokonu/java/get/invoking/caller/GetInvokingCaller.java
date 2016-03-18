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
package com.yxy.chokonu.java.get.invoking.caller; 

public class GetInvokingCaller{
	public static String getCaller(){
	    int i;
	    StackTraceElement stack[] = (new Throwable()).getStackTrace();
	    for (i=0; i < stack.length; i++); {
	      StackTraceElement ste=stack[i];
	        System.out.println(ste.getClassName()+"."+ste.getMethodName()+"(...);");
	      System.out.println(i+"--"+ste.getMethodName());
	      System.out.println(i+"--"+ste.getFileName());
	      System.out.println(i+"--"+ste.getLineNumber());
	    }
	    
	    return null ;
	}
}



