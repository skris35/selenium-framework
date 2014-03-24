package CoreFramework;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import Logger.Logger;


public class ObjectRepo {	
	
	public static PageObject LoginPage()
	{
		PageObject pageObj = new PageObject(GetProperties("LoginPage.properties"));
		return pageObj;
	}
	
	public static Properties GetProperties(String pageName)
	{
		Properties pageObject = new Properties();
		
		try {
			Path currentRelativePath = Paths.get("");		
			FileReader reader = new FileReader((currentRelativePath.toAbsolutePath().toString())+"\\ObjectRepo\\"+pageName);			
			pageObject.load(reader);			
		} catch (IOException e) {
			Logger.GetLoggerInstance().LogException("Page object could not be loaded", e, false);
			e.printStackTrace();
		}
		return pageObject;
	}

}
