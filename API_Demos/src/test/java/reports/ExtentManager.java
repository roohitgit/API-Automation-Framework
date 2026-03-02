package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	public static ExtentReports extent;

    public static ExtentReports getInstance() {

    	System.getProperty("user.dir");
    	
        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("docs/index.html");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }

        return extent;
    }

}
