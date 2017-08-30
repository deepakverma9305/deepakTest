package com.ilabquality.gtaf.result.callService;

import org.joda.time.DateTime;

import com.ilabquality.gtaf.service.serviceTests.RestServiceTest;

public class ServiceResult extends GenericResult{
	private boolean wasAlive = false;
	private String serviceLocation;
	private boolean dataVerified;
	private double testDuration;
	private int svcID;
	private DateTime executionDateTime;
	
	public ServiceResult(RestServiceTest rt, double testDuration, DateTime executionDateTime ){
		super(rt);
		setTestDuration(testDuration);	
		setExecutionDateTime(executionDateTime);
	}
	
	public boolean isWasAlive() {
		return wasAlive;
	}
	public void setWasAlive(boolean wasAlive) {
		this.wasAlive = wasAlive;
	}
	public String getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public boolean isDataVerified() {
		return dataVerified;
	}
	public void setDataVerified(boolean dataVerified) {
		this.dataVerified = dataVerified;
	}
	public double getTestDuration() {
		return testDuration;
	}
	public void setTestDuration(double testDuration) {
		this.testDuration = testDuration;
	}



	public int getSvcID() {
		return svcID;
	}



	public void setSvcID(int svcID) {
		this.svcID = svcID;
	}



	public DateTime getExecutionDateTime() {
		return executionDateTime;
	}



	public void setExecutionDateTime(DateTime executionDateTime) {
		this.executionDateTime = executionDateTime;
	}
}
