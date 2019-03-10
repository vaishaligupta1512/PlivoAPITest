package requests;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MessageAPIRequests {

	public Response getAccountDetails(String authID, String authToken) {
		Response accountDetails = given().auth().basic(authID, authToken).when().get("Account/" + authID + "/").then()
				.extract().response();
		return accountDetails;
	}

	public Response getAccountNumbers(String authID, String authToken) {
		Response accountNumbers = given().auth().basic(authID, authToken).when().get("Account/" + authID + "/Number")
				.then().extract().response();
		return accountNumbers;
	}

	public Response sendTextMessage(String authID, String authToken, String source, String destination, String text) {
		Map<String, String> msgBody = new HashMap<>();
		msgBody.put("src", source);
		msgBody.put("dst", destination);
		msgBody.put("text", text);
		Response textMessage = given().auth().basic(authID, authToken).contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(msgBody).when().post("Account/" + authID + "/Message/").then().extract()
				.response();
		return textMessage;
	}

	public Response getMessageDetails(String authID, String authToken, String msgUID) {
		Response messageDetails = given().auth().basic(authID, authToken).when()
				.get("Account/" + authID + "/Message/" + msgUID).then().extract().response();
		return messageDetails;
	}

	public Response getCountryPricing(String authID, String authToken, String countryISO) {
		Response countryPricing = given().auth().basic(authID, authToken).queryParams("country_iso", countryISO).when()
				.get("Account/" + authID + "/Pricing/").then().extract().response();
		return countryPricing;
	}
}
