package com.ilabquality.gtaf.result.callService;

import com.ilabquality.gtaf.service.serviceTests.RestServiceTest;

public class GenericResult {
	// To be used in the header section of a report. Generic across all tests. 

	RestServiceTest associatedTest;
	
	
	public RestServiceTest getAssociatedTest() {
		return associatedTest;
	}


	public GenericResult(RestServiceTest associatedTest) {
		this.associatedTest = associatedTest;
	}


	
	

}
