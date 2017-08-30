package com.ilabquality.gtaf.environment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.LoggerFactory;

import com.ilabquality.gtaf.gtafAppl.Runner;



public class EnvironmentFactory {
	private static String envDescriptor = "src/eu/sia/environment/TestEnvironments.xml";
	
	public static TestRepository getTestRepository(){
		@SuppressWarnings("unused")
		int debug = 0;
			try{
	 	        //Document jdomDocument = new SAXBuilder().build(new File(envDescriptor));
//				Element rootNode = jdomDocument.getRootElement();  
//				List<Element> info = rootNode.getChildren();
				//Element repoNode = info.get(0);//Test Repository Information
			}catch(Exception e){
				LoggerFactory.getLogger(Runner.class).error("Exception!", e);
			}
		return null;
	}
	
	public static TestEnvironment loadTestEnvironment(String name){
		TestEnvironment te = null;
//		@SuppressWarnings("unused")
//		int debug = 0;
//		try{
//			Document jdomDocument = new SAXBuilder().build(new File(envDescriptor));
//			Element rootNode = jdomDocument.getRootElement();  
//			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
//			for ( Element e : info ){
//				String envName = e.getChildText("ENVName");
//				if ( envName.equalsIgnoreCase(name)){
//					//We have the right env, load into a new Test Environment Object. 
//					te = new TestEnvironment(envName);
//					te.setDBServerIP(e.getChildText("DBServerIP"));
//					te.setDBSID(e.getChildText("DBSID"));
//					te.setDBUserName(e.getChildText("DBUserName"));
//					te.setDBPassword(e.getChildText("DBPassword"));
////					te.setJMSContextFactory(e.getChildText("JMSContextFactory"));
////					te.setJMSServerIP(e.getChildText("JMSServerIP"));
////					te.setJMSInFactory(e.getChildText("JMSInFactory"));
////					te.setJMSPort(e.getChildText("JMSPort"));
////					te.setJMSScheme(e.getChildText("JMSScheme"));
//					List<Element> queueDetails = e.getChildren("QueueDetails");
//					for ( Element qDet : queueDetails ){
//						List<Element> availableQueues =  qDet.getChildren("Queue");
//						for ( Element q : availableQueues ){
////							QueueDetail qd = new QueueDetail();
////							qd.setName(q.getChildText("Name"));
////							qd.setFriendlyName(q.getChildText("FriendlyName"));
////							qd.setIsUrgent(( q.getChildText("IsUrgent").equalsIgnoreCase("0")?false:true));
////							te.addQueue(qd);
//						}
//					}
//				}
//			}
//		}catch(Exception e){
//			LoggerFactory.getLogger(AbsaDriver.class).error("Exception!", e);
//		}
		return te;
	}
	
