package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.restassured.RestAssured;
import reports.ExtentManager;
import utils.ConfigReader;

public class BaseTest {

	protected static ExtentReports extent;
	protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	    @BeforeClass
	    public void setup() {

	        RestAssured.baseURI = ConfigReader.getProperty("baseURL");

	        extent = ExtentManager.getInstance();
	    }

	    @AfterClass
	    public void tearDown() {

	        extent.flush();
	    }
}
