package cucumberTest;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
	
	
	public static String driverPath = "C:\\Users\\abdv220\\workspace\\all External jars\\";
	
	 public static WebDriver driver;
	 
		public static void main(String []args) {
			System.out.println("launching chrome browser");
			System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
			driver = new ChromeDriver();
			driver.navigate().to("http://22.149.62.84:8085/#/tasks");
		}
	
	
	
	

}
