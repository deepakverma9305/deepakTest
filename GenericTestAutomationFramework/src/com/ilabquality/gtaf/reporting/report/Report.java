package com.ilabquality.gtaf.reporting.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;
import com.ilabquality.gtaf.gtafgui.reportsViewer.TestReportFactory;

public abstract class Report {

	public StringBuilder _body = new StringBuilder(); // HTML Body
	public String _htmlFileName;
	public String multiReportName;
	private String chartName;
	private String cName="";
	public abstract void generateAndShow();

	public abstract StringBuilder getMultiReportContent();

	// public abstract void doS();
	public StringBuilder prepareHeaders(String title) {
		StringBuilder htmlHeaders = new StringBuilder();
		htmlHeaders.append("<!DOCTYPE html> \n" + "<HTML> \n" + "	    <HEAD>  \n");
		htmlHeaders.append("		<TITLE> \n");
		htmlHeaders.append(title + "\n");
		htmlHeaders.append("</TITLE>  \n" + "		<style type=\"text/css\"> " + getCSSStyles() + "</style>  \n"
				+ "	      \n" + "	    <script type=\"text/javascript\">" + getExpanderJS1() + getExpanderJS4()
				+ getExpanderJS5() + "	    </script> \n</HEAD> ");
		return htmlHeaders;
	}

	public ArrayList<XYSeries> chartData = new ArrayList<>();

	/**
	 * Populates the styles for the HTML report
	 * 
	 * @return
	 */
	public String getCSSStyles() {
		return ".h2 			{text-align:center;font-size:small;font-family: -webkit-pictograph;} \n"
				+ "h5 			{text-align:center; font-family: HELVETICA; font-size: x-large; font-weight: normal; text-decoration: none; word-wrap: break-word;\n"
				+ "			border-bottom-color: #CCCCCC; border-bottom-style: solid; border-bottom-width: 1pt;} \n"
				+ "h3 			{text-align:center;font-size:small; } \n"
				+ ".header 	{text-align:center;border-style: solid;border-color:black;border-width: 1px;font-family: Arial, Helvetica, sans-serif;font-size: xsmall;word-wrap: break-word; \n"
				+ "			font-weight: lighter;text-decoration: none;} \n"
				+ ".cellData 	{font-size: small;border-left-color: #CCCCCC; border-left-style: solid; \n"
				+ "			border-left-width: 1pt;	border-top-color: #CCCCCC; border-top-style: solid; border-top-width: 1pt; word-wrap: break-word;\n"
				+ "			border-right-style: solid; border-right-width: 1pt;border-right-color: #CCCCCC;} \n"
				+ ".tdStyle1 	{background-color:white; font-family: Arial, Helvetica, sans-serif;text-align: center;font-size: small;font-weight: lighter; word-wrap: break-word;\n"
				+ "			text-decoration: none;border-bottom-color: #000000;} \n"
				+ ".tdStyle2 	{background-color:#EDEDED; font-family: Arial, Helvetica, sans-serif;text-align: center;font-size: small;font-weight: lighter; word-wrap: break-word;\n"
				+ "			text-decoration: none;border-bottom-color: #000000;} \n"
				+ ".tdStyle 	{font-family: Arial, Helvetica, sans-serif;text-align: center;font-size: small;font-weight: lighter; word-wrap: break-word;\n"
				+ "			text-decoration: none;border-bottom-color: #000000;} \n"
				+ ".tdErrorStyle {font-family: Arial, Helvetica, sans-serif;text-align: center;font-size: small; word-wrap: break-word;\n"
				+ "				font-weight: lighter;text-decoration: none;border-bottom-color:red;color:Red;} \n"
				+ "td.h2 		{text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: small; font-weight: bold; text-decoration: none; background-color: #E6E6E6; \n"
				+ "			border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 1px; border-right-style: solid; border-right-width: 1px; border-right-color: black;} \n"
				+ "td.h3 		{text-align:center; font-family: Arial, Helvetica, sans-serif; font-size: small; font-weight: bold; text-decoration: none; background-color: #FFFFFF; \n"
				+ "			border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 1px; border-right-style: solid; border-right-width: 1px; border-right-color: black;} \n"
				+ ".tableNoBorder	{border-bottom: none; border-top: none; border-right: none; font-family: Arial, Helvetica, sans-serif; \n"
				+ "           text-align: center; font-size: small; font-weight: lighter; text-decoration: none; border-left: none;} \n"
				+ ".table		{border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: black; border-right-color: black; border-top-style: solid; \n"
				+ "			border-top-width: 1px; border-top-color: black; border-right-style: solid; border-right-width: 1px; font-family: Arial, Helvetica, sans-serif; \n"
				+ "			text-align: left; font-size: small; font-weight: lighter; text-decoration: none; border-left-style: solid; border-left-width: 1px; \n"
				+ "			border-left-color:black} \n"
				+ ".tableInd  {border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: black; border-right-color: black; border-top-style: none; \n"
				+ "			border-top-width: 0px; border-top-color: black; border-right-style: solid; border-right-width: 1px; font-family: Arial, Helvetica, sans-serif; \n"
				+ "			text-align: left; font-size: small; font-weight: lighter; text-decoration: none; border-left-style: solid; border-left-width: 1px; \n"
				+ "			border-left-color:black} \n"
				+ ".tableNB   {border-bottom-style: none; border-bottom-width: 0px; border-bottom-color: white; border-right-color: white; border-top-style: none; \n"
				+ "			border-top-width: 0px; border-top-color: white; border-right-style: none; border-right-width: 0px; font-family: Arial, Helvetica, sans-serif; \n"
				+ "			text-align: left; font-size: small; font-weight: lighter; text-decoration: none; border-left-style: none; border-left-width: 0px; \n"
				+ "			border-left-color:white} \n"
				+ ".cellData1	{border-right-style: solid; border-right-width: 1px; border-right-color: black;} \n"
				+ ".rowData1	{background-color:#EDEDED; border-right-style: solid; border-right-width: 1px; border-right-color: black;} \n"
				+ ".rowData2	{background-color:white; border-right-style: solid; border-right-width: 1px; border-right-color: black;} \n"
				+ ".rowData	{background-color:#EDEDED; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: black;} \n";
	}

