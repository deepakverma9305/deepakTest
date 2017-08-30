import org.openqa.selenium.WebDriver;

public class dfdf {
	public static WebDriver driver;
	//public static BrowserMobProxyServer server;
public static void main(String[] args) throws Exception {
	dfdf d = new dfdf();
	d.setup();
	d.teknosa_test1();
	d.shutdown();
}
	
	
	public void setup() throws Exception {
//		System.setProperty("webdriver.gecko.driver","D:\\pangea.poc.priv.autotest\\GTAF\\src\\ExternalJars\\geckodriver.exe");
//		System.setProperty("webdriver.firefox.bin","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		System.setProperty("webdriver.chrome.driver","D:\\pangea.poc.priv.autotest\\GTAF\\src\\ExternalJars\\chromedriver.exe");
		
//		server = new BrowserMobProxyServer();
//		server.start();
//		int port = server.getPort();
//		Proxy proxy = ClientUtil.createSeleniumProxy(server);
////		Proxy proxy = new Proxy();
//		DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
//		seleniumCapabilities.setCapability(CapabilityType.PROXY, proxy);
//		driver = new ChromeDriver(seleniumCapabilities);
//		
//		System.out.println("Port started:" + port);
//		System.out.println("Proxy started:" + proxy.getHttpProxy());
//		System.out.println("PP");
//		
	}

	public void teknosa_test1() throws InterruptedException {

//		server.newHar("teknosa.har");
//
//		driver.get("http://www.teknosa.com");
//		driver.manage().window().maximize();
//		Thread.sleep(15000);
	}

	public void shutdown() {
//		try {
//			// Get the HAR data
//			Har har = server.getHar();
//			File harFile = new File("C:\\temp\\teknosa_test.har");
//			har.writeTo(harFile);
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//		driver.quit();
//		server.stop();
	}
}