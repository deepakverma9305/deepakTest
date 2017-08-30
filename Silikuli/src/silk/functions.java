package silk;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.text.SimpleDateFormat;


public class functions {
	
static Screen s=new Screen();	
static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
static String path= "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test_data\\Sikuli_Images\\";	
static String ResultPath= "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test Results\\";
static String ResultDestinationFile="C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test Results\\ResultSheet\\TestResults.xlsx";

	
public static void Wait(int time)
	
	
	
	{
		try {
		    Thread.sleep(time);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	
}

public static String ReadFromExcel(int sheetno, int rowno, int colno) 
{
  
	String cell = null;
	
	try{
	       
	String excelFilePath = "C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test_data\\DataSet.xlsx";
	
	FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	Workbook workbook = new XSSFWorkbook(inputStream);
	Sheet firstSheet = workbook.getSheetAt(sheetno);
	 Row row= firstSheet.getRow(rowno);
	 cell= row.getCell(colno).toString();
	 
	
	 
	 workbook.close();
	 
	 
	
	
	}
	
	catch (Exception e)
	{
		
		e.getStackTrace();
		
	}
	
	return cell;
	// TODO Auto-generated method stub
	
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

public static void launchIMX() throws Exception

{
			
	Robot r = new Robot();		
	Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe http:10.6.20.46:8101/pls/imx/session_web_v8?&ip=&lng=eng" );
	silk.functions.Wait(2000);
	s.click(path+"Url.png");
	silk.functions.Wait(2000);
	r.keyPress(KeyEvent.VK_ENTER);
	r.keyRelease(KeyEvent.VK_ENTER);
	silk.functions.Wait(2000);
	s.click(path+"Yes12.png");
	silk.functions.Wait(10000);
	silk.functions.robo(ResultPath+"Screens_Test_1\\");

	
}

public static void loginIMX(String username, String Password) throws Exception


{
	silk.functions.Wait(20000);	
	s.click(path+"login.png");	
	s.type(path+"login.png", username);
	s.type(path+"password.png",Password );
	s.click(path+"loginbutton");					
	silk.functions.Wait(5000);
	silk.functions.robo(ResultPath+"Screens_Test_2\\");
	

}

public static void FFCreateContract(String Client_Name) throws Exception

{
	
	Date myDate = new Date();
	System.out.println(myDate);
	String date= new SimpleDateFormat("dd/MM/yyyy").format(myDate).toString();
		
	
	s.click(path+"maximize.png"); 
	s.click(path+"production.png");
	
	Location Loc = new Location(50,150);
	 
	Loc.click();
	
	silk.functions.Wait(5000);
	s.click(path+"createcontract.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"casecategory.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"contracttype_1.png");
	silk.functions.Wait(5000);
	s.type("CLI");
	s.click(path+"clientnameinput_1.png");
	silk.functions.Wait(5000);
	s.type(Client_Name);
	silk.functions.Wait(5000);
	Location loc1001=new Location(200,320);
  	loc1001.doubleClick();
	silk.functions.Wait(5000);
	s.click(path+"close_1");
	silk.functions.Wait(5000);
	s.click(path+"addclient_1");
	silk.functions.Wait(5000);
	s.click(path+"Reference.png");
	s.doubleClick(path+"cc.png");
	s.type("BRITS JACO");
	
	s.doubleClick(path+"ccname.png");
	silk.functions.Wait(5000);
	s.click(path+"contracttype_3.png");
	s.type("DOMESTIC CONTRACT");
	s.doubleClick(path+"contracttype_2.png");
	silk.functions.Wait(15000);
	
	
	s.click(path+"screenreach_1.PNG");
	silk.functions.Wait(10000);
	
	s.click(path+"template_1.PNG");
	
	Location loc1=new Location(150,170);
	loc1.click();
	silk.functions.Wait(2000);
	s.click(path+"ok_template.PNG");
	silk.functions.Wait(30000);
	
	
	silk.functions.robo(ResultPath+"Screens_Test_5\\");
	
	s.doubleClick(path+"pscontract.png");
	silk.functions.Wait(2000);
	
	s.doubleClick(path+"pscontactselect.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"ssofcontract.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"selectssofcontract.png");
	silk.functions.Wait(5000);
	Location loca50=new Location(245,635);
	loca50.click();
	silk.functions.Wait(2000);
 	loca50.doubleClick();
 	silk.functions.Wait(2000);
	Location loca51=new Location(251,169);
 	loca51.doubleClick();
	
	
	

	Location loc3=new Location(290,380);
	loc3.click();
	s.type(date);
				
	Location loc4=new Location(290,420);
	loc4.click();
	s.type(date);
	
	
	//FinanceTab========================
	
	s.click(path+"financingtab.png");
	silk.functions.Wait(2000);
	s.click(path+"finrate.png");
	s.type("100");
	silk.functions.Wait(2000);
	s.click(path+"finstartdate.png");
	s.type(date);
	
	//ClientSpecTab====================
	
	s.click(path+"clientspectab.png");
	silk.functions.Wait(2000);
	s.click(path+"minamountofinvoice.png");
	silk.functions.robo(ResultPath+"Screens_Test_5\\");
	s.type("10000");
	silk.functions.Wait(2000);
	s.click(path+"maxamountofinvoices.png");
	s.type("1000000");
	silk.functions.Wait(2000);
	Location loc6=new Location(350,550);
    loc6.click();
	s.type("10");
	silk.functions.Wait(2000);
	Location loc5=new Location(350,570);
	loc5.click();
	s.type("999");
	silk.functions.Wait(2000);	
	silk.functions.robo(ResultPath+"Screens_Test_5\\");	
	Location loc7=new Location(345,608);
	loc7.click();
	s.type("180");
	silk.functions.Wait(2000);
    Location loc8=new Location(345,630);
	loc8.click();
	s.type("999");
	
	//Criteria=========================================
	
	
	s.click(path+"criteriatab.png");
	silk.functions.robo(ResultPath+"Screens_Test_5\\");
	silk.functions.Wait(2000);
	Location loc9=new Location(877,460);
	loc9.click();
    s.type(" ");	
    silk.functions.Wait(2000);
    Location loc10=new Location(877,480);
	loc10.click();
	s.type(" ");
	silk.functions.Wait(2000);
	Location loc11=new Location(1090,515);
	loc11.click();
	s.type("999");
	Location loc12=new Location(300,555);
	loc12.click();
	silk.functions.Wait(2000);
	Location loc13=new Location(365,450);
	loc13.click();
    Location loc14=new Location(320,490);
    loc14.click();
	
    
  //Debtors...===============
        s.click(path+"debtortab.png");
        silk.functions.Wait(2000);
		s.click(path+"termbeforeclosing.png");	
  		s.type("99");
    
//Financing...===============
    
    s.click(path+"financing2tab.png");	
    silk.functions.Wait(2000);
	s.click(path+"fincommdate.png");	
	s.type(date);
	
	//Service Cost==================
        
	s.click(path+"servicecosttab.png");	
	silk.functions.Wait(2000);
	s.click(path+"fees3.png");	
	silk.functions.Wait(2000);
	Location loc15=new Location(800,730);
	loc15.click();
	s.type("10000");
	silk.functions.Wait(2000);
	Location loc16=new Location(910,730);
	loc16.click();
	s.type(date);
	
	//Subcontract=================================
    
	s.click(path+"subcontract.png");
	silk.functions.robo(ResultPath+"Screens_Test_5\\");
	silk.functions.Wait(4000);
	Location loc17=new Location(270,440);
	 loc17.click();
	 s.type(Client_Name);
		
	 s.click(path+"listofanex.png");
	 silk.functions.Wait(4000);
	  s.click(path+"yes1.png");
	 silk.functions.Wait(4000);
	 Location loc18=new Location(270,200);
	 loc18.click();
	 silk.functions.Wait(4000);
	  Location loc19=new Location(270,70);
	 loc19.click();
	 
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	 Location loc20=new Location(270,220);
	 loc20.click();
	 silk.functions.Wait(4000);
	 Location loc21=new Location(270,100);
	 loc21.click();
	 silk.functions.robo(ResultPath+"Screens_Test_5\\");
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	Location loc22=new Location(270,240);
	 loc22.click();
	 silk.functions.Wait(4000);
	 Location loc23=new Location(270,140);
	loc23.click();
	 Location loc24=new Location(440,240);
	 loc24.click();
	 Location loc25=new Location(440,220);
	loc25.click();
	
	
	s.click(path+"createrec.png");
	
	 silk.functions.Wait(4000);
	 Location loc26=new Location(270,260);
	 loc26.click();
	 silk.functions.Wait(4000);
	 Location loc27=new Location(270,180);
	 loc27.click();
	 Location loc28=new Location(440,260);
	 loc28.click();
	 Location loc29=new Location(440,240);
	 loc29.click();
	 silk.functions.robo(ResultPath+"Screens_Test_5\\");
	 
	s.click(path+"createrec.png");	
	silk.functions.Wait(4000);
	Location loc30=new Location(270,280);
	loc30.click();
	silk.functions.Wait(4000);
	Location loc31=new Location(270,220);
	loc31.click();
			
							
			
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc32=new Location(335,405);
			loc32.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc33=new Location(335,405);
			loc33.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(4000);
			s.click(path+"close1.png");
	
			silk.functions.Wait(5000);
			Location loc34=new Location(460,710);
			  loc34.click();		
		 	 s.type(date);
		 	 silk.functions.Wait(2000);
		 	 
		 	 
		 	 
			
			   Location loc35=new Location(680,710);
			  	loc35.click();
			  	s.type(Client_Name);
			  	silk.functions.Wait(2000);
						
			   Location loc36=new Location(730,710);
			  	loc36.doubleClick();
			 	 silk.functions.Wait(2000);
				
			    Location loc40=new Location(528,183);
			 	loc40.doubleClick();
			silk.functions.Wait(2000);
			Location loc37=new Location(980,710);
			loc37.doubleClick();
			silk.functions.Wait(2000);
			Location loc38=new Location(528,183);
			loc38.doubleClick();
			silk.functions.Wait(2000);
			s.click(path+"save2.png");	
			silk.functions.Wait(5000);
			
			s.click(path+"generaltab.png");
			silk.functions.robo(ResultPath+"Screens_Test_5\\");
			silk.functions.Wait(5000);
			Location loc43=new Location(880,720);
			loc43.click();
			silk.functions.Wait(5000);
			Location loc41=new Location(830,720);
			loc41.click();
			silk.functions.Wait(5000);
			Location loc42=new Location(680,630);
			loc42.click();
			silk.functions.Wait(15000);
			s.click(path+"save3.png");
			silk.functions.Wait(10000);
			s.click(path+"close3.png");
			silk.functions.Wait(10000);
			s.click(path+"save3.png");
			silk.functions.robo(ResultPath+"Screens_Test_5\\");
			
			//SACA =======================
			silk.functions.Wait(15000);
			s.doubleClick(path+"informationtab.png");
			silk.functions.Wait(5000);
			Location loc45=new Location(280,240);
			loc45.click();
			
			s.type("SACA");
			silk.functions.Wait(5000);
			Location loc44=new Location(430,240);
			loc44.click();
			silk.functions.Wait(5000);
			s.doubleClick(path+"contval.png");
			silk.functions.Wait(5000);
			s.click(path+"save1.png");
			
			
			
	

}

public static void Create_client(String Clinet_Name,String Reg_Number,String CIF_KEY,String CIF_NUMBER) throws Exception



{
	        s.click(path+"maximize.png"); 
	        s.click(path+"production.png");
			silk.functions.Wait(2000);
			Location loc10=new Location(200,470);
		 	loc10.click();
		 	silk.functions.Wait(2000);
		 	Location loc11=new Location(250,470);
			loc11.click();
			silk.functions.Wait(2000);
			Location loc16=new Location(320,630);
			loc16.click();
			silk.functions.Wait(2000);
			Location loc12=new Location(335,560);
			loc12.click();
			silk.functions.Wait(1000);
			Location loc13=new Location(335,560);
			loc13.click();
			silk.functions.Wait(1000);
			Location loc14=new Location(335,560);
			loc14.click();
			silk.functions.Wait(1000);
			Location loc15=new Location(320,580);
			loc15.click();
			silk.functions.Wait(2000);
			s.click(path+"create1.png");
			silk.functions.Wait(5000);
			s.click(path+"create2.png");

			//Form Fill================================
			silk.functions.robo(ResultPath+"Screens_Test_3\\");
			 silk.functions.Wait(1000);
			 Location loc161=new Location(220,260);
		 	loc161.click();
			
		 	for(int i=0;i<=3;i++)
				
		 	{
			 silk.functions.Wait(1000);
			 Location loc17=new Location(550,150);
		 	 loc17.click();}
			
			 silk.functions.Wait(1000);
			 Location loc18=new Location(530,140);
			 loc18.click();
			
			  silk.functions.Wait(1000);
			  Location loc19=new Location(330,280);
			  loc19.click();
			  s.type(Clinet_Name);
					
			  silk.functions.Wait(1000);
			  Location loc20=new Location(280,330);
			  loc20.click();
			  s.type(Reg_Number);
			
			
			 silk.functions.Wait(1000);
			 Location loc201=new Location(280,310);
			 loc201.click();
			 s.type("JOHANNESBURG");
			 silk.functions.robo(ResultPath+"Screens_Test_3\\");
			 silk.functions.Wait(1000);
			 Location loc210=new Location(280,310);
			 loc210.doubleClick();
			 silk.functions.Wait(3000);
			 s.doubleClick(path+"city.png");
			
			 silk.functions.Wait(1000);
			 Location loc211=new Location(480,310);
			 loc211.click();
			 s.type("");
			
			 silk.functions.Wait(1000);
			 Location loc221=new Location(480,360);
		 	loc221.click();
			 silk.functions.Wait(1000);
			 Location loc232=new Location(480,340);
			 loc232.click();
			
			s.doubleClick(path+"sic.png");
			silk.functions.Wait(3000);
			s.doubleClick(path+"siccode.png");
			silk.functions.Wait(3000);
			s.click(path+"addline1.png");
			s.type("Address_1");
			silk.functions.Wait(1000);
			s.click(path+"addline2.png");
			s.type("Address_2");
			silk.functions.Wait(1000);
			s.click(path+"addline3.png");
			s.type("Address_3");
			silk.functions.Wait(1000);
			s.click(path+"addline4.png");
			s.type("Address_4");
			
			silk.functions.Wait(1000);
			Location loc111=new Location(550,500);
			loc111.click();
			
			 for(int j=0;j<=5;j++)
			 {
			 silk.functions.Wait(2000);
			 Location loc110=new Location(680,475);
			 loc110.click();}
			 silk.functions.Wait(2000);
			 Location loc130=new Location(650,475);
			 loc130.click();
			 
			 Location loc170=new Location(410,500);
			 loc170.click();
			 silk.functions.Wait(2000);
			 s.type("JOHANNESBURG");
			 silk.functions.Wait(2000);
			 loc170.doubleClick();
			 silk.functions.Wait(2000);
			 s.doubleClick(path+"city.png");
			 silk.functions.Wait(5000);
			
			 
			 s.click(path+"externalref.png");
			 
			 silk.functions.Wait(2000);
			 
			 Location loc1330=new Location(230,180);
			 loc1330.click();
			 s.type("CIF KEY");
			 silk.functions.Wait(2000);

			 Location loc1001=new Location(310,180);
			 loc1001.click();
			 s.type(CIF_KEY);
			 silk.functions.Wait(2000);
			 
			 Location loc1002=new Location(250,200);
			 loc1002.click();
			 silk.functions.Wait(1000);
			 Location loc1012=new Location(250,200);
			 loc1012.click();
			 s.type("CIF NUMBER");
			 silk.functions.Wait(5000);

			 Location loc1003=new Location(310,200);
			 loc1003.click();
			 s.type(CIF_NUMBER);
			 silk.functions.Wait(2000);
			 s.click(path+"save1.png");
			 silk.functions.Wait(2000);
			 s.click(path+"close1.png");
			
//Phone
			 silk.functions.Wait(5000);
			 s.click(path+"Telephone.png");
			 silk.functions.robo(ResultPath+"Screens_Test_3\\");
			 silk.functions.Wait(2000);
			 Location loc90=new Location(80,300);
			 loc90.click();
			 silk.functions.Wait(2000);
			 silk.functions.Wait(2000);
		 	Location loc91=new Location(120,190);
		 	loc91.click();
		 	silk.functions.Wait(2000);
			 Location loc92=new Location(120,190);
		 	loc92.click();
			 
		 	silk.functions.Wait(2000);
		 	Location loc93=new Location(90,190);
		 	loc93.click();
	        
			 silk.functions.Wait(2000);
			
			Location loc94=new Location(180,300);
			loc94.click();
			s.type("deepak@gmail.com");
			 
//Bank
			
			silk.functions.Wait(5000);
			s.click(path+"bank.png");
			silk.functions.robo(ResultPath+"Screens_Test_3\\");
			silk.functions.Wait(2000);
			Location loc95=new Location(680,630);
		 	loc95.click();
		 	silk.functions.Wait(2000);
		 	Location loc96=new Location(280,340);
			loc96.doubleClick();
			silk.functions.Wait(2000);
			s.doubleClick(path+"absa_bank.png");
			silk.functions.Wait(2000);			
			Location loc97=new Location(680,630);
			loc97.click();
			s.type("2596563626");
			 
//client
			silk.functions.Wait(2000);
			 s.click(path+"clienttab.png");
			 silk.functions.robo(ResultPath+"Screens_Test_3\\");
			 silk.functions.Wait(5000);
			 Location loc98=new Location(680,220);
		 	loc98.click();
		 	silk.functions.Wait(2000);
		 	Location loc99=new Location(680,160);
		 	loc99.click();
		 	silk.functions.Wait(2000);
			
			 Location loc100=new Location(800,220);
		 	loc100.doubleClick();
			 silk.functions.Wait(2000);
			 s.doubleClick(path+"Site_Code.png");
			 silk.functions.Wait(2000);
			
			 Location loc101=new Location(400,320);
		 	loc101.click();
		 	silk.functions.Wait(2000);
		 	Location loc102=new Location(400,280);
		 	loc102.click();
		 	silk.functions.Wait(2000);
			 s.click(path+"save1.png");
			silk.functions.Wait(2000);
			s.click(path+"close1.png");
			silk.functions.Wait(5000);
			s.click(path+"save1.png");
			silk.functions.Wait(2000);
			s.click(path+"close1.png");
			silk.functions.Wait(2000);
			s.click(path+"close1.png");
			silk.functions.Wait(2000);
	        s.click(path+"exit.png");
	        silk.functions.Wait(2000);
	        s.click(path+"Yes121.png"); 

}

public static void Create_Debtor(String Debtor_Name,String Reg_Number,String CIF_KEY,String CIF_NUMBER) throws Exception

{
	
	    s.click(path+"maximize.png"); 
        s.click(path+"production.png");
		silk.functions.Wait(2000);
		Location loc10=new Location(200,470);
	 	loc10.click();
	 	silk.functions.Wait(2000);
	 	Location loc11=new Location(250,470);
		loc11.click();
		silk.functions.Wait(2000);
		Location loc16=new Location(320,630);
		loc16.click();				
		silk.functions.Wait(2000);
		s.click(path+"create1.png");
		silk.functions.Wait(5000);
		s.click(path+"create2.png");

		//Form Fill================================

		 silk.functions.Wait(1000);
		 Location loc161=new Location(220,260);
	 	loc161.click();
	 	silk.functions.robo(ResultPath+"Screens_Test_4\\");
	 	for(int i=0;i<=3;i++)
			
	 	{
		 silk.functions.Wait(1000);
		 Location loc17=new Location(550,150);
	 	 loc17.click();}
		
		 silk.functions.Wait(1000);
		 Location loc18=new Location(530,140);
		 loc18.click();
		
		  silk.functions.Wait(1000);
		  Location loc19=new Location(330,280);
		  loc19.click();
		  s.type(Debtor_Name);
				
		  silk.functions.Wait(1000);
		  Location loc20=new Location(280,330);
		  loc20.click();
		  s.type(Reg_Number);
		
		  silk.functions.robo(ResultPath+"Screens_Test_4\\");
		 silk.functions.Wait(1000);
		 Location loc201=new Location(280,310);
		 loc201.click();
		 s.type("JOHANNESBURG");
		
		 silk.functions.Wait(1000);
		 Location loc210=new Location(280,310);
		 loc210.doubleClick();
		 silk.functions.Wait(3000);
		 s.doubleClick(path+"city.png");
		
		 silk.functions.Wait(1000);
		 Location loc211=new Location(480,310);
		 loc211.click();
		 s.type("");
		
		 silk.functions.Wait(1000);
		 Location loc221=new Location(480,360);
	 	loc221.click();
		 silk.functions.Wait(1000);
		 Location loc232=new Location(480,340);
		 loc232.click();
		
		s.doubleClick(path+"sic.png");
		silk.functions.Wait(3000);
		s.doubleClick(path+"siccode.png");
		silk.functions.Wait(3000);
		s.click(path+"addline1.png");
		s.type("Address_1");
		silk.functions.Wait(1000);
		s.click(path+"addline2.png");
		s.type("Address_2");
		silk.functions.Wait(1000);
		s.click(path+"addline3.png");
		s.type("Address_3");
		silk.functions.Wait(1000);
		s.click(path+"addline4.png");
		s.type("Address_4");
		
		silk.functions.Wait(1000);
		Location loc111=new Location(550,500);
		loc111.click();
		
		 for(int j=0;j<=5;j++)
		 {
		 silk.functions.Wait(2000);
		 Location loc110=new Location(680,475);
		 loc110.click();}
		 silk.functions.Wait(2000);
		 Location loc130=new Location(650,475);
		 loc130.click();
		 
		 Location loc170=new Location(410,500);
		 loc170.click();
		 silk.functions.Wait(2000);
		 s.type("JOHANNESBURG");
		 silk.functions.Wait(2000);
		 loc170.doubleClick();
		 silk.functions.Wait(2000);
		 s.doubleClick(path+"city.png");
		 silk.functions.Wait(5000);
		
		 
		 s.click(path+"externalref.png");
		 
		 silk.functions.Wait(2000);
		 
		 Location loc1330=new Location(230,180);
		 loc1330.click();
		 s.type("CIF KEY");
		 silk.functions.Wait(2000);

		 Location loc1001=new Location(310,180);
		 loc1001.click();
		 s.type(CIF_KEY);
		 silk.functions.Wait(2000);
		 
		 Location loc1002=new Location(250,200);
		 loc1002.click();
		 silk.functions.Wait(1000);
		 Location loc1012=new Location(250,200);
		 loc1012.click();
		 s.type("CIF NUMBER");
		 silk.functions.Wait(5000);

		 Location loc1003=new Location(310,200);
		 loc1003.click();
		 s.type(CIF_NUMBER);
		 silk.functions.Wait(2000);
		 s.click(path+"save1.png");
		 silk.functions.Wait(2000);
		 s.click(path+"close1.png");
		
//Phone
		 silk.functions.Wait(5000);
		 s.click(path+"Telephone.png");
		 silk.functions.robo(ResultPath+"Screens_Test_4\\");
		 silk.functions.Wait(2000);
		 Location loc90=new Location(80,300);
		 loc90.click();
		 silk.functions.Wait(2000);
		 silk.functions.Wait(2000);
	 	Location loc91=new Location(120,190);
	 	loc91.click();
	 	silk.functions.Wait(2000);
		 Location loc92=new Location(120,190);
	 	loc92.click();
		 
	 	silk.functions.Wait(2000);
	 	Location loc93=new Location(90,190);
	 	loc93.click();
      
		 silk.functions.Wait(2000);
		
		Location loc94=new Location(180,300);
		loc94.click();
		s.type("deepak@gmail.com");
		 
//Bank
		
		silk.functions.Wait(5000);
		s.click(path+"bank.png");
		silk.functions.robo(ResultPath+"Screens_Test_4\\");
		silk.functions.Wait(2000);
		Location loc95=new Location(680,630);
	 	loc95.click();
	 	silk.functions.Wait(2000);
	 	Location loc96=new Location(280,340);
		loc96.doubleClick();
		silk.functions.Wait(2000);
		s.doubleClick(path+"absa_bank.png");
		silk.functions.Wait(2000);			
		Location loc97=new Location(680,630);
		loc97.click();
		s.type("2596563626");
		silk.functions.Wait(2000);
		 s.click(path+"save1.png");
		silk.functions.Wait(2000);
		s.click(path+"close1.png");
		silk.functions.robo(ResultPath+"Screens_Test_4\\");
		silk.functions.Wait(2000);
		s.click(path+"close1.png");
		silk.functions.Wait(2000);
        s.click(path+"exit.png");
        silk.functions.Wait(2000);
        s.click(path+"Yes121.png"); 




}

public static void BulkCreateContract(String Client_Name) throws Exception


{
	
	Date myDate = new Date();
	System.out.println(myDate);
	String date= new SimpleDateFormat("dd/MM/yyyy").format(myDate).toString();
		
	
	s.click(path+"maximize.png"); 
	s.click(path+"production.png");
	
	Location Loc = new Location(50,150);
	 
	Loc.click();
	
	silk.functions.Wait(5000);
	s.click(path+"createcontract.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"casecategory.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"contracttype_1.png");
	silk.functions.Wait(5000);
	s.type("CLI");
	s.click(path+"clientnameinput_1.png");
	silk.functions.Wait(5000);
	s.type(Client_Name);
	silk.functions.Wait(5000);
	Location loc1001=new Location(200,320);
  	loc1001.doubleClick();
	silk.functions.Wait(5000);
	
	s.click(path+"close_1");
	silk.functions.Wait(5000);
	s.click(path+"addclient_1");
	silk.functions.Wait(5000);
	s.click(path+"Reference.png");
	s.doubleClick(path+"cc.png");
	s.type("BRITS JACO");
	
	s.doubleClick(path+"ccname.png");
	silk.functions.Wait(5000);
	s.click(path+"contracttype_3.png");
	s.type("DOMESTIC CONTRACT");
	s.doubleClick(path+"contracttype_2.png");
	silk.functions.Wait(15000);
	
	
	s.click(path+"screenreach_1.PNG");
	silk.functions.Wait(10000);
	
	s.click(path+"template_1.PNG");
	
	Location loc1=new Location(150,170);
	loc1.click();
	silk.functions.Wait(2000);
	s.click(path+"ok_template.PNG");
	silk.functions.Wait(30000);
	
	Location locbulk=new Location(1080,440);
	locbulk.click();
	silk.functions.Wait(2000);
	s.doubleClick(path+"pscontract.png");
	silk.functions.Wait(2000);
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	s.doubleClick(path+"pscontactselect.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"ssofcontract.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"selectssofcontract.png");
	silk.functions.Wait(5000);
	Location loca50=new Location(245,635);
	loca50.click();
	silk.functions.Wait(2000);
 	loca50.doubleClick();
 	silk.functions.Wait(2000);
	Location loca51=new Location(251,169);
 	loca51.doubleClick();
	
	
 	silk.functions.robo(ResultPath+"Screens_Test_6\\");

	Location loc3=new Location(290,380);
	loc3.click();
	s.type(date);
				
	Location loc4=new Location(290,420);
	loc4.click();
	s.type(date);
	
	
	//FinanceTab========================
	
	s.click(path+"financingtab.png");
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	silk.functions.Wait(2000);
	s.click(path+"finrate.png");
	s.type("100");
	silk.functions.Wait(2000);
	s.click(path+"finstartdate.png");
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	
	//ClientSpecTab====================
	
	s.click(path+"clientspectab.png");
	silk.functions.Wait(2000);
	s.click(path+"minamountofinvoice.png");
	  
	s.type("10000");
	silk.functions.Wait(2000);
	s.click(path+"maxamountofinvoices.png");
	s.type("1000000");
	silk.functions.Wait(2000);
	Location loc6=new Location(350,550);
    loc6.click();
	s.type("10");
	silk.functions.Wait(2000);
	Location loc5=new Location(350,570);
	loc5.click();
	s.type("999");
	silk.functions.Wait(2000);	
		
	Location loc7=new Location(345,608);
	loc7.click();
	s.type("180");
	silk.functions.Wait(2000);
    Location loc8=new Location(345,630);
	loc8.click();
	s.type("999");
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	
	//Criteria=========================================
	
   
	s.click(path+"criteriatab.png");
	silk.functions.Wait(2000);
	Location loc9=new Location(877,460);
	loc9.click();
    s.type(" ");	
    silk.functions.Wait(2000);
    Location loc10=new Location(877,480);
	loc10.click();
	s.type(" ");
	silk.functions.Wait(2000);
	Location loc11=new Location(1090,515);
	loc11.click();
	s.type("999");
	Location loc12=new Location(300,555);
	loc12.click();
	silk.functions.Wait(2000);
	Location loc13=new Location(365,450);
	loc13.click();
    Location loc14=new Location(320,490);
    loc14.click();
	
    silk.functions.robo(ResultPath+"Screens_Test_6\\");
  //Debtors...===============
        s.click(path+"debtortab.png");
        silk.functions.Wait(2000);
		s.click(path+"termbeforeclosing.png");	
  		s.type("99");
  		silk.functions.robo(ResultPath+"Screens_Test_6\\");
//Financing...===============
    
    s.click(path+"financing2tab.png");	
    silk.functions.Wait(2000);
	s.click(path+"fincommdate.png");	
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	//Service Cost==================
        
	s.click(path+"servicecosttab.png");	
	silk.functions.Wait(2000);
	s.click(path+"fees3.png");	
	silk.functions.Wait(2000);
	Location loc15=new Location(800,730);
	loc15.click();
	s.type("10000");
	silk.functions.Wait(2000);
	Location loc16=new Location(910,730);
	loc16.click();
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	//Subcontract=================================
    
	s.click(path+"subcontract.png");
	silk.functions.Wait(4000);
	Location loc17=new Location(270,440);
	 loc17.click();
	 s.type(Client_Name);
		
	 s.click(path+"listofanex.png");
	 silk.functions.Wait(4000);
	  s.click(path+"yes1.png");
	 silk.functions.Wait(4000);
	 Location loc18=new Location(270,200);
	 loc18.click();
	 silk.functions.Wait(4000);
	  Location loc19=new Location(270,70);
	 loc19.click();
	 
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	 Location loc20=new Location(270,220);
	 loc20.click();
	 silk.functions.Wait(4000);
	 Location loc21=new Location(270,100);
	 loc21.click();
	
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	Location loc22=new Location(270,240);
	 loc22.click();
	 silk.functions.Wait(4000);
	 Location loc23=new Location(270,140);
	loc23.click();
	 Location loc24=new Location(440,240);
	 loc24.click();
	 Location loc25=new Location(440,220);
	loc25.click();
	silk.functions.robo(ResultPath+"Screens_Test_6\\");
	
	s.click(path+"createrec.png");
	
	 silk.functions.Wait(4000);
	 Location loc26=new Location(270,260);
	 loc26.click();
	 silk.functions.Wait(4000);
	 Location loc27=new Location(270,180);
	 loc27.click();
	 Location loc28=new Location(440,260);
	 loc28.click();
	 Location loc29=new Location(440,240);
	 loc29.click();
	
	 silk.functions.robo(ResultPath+"Screens_Test_6\\");
	s.click(path+"createrec.png");	
	silk.functions.Wait(4000);
	Location loc30=new Location(270,280);
	loc30.click();
	silk.functions.Wait(4000);
	Location loc31=new Location(270,220);
	loc31.click();
			
							
			
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc32=new Location(335,405);
			loc32.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc33=new Location(335,405);
			loc33.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(4000);
			s.click(path+"close1.png");
	
			silk.functions.Wait(5000);
			Location loc34=new Location(460,710);
			  loc34.click();		
		 	 s.type(date);
		 	 silk.functions.Wait(2000);
		 	 
		 	 
		 	silk.functions.robo(ResultPath+"Screens_Test_6\\"); 
			
			   Location loc35=new Location(680,710);
			  	loc35.click();
			  	s.type(Client_Name);
			  	silk.functions.Wait(2000);
						
			   Location loc36=new Location(730,710);
			  	loc36.doubleClick();
			 	 silk.functions.Wait(2000);
				
			    Location loc40=new Location(528,183);
			 	loc40.doubleClick();
			silk.functions.Wait(2000);
			Location loc37=new Location(980,710);
			loc37.doubleClick();
			silk.functions.Wait(2000);
			Location loc38=new Location(528,183);
			loc38.doubleClick();
			silk.functions.Wait(2000);
			s.click(path+"save2.png");	
			silk.functions.Wait(5000);
			
			s.click(path+"generaltab.png");
			silk.functions.robo(ResultPath+"Screens_Test_6\\");
			silk.functions.Wait(5000);
			Location loc43=new Location(880,720);
			loc43.click();
			silk.functions.Wait(5000);
			Location loc41=new Location(830,720);
			loc41.click();
			silk.functions.Wait(5000);
			Location loc42=new Location(680,630);
			loc42.click();
			silk.functions.Wait(15000);
			s.click(path+"save3.png");
			silk.functions.Wait(10000);
			s.click(path+"close3.png");
			silk.functions.Wait(10000);
			s.click(path+"save3.png");
			silk.functions.robo(ResultPath+"Screens_Test_6\\");
			
			//SACA =======================
			silk.functions.Wait(15000);
			s.doubleClick(path+"informationtab.png");
			silk.functions.Wait(5000);
			Location loc45=new Location(280,240);
			loc45.click();
			silk.functions.robo(ResultPath+"Screens_Test_6\\");
			s.type("SACA");
			silk.functions.Wait(5000);
			Location loc44=new Location(430,240);
			loc44.click();
			silk.functions.Wait(5000);
			s.doubleClick(path+"contval.png");
			silk.functions.Wait(5000);
			s.click(path+"save1.png");
			silk.functions.robo(ResultPath+"Screens_Test_6\\");


}

public static void CreateABSContract(String Client_Name) throws Exception
		
		
	{
	
	
	Date myDate = new Date();
	System.out.println(myDate);
	String date= new SimpleDateFormat("dd/MM/yyyy").format(myDate).toString();
		
	
	s.click(path+"maximize.png"); 
	s.click(path+"production.png");
	
	Location Loc = new Location(50,150);
	 
	Loc.click();
	
	silk.functions.Wait(5000);
	s.click(path+"createcontract.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"casecategory.png");
	silk.functions.Wait(5000);
	s.doubleClick(path+"contracttype_1.png");
	silk.functions.Wait(5000);
	s.type("CLI");
	s.click(path+"clientnameinput_1.png");
	silk.functions.Wait(5000);
	s.type(Client_Name);
	silk.functions.Wait(5000);
	Location loc10=new Location(200,320);
  	loc10.doubleClick();
	silk.functions.Wait(5000);
	
	s.click(path+"close_1");
	silk.functions.Wait(5000);
	s.click(path+"addclient_1");
	silk.functions.Wait(5000);
	s.click(path+"Reference.png");
	s.doubleClick(path+"cc.png");
	s.type("BRITS JACO");
	
	s.doubleClick(path+"ccname.png");
	silk.functions.Wait(5000);
	s.click(path+"contracttype_3.png");
	s.type("DOMESTIC CONTRACT");
	s.doubleClick(path+"contracttype_2.png");
	silk.functions.Wait(15000);
	
	
	s.click(path+"screenreach_1.PNG");
	silk.functions.Wait(10000);
	
	s.click(path+"template_1.PNG");
	
	Location loc1=new Location(150,170);
	loc1.click();
	silk.functions.Wait(2000);
	s.click(path+"ok_template.PNG");
	silk.functions.Wait(30000);
	
	Location locABS=new Location(890,440);
	locABS.click();
	silk.functions.Wait(2000);
	s.doubleClick(path+"pscontract.png");
	silk.functions.Wait(2000);
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	s.doubleClick(path+"pscontactselect.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"ssofcontract.png");
	silk.functions.Wait(2000);
	s.doubleClick(path+"selectssofcontract.png");
	silk.functions.Wait(5000);
	Location loca50=new Location(245,635);
	loca50.click();
	silk.functions.Wait(2000);
 	loca50.doubleClick();
 	silk.functions.Wait(2000);
	Location loca51=new Location(251,169);
 	loca51.doubleClick();
	
 	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	

	Location loc3=new Location(290,380);
	loc3.click();
	s.type(date);
				
	Location loc4=new Location(290,420);
	loc4.click();
	s.type(date);
	
	
	//FinanceTab========================
	
	s.click(path+"financingtab.png");
	silk.functions.Wait(2000);
	s.click(path+"finrate.png");
	s.type("100");
	silk.functions.Wait(2000);
	s.click(path+"finstartdate.png");
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	//ClientSpecTab====================
	
	s.click(path+"clientspectab.png");
	silk.functions.Wait(2000);
	s.click(path+"minamountofinvoice.png");
	  
	s.type("10000");
	silk.functions.Wait(2000);
	s.click(path+"maxamountofinvoices.png");
	s.type("1000000");
	silk.functions.Wait(2000);
	Location loc6=new Location(350,550);
    loc6.click();
	s.type("10");
	silk.functions.Wait(2000);
	Location loc5=new Location(350,570);
	loc5.click();
	s.type("999");
	silk.functions.Wait(2000);	
		
	Location loc7=new Location(345,608);
	loc7.click();
	s.type("180");
	silk.functions.Wait(2000);
    Location loc8=new Location(345,630);
	loc8.click();
	s.type("999");
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	//Criteria=========================================
	
   
	s.click(path+"criteriatab.png");
	silk.functions.Wait(2000);
	Location loc9=new Location(877,460);
	loc9.click();
    s.type(" ");	
    silk.functions.Wait(2000);
    Location loc1001=new Location(877,480);
	loc1001.click();
	s.type(" ");
	silk.functions.Wait(2000);
	Location loc11=new Location(1090,515);
	loc11.click();
	s.type("999");
	Location loc12=new Location(300,555);
	loc12.click();
	silk.functions.Wait(2000);
	Location loc13=new Location(365,450);
	loc13.click();
    Location loc14=new Location(320,490);
    loc14.click();
	
    silk.functions.robo(ResultPath+"Screens_Test_7\\");
  //Debtors...===============
        s.click(path+"debtortab.png");
        silk.functions.Wait(2000);
		s.click(path+"termbeforeclosing.png");	
  		s.type("99");
  		silk.functions.robo(ResultPath+"Screens_Test_7\\");
//Financing...===============
    
    s.click(path+"financing2tab.png");	
    silk.functions.Wait(2000);
	s.click(path+"fincommdate.png");	
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	//Service Cost==================
        
	s.click(path+"servicecosttab.png");	
	silk.functions.Wait(2000);
	s.click(path+"fees3.png");	
	silk.functions.Wait(2000);
	Location loc15=new Location(800,730);
	loc15.click();
	s.type("10000");
	silk.functions.Wait(2000);
	Location loc16=new Location(910,730);
	loc16.click();
	s.type(date);
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	//Subcontract=================================
    
	s.click(path+"subcontract.png");
	silk.functions.Wait(4000);
	Location loc17=new Location(270,440);
	 loc17.click();
	 s.type(Client_Name);
		
	 s.click(path+"listofanex.png");
	 silk.functions.Wait(4000);
	  s.click(path+"yes1.png");
	 silk.functions.Wait(4000);
	 Location loc18=new Location(270,200);
	 loc18.click();
	 silk.functions.Wait(4000);
	  Location loc19=new Location(270,70);
	 loc19.click();
	 
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	 Location loc20=new Location(270,220);
	 loc20.click();
	 silk.functions.Wait(4000);
	 Location loc21=new Location(270,100);
	 loc21.click();
	
	 s.click(path+"createrec.png");
	silk.functions.Wait(4000);
	Location loc22=new Location(270,240);
	 loc22.click();
	 silk.functions.Wait(4000);
	 Location loc23=new Location(270,140);
	loc23.click();
	 Location loc24=new Location(440,240);
	 loc24.click();
	 Location loc25=new Location(440,220);
	loc25.click();
	silk.functions.robo(ResultPath+"Screens_Test_7\\");
	
	s.click(path+"createrec.png");
	
	 silk.functions.Wait(4000);
	 Location loc26=new Location(270,260);
	 loc26.click();
	 silk.functions.Wait(4000);
	 Location loc27=new Location(270,180);
	 loc27.click();
	 Location loc28=new Location(440,260);
	 loc28.click();
	 Location loc29=new Location(440,240);
	 loc29.click();
	 silk.functions.robo(ResultPath+"Screens_Test_7\\");
	 
	s.click(path+"createrec.png");	
	silk.functions.Wait(4000);
	Location loc30=new Location(270,280);
	loc30.click();
	silk.functions.Wait(4000);
	Location loc31=new Location(270,220);
	loc31.click();
			
							
			
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc32=new Location(335,405);
			loc32.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(6000);
			s.click(path+"ok3.png");
			silk.functions.Wait(5000);
			Location loc33=new Location(335,405);
			loc33.click();
			silk.functions.Wait(6000);
			s.click(path+"save1.png");
			silk.functions.Wait(4000);
			s.click(path+"close1.png");
	
			silk.functions.Wait(5000);
			Location loc34=new Location(460,710);
			 loc34.click();		
		 	 s.type(date);
		 	 silk.functions.Wait(2000);
		 	 
			 Location loc35=new Location(680,710);
			 loc35.click();
			 s.type(Client_Name);
			 silk.functions.Wait(2000);
						
			 Location loc36=new Location(730,710);
			 loc36.doubleClick();
			 silk.functions.Wait(2000);
				
			 Location loc40=new Location(528,183);
			 loc40.doubleClick();
			silk.functions.Wait(2000);
			Location loc37=new Location(980,710);
			loc37.doubleClick();
			silk.functions.Wait(2000);
			Location loc38=new Location(528,183);
			loc38.doubleClick();
			silk.functions.Wait(2000);
			s.click(path+"save2.png");	
			silk.functions.Wait(5000);
			
			s.click(path+"generaltab.png");
			
			silk.functions.Wait(5000);
			Location loc43=new Location(880,720);
			loc43.click();
			silk.functions.Wait(5000);
			Location loc41=new Location(830,720);
			loc41.click();
			silk.functions.Wait(5000);
			Location loc42=new Location(680,630);
			loc42.click();
			silk.functions.Wait(15000);
			s.click(path+"save3.png");
			silk.functions.Wait(10000);
			s.click(path+"close3.png");
			silk.functions.Wait(10000);
			s.click(path+"save3.png");
			silk.functions.robo(ResultPath+"Screens_Test_7\\");
			
			//SACA =======================
			silk.functions.Wait(15000);
			s.doubleClick(path+"informationtab.png");
			silk.functions.Wait(5000);
			Location loc45=new Location(280,240);
			loc45.click();
			silk.functions.robo(ResultPath+"Screens_Test_7\\");
			s.type("SACA");
			silk.functions.Wait(5000);
			Location loc44=new Location(430,240);
			loc44.click();
			silk.functions.Wait(5000);
			s.doubleClick(path+"contval.png");
			silk.functions.Wait(5000);
			s.click(path+"save1.png");
			
	
	
	
	
	
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
		
		e.getStackTrace();
		
	}
	
	
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

public static void FlushOldResults(String path1)

{
	try{
	  String path= path1; 
        File file = new File(path);
        File[] files = file.listFiles(); 
        
        for (File f:files) {
       
           f.delete();
        }
	} catch(Exception e){e.getStackTrace();}
	
}

public static void CreateMFCLimit(String ClientCode, String Limit)


{
	

    Date myDate = new Date();
	
	String date= new SimpleDateFormat("dd/MM/yyyy").format(myDate).toString();
	System.out.println(date);
		
	try{
		s.click(path+"maximize.png"); 
		 silk.functions.Wait(2000);
	    s.click(path+"production.png");
	    silk.functions.Wait(2000);
	    Location loc13=new Location(200,640);
	    loc13.click();
	    silk.functions.Wait(5000);		
	    Location loc10=new Location(300,540);
	    loc10.click();
	    silk.functions.Wait(2000);
        Location loc11=new Location(300,300);
	    loc11.click();
	    silk.functions.Wait(2000);
	    Location loc12=new Location(280,240);
	    loc12.click();	 
	    Location loc15=new Location(100,540);
	    loc15.click();
	    silk.functions.Wait(2000);
	    Location loc16=new Location(280,300);
	    loc16.click();
	    s.type(ClientCode);
	    silk.functions.Wait(2000);
	    loc16.doubleClick();
	    silk.functions.Wait(2000);
	    Location loc17=new Location(200,160);
	    loc17.doubleClick();
	    silk.functions.Wait(2000);
	    Location loc20=new Location(300,540);
	    loc20.click();
	    silk.functions.Wait(2000);
	    Location loc21=new Location(180,300);
        loc21.click();
        silk.functions.Wait(2000);
        s.type(Limit);
       Location loc22=new Location(580,300);
       loc22.click();
       silk.functions.Wait(2000);
       s.type(date);
       silk.functions.Wait(2000);
       
       s.click(path+"save1.png");
       silk.functions.Wait(2000);
       s.click(path+"close1.png");
		silk.functions.Wait(2000);
		s.click(path+"Limit_Yes.png");
		silk.functions.Wait(5000);
       s.click(path+"exit.png");
       silk.functions.Wait(2000);
       s.click(path+"Yes121.png"); 
       
       
       }catch(Exception e){e.getStackTrace();}



}

public static void CreateMFGLimit(String GroupCode,String Limit)


{
Date myDate = new Date();
	
	String date= new SimpleDateFormat("dd/MM/yyyy").format(myDate).toString();
	System.out.println(date);
	try{
		
		
		 s.click(path+"maximize.png"); 
		 silk.functions.Wait(2000);
	     s.click(path+"production.png");
	     silk.functions.Wait(2000);
	     Location loc13=new Location(200,640);
	     loc13.click();
	     silk.functions.Wait(5000);		
	     Location loc10=new Location(300,540);
	     loc10.click();
	     silk.functions.Wait(2000);
         Location loc11=new Location(300,300);
	     loc11.click();
	     silk.functions.Wait(2000);
	     Location loc12=new Location(280,260);
	     loc12.click();
	     silk.functions.Wait(2000);	 
	     Location loc15=new Location(100,540);
	     loc15.click();
	     silk.functions.Wait(2000);
	     Location loc16=new Location(370,300);
	     loc16.click();
	     s.type(GroupCode);
	     silk.functions.Wait(2000);
	     loc16.doubleClick();
	     silk.functions.Wait(2000);
	     Location loc17=new Location(200,160);
	     loc17.doubleClick();
	     silk.functions.Wait(2000);
	     Location loc19=new Location(300,540);
	     loc19.click();
	     silk.functions.Wait(2000);
	     Location loc20=new Location(180,300);
         loc20.click();
         s.type(Limit);
         silk.functions.Wait(2000);
         Location loc21=new Location(580,300);
         loc21.click();
         silk.functions.Wait(2000);
         s.type(date);
         silk.functions.Wait(2000);       
         s.click(path+"save1.png");
         silk.functions.Wait(2000);
         s.click(path+"close1.png");
         silk.functions.Wait(2000);
 		 s.click(path+"Limit_Yes.png");
  		 silk.functions.Wait(5000);
         s.click(path+"exit.png");
         silk.functions.Wait(2000);
         s.click(path+"Yes121.png");  
	
	
	
	}catch(Exception e){e.getStackTrace();}


}

public static void VerifyClient(String ClientName)

{
	try{
	 s.click(path+"maximize.png"); 
     s.click(path+"production.png");
	 silk.functions.Wait(2000);
     Location loc10=new Location(200,470);
     loc10.click();
    silk.functions.Wait(2000);
  	Location loc11=new Location(250,470);
 	loc11.click();
 	silk.functions.Wait(2000);
	Location loc12=new Location(350,245);
    loc12.click();
    silk.functions.Wait(2000);
	s.type(ClientName);
	loc12.doubleClick();
	silk.functions.Wait(5000);}catch(Exception e){e.getStackTrace();}
	



}




}
