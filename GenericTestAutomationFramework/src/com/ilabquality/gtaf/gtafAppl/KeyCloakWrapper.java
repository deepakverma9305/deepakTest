package com.ilabquality.gtaf.gtafAppl;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class KeyCloakWrapper {
	private String access_token,
	refresh_token,
	id_token,
	token_type,
	session_state,token_Prefix;
	
	private int expires_in,not_before_policy,refresh_expires_in;
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getSession_state() {
		return session_state;
	}

	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}

	public String getToken_Prefix() {
		return token_Prefix;
	}

	public void setToken_Prefix(String token_Prefix) {
		this.token_Prefix = token_Prefix;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public int getNot_before_policy() {
		return not_before_policy;
	}

	public void setNot_before_policy(int not_before_policy) {
		this.not_before_policy = not_before_policy;
	}

	public int getRefresh_expires_in() {
		return refresh_expires_in;
	}

	public void setRefresh_expires_in(int refresh_expires_in) {
		this.refresh_expires_in = refresh_expires_in;
	}

	public DateTime getTokenInitiatedDateTime() {
		return tokenInitiatedDateTime;
	}

	public void setTokenInitiatedDateTime(DateTime tokenInitiatedDateTime) {
		this.tokenInitiatedDateTime = tokenInitiatedDateTime;
	}

	private DateTime tokenInitiatedDateTime;
	
	public KeyCloakWrapper(){}
	
	@SuppressWarnings("unchecked")
	public void set(JSONObject obj){
		int debug=0;
		tokenInitiatedDateTime = new DateTime(DateTime.now());
		try{
			Iterator<String> it =  obj.keys();
			while(it.hasNext()){
				String key = it.next();
				switch(key){
					case "access_token":{
						this.access_token = (String) obj.get("access_token");
						break;
					}
					case "refresh_token":{
						this.refresh_token = (String) obj.get("refresh_token");
						break;
					}
					case "refresh_expires_in":{
						this.refresh_expires_in = obj.getInt("refresh_expires_in");
						break;
					}
					case "not-before-policy":{
						this.not_before_policy = obj.getInt("not-before-policy");
						break;
					}
					case "id_token":{
						this.id_token = (String) obj.get("id_token");
						break;
					}
					case "token_type":{
						this.token_type = (String) obj.get("token_type");
						break;
					}
					case "session_state":{
						this.session_state = (String) obj.get("session_state");
						break;
					}
					case "expires_in":{
						this.expires_in = obj.getInt("expires_in");
						break;
					}
					default:
						LoggerFactory.getLogger(Runner.class).error("Unsupported KeyCloak value! " + key);
						
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				Runner.cliExitCode = 2;
			}
	}
	
	public String getTokenPrefix(){
		return this.token_Prefix;
	}
	
	public String getServerToken(){
//		if ( tokenInitiatedDateTime.plusMillis(expires_in).isBefore(new DateTime(DateTime.now()))){ // 100 millisecond added for latency/processing
//			System.out.println("Returning Access token of : " + access_token);
//			this.token_Prefix = "Bearer ";
			return this.access_token;
//		}else{
//			System.out.println("Returning refresh token of : " + refresh_token);
//			this.token_Prefix = "Refresh ";
//			return this.refresh_token;
//		}
	}
	
}
