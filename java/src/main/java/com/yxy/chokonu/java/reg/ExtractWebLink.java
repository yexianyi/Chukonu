package com.yxy.chokonu.java.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractWebLink{
	
	public static void extract(){
		String reg = "((http|https|ftp)\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&amp;%\\$#\\=~_\\-@]*)*";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher("my link is http://www.sohu.com afadfadf adfasd adfa, https://www.google.com,adfadf, www.google.com");
		StringBuffer buffer = new StringBuffer();
		while(matcher.find()){              
		    System.out.println(matcher.group());
		}  
	}
	
	
}

 

