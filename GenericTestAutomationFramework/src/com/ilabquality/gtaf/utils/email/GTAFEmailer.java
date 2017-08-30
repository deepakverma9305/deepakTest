package com.ilabquality.gtaf.utils.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Bligna_S
 *
 */
public abstract class GTAFEmailer {
	
	/**
	 * Static list of all current recipients. 
	 */
	public static final String StevenBlignaut = "stebli1978@gmail.com";
	public static final String PangeaAppSandbox = "pangeatfsandbox@gmail.com";
	public static final String PangeaAppDEV = "pangeatfdev@gmail.com";
	

	/**
	 * Container to add all valid recipents/attachments to
	 */
	public List<String> toRecipients = new ArrayList<String>();
	public List<String> attachments = new ArrayList<String>();
	public String _attachment = "";
	public String _attachmentName = "";

	private String _emailSubject;

	//Body Content
	public abstract String getBodyContent();
	
	public GTAFEmailer(String subject){
		this._emailSubject = subject;
	}
	/**
	 * Function to prepare email to be sent
	 */
	   public void send(){
	      String from = "pangeaautomation@gmail.com";
	      String host = "smtp.gmail.com"; //DO NOT CHANGE
	      // Get system properties
	      System.out.println("TLSEmail Start");
          Properties props = new Properties();
          props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
          props.put("mail.smtp.port", "587"); //TLS Port
          props.put("mail.smtp.auth", "true"); //enable authentication
          props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
          Authenticator auth = new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication("pangeaautomation@gmail.com", "ABSA123!");
              }
          };
          Session session = Session.getInstance(props, auth);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         
	        for ( String recipient : toRecipients){
		         message.addRecipient(Message.RecipientType.TO,
		                                  new InternetAddress(recipient));
	        }
        	if ( toRecipients.size() == 0 ){//Include Team Leads and Patrick only.
        		message.addRecipient(Message.RecipientType.TO, new InternetAddress(GTAFEmailer.PangeaAppDEV));
        		message.addRecipient(Message.RecipientType.TO, new InternetAddress(GTAFEmailer.PangeaAppSandbox));
        	}
	         message.setSubject(_emailSubject);
	         Multipart multipart = new MimeMultipart();
	         BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent(getBodyContent(),"text/html");
	         multipart.addBodyPart(messageBodyPart);
	         messageBodyPart = new MimeBodyPart();
	         
	         //Attach files to email
	         if (attachments.size()> 0){
	         for ( String attachment : attachments){
		         DataSource source = new FileDataSource(new File(attachment));
		         messageBodyPart.setDataHandler(new DataHandler(source)); 
		         messageBodyPart.setFileName(attachment);
		         multipart.addBodyPart(messageBodyPart);
	         	}
	         }else if (!_attachment.equalsIgnoreCase("")){
		         DataSource source = new FileDataSource(new File(_attachment));
		         messageBodyPart.setDataHandler(new DataHandler(source)); 
		         messageBodyPart.setFileName(_attachmentName);
		         multipart.addBodyPart(messageBodyPart);
	         }
	         message.setContent( multipart );
	         Transport.send(message);
	         System.out.println("Message Sent");
	         
	      }catch (MessagingException mex) {
	    	  mex.printStackTrace();
	      }
	   }
	
	   public static void main(String[] args) {
		GTAFEmailer gtf = new GTAFEmailer("EMPTY email, Straight to query") {
			
			@Override
			public String getBodyContent() {
				return "hello";
			}
		};
	
		//gtf.attachments.add("D:\\pangea.poc.priv.autotest\\GTAF\\src\\com\\ilabquality\\gtaf\\utils\\email\\Attachment.pdf");
		gtf.send();
		gtf.check();
	}
	   
	   
	   public void check() 
			   {
			      try {

			      //create properties field
			      Properties properties = new Properties();

			      properties.put("mail.pop3.host", "pop.gmail.com");
			      properties.put("mail.pop3.port", "995");
			      properties.put("mail.pop3.starttls.enable", "true");
			      Session emailSession = Session.getDefaultInstance(properties);
			  
			      //create the POP3 store object and connect with the pop server
			      Store store = emailSession.getStore("pop3s");

			      store.connect("pop.gmail.com", "pangeaautomation@gmail.com", "ABSA123!");

			      //create the folder object and open it
			      Folder emailFolder = store.getFolder("INBOX");
			      emailFolder.open(Folder.READ_WRITE);

			      // retrieve the messages from the folder in an array and print it
			      Message[] messages = emailFolder.getMessages();
			      System.out.println("messages.length---" + messages.length);
			      
			      int safetyNet = 0;
			      while ( messages.length == 0 && safetyNet < 31 ){
			    	 
			    	  System.out.println("Waited " + safetyNet + " seconds for Pangea Reference.");
			    	  messages = emailFolder.getMessages();
			    	  Thread.currentThread();
			    	  Thread.sleep(1000);
			    	  safetyNet++;
			    	  emailFolder.close(true);
			    	  emailFolder.open(Folder.READ_WRITE);
			      }
			      	
			      if ( messages.length == 0)
			    	  throw new RuntimeException("Message didnt arrive in the 30 seconds allocated");
			      for (int i = 0, n = messages.length; i < n; i++) {
			         Message message = messages[i];
			         if ( message.getFrom()[0].toString().equalsIgnoreCase("pangeatfsandbox@gmail.com")){
			        	 System.out.println("Message Received");
			        	 System.out.println("Pangea Reference is " + message.getSubject().replace("AUTO Response Message - Reference :",""));
			         }
			         System.out.println("Message Body : " + message.getContent().toString());

			      }

			      //close the store and folder objects
			      emailFolder.close(false);
			      store.close();

			      } catch (NoSuchProviderException e) {
			         e.printStackTrace();
			      } catch (MessagingException e) {
			         e.printStackTrace();
			      } catch (Exception e) {
			         e.printStackTrace();
			      }
			   }

}

