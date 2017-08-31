package terminalEmulator;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

public class imsv {
	
	public static void main(String[] args) throws IOException, FindFailed, AWTException
	
	
	{
		
		String path="C:\\Users\\abdv220\\workspace\\Silikuli\\Images_IMSV\\";
		 Robot r = new Robot();
		 boolean result= false;
		 
	
		
		Screen s= new Screen();
		
	       s.doubleClick(path+"SessionA.png");
	      
	      terminalEmulator.imsv.Wait(2000);
	      s.doubleClick(path+"maximize.png");
	      terminalEmulator.imsv.Wait(2000);
		
	      Location loc12=new Location(420, 880);
	      loc12.click();
	       terminalEmulator.imsv.Wait(2000);
	       s.type("IMSV");
		 		 		
		  r.keyPress(KeyEvent.VK_ENTER);
	 	 r.keyRelease(KeyEvent.VK_ENTER);
	 	
	 	  terminalEmulator.imsv.Wait(2000);
		
	 	  s.type("abdv220");
	  	 terminalEmulator.imsv.Wait(2000);
	 	 r.keyPress(KeyEvent.VK_TAB);
	 	 r.keyRelease(KeyEvent.VK_TAB);
		 
		  s.type("Aug@2016");
		  r.keyPress(KeyEvent.VK_ENTER);
	 	 r.keyRelease(KeyEvent.VK_ENTER);
		 
		 
	 	 
		//  terminalEmulator.imsv.Wait(2000);
			
	   //  Location loc13=new Location(20, 180);
	  //   loc13.click();
		
	//	s.type("/test mfs");
		
		//r.keyPress(KeyEvent.VK_ENTER);
		//r.keyRelease(KeyEvent.VK_ENTER);
		
		//terminalEmulator.imsv.Wait(2000);
	//	loc13.click();
	//	s.type("MBEN");
	//	r.keyPress(KeyEvent.VK_ENTER);
	//	r.keyRelease(KeyEvent.VK_ENTER);
		
		if(s.find(path+"sessionReady.png").isValid())
			
		{
			result=true;
			
			
			
		}
		
		System.out.println(result);
		
		
	}
	
	
	
	
public static void Wait(int time)
	
	
	
	{
		try {
		    Thread.sleep(time);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	
}
	
	



}