	public static ArrayList<String> getAvailableEnvironments(){
		ArrayList<String> te = new ArrayList<String>();
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			Document jdomDocument = new SAXBuilder().build(new File(EnvironmentFactory.class.getResource("TestEnvironments.xml").toURI()));
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
			for ( Element e : info ){
				te.add(e.getChildText("ENVName"));
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		Collections.sort(te);
		return te;
	}


	public static void saveEnvironment(String text) {
		try{
			File f = new File(envDescriptor);
			Document jdomDocument = new SAXBuilder().build(f);
			Element rootNode = jdomDocument.getRootElement();  
			Element newEnv = new Element("TestEnvironment");
			newEnv.addContent(new Element("ENVName").setText(text));// ENV Name
			newEnv.addContent(new Element("DBServerIP").setText(""));
			newEnv.addContent(new Element("DBSID").setText(""));
			newEnv.addContent(new Element("DBUserName").setText(""));
			newEnv.addContent(new Element("DBPassword").setText(""));
			newEnv.addContent(new Element("JMSContextFactory").setText(""));
			newEnv.addContent(new Element("JMSServerIP").setText(""));
			newEnv.addContent(new Element("JMSInFactory").setText(""));
			newEnv.addContent(new Element("JMSPort").setText(""));
			newEnv.addContent(new Element("JMSScheme").setText(""));
			newEnv.addContent(new Element("LocalFolder").setText(""));
			
			Element queueDetailsElement = new Element("QueueDetails");
			newEnv.addContent(queueDetailsElement);
			rootNode.addContent(newEnv);
			write(jdomDocument,f);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}
	public synchronized static void write(Document jdomDocument, File f){
		try{
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(jdomDocument, new FileWriter(f));
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}
	public static void updateQueueDetails(String activeEnvironment,
			String oldName, String newName, String newFriendlyName, boolean newIsUrgent) {
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			File f = new File(envDescriptor);
			Document jdomDocument = new SAXBuilder().build(f);
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
			for ( Element e : info ){
				String envName = e.getChildText("ENVName");
				if ( envName.equalsIgnoreCase(activeEnvironment)){
					//get the Queue Node
					List<Element> queueDetails = e.getChildren("QueueDetails");
					for ( Element qDet : queueDetails ){
						List<Element> queues = qDet.getChildren("Queue");
						for ( Element queue : queues ){
							System.out.println(queue.getChildText("Name"));
							if ( queue.getChildText("Name").equalsIgnoreCase(oldName)){
								//Update
								queue.getChild("Name").setText(newName);
								queue.getChild("FriendlyName").setText(newFriendlyName);
								queue.getChild("IsUrgent").setText((newIsUrgent == true ? "1":"0"));
								break;
							}
						}
					}
				}
			}
			write(jdomDocument, f);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}

	public static void updateEnvsDetails(TestEnvironment te) {
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			File f = new File(envDescriptor);
			Document jdomDocument = new SAXBuilder().build(f);
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
			for ( Element e : info ){
				String envName = e.getChildText("ENVName");
				if ( envName.equalsIgnoreCase(te.getName())){
					e.getChild("DBServerIP").setText(te.getDBServerIP());
					e.getChild("DBSID").setText(te.getDBSSID());
					e.getChild("DBUserName").setText(te.getDBUserName());
					e.getChild("DBPassword").setText(te.getDBPassword());
				}
			}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}

	public static void saveRepository(TestRepository tr) {
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			File f = new File(envDescriptor);
			Document jdomDocument = new SAXBuilder().build(f);
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestRepository");// TestRepository
			for ( Element e : info ){
				e.getChild("ServerIP").setText(tr.get_trServerIP());
				e.getChild("SID").setText(tr.get_trSID());
				e.getChild("UserName").setText(tr.get_trUserName());
				e.getChild("Password").setText(tr.get_trPassword());
				e.getChild("LocalFolder").setText(tr.get_trLocalFolder());
			}
			write(jdomDocument, f);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
	}
	
	public static TestEnvironment refreshRepository(String name){
		TestEnvironment te = null;
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			Document jdomDocument = new SAXBuilder().build(new File(envDescriptor));
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
			for ( Element e : info ){
				String envName = e.getChildText("ENVName");
				if ( envName.equalsIgnoreCase(name)){
					//We have the right env, load into a new Test Environment Object. 
					te = new TestEnvironment(envName);
					te.setDBServerIP(e.getChildText("DBServerIP"));
					te.setDBSID(e.getChildText("DBSID"));
					te.setDBUserName(e.getChildText("DBUserName"));
					te.setDBPassword(e.getChildText("DBPassword"));
//					List<Element> queueDetails = e.getChildren("QueueDetails");
//					for ( Element qDet : queueDetails ){
//						List<Element> availableQueues =  qDet.getChildren("Queue");
//						}
					}
				}
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		return te;
	}
	public static void deleteQueue(String _activeEnvironment,
			String selectedItem) {
		@SuppressWarnings("unused")
		int debug = 0;
		try{
			File f = new File(envDescriptor);
			Document jdomDocument = new SAXBuilder().build(f);
			Element rootNode = jdomDocument.getRootElement();  
			List<Element> info = rootNode.getChildren("TestEnvironment");// TestEnvironment
			for ( Element e : info ){
				String envName = e.getChildText("ENVName");
				if ( envName.equalsIgnoreCase(_activeEnvironment)){
					//We have the right env, load into a new Test Environment Object. 
					List<Element> queueDetails = e.getChildren("QueueDetails");
					for ( Element qDet : queueDetails ){
						List<Element> availableQueues =  qDet.getChildren("Queue");
						for ( Element q : availableQueues ){
							if ( q.getChildText("Name").equalsIgnoreCase(selectedItem)){
								q.detach();
								break;
							}
						}
					}
				}
			}
			write(jdomDocument, f);
		}catch(Exception e){
			LoggerFactory.getLogger(Runner.class).error("Exception!", e);
		}
		
	}
}
