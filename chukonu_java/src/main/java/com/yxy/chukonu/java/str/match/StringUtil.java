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
package com.yxy.chukonu.java.str.match;

public class StringUtil{
	public static int match(String source, String pattern)
	{
	    char[] sourceArray = source.toCharArray() ;
	    char[] patternArray = pattern.toCharArray() ;
	    
	    int source_length = sourceArray.length ;
	    int pattern_length = patternArray.length ;
	    
	    int source_index = 0;
	    int pattern_index = 0;
	    int foundtimes = 0 ;
	    
	    while(source_index < source_length)
	    {
	    	
	        if(sourceArray[source_index]==patternArray[pattern_index]) //equals
	        {
	            
	            if(pattern_index == pattern_length-1)
			    {
	            	int start = source_index - pattern_index ;
	            	int end = source_index - pattern_index+pattern_length ;
	            	System.out.println(source.substring(start,end));
		        	
	            	foundtimes++ ;
		        	pattern_index = 0 ;
		        	
		        	continue ;
			    }
	            
	            source_index++;
	            pattern_index++;
	        }
	        else //not equals
	        {
	        	source_index = source_index - (pattern_index-1); 
	            pattern_index = 0;
	        }
	        
	    }//end while
	    
	    return foundtimes ;
	    
	}

}


