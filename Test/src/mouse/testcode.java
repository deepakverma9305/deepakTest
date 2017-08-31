package mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class testcode {
	
	public static final int FIVE_SECONDS = 5000;
    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;
	
	
	public static  void main(String[] args) throws AWTException, InterruptedException, IOException
	
	
	{
		
		
		Robot robot = new Robot();
        
          
          
    //    Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe http:10.6.20.46:8101/pls/imx/session_web_v8?&ip=&lng=eng" );
      
          
         // robot.mouseMove(474, 134);
       //   robot.mousePress(InputEvent.BUTTON1_MASK);
       //   robot.mouseRelease(InputEvent.BUTTON1_MASK);
       //   robot.mousePress(InputEvent.BUTTON1_MASK);
       //   robot.mouseRelease(InputEvent.BUTTON1_MASK);
        //robot.delay(400);
         //ouse.functions.Type("Four the go");
         //obot.delay(400);
       //mouse.functions.KeyPress(KeyEvent.VK_ENTER);
          
	//	mouse.functions.KeyPress(KeyEvent.VK_WINDOWS);
	//	mouse.functions.KeyPress(KeyEvent.VK_UP);
		
		mouse.functions.Maximize();
		
        
	}
	
	
	
	
	
	
	
	
	
	
	

}
