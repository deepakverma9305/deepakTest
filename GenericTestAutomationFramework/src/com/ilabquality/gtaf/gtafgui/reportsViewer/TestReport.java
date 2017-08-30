package com.ilabquality.gtaf.gtafgui.reportsViewer;

import java.sql.Date;

public class TestReport implements Runnable {
	private int id;
	private String reportName;
	private String fqFileName;
	private Date reportDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportFQFileName() {
		return fqFileName;
	}
	public void setReportFQFileName(String fName) {
		this.fqFileName = fName;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	@Override
	public void run() {
		save();
	}
	
	private void save(){
		new TestReportFactory().saveTestReport(this);
	}
	
}
