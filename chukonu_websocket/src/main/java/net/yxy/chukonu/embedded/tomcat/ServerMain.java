package net.yxy.chukonu.embedded.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import org.apache.catalina.startup.Tomcat;

public class ServerMain {

	public static void main(String[] args) throws Exception {

		 Tomcat tomcat = new Tomcat();
	        String port = "8080"; // Also change in index.html
	        tomcat.setPort(Integer.parseInt(port));
	        String webappDirLocation = setupWebapp();
	        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
	        tomcat.start();
	        tomcat.getServer().await();

	}
	
	private static String setupWebapp() throws Exception
	   {
	       File thisJar = new File(ServerMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	       String target = "tempwebapp";
	       File targetFolder = new File(target + File.separator + "WEB-INF" + File.separator +"lib");
	       targetFolder.mkdirs();
	       copyFileUsingChannel(thisJar, new File(targetFolder.getPath() + File.separator + "app.jar"));
	       InputStream stream = null;
	       OutputStream resStreamOut = null;
	       try {
	           stream = ServerMain.class.getResourceAsStream("index.html");
	           if(stream == null) {
	               throw new Exception("Cannot get resource \"index.html\" from Jar file.");
	           }

	           int readBytes;
	           byte[] buffer = new byte[4096];

	           resStreamOut = new FileOutputStream(target + File.separator + "index.html");
	           while ((readBytes = stream.read(buffer)) > 0) {
	               resStreamOut.write(buffer, 0, readBytes);
	           }
	       } catch (Exception ex) {
	           throw ex;
	       } finally {


	       }
	       return target;
	   }

	    private static void copyFileUsingChannel(File source, File dest) throws IOException {
	        FileChannel sourceChannel = null;
	        FileChannel destChannel = null;
	        try {
	            sourceChannel = new FileInputStream(source).getChannel();
	            destChannel = new FileOutputStream(dest).getChannel();
	            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	        }finally{
	            sourceChannel.close();
	            destChannel.close();
	        }
	    }

}