package Winautomate;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Screen;

import Winautomate.function_library;



     public class function_library 
     
     
 {
    	   	
    	
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	static Calendar now = Calendar.getInstance();
	 public static WebDriver driver;
	 public String baseUrl;
	 static Screen s= new Screen();
	//Start File locations
	
	     public static String sikulImages= Winautomate.function_library.ReadFromExcel(2, 8, 1);
		 public static String MainPath =Winautomate.function_library.ReadFromExcel(2, 7, 1);
		 public static String Resultpath = MainPath+"Test Results\\";	
		 public String AppUrl= Winautomate.function_library.ReadFromExcel(2, 2, 1).trim();
		 public static String RAOSourceFile= Winautomate.function_library.ReadFromExcel(2, 3, 1).trim();
		 public static String RAODestLocation= Winautomate.function_library.ReadFromExcel(2, 4, 1).trim();
		 public static String ReconcileFileSourcePath=Winautomate.function_library.ReadFromExcel(2, 5, 1).trim();
		 public static String ReconcileFileDestPath=Winautomate.function_library.ReadFromExcel(2, 6, 1).trim();
		 public static String DBErrorLogPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Database_Report\\"+formatter.format(now.getTime())+".txt";
		 public static String DBErrorLogPath1=Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Database_Report\\"+formatter.format(now.getTime())+".txt";
		 public static String ReconErrorPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"Reconcile\\"+formatter.format(now.getTime())+".txt";
		 public static String FlushErrorPath= Winautomate.function_library.ReadFromExcel(2, 1, 1).trim()+"System_Error\\Flush_error\\"+formatter.format(now.getTime())+".txt";
		 public static String DestinationResult=MainPath+"Test Results\\TestResults\\TestResults.xlsx";
		 // End File Locations
		 	
	public static void Wait(int time)
	
	
	
	{
		try {
		    Thread.sleep(time);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		
		
		
	}
	
    public static void DeleteBenifeciary()

{
	

    try {
    	
    	Class.forName("org.postgresql.Driver");
    	Connection connection = null;
    	
    	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/SIT_supplierfinance","postgres", "postgres");
    	
        Statement stmt = connection.createStatement();
     
        stmt.executeQuery("DELETE FROM public.trading_entity_bank_account WHERE account_name='Real Tree Trading1 1 pty ltd';");
        connection.close();
        
        
    } catch (Exception e) {e.getStackTrace();
        
    }

}
	
	public static void FlushOldResults(String path1, String errorPath)
		
		{
			try{
			  String path= path1; 
		        File file = new File(path);
		        File[] files = file.listFiles(); 
		        
		        for (File f:files) {
		       
		           f.delete();
		        }
			} catch(Exception e){Winautomate.function_library.CreateLog(e, errorPath);}
			
		}
	
	public static void copyFileUsingStream(String source, String dest) throws IOException 
		
		
		
		{
			
			File source1 = new File(source);
			File dest1 = new File(dest);
			
		    InputStream is = null;
		    OutputStream os = null;
		    try {
		        is = new FileInputStream(source1);
		        os = new FileOutputStream(dest1);
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = is.read(buffer)) > 0) {
		            os.write(buffer, 0, length);
		        }
		    } finally {
		        is.close();
		        os.close();
		    }
		}
	
	public static void DeleteDBMain(String ErroarLogPath)
	    
	    {
	        try {
	        	
	        	Class.forName("org.postgresql.Driver");
	        	Connection connection = null;
	        	//connection = DriverManager.getConnection(Str);
	        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/SIT2_supplierfinance","postgres", "postgres");
	        	
	           
	            Statement stmt = connection.createStatement();
	           // ResultSet rs;
	 
	            //rs = stmt.executeQuery("truncate batch_run cascade;");
	            stmt.executeQuery("truncate detail_recon_report,recon_summary_report,trade_loan_batch_link,trade_loan_batch,batch_run,trade_loan_batch_link,trade_loan_batch,payment_transmission,exception_payment_request,exception_task_detail,file_import,payment_request,payment_request_exception_task_detail,payment_request_file_import,primary_transaction,propell_payment,rao_file_import,supplier_payment,trade_loan cascade;");
	            connection.close();
	            
	            
	            
	            
	        } catch (Exception e) {
	            Winautomate.function_library.CreateLog(e,ErroarLogPath );
	        }
	    }
	    
	public static void DeleteDBWF(String ErroarLogPath)
	    
	    {
	        try {
	        	
	        	Class.forName("org.postgresql.Driver");
	        	Connection connection = null;
	        	//connection = DriverManager.getConnection(Str);
	        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/SIT_supplierfinance_workflow","postgres", "postgres");
	        	
	           
	            Statement stmt = connection.createStatement();
	           // ResultSet rs;
	 
	            //rs = stmt.executeQuery("truncate batch_run cascade;");
	            stmt.executeQuery("truncate act_hi_procinst,act_ru_task,act_hi_actinst,act_hi_taskinst,act_ru_job cascade;");
	            connection.close();
	            
	            
	            
	            
	        } catch (Exception e) {
	        	Winautomate.function_library.CreateLog(e,ErroarLogPath );
	        }
	    }
	
	public static void UATDeleteDBMain(String ErroarLogPath)
    
    {
        try {
        	
        	Class.forName("org.postgresql.Driver");
        	Connection connection = null;
        	//connection = DriverManager.getConnection(Str);
        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/supplierfinance","postgres", "postgres");
        	
           
            Statement stmt = connection.createStatement();
           // ResultSet rs;
 
            //rs = stmt.executeQuery("truncate batch_run cascade;");
            stmt.executeQuery("truncate trade_loan_batch_link,trade_loan_batch,batch_run,payment_transmission,exception_payment_request,exception_task_detail,file_import,payment_request,payment_request_exception_task_detail,payment_request_file_import,primary_transaction,propell_payment,rao_file_import,supplier_payment,trade_loan cascade;");
            connection.close();
            
            
            
            
        } catch (Exception e) {
            Winautomate.function_library.CreateLog(e,ErroarLogPath );}
        }
	
	
	public static void UATDeleteDBWF(String ErroarLogPath)
    
    {
        try {
        	
        	Class.forName("org.postgresql.Driver");
        	Connection connection = null;
        	//connection = DriverManager.getConnection(Str);
        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/supplierfinance_workflow","postgres", "postgres");
        	
           
            Statement stmt = connection.createStatement();
           // ResultSet rs;
 
            //rs = stmt.executeQuery("truncate batch_run cascade;");
            stmt.executeQuery("truncate act_hi_procinst,act_ru_task,act_hi_actinst,act_hi_taskinst,act_ru_job cascade;");
            connection.close();
            
            
            
            
        } catch (Exception e) {
        	Winautomate.function_library.CreateLog(e,ErroarLogPath );
        }
    }
	    
	
	
