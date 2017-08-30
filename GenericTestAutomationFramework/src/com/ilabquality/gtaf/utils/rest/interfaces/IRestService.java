package com.ilabquality.gtaf.utils.rest.interfaces;

public interface IRestService {

	public String callService(String webService, String userName, String password);
	public String callPostService(String webService,String data, String userName, String password);
	public String logon(String url, String userName , String password);
	public void resetEnvironment();
}
