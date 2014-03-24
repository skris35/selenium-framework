/**
 * 
 */
package Logger;

import java.io.IOException;

/**
 * @author Shishir.Kanthi
 *
 */
public interface ILogger {
	public void InitLogger(String logPath);
	public void LogStatus(TestStatus level,String logString, boolean captureScreenshot);//, String testCaseName);
	public void LogException(String logString, Exception ex, boolean captureScreenshot);
	public String CaptureScreenshot();
	public void LogEndTestCase(Boolean result) throws IOException;
	public void LogTestCaseStart(String testCaseName, String module);
}