public static void MaintDeleteDBMain(String ErroarLogPath)
    
    {
        try {
        	
        	Class.forName("org.postgresql.Driver");
        	Connection connection = null;
        	//connection = DriverManager.getConnection(Str);
        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/supplierfinanceMaint","postgres", "postgres");
        	
           
            Statement stmt = connection.createStatement();
           // ResultSet rs;
 
            //rs = stmt.executeQuery("truncate batch_run cascade;");
            stmt.executeQuery("truncate detail_recon_report,recon_summary_report,trade_loan_batch_link,trade_loan_batch,batch_run,payment_transmission,exception_payment_request,exception_task_detail,file_import,payment_request,payment_request_exception_task_detail,payment_request_file_import,primary_transaction,propell_payment,rao_file_import,supplier_payment,trade_loan cascade;");
            connection.close();
            
            
            
            
        } catch (Exception e) {
            Winautomate.function_library.CreateLog(e,ErroarLogPath );}
        }
	
	
	public static void MaintDeleteDBWF(String ErroarLogPath)
    
    {
        try {
        	
        	Class.forName("org.postgresql.Driver");
        	Connection connection = null;
        	//connection = DriverManager.getConnection(Str);
        	connection = DriverManager.getConnection("jdbc:postgresql://22.149.62.84:5432/supplierfinance_workflowMaint","postgres", "postgres");
        	
           
            Statement stmt = connection.createStatement();
           // ResultSet rs;
 
            //rs = stmt.executeQuery("truncate batch_run cascade;");
            stmt.executeQuery("truncate act_hi_procinst,act_ru_task,act_hi_actinst,act_hi_taskinst,act_ru_job cascade;");
            connection.close();
            
            
            
            
        } catch (Exception e) {
        	Winautomate.function_library.CreateLog(e,ErroarLogPath );
        }
    }
	
	
	
	
	public static void Reconcile(String Source, String Dest, String ReconErrorPath)
		
		
		{
			
			// Put file in SIT
			try {
			    Thread.sleep(5000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
					try {
						Winautomate.function_library.copyFileUsingStream(Source, Dest);
						
					} catch (IOException e) {
						Winautomate.function_library.CreateLog(e, ReconErrorPath);
					}
			
			
			
			
		}
		
	public static void robo(String str) throws Exception
	    {
	        Calendar now = Calendar.getInstance();
	        Robot robot = new Robot();
	        try {
			    Thread.sleep(2000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
	        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        ImageIO.write(screenShot, "JPG", new File(str+formatter.format(now.getTime())+".jpg"));
	        System.out.println(formatter.format(now.getTime()));
	    }
		
	public static void CreateLog(Exception e, String path)
		
		{
			
			
			try {

				

				File file = new File(path);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(e.getMessage());
				bw.close();

				

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
		}
		
	public static void WriteToExcel(int Sheetno, int rowno, int Colno, String Source, String Destination, String Result) 
	{
		
				
		try{
		       
			FileInputStream file = new FileInputStream(new File(Source));
			//"C:\\Users\\abdv220\\Desktop\\Selinium Framework_Supplier Finance\\Test Results\\TestResults\\TestResults.xlsx"

	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        Sheet sheet = workbook.getSheetAt(Sheetno);
	        Cell cell = null;

	        //Update the value of cell
	       cell = sheet.getRow(rowno).getCell(Colno); // in excel this is cell D1
	       cell.setCellValue(Result); // line 28

	        file.close();

	        FileOutputStream outFile =new FileOutputStream(new File(Destination));
	      //  "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Supplier Finance\\Templates\\TestCases.xlsx"
	        workbook.write(outFile);
	        outFile.close(); 
		     workbook.close();
		 
		
		
		}
		
		catch (Exception e)
		{
			
			Winautomate.function_library.CreateLog(e, "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Supplier Finance\\Test Results\\Write_error.txt");
			
		}
		
		
}

	public static String ReadFromExcel(int sheetno, int rowno, int colno) 
	{
      
		String cell = null;
		
		try{
		       
		String excelFilePath = "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Supplier Finance\\Test_data\\DataSet.xlsx";
		
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(sheetno);
		 Row row= firstSheet.getRow(rowno);
		 cell= row.getCell(colno).toString();
		 
		
		 
		 workbook.close();
		 
		 
		
		
		}
		
		catch (Exception e)
		{
			
			Winautomate.function_library.CreateLog(e, "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Supplier Finance\\Test Results\\Read_error.txt");
			
		}
		
		return cell;
		// TODO Auto-generated method stub
		
	}
	
	public static void PutFilesInServer()
	
	
	{
		function_library.Wait(5000);
		
		try {
			Winautomate.function_library.copyFileUsingStream(RAOSourceFile,RAODestLocation);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void ConfirmTradeLoan()
	
	
	{
		
		try{ 	
    		
	    	  driver = new FirefoxDriver();
	    	 
		      driver.get("http://22.149.62.84:8088/auth/realms/supplierfinance/protocol/openid-connect/auth?client_id=supplierfinance-app&redirect_uri=http%3A%2F%2F22.149.62.84%3A9085%2F%3Fredirect_fragment%3D%252F&state=e2322eb9-1218-4a48-9021-47e5d4383608&nonce=306f8c21-8d06-4f36-9b4b-aaffda2991d2&response_mode=fragment&response_type=code");
		      s.click(sikulImages+"maximize.png");
		      driver.findElement(By.id("username")).clear();
		      driver.findElement(By.id("username")).sendKeys("abdv220");
		      driver.findElement(By.id("password")).clear();
		      driver.findElement(By.id("password")).sendKeys("Deepak@123");	      
		      driver.findElement(By.id("kc-login")).click();
		      Winautomate.function_library.Wait(2000);
		      driver.findElement(By.linkText("View Task Details")).click();  
	
		      Winautomate.function_library.Wait(2000);
		      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[4]/div[2]/button")).click();
		      Winautomate.function_library.robo(Resultpath+"ConfirmTradeLoan_TestScreens\\");
		      driver.findElement(By.cssSelector("div.form-group > button.btn.btn-default")).click();
		      Winautomate.function_library.Wait(2000);

		      new Select(driver.findElement(By.name("confirmedBuyer"))).selectByVisibleText("ArcelorMittal South Africa Limited");
		      driver.findElement(By.name("confirmedLoanAmount")).clear();
		      driver.findElement(By.name("confirmedLoanAmount")).sendKeys("3,621.00");
		      driver.findElement(By.name("confirmedMaturityDate")).clear();
		      driver.findElement(By.name("confirmedMaturityDate")).sendKeys("2016-09-01");		      	   
		      driver.findElement(By.name("tradeLoanRef")).clear();
		      driver.findElement(By.name("tradeLoanRef")).sendKeys("1213");
		      new Select(driver.findElement(By.name("paymentBankAccount"))).selectByVisibleText("ZAR Suspense Account - ABSAZAJJ, 4082761030");
		      Winautomate.function_library.Wait(2000);
		      Winautomate.function_library.Wait(2000);		      
		      driver.findElement(By.xpath("//div[@id='container-fluid']/div[3]/div/form/div[3]/button[3]")).click();
		      	
	}catch(Exception e){e.printStackTrace();}
	
	
	
	
	
	
	
	
	
	


	}

}
	
	
	
	

