package Workflows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.*;

import CoreFramework.FWdriver;
import Logger.*;

public class epo_ImportPolicy {
	WebDriver driver = null;
	public Boolean testepo_ImportPolicy()
	{
		Logger logger = Logger.GetLoggerInstance();
		try
		{
			driver = FWdriver.GetWebDriverInstance();
			
			for (String handle : driver.getWindowHandles()) {
		        String newWindowTitle = driver.switchTo().window(handle).getTitle();
		        if(newWindowTitle.contains("ePolicy Orchestrator"))
		            break;
		            
		    }
		}
		catch(Exception ex)
		{
			logger.LogException("Exception caught in epo_ImportPolicy", ex, false);
		}
		return true;
	}
}
