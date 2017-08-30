package silk;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class test {
	
	
	
	
	static String path= "C:\\Users\\abdv220\\workspace\\Silikuli\\Images\\";
	static Screen s=new Screen();
	
	public static void main(String[] args) 
	
	
	{
		
try{
	
	
		String path="C:\\Users\\abdv220\\workspace\\Silikuli\\Images_IMSV\\";
		 Robot r = new Robot();
		 boolean result= false;
		 
			
		 Screen s= new Screen();
		
	       s.doubleClick(path+"SessionA.png");
	      silk.functions.Wait(2000);
	   
	      s.doubleClick(path+"maximize.png");
	      silk.functions.Wait(2000);
		
	      Location loc12=new Location(420, 880);
	      loc12.click();
	      silk.functions.Wait(2000);
	       s.type("IMSV");
		 		 		
		  r.keyPress(KeyEvent.VK_ENTER);
	 	 r.keyRelease(KeyEvent.VK_ENTER);
	 	
	 	silk.functions.Wait(2000);
		
	 	  s.type("abdv220");
	 	 silk.functions.Wait(2000);
	 	 r.keyPress(KeyEvent.VK_TAB);
	 	 r.keyRelease(KeyEvent.VK_TAB);
		 
		  s.type("Aug@2016");
		  r.keyPress(KeyEvent.VK_ENTER);
	 	 r.keyRelease(KeyEvent.VK_ENTER);
		 
		 
	 	 
	 	silk.functions.Wait(2000);
			
	      Location loc13=new Location(20, 180);
	      loc13.click();
		
	 	s.type("/test mfs");
		
		 r.keyPress(KeyEvent.VK_ENTER);
		 r.keyRelease(KeyEvent.VK_ENTER);
		
		 silk.functions.Wait(2000);
	 	loc13.click();
	 	s.type("MBEN");
	 	r.keyPress(KeyEvent.VK_ENTER);
	 	r.keyRelease(KeyEvent.VK_ENTER);
		
		if(s.find(path+"sessionReady.png").isValid())
			
		{
			result=true;
			
			
			
		}
		
		System.out.println(result);
		
		
	
	
	
	
	
	
	
	
}
		
		
		catch(Exception e){e.getStackTrace();}
		
		
		
		
	}

}
