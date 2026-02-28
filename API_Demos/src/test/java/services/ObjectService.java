package services;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ObjectRequest;

public class ObjectService {

	public Response getAllObjects() {

	    return RestAssured
	            .given()
	            .get("/objects");
	}
	
	public Response getObject(String id)
	{
		return RestAssured
				.given()
				.get("/objects/"+id);
	}
	
	public Response createObject(ObjectRequest request)
	{
		return RestAssured
				.given()
				.header("Content-Type", "application/json")
				.body(request)
				.post("/objects");
	}
	
//	public Response updateObject(String id, ObjectRequest request) {
//
//	    return RestAssured
//	            .given()
//	            .header("Content-Type", "application/json")
//	            .body(request)
//	            .put("/objects/" + id);
//	}
	
	public Response updateObject(String id, ObjectRequest request)
	{
		return RestAssured
				.given()
				.pathParam("id", id)
				.header("Content-Type", "application/json")
				.body(request)
				.put("/objects/{id}");
	}
	
	public Response patchObject(String id, Map<String, Object> patchData) {

	    return RestAssured
	            .given()
	            .pathParam("id", id)
	            .header("Content-Type", "application/json")
	            .body(patchData)
	            .patch("/objects/{id}");
	}
	
	public Response deleteObject(String id) {

	    return RestAssured
	            .given()
	            .pathParam("id", id)
	            .delete("/objects/{id}");
	}
	
}
