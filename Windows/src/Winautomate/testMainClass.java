package Winautomate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.sikuli.script.FindFailed;

public class testMainClass {
	
	
public static void main(String[] args) throws FindFailed

{	

	 try {
     	
     	Class.forName("org.postgresql.Driver");
     	Connection connection = null;
     	//connection = DriverManager.getConnection(Str);
     	connection = DriverManager.getConnection("jdbc:postgresql://10.110.180.202:5432/SIT_supplierfinance_workflow","postgres", "postgres");
     	
        
         Statement stmt = connection.createStatement();
        // ResultSet rs;

         //rs = stmt.executeQuery("truncate batch_run cascade;");
         stmt.executeQuery("truncate act_ru_task,act_ru_job cascade;");
         connection.close();
         
         
        
        
        
    } catch (Exception e) {System.out.println(e.toString());
        
    }
	
	
}
 
   	  
     }



