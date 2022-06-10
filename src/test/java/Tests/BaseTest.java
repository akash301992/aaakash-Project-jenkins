package Tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.Screenshotcod;

public class BaseTest {

	WebDriver driver;
	Properties prop;
	Logger logger;
	ExtentHtmlReporter htmlreporter;
	ExtentReports extent;
	ExtentTest test;

	public BaseTest() throws IOException {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(".\\resources\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		logger = Logger.getLogger(BaseTest.class);
		PropertyConfigurator.configure(".\\resources\\log4j.properties");
	}

	@BeforeSuite
	void browserSetup() {
		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			logger.debug("Chrome driver binary successfully set");
			logger.info("chrome browser opened");
		}

		// driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
	}

	@BeforeTest
	public void setupExtentEnv() {
		htmlreporter = new ExtentHtmlReporter(".\\extentreport\\extent-report.html");
		htmlreporter.config().setReportName("Automation Result");
		htmlreporter.config().setDocumentTitle("Test Result");
		htmlreporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("tester", "akash");
		extent.setSystemInfo("Browser", "Chrome");
		logger.info("extent report set");

	}

	@BeforeMethod
	public void register(Method method) {
		String testname = method.getName();
		test = extent.createTest(testname);
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED is" + result.getName());
			test.log(Status.FAIL, "TEST CASE FAILED is" + result.getThrowable());
			String screenshotpath = Screenshotcod.getScreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotpath);
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(Status.SKIP, "TEST CASE SkIPPED:"+result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(Status.PASS, "TEST CASE PASSED:"+result.getName());
		}

	}
	
	@AfterTest
	public void cleanup()
	{
		extent.flush();
	}

	@AfterSuite
	void browserClose() {
		driver.quit();
	}

}
