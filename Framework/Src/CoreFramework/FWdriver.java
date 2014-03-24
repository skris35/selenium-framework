package CoreFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Logger.Logger;
import Logger.TestStatus;

public class FWdriver {
	public static LinkedHashMap<String, Hashtable<String,String>> testcaseCollection = new LinkedHashMap<String, Hashtable<String,String>>();
	static WebDriver driver =null;
	
	public static WebDriver GetWebDriverInstance()
	{
		if(null!=driver)
			return driver;
		else
			driver = new FirefoxDriver();
		return driver;
	}
	

	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		InputStream is = new FileInputStream("D:\\Personal\\Workspace\\TinyFramework\\MasterExcel.xlsx");
		Workbook wb = new XSSFWorkbook(is);
		Sheet worksheet = wb.getSheet("Sheet1");
		Row row = null;
		Cell cell = null;
		
		Method  method = null;
		String logPath = "C:\\MyLog";
		Logger logger = Logger.GetLoggerInstance();
		logger.InitLogger(logPath);
		int rowCount = worksheet.getLastRowNum();
		
		for(int i =1;i<=rowCount;i++)
		{
			try
			{
				row = worksheet.getRow(i);
				cell = row.getCell(1);
				
				FWdriver.testcaseCollection.put(cell.getStringCellValue(), new Hashtable<String,String>());
			}
			catch(Exception ex)
			{
			}
		}
		String testCaseName;
		Set<String> e = FWdriver.testcaseCollection.keySet();
	
		Object retValue =false;
		Class<?> c = null;
		Iterator<String> iter = e.iterator();
		while(iter.hasNext()) {
			try
			{
				testCaseName = (iter.next());
				logger.LogTestCaseStart(testCaseName, "M1");
				c = Class.forName("Workflows."+testCaseName);
				method = c.getDeclaredMethod ("test"+testCaseName);
				retValue = method.invoke(c.newInstance());
				logger.LogEndTestCase((Boolean)retValue);
			}
			catch(Exception ex)
			{
				logger.LogException("Got fucked up", ex, false);
				logger.LogEndTestCase((Boolean)retValue);
			}
		}
	}
	//System.out.println(cell.getStringCellValue());	
}	


