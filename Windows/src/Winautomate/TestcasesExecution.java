package Winautomate;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import Winautomate.function_library;

import org.sikuli.script.Screen;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestcasesExecution {

//===========================Setting up Pre-Test Conditions and Data==============================================================
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	Calendar now = Calendar.getInstance();
	 
	//Start File locations
	 
	 public String sikulImages= Winautomate.function_library.ReadFromExcel(2, 8, 1);
	 public String MainPath =Winautomate.function_library.ReadFromExcel(2, 7, 1);
	 public String Resultpath = MainPath+"Test Results\\";	
	 public String AppUrl= Winautomate.function_library.ReadFromExcel(2, 2, 1).trim();
	 public String RAOSourceFile= Winautomate.function_library.ReadFromExcel(2, 3, 1).trim();
	 public String RAODestLocation= Winautomate.function_library.ReadFromExcel(2, 4, 1).trim();
	 public String ReconcileFileSourcePath=Winautomate.function_library.ReadFromExcel(2, 5, 1).trim();	
	 public String ReconcileFileDestPath=Winautomate.function_library.ReadFromExcel(2, 6, 1).trim();
	 public String ReconcileFileSourcePathException=Winautomate.function_library.ReadFromExcel(2, 9, 1).trim();
	 public String DBErrorLogPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Database_Report\\"+formatter.format(now.getTime())+".txt";
	 public String DBErrorLogPath1=Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Database_Report\\"+formatter.format(now.getTime())+".txt";
	 public String ReconErrorPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Reconcile\\"+formatter.format(now.getTime())+".txt";
	 public String FlushErrorPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"System_Error\\Flush_error\\"+formatter.format(now.getTime())+".txt";
	 public String sourceResult= MainPath+"Templates\\TestCases.xlsx";
	 public String DestinationResult=MainPath+"Test Results\\TestResults\\TestResults.xlsx";
	 // End File Locations
	  
	  public WebDriver driver;
	  public String baseUrl;
	  Screen s= new Screen();
	 
	  	  
	  @Test
	    public void AAA_ClearOldResults() {
		  
		Winautomate.function_library.FlushOldResults(Resultpath+"TestResults\\", FlushErrorPath);
		Winautomate.function_library.FlushOldResults(Resultpath+"TaskCreation_TestScreens\\", FlushErrorPath);
		Winautomate.function_library.FlushOldResults(Resultpath+"ConfirmSupplierFinance_TestScreens\\", FlushErrorPath);
		Winautomate.function_library.FlushOldResults(Resultpath+"ConfirmTradeLoan_TestScreens\\", FlushErrorPath);
		Winautomate.function_library.FlushOldResults(Resultpath+"Database_Report\\", FlushErrorPath);
		Winautomate.function_library.FlushOldResults(Resultpath+"Reconcile\\", FlushErrorPath);			
		//Write Result
		Winautomate.function_library.WriteToExcel(0, 1, 4,sourceResult , DestinationResult, "");
								
			
		}
	  
	 @Test
		public void AA_DeleteDB() {
			
			
			Winautomate.function_library.DeleteDBMain(DBErrorLogPath);
			Winautomate.function_library.DeleteDBWF(DBErrorLogPath1);
			Winautomate.function_library.WriteToExcel(0, 1, 4,DestinationResult , DestinationResult, "Pass");	
		
			
			
		}
	  
	//  @Test
		public void AA_DeleteDBMaint() {
				
				
				Winautomate.function_library.MaintDeleteDBMain(DBErrorLogPath);
				Winautomate.function_library.MaintDeleteDBWF(DBErrorLogPath1);
				Winautomate.function_library.WriteToExcel(0, 1, 4,DestinationResult , DestinationResult, "Pass");	
			
				
				
			}
	  
	  @Test
		public void AA_UATDeleteDB() {
			
			
			Winautomate.function_library.UATDeleteDBMain(DBErrorLogPath);
			Winautomate.function_library.UATDeleteDBWF(DBErrorLogPath1);
			Winautomate.function_library.WriteToExcel(0, 1, 4,DestinationResult , DestinationResult, "Pass");	
		
			
			
		}
		   
		@Test
		public void AB_PutFileinServer() throws Exception {
			
			
			function_library.Wait(5000);
			
					try {
						Winautomate.function_library.copyFileUsingStream(RAOSourceFile,RAODestLocation);
						Winautomate.function_library.WriteToExcel(0, 2, 4,DestinationResult , DestinationResult, "Pass");
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
			
			
		}

	    @Test
	    public  void AC_Taks() 
	    
	    
	    {
	    	try{
	    		
	      driver = new FirefoxDriver();
	      driver.get(AppUrl);
	      s.click(sikulImages+"maximize.png");
	      driver.findElement(By.id("username")).clear();
	      driver.findElement(By.id("username")).sendKeys("abdv220");
	      driver.findElement(By.id("password")).clear();
	      driver.findElement(By.id("password")).sendKeys("Deepak@123");	      
	      driver.findElement(By.id("kc-login")).click();
	      
	      Winautomate.function_library.robo(Resultpath+"TaskCreation_TestScreens\\");
	      Winautomate.function_library.WriteToExcel(0, 3, 4,DestinationResult , DestinationResult, "Pass");
	      
	      driver.close();
	     
	      }
	           	      
	         	    
	    catch (Exception e) {
            Winautomate.function_library.CreateLog(e, Resultpath+"TaskCreation_TestScreens\\Error_"+formatter.format(now.getTime())+".txt");
            driver.close();
	    }
	    	
	    	 
	    }
        	    
	    @Test
	    public void AD_ConfirmTradeLoan() throws Exception {
	     
	    	try{ 	
	    		
	    	  driver = new FirefoxDriver();
	    	 
		      driver.get("http://10.110.180.202:8088/auth/realms/supplierfinance/protocol/openid-connect/auth?client_id=supplierfinance-app&redirect_uri=http%3A%2F%2F10.110.180.202%3A9085%2F%3Fredirect_fragment%3D%252F&state=e2322eb9-1218-4a48-9021-47e5d4383608&nonce=306f8c21-8d06-4f36-9b4b-aaffda2991d2&response_mode=fragment&response_type=code");
		      s.click(sikulImages+"maximize.png");
		      driver.findElement(By.id("username")).clear();
		      driver.findElement(By.id("username")).sendKeys("abdv220");
		      driver.findElement(By.id("password")).clear();
		      driver.findElement(By.id("password")).sendKeys("Deepak@123");	      
		      driver.findElement(By.id("kc-login")).click();
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      Winautomate.function_library.Wait(2000);
		      driver.findElement(By.linkText("View Task Details")).click();  
		     
		      
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[4]/div[2]/button")).click();
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      driver.findElement(By.cssSelector("div.form-group > button.btn.btn-default")).click();
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      
		      new Select(driver.findElement(By.name("confirmedBuyer"))).selectByVisibleText("ArcelorMittal South Africa Limited");
		      driver.findElement(By.name("confirmedLoanAmount")).clear();
		      driver.findElement(By.name("confirmedLoanAmount")).sendKeys("6,000,000.00");
		      driver.findElement(By.name("confirmedMaturityDate")).clear();
		      driver.findElement(By.name("confirmedMaturityDate")).sendKeys("2016-09-01");		      	   
		      driver.findElement(By.name("tradeLoanRef")).clear();
		      driver.findElement(By.name("tradeLoanRef")).sendKeys("1213");
		      new Select(driver.findElement(By.name("paymentBankAccount"))).selectByVisibleText("ZAR Suspense Account - ABSAZAJJ, 4082761030");
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      Winautomate.function_library.Wait(2000);		      
		      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/button[3]")).click();
		      	          
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      
		     
		      s.click(sikulImages+"summarytab.png");
		      Winautomate.function_library.Wait(5000);
		      
		      s.click(sikulImages+"confirmtradeloan2.png");     
		      Winautomate.function_library.Wait(2000); 
		      
		      if(s.find(sikulImages+"funded.png").isValid() )		       	  
		      {
		    	 
		    	  
		    	  System.out.println("Tradeloan Confirm");
		    	  Winautomate.function_library.WriteToExcel(0, 4, 4,DestinationResult , DestinationResult, "Pass");
		    	  Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		    	  
		      }
		      
		   
		      
		      s.click(sikulImages+"supplierPaymentTab.png");
		      
		      Winautomate.function_library.Wait(2000);
		      s.click(sikulImages+"SupplierFinanceCreated.png");  
		      Winautomate.function_library.Wait(2000);
		      
		     if(s.find(sikulImages+"funded.png").isValid() )
		       	  
		      {
		    	  
		    	  System.out.println("Supplier Payment Created");
		    	  Winautomate.function_library.WriteToExcel(0, 5, 4,DestinationResult , DestinationResult, "Pass");
		    	  Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		    
		    	  
		      }
		       	    
		     

		      s.click(sikulImages+"propellPaymentTab.png");

		       Winautomate.function_library.Wait(2000);
		       s.click(sikulImages+"PropellPaymentCreated.png");  
			      Winautomate.function_library.Wait(2000);

		     if(s.find(sikulImages+"funded.png").isValid() )
		 
		{

		    	
		    	 
		System.out.println("Propell Payment Created");
		Winautomate.function_library.WriteToExcel(0, 6, 4,DestinationResult , DestinationResult, "Pass");
		Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");


		}		      
		         
		   	   	      
	  	      
	  	      driver.close();
	  	      
	  	     
	  	    }
	  	    catch (Exception e) {
	              Winautomate.function_library.CreateLog(e, Resultpath+"ConfirmTradeLoan_TestScreens\\Error_"+formatter.format(now.getTime())+".txt");
	              driver.close();
	  	    }
	    	
	   
	     
	    }
	  
	    @Test
		public void AE_Reconcile() throws Exception {
						
	    Winautomate.function_library.Reconcile(ReconcileFileSourcePath,ReconcileFileDestPath, ReconErrorPath );// put Source and Destination file path
	    Winautomate.function_library.Wait(5000);   
	   
	    
	      driver = new FirefoxDriver();
	      driver.get(AppUrl);
	      s.click(sikulImages+"maximize.png");
	      driver.findElement(By.id("username")).clear();
	      driver.findElement(By.id("username")).sendKeys("abdv220");
	      driver.findElement(By.id("password")).clear();
	      driver.findElement(By.id("password")).sendKeys("Deepak@123");	      
	      driver.findElement(By.id("kc-login")).click();
	      
	      s.click(sikulImages+"summarytab.png");
	      Winautomate.function_library.Wait(2000);      	     
	      		     	     
	      
	      s.click(sikulImages+"supplierPaymentTab.png");
	      
	      Winautomate.function_library.Wait(2000);
	      s.click(sikulImages+"Reconcile_SF.png");
	      Winautomate.function_library.Wait(2000);
	      
	     if(s.find(sikulImages+"funded.png").isValid() )
	       	  
	      {
	    	  
	    	  System.out.println("Supplier Payment REconciled");
	    	  Winautomate.function_library.WriteToExcel(0, 7, 4,DestinationResult , DestinationResult, "Pass");
	    	  Winautomate.function_library.robo(Resultpath+"Reconcile\\");
	    
	    	  
	      }
	       	    
	     
	     driver.close();

   
		}
	    
	    @Test
	    public void AF_ConfirmSupplierfinance() throws Exception {
	      driver = new FirefoxDriver();
	      driver.get(AppUrl);
	      s.click(sikulImages+"maximize.png");
	      driver.findElement(By.id("username")).clear();
	      driver.findElement(By.id("username")).sendKeys("abdv220");
	      driver.findElement(By.id("password")).clear();
	      driver.findElement(By.id("password")).sendKeys("Deepak@123");	      
	      driver.findElement(By.id("kc-login")).click();
	      Winautomate.function_library.robo(Resultpath+"ConfirmSupplierFinance_TestScreens\\");	        
	      driver.findElement(By.linkText("View Task Details")).click();
	      Winautomate.function_library.Wait(2000); 
	      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/div[9]/button[2]")).click();
	      Winautomate.function_library.Wait(2000);
	      driver.findElement(By.xpath("//input[@type='checkbox']")).click();
	      Winautomate.function_library.Wait(2000);
	      new Select(driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/div[4]/div/select"))).selectByVisibleText("Yes");
	      Winautomate.function_library.Wait(2000);
	      driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
	      Winautomate.function_library.Wait(2000);
	      driver.findElement(By.xpath("(//input[@type='checkbox'])[3]")).click();
	      driver.findElement(By.name("paymentRef")).clear();
	      driver.findElement(By.name("paymentRef")).sendKeys("123456");
	      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/div[8]/button[2]")).click();
	      Winautomate.function_library.Wait(2000);
	      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/div[8]/button[2]")).click();
	      Winautomate.function_library.Wait(2000);
	      s.click(sikulImages+"summarytab.png");
	      Winautomate.function_library.Wait(2000);
	      
	      s.click(sikulImages+"confirmtradeloan2.png");     
	      Winautomate.function_library.Wait(2000); 
	      
	      if(s.find(sikulImages+"paid.png").isValid() )		       	  
	      {
	    	 
	    	  
	    	  System.out.println("Tradeloan PAID");
	    	  Winautomate.function_library.WriteToExcel(0, 8, 4,DestinationResult , DestinationResult, "Pass");
	    	  Winautomate.function_library.robo(Resultpath+"ConfirmSupplierFinance_TestScreens\\");
	    	  
	      }
	      
	   
	      
	      s.click(sikulImages+"supplierPaymentTab.png");
	      
	      Winautomate.function_library.Wait(2000);
	      s.click(sikulImages+"RealTree.png");  
	      Winautomate.function_library.Wait(2000);
	      
	     if(s.find(sikulImages+"paid.png").isValid() )
	       	  
	      {
	    	  
	    	  System.out.println("Supplier Payment PAID");
	    	  Winautomate.function_library.WriteToExcel(0, 9, 4,DestinationResult , DestinationResult, "Pass");
	    	  Winautomate.function_library.robo(Resultpath+"ConfirmSupplierFinance_TestScreens\\");
	    
	    	  
	      }
	       	    
	     

	      s.click(sikulImages+"propellPaymentTab.png");

	       Winautomate.function_library.Wait(2000);
	       s.click(sikulImages+"PropellPaymentCreated.png");  
		      Winautomate.function_library.Wait(2000);

	     if(s.find(sikulImages+"paid.png").isValid() )
	 
	{

	    	
	    	 
	System.out.println("Propell Payment PAID");
	Winautomate.function_library.WriteToExcel(0, 10, 4,DestinationResult , DestinationResult, "Pass");
	Winautomate.function_library.robo(Resultpath+"ConfirmSupplierFinance_TestScreens\\");


	}		      
	         
	 
	      driver.close();
	      
	      		     
	    }
	    
	  @Test
	    public void AG_ResolveException() throws Exception
	    
	    
	    
	    {
	    	
	    	Winautomate.function_library.DeleteDBMain(DBErrorLogPath);
	    	Winautomate.function_library.Wait(5000);
	    	Winautomate.function_library.DeleteDBWF(DBErrorLogPath1);
	    	Winautomate.function_library.Wait(5000);
	    	Winautomate.function_library.DeleteBenifeciary();
	    	Winautomate.function_library.Wait(5000);
	    	Winautomate.function_library.PutFilesInServer();
	    	Winautomate.function_library.Wait(5000);
	    	Winautomate.function_library.ConfirmTradeLoan();
	    	Winautomate.function_library.Wait(5000);
	    	Winautomate.function_library.Reconcile(ReconcileFileSourcePathException,ReconcileFileDestPath, ReconErrorPath );
	    	Winautomate.function_library.Wait(5000);
	    	
	    	 s.click(sikulImages+"summarytab.png");
	    	 Winautomate.function_library.Wait(2000);
	    	 s.click(sikulImages+"Tasks.png");
	    	 Winautomate.function_library.Wait(2000);
	    	if(s.find(sikulImages+"BenefException.png").isValid())
	    		
	    	{
	    		
	    		 System.out.println("Benefiecary Exception Exist");
	    		 Winautomate.function_library.WriteToExcel(0, 11, 4,DestinationResult , DestinationResult, "Pass");
		    	 Winautomate.function_library.robo(Resultpath+"Exception\\");
	    		
		    	
	    	}
	    	 
	    	 
	    	
	    	
	    	
	    	
	    }
	
	  }

	
	