	/**
	 * Gets the _htmlFileName associated with this instance
	 * 
	 * @return the _htmlFileName
	 * @throws FileNotFoundException
	 */
	public String get_htmlFileName() throws FileNotFoundException {
		prepareImages();
		
		if (_htmlFileName == null || _htmlFileName.equalsIgnoreCase("")) {
			LoggerFactory.getLogger(Runner.class).info("Report will be located at :"
					+ java.nio.file.Paths.get(".").toAbsolutePath().normalize().toString());
			if (System.getProperty("BUILD_NUMBER") == null || System.getProperty("BUILD_NUMBER").equalsIgnoreCase("")) {
				_htmlFileName = System.getProperty("java.io.tmpdir").replace("\\", "/") + "ResultsFor"
						+ (new SimpleDateFormat("ddMMYYYYhhmmss").format(new java.util.Date())).replace(':', '_')
						+ ".html";
				cName = System.getProperty("java.io.tmpdir").replace("\\", "/") + getChartName();
			} else {
				_htmlFileName = "ResultsForBuild___" + System.getProperty("BUILD_NUMBER") + ".html";
				cName =  getChartName();
			}
			File file1 = new File(_htmlFileName);
			if (file1.exists()) {
				file1.delete();
				try {
					file1.createNewFile();
				} catch (IOException e) {
					// 
					LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				}
			} else {
				try {
					file1.createNewFile();
				} catch (IOException e) {
					// 
					LoggerFactory.getLogger(Runner.class).error("Exception!", e);
				}
			}
		}
		return _htmlFileName;
	}

	/**
	 * Sets the _htmlFileName associated with this instance
	 * 
	 * @param _htmlFileName
	 *            the _htmlFileName to set
	 */
	public void set_htmlFileName(String _htmlFileName) {
		this._htmlFileName = _htmlFileName;
	}

	/**
	 * Populates the JavaScript functions for the HTML report
	 * 
	 * @return HTML String
	 */
	public String getExpanderJS1() {
		return

		"function expander(a,b)\n" + "  {  \n" + "		 var e=document.getElementById(a);  \n"
				+ "		 var image = document.getElementById(b); \n" + "		 if(!e)return true;   \n"
				+ "		 if(e.style.display==\"none\"){  \n" + "			e.style.display=\"block\"; \n"
				+ "			if (image.src.indexOf(\"down.png\") != -1) {\n" + "				image.src = \"up.png\"; \n"
				+ "			} \n" + "			else if (image.src.indexOf(\"plus.png\") != -1) {\n"
				+ "				image.src = \"minus.png\";\n" + "			}	 \n" + "		 } else {   \n"
				+ "		    e.style.display=\"none\";  \n" + "			if (image.src.indexOf(\"up.png\") != -1) {\n"
				+ "				image.src = \"down.png\";   \n"
				+ "			} else if (image.src.indexOf(\"minus.png\") != -1) {\n"
				+ "				image.src = \"plus.png\";\n" + "			}  \n" + "		 }    \n"
				+ "			return true;  \n" + "	  }  \n";
	}

