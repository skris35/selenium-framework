/**
 * 
 */
package Logger;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
/**
 * @author Shishir.Kanthi
 *
 */
public class Logger implements ILogger {

	StringBuilder logString;
	String logPath = new String();
	String testCaseName = new String();
	private static Logger staticInstance= null;

	private Logger(){};

	public static Logger GetLoggerInstance()
	{
		if(null==staticInstance)
		{
			staticInstance = new Logger();
		}
		return staticInstance;
	}

	/* (non-Javadoc)
	 * @see Logger.ILogger#CaptureScreenshot()
	 * Captures and saves screenshot with test case name.
	 * Return value: screenshot path if saved successfully. null if capture screenshot fails.
	 */
	@Override
	public String CaptureScreenshot() {
		String screenshotPath = this.logPath+"\\Screenshots\\"+this.testCaseName+(new SimpleDateFormat("MMdd_HHmmss").format(Calendar.getInstance().getTime()))+".jpg";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		try
		{
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image, "jpg", new File(screenshotPath));
		}
		catch(Exception ex)
		{
			LogException("Failed to save screenshot.", ex,false);
			screenshotPath = null;
		}
		return screenshotPath;
	}

	@Override
	public void LogEndTestCase(Boolean result) throws IOException {
		File logFile = new File(this.logPath+"\\"+this.testCaseName+".xml");
		if(logFile.exists())
		{
		
		}
		else
		{
			logFile.createNewFile();
		}
		this.logString.replace(0,9,"<testcase status=\""+result.toString()+"\"");
		this.logString.append("<endtime>"+new SimpleDateFormat("MM/dd/yyyy_HHmmss").format(Calendar.getInstance().getTime())+"</endtime>\n");
		this.logString.append("</logs>\n");
		this.logString.append("</testcase>\n");
		FileWriter writer = new FileWriter(logFile);
		writer.write(this.logString.toString());
		writer.close();
	}

	@Override
	public void LogStatus(TestStatus level, String logString, boolean captureScreenshot) {
		this.logString.append("<log status=\""+level+"\">\n");

		if(level==TestStatus.INFO)
		{			
			this.logString.append("<message>"+logString+"</message>\n");
			if(captureScreenshot)
			{
				this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
			}			
		}

		if(level==TestStatus.PASS)
		{			
			this.logString.append("<message>"+logString+"</message>\n");
			if(captureScreenshot)
			{
				this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
			}

		}
		if(level==TestStatus.FAIL)
		{			
			this.logString.append("<message>"+logString+"</message>\n");
			if(captureScreenshot)
			{
				this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
			}
		}

		if(level==TestStatus.ERROR)
		{			
			this.logString.append("<message>"+logString+"</message>\n");
			this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
		}
		
		if(level==TestStatus.BLOCKED)
		{			
			this.logString.append("<message>"+logString+"</message>\n");
			if(captureScreenshot)
			{
			this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
			}
		}

		this.logString.append("</log>\n");	
	}

	@Override
	public void LogException(String logString, Exception ex, boolean captureScreenshot){
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String exceptionStack = sw.toString();
		this.logString.append("<log status=\""+TestStatus.ERROR+"\">\n");
		this.logString.append("<message>"+ex.getMessage()+"</message>\n");
		this.logString.append("<exception msg=\""+exceptionStack+"\">\n");
		this.logString.append("<stack>"+ex+"</stack>\n");
		if(captureScreenshot)
			this.logString.append("<screenshot>"+this.CaptureScreenshot()+"</screenshot>\n");
		this.logString.append("</log>\n");
	}

	@Override
	public void InitLogger(String logPath) {
		try
		{
			this.logPath = logPath;
		}
		catch(Exception ex)
		{
		}		
	}

	@Override
	public void LogTestCaseStart(String testCaseName, String module)
	{
		this.testCaseName = testCaseName;
		this.logString = new StringBuilder();
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy_HHmmss").format(Calendar.getInstance().getTime());
		this.logString.append("<testcase name=\""+testCaseName+"\" module=\""+module+"\" startTime=\""+timeStamp+"\">\n");
		this.logString.append("<logs>\n");
	}

}
