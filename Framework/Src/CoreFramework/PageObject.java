package CoreFramework;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageObject {
	Properties prop;
	ObjIdTech objectIdMethod;
	
	public PageObject(Properties prop)
	{
		this.prop = prop;	
	}
	
	public WebElement GetElement(String objectName)
	{
		WebDriver driver = FWdriver.GetWebDriverInstance();
		WebElement element = null;
		try
		{
		String[] objValuePair = (prop.getProperty(objectName)).split("#");
		objectIdMethod = ObjIdTech.valueOf(objValuePair[0]);
		switch(objectIdMethod)
		{
		case classname:
			element = driver.findElement(By.className(objValuePair[1]));
			break;
		case name:
			element = driver.findElement(By.name(objValuePair[1]));
			break;
		case id:
			element = driver.findElement(By.id(objValuePair[1]));
			break;
		case xpath:
			element = driver.findElement(By.xpath(objValuePair[1]));
			break;
		case linktext:
			element = driver.findElement(By.linkText(objValuePair[1]));
			break;			
			default:
				break;		
		}
		}
		catch(Exception e)
		{
		Logger.Logger.GetLoggerInstance().LogException("Object not found", e, true);
		}
		return element;
	}

}
