package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import services.ObjectService;
import io.restassured.response.Response;
import models.ObjectRequest;

public class AppTest extends BaseTest {

	String objectID;

	@Test
	public void getAllObjectsTest() {
		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());

		ObjectService service = new ObjectService();
		Response response = service.getAllObjects();

		test.info("Response- " + "<pre>" + response.getBody().asPrettyString() + "</pre>");

		test.info("Status Code- " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

		String firstId = response.jsonPath().getString("[0].id");

		test.info("First Object ID- " + firstId);

		Assert.assertNotNull(firstId);

		test.pass("Get all objects API validated successfully");

	}

	@Test
	public void getSingleObjectTest() {

		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());

		ObjectService service = new ObjectService();

		String objectID = "3";

		Response response = service.getObject(objectID);

		test.info("Status Code- " + response.getStatusCode());

		test.info("Response- " + "<pre>" + response.getBody().asPrettyString() + "</pre>");

		Assert.assertEquals(response.getStatusCode(), 200);

		String returnID = response.jsonPath().getString("id");
		String name = response.jsonPath().getString("name");
		String color = response.jsonPath().getString("data.color");
		int capacity = response.jsonPath().getInt("data.'capacity GB'");

		test.info("ID " + returnID);
		test.info("Name " + name);
		test.info("Color " + color);
		test.info("Capacity " + capacity);

		Assert.assertEquals(returnID, objectID);
		Assert.assertNotNull(name);
		Assert.assertEquals(color, "Cloudy White");
		Assert.assertEquals(capacity, 512);

		test.pass("GET Single Object validated successfully");

	}

	@Test
	public void createObjectTest() {
		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());

		ObjectService service = new ObjectService();

		ObjectRequest request = new ObjectRequest();
		request.setName("Pixel 10 Rohit");

		Map<String, Object> data = new HashMap<>();
		data.put("year", 2027);
		data.put("price", 35020);
		data.put("CPU model", "G5");
		data.put("Hard disk size", "20TB");

		request.setData(data);

		Response response = service.createObject(request);

		test.info("Response - " + "<pre>" + response.getBody().asPrettyString() + "</pre>");
		test.info("Status Code - " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

		this.objectID = response.jsonPath().getString("id");
		String name = response.jsonPath().getString("name");
		int year = response.jsonPath().getInt("data.year");
		String createdAt = response.jsonPath().getString("createdAt");

		test.info("Created ID: " + objectID);
		test.info("Created At: " + createdAt);

		Assert.assertEquals(name, request.getName());
		Assert.assertEquals(year, 2027);
		Assert.assertNotNull(objectID);
		Assert.assertNotNull(createdAt);

		test.pass("POST Object validated successfully");

	}

	@Test(dependsOnMethods = "createObjectTest")
	public void updateObjectTest() {
		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());

		ObjectRequest updateRequest = new ObjectRequest();
		ObjectService service = new ObjectService();
		updateRequest.setName("Pixel 10 Updated Rohit");

		Map<String, Object> udpateData = new HashMap<>();
		udpateData.put("year", 2028);
		udpateData.put("price", 38000);
		udpateData.put("CPU model", "G8");
		udpateData.put("Hard disk size", "30TB");

		updateRequest.setData(udpateData);

		Response updateResponse = service.updateObject(objectID, updateRequest);

		test.info("Id is using to update- " + objectID);

		test.info("Updated Response- " + "<pre>" + updateResponse.getBody().asPrettyString() + "</pre>");
		test.info("Status Code- " + updateResponse.getStatusCode());
		Assert.assertEquals(updateResponse.getStatusCode(), 200);

		String updatedName = updateResponse.jsonPath().getString("name");
		int updatedYear = updateResponse.jsonPath().getInt("data.year");

		test.info("Updated Name: " + updatedName);
		test.info("Updated Year: " + updatedYear);

		Assert.assertEquals(updatedName, "Pixel 10 Updated Rohit");
		Assert.assertEquals(updatedYear, 2028);

		test.pass("PUT Object validated successfully");

	}

	@Test(dependsOnMethods = { "createObjectTest", "updateObjectTest" })
	public void patchObjectTest() {

		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());

		ObjectService service = new ObjectService();

		// Only update name
		Map<String, Object> patchData = new HashMap<>();
		patchData.put("name", "Pixel 10 Patched Rohit");
		test.info("Id is using to update- " + objectID);
		Response patchResponse = service.patchObject(objectID, patchData);

		test.info("<pre>" + patchResponse.getBody().asPrettyString() + "</pre>");

		test.info("Status Code- " + patchResponse.getStatusCode());
		Assert.assertEquals(patchResponse.getStatusCode(), 200);

		String updatedName = patchResponse.jsonPath().getString("name");

		test.info("Updated Name: " + updatedName);

		Assert.assertEquals(updatedName, "Pixel 10 Patched Rohit");

		test.pass("PATCH Object validated successfully");
	}

	@Test(dependsOnMethods = { "createObjectTest", "updateObjectTest", "patchObjectTest" })
	public void deleteObjectTest() {

		ExtentTest test = extent.createTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectService service = new ObjectService();

		Response deleteResponse = service.deleteObject(objectID);
		test.info("Id is using to delete- " + objectID);
		test.info("Status Code- " + deleteResponse.getStatusCode());
		Assert.assertEquals(deleteResponse.getStatusCode(), 200);

		test.info("Object Deleted Successfully");

		// Validate deletion
		Response getResponse = service.getObject(objectID);

		Assert.assertEquals(getResponse.getStatusCode(), 404);

		test.pass("DELETE API validated with verification");
	}

}