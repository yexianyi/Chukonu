package com.yxy.chokonu.java.str.match;

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