	public String getMultiReportName() {
		return multiReportName;
	}

	public void setMultiReportName(String multiReportName) {
		this.multiReportName = multiReportName;
	}

	public void prepareImages() throws FileNotFoundException {
		new TestReportFactory().writeImages();
		//Replaced by the above method. 
//		copyFile("plus.png");
//		copyFile("minus.png");
//		copyFile("Pangea.jpg");
//		copyFile("AbsaBarclays.png");
//		copyFile("cross.jpg");
//		copyFile("tick.jpg");
//		copyFile("blank.png");
	}

	@SuppressWarnings("unused")
	private void copyFile(String fileName) throws FileNotFoundException {
		int c = 1;
		InputStream in = Report.class.getResourceAsStream(fileName);
		OutputStream outputStream = null;
		if (System.getProperty("BUILD_NUMBER") == null || System.getProperty("BUILD_NUMBER").equalsIgnoreCase("")) {
			outputStream = new FileOutputStream(
					new File(System.getProperty("java.io.tmpdir").replace("\\", "/") + fileName));
		} else {
			outputStream = new FileOutputStream(new File(fileName));
		}
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(Runner.class).error("Unable to write report images", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * Populates the JavaScript functions for the HTML report
	 * 
	 * @return HTML String
	 */
	private String getExpanderJS4() {
		return "	function collapseAll(){ \n" + "	var evtDiv = document.getElementsByTagName(\"DIV\"); \n"
				+ "	var imgs = document.getElementsByTagName(\"IMG\");  \n"
				+ "	for(var i = 0; i < evtDiv.length; i++){ \n" + "		var a = evtDiv[i];  \n"
				+ "    if ( a.getAttribute(\"id\").indexOf(\"ReportHeader\") == -1 ){"
				+ "		a.style.display = \"none\";" + "    }" + "	}  \n"
				+ "	for(var i = 0; i < imgs.length; i++){ \n" + "		var b = imgs[i];  \n"
				+ "		if(b.getAttribute(\"src\").indexOf(\"minus\") != -1){ \n"
				+ "				b.src = \"plus.png\" \n" + "		}  \n" + "	}	  \n" + "} \n";
	}

	/**
	 * Populates the JavaScript functions for the HTML report
	 * 
	 * @return HTML String
	 */
	private String getExpanderJS5() {
		return "	function expandAll(){ \n" + "	var evtDiv = document.getElementsByTagName(\"DIV\"); \n"
				+ "	var imgs = document.getElementsByTagName(\"IMG\");  \n"
				+ "	for(var i = 0; i < evtDiv.length; i++){ \n" +

				"		var a = evtDiv[i];  \n" + "		a.style.display = \"block\";" + "	}  \n"
				+ "	for(var i = 0; i < imgs.length; i++){ \n" + "		var b = imgs[i];  \n"
				+ "		if(b.getAttribute(\"src\").indexOf(\"plus\") != -1){ \n"
				+ "				b.src = \"minus.png\" \n" + "		}  \n" + "	}	  \n" + "} \n";
	}

	// public void setReportLines(ArrayList<ReportLine> reportLines) {
	// this.reportLines = reportLines;
	// }

	public void createZip() {
		byte[] buffer = new byte[1024];
		try {
			FileOutputStream fos = new FileOutputStream("c:\\temp\\test.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry("ResultsFor29112016073811.html");
			zos.putNextEntry(ze);
			FileInputStream in = new FileInputStream(
					"C:\\Users\\Steve\\AppData\\Local\\Temp\\ResultsFor29112016073811.html");
			int len;
			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			in.close();
			zos.closeEntry();
			zos.close();
			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void addChartData(XYSeries col) {
		this.chartData.add(col);
	}

	public String writeChart(Hashtable<Integer, Double> data) throws IOException {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeries xsc : chartData) {
			dataset.addSeries(xsc);
		}
		JFreeChart ieChart = ChartFactory.createXYLineChart("Suite Timings", "Execution Order (Test)", "Test Duration (Service Call Duration)",
				dataset);
		ieChart.getXYPlot().getRenderer().setBaseSeriesVisible(true);// Create
																		// the
																		// dots
																		// on
																		// the
																		// plotted
																		// graph
																		// for
																		// each
																		// test.
		//ieChart.getCategoryPlot().setRenderer(new MinMaxCategoryRenderer());
		
		ChartUtilities.saveChartAsJPEG(new File(cName), ieChart, 1024, 768);
		return cName;
	}

	public String getChartName() {
		if (chartName == null || chartName.equalsIgnoreCase("")) {
			chartName = UUID.randomUUID().toString().substring(0, 6) + ".jpg";
		}
		return this.chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

}
