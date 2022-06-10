package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	WebDriver driver;
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	//pageobjects
	@FindBy(xpath="//*[text()='new arrivals']")
	WebElement newArrivalHeader;
	
	//methods
	public boolean newArrivalHeaderIsDisplayed() {
		return newArrivalHeader.isDisplayed();
	}

	
	
}
