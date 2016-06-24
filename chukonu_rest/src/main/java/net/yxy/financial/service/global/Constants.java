package net.yxy.financial.service.global;

public final class Constants {
	
	
	public static final int CONNENTION_TIMEOUT = 1000*60*5 ;
	public static final int REFRESH_INTERVAL_MILISEC = 3000 ;
	public static final int REFRESH_INTERVAL = REFRESH_INTERVAL_MILISEC/1000 ;
	public static final String DB_NAME = "athena_db" ;
	public static final String DB_PATH= "plocal:./databases/"+Constants.DB_NAME ;
	public static final String DB_USERNAME = "admin" ;
	public static final String DB_PASSWORD = "admin" ;
	public static final int DB_MAX_POOL_SIZE = 50;
	
	//ENTITY CLASS NAME
	public static final String ENTITY_SERVER = "Server" ;

}
