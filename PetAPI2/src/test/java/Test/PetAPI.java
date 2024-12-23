package Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PetAPI {

	@BeforeClass
	public void getBaseURI() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";

	}

	@Test(priority = 1)
	public void testCreatePet() {
		getBaseURI();
		String petJson = "{ \"id\": 1234, \"name\": \"Pati\", \"status\": \"available\" }";

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(petJson)
				.post("/pet");

		System.out.println(response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "Pati");

	}

	@Test(priority = 2)
	public void testGetPet() {
		getBaseURI();

		int petId = 1234;

		Response response = RestAssured.given().get("/pet/" + petId);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("id"), petId);
	}
	@Test(priority = 3)
	public void testUpdatedPet() {
		getBaseURI();
		
		String UpdatedPetJson = "{ \"id\": 1234, \"name\": \"Bidik\", \"status\": \"sold\" }";
		
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(UpdatedPetJson)
				.put("/pet");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("status"), "sold");
		
	}
	
	
	@Test(priority = 4)
	public void testDeletePet() {
		getBaseURI();
	    int petId = 1234;

	    Response response = RestAssured.given()
	            .get("/pet/" + petId);
	    System.out.println(response.getStatusCode());
	    	System.out.println(response.jsonPath());
	        Response deleteResponse = RestAssured.given()
	                .delete("/pet/" + petId);

	        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
	        Assert.assertEquals(deleteResponse.jsonPath().getString("message"), String.valueOf(petId));
	    
	}
	@Test(priority = 5)
	public void testNegativeTest() {
		getBaseURI();
		
		int nonExistPetId = 9345234;
		
		Response response = RestAssured.given()
				.get("/pet/" + nonExistPetId);
		
		Assert.assertEquals(response.getStatusCode(), 404);
		
	}
	

}
