package Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomePage;

public class HomeTest extends BaseTest {

	public HomeTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	HomePage homepage;

	

	@BeforeMethod
	public void setup() {
		homepage = new HomePage(driver);
		driver.get(prop.getProperty("baseurl"));
	}

	@Test
	void verifyPageTitle() {
		String actualTitle = homepage.getPageTitle();
		Assert.assertEquals(actualTitle, "Automation Practice Site");
		 logger.info(" verifyHomePageTitle.......Pass");
	}

	@Test
	void verifyNewArrivalHeaderDisplay() {
		boolean flag = homepage.newArrivalHeaderIsDisplayed();
		Assert.assertTrue(flag);
		logger.info(" verifyNewArrivalsDisplayed.......Pass");
	}
		
		@Test
		void verifyscrshot() {
			
			Assert.assertTrue(false);
			logger.info(" verifyNewArrivalsDisplayed.......Pass");
	}
}
