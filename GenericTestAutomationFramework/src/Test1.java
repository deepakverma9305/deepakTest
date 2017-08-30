import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
public class Test1 implements Runnable{
	public WebDriver driver;
	public String URL, Node;
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;
	
	public static void main(String[] args) throws IOException {
		Test1.writeChart();
	}
	
	public void run() {
		execute();
	}
	
	public void execute() {
		try{
			URL = "http://10.110.43.105:8085/job/Pangea.Test.Sandbox/";
			System.out.println(" Executing on CHROME");
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			
			cap.setBrowserName("chrome");
			String Node = "http://10.110.52.136:4444/wd/hub";
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Launch website
			driver.navigate().to(URL);
			//driver.manage().window().maximize();
			driver.findElement(By.linkText("Jenkins")).click();
			driver.findElement(By.linkText("Jenkins")).click();
			driver.findElement(By.linkText("Pangea.Test.Sandbox")).click();
			driver.findElement(By.linkText("Recent Changes")).click();
			driver.findElement(By.linkText("Jenkins")).click();
			driver.close();
			driver.quit();
			
			
		}catch(MalformedURLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	private static void writeChart() throws IOException{
			XYSeriesCollection  dataset = new XYSeriesCollection ();
		     XYSeries series = new XYSeries("Suite 1");
		     series.add(0, 0);
		     series.add(1, 0.015);
		     series.add(2,0.023);
		     series.add(3,0.013);
		     series.add(4,1.425);
		     series.add(5,0.104);
		     series.add(50,10.4);
		     
		     
	        dataset.addSeries(series);
	        JFreeChart ieChart = ChartFactory.createXYLineChart("Test Title", "Execution Order", "Test Duration", dataset);
	        ChartUtilities.saveChartAsJPEG(new File("C:/temp/FirstChart6.jpg"),ieChart,800,600);
	}
}
