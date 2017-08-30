package silk;


 import org.sikuli.script.Screen;
 


public class silikuliTest {
	
	static String path= "C:\\Users\\abdv220\\workspace\\Silikuli\\Images\\";
	static Screen s=new Screen();
	
	public static void main(String[] args) 
	
	
	{
		try{
		
		//Read Data From Excel
		String ResultPath="C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test Results\\";
		String ResultSourceFile="C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Templates\\TestCases.xlsx";
		String ResultDestinationFile="C:\\Users\\abdv220\\Desktop\\Selinium Framework_Recievable Finance\\Test Results\\ResultSheet\\TestResults.xlsx";
		String Client_Name= silk.functions.ReadFromExcel(1, 1, 1).trim();
		String Client_Reg_Number= silk.functions.ReadFromExcel(1, 8, 1).trim();
		String Client_CIF_Key= silk.functions.ReadFromExcel(1, 9, 1).trim();
		String Client_CIF_Number= silk.functions.ReadFromExcel(1, 10, 1).trim();		
		String Login_Name= silk.functions.ReadFromExcel(1, 4, 1).trim();
		String Login_Password= silk.functions.ReadFromExcel(1, 5, 1).trim();		
		String Debtor_Name= silk.functions.ReadFromExcel(1, 2, 1).trim();
		String Debtor_Reg_Number= silk.functions.ReadFromExcel(1, 11, 1).trim();
		String Debtor_CIF_Key= silk.functions.ReadFromExcel(1, 12, 1).trim();
		String Debtor_CIF_Number= silk.functions.ReadFromExcel(1, 13, 1).trim();
		String MFC_ClientCode= silk.functions.ReadFromExcel(1, 14, 1).trim();
		String MFG_GroupCode= silk.functions.ReadFromExcel(1, 15, 1).trim();
		String MFC_Limit= silk.functions.ReadFromExcel(1, 16, 1).trim();
		String MFG_Limit= silk.functions.ReadFromExcel(1, 17, 1).trim();
		String Test_1=silk.functions.ReadFromExcel(0, 1, 3).trim();
		String Test_2=silk.functions.ReadFromExcel(0, 2, 3).trim();
		String Test_3=silk.functions.ReadFromExcel(0, 3, 3).trim();
		String Test_4=silk.functions.ReadFromExcel(0, 4, 3).trim();
		String Test_5=silk.functions.ReadFromExcel(0, 5, 3).trim();
		String Test_6=silk.functions.ReadFromExcel(0, 6, 3).trim();
		String Test_7=silk.functions.ReadFromExcel(0, 7, 3).trim();
		String Test_8=silk.functions.ReadFromExcel(0, 8, 3).trim();
		String Test_9=silk.functions.ReadFromExcel(0, 9, 3).trim();
		String Test_10=silk.functions.ReadFromExcel(0, 10, 3).trim();
		
	//	System.out.println(MFC_ClientCode);
		
//=================================================================================================================================================	
	
//Flush Results==============================
		
		
		 silk.functions.FlushOldResults(ResultDestinationFile);
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_1");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_2");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_3");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_4");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_5");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_6");
		 silk.functions.FlushOldResults(ResultPath+"Screens_Test_7");
		
		
		
//Copy Result Sheet====================
		
    silk.functions.copyFileUsingStream(ResultSourceFile, ResultDestinationFile);
   
    
