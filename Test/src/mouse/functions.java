package mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.SplashScreen;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class functions {
	
	
	
	
	public static void Type(String s) throws AWTException
	
	
	{
		

		Robot robot = new Robot();
		
		
		byte[] bytes = s.getBytes();
	    for (byte b : bytes)
	    {
	      int code = b;
	      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
	      if (code > 96 && code < 123) code = code - 32;
	      robot.delay(40);
	      robot.keyPress(code);
	      robot.keyRelease(code);
	    }
		
	}
	
	public static void KeyPress(int i) throws AWTException
	  {
		  
		Robot robot = new Robot();
	    robot.delay(40);
	    robot.keyPress(i);
	    robot.keyRelease(i);
	  }
	
	public static void Maximize() throws AWTException
	  
	  
	  {
		     
		  
		    Robot robot = new Robot();		  
		    robot.mouseMove(502, 604);
			robot.mousePress(InputEvent.BUTTON1_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);			
		    robot.delay(400);
			robot.keyPress(KeyEvent.VK_WINDOWS);			
			robot.keyPress(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_WINDOWS);
			robot.keyRelease(KeyEvent.VK_UP);
		  
		  
		  
	  }
	
	public static void MouseLeftClick()
		 
		 
		 {
			 
			 
			 
			 
			 
			 
		 }
		 
		 
	
	

}
