package Workflows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.*;

import CoreFramework.FWdriver;
import CoreFramework.ObjectRepo;
import CoreFramework.PageObject;
import Logger.*;

public class epo_Login {
	WebDriver driver = null;
	public Boolean testepo_Login()
	{
		Logger logger = Logger.GetLoggerInstance();
		try
		{
			//driver = new FirefoxDriver();
			driver = FWdriver.GetWebDriverInstance();
			driver.get("http://qtpselenium.com/login/seleniumLogin.php");
			PageObject loginPage = ObjectRepo.LoginPage();
			loginPage.GetElement("userName").sendKeys("shishirkanthi@gmail.com");
			String title = driver.getTitle();			
				
		}
		catch(Exception ex)
		{
			logger.LogException("Exception caught", ex, true);
			return false;
		}
		finally
		{			
		}
		logger.LogStatus(TestStatus.PASS, "Login Successful", true);
		return true;
	}
}