//Test Cases===============================
    
	    
	 if(Test_1.equals("YES"))
			
		{
		      Runtime rt = Runtime.getRuntime();
		      rt.exec("taskkill /F /IM iexplore.exe");
		      rt.exec("taskkill /F /IM jp2launcher.exe");
		      silk.functions.Wait(10000);		    
		      silk.functions.launchIMX();
		      silk.functions.Wait(10000);
		      if(s.find(path+"login.png").isValid())
		      
		      {
		    	 
		    	  silk.functions.WriteToExcel(0, 1, 3, ResultDestinationFile, ResultDestinationFile, "Passs");
		    	  
		    	  
		    	  
		      }
			
			
		}

	
	
	if(Test_2.equals("YES"))
			
		try {
	          silk.functions.loginIMX(Login_Name, Login_Password);
	          silk.functions.Wait(2000);
	          if(s.find(path+"exit.png").isValid())

{
	        	  silk.functions.WriteToExcel(0, 2, 3, ResultDestinationFile, ResultDestinationFile, "Passs");


}

	          s.click(path+"exit.png");
	          silk.functions.Wait(2000);
	          s.click(path+"Yes121.png");
	         
	          silk.functions.Wait(2000);
	          Runtime rt = Runtime.getRuntime();
	  		  rt.exec("taskkill /F /IM iexplore.exe");
	  		  rt.exec("taskkill /F /IM jp2launcher.exe");
	          
		}catch(Exception e){e.getStackTrace();}
		
	
	if(Test_3.equals("YES"))
		
	{
		
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);		
		silk.functions.Create_client(Client_Name,Client_Reg_Number,Client_CIF_Key,Client_CIF_Number);
		
		
	}
		
	
	if(Test_4.equals("YES"))
		
	{
		 
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);	
		silk.functions.Create_Debtor(Debtor_Name,Debtor_Reg_Number,Debtor_CIF_Key,Debtor_CIF_Number);
		
		
	}
	
	if(Test_5.equals("YES"))
		
	{
		 
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);	
		silk.functions.FFCreateContract(Client_Name);	
		
		
	}
	
	if(Test_6.equals("YES"))
		
	{
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);	
		silk.functions.BulkCreateContract(Client_Name);	
		
		
	}
	
	
    if(Test_7.equals("YES"))
		
	{
    	 
    	Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);	
    	silk.functions.CreateABSContract(Client_Name);	
		
		
	}
	
		
    
    
    if(Test_8.equals("YES"))
		
   	{
       	 
       	Runtime rt = Runtime.getRuntime();
   		rt.exec("taskkill /F /IM iexplore.exe");
   		rt.exec("taskkill /F /IM jp2launcher.exe");
   		silk.functions.Wait(10000);
   		silk.functions.launchIMX();
   		silk.functions.Wait(10000);
   		silk.functions.loginIMX(Login_Name, Login_Password);
   		silk.functions.Wait(10000);	
   		silk.functions.CreateMFCLimit(MFC_ClientCode,MFC_Limit);
   		
   		
   	}
   	
 if(Test_9.equals("YES"))
		
   	{
       	 
       	Runtime rt = Runtime.getRuntime();
   		rt.exec("taskkill /F /IM iexplore.exe");
   		rt.exec("taskkill /F /IM jp2launcher.exe");
   		silk.functions.Wait(10000);
   		silk.functions.launchIMX();
   		silk.functions.Wait(10000);
   		silk.functions.loginIMX(Login_Name, Login_Password);
   		silk.functions.Wait(10000);	
   		silk.functions.CreateMFGLimit(MFG_GroupCode,MFG_Limit);
   		
   		
   	}
    
    
 
 
 if(Test_10.equals("YES"))
		
	{
    	 
    	Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM iexplore.exe");
		rt.exec("taskkill /F /IM jp2launcher.exe");
		silk.functions.Wait(10000);
		silk.functions.launchIMX();
		silk.functions.Wait(10000);
		silk.functions.loginIMX(Login_Name, Login_Password);
		silk.functions.Wait(10000);	
		silk.functions.VerifyClient(Client_Name);
		silk.functions.Wait(5000);
		 if(s.find(path+"bank.png").isValid())

		 {
		 	        	  silk.functions.WriteToExcel(0, 10, 3, ResultDestinationFile, ResultDestinationFile, "Pass");
		 	        	  


		 }
		
		
		 
		  s.click(path+"close1.png");
		  silk.functions.Wait(2000);
		  s.click(path+"close1.png");
		  silk.functions.Wait(2000);
		  s.click(path+"exit.png");
          silk.functions.Wait(2000);
          s.click(path+"Yes121.png");
         
		
		
		
	}
 
 
 
		}catch(Exception e){e.getStackTrace();}
		
		
		
		
		
		
		
	}
	
	
				

}
