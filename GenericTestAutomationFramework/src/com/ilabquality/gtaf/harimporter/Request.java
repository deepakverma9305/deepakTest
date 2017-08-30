package com.ilabquality.gtaf.harimporter;
public class Request {
	public String method;
	public String url;
	public PostData postData;
	public PostData getPostData() {
		return postData;
	}

	public void setPostData(PostData postData) {
		this.postData = postData;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
