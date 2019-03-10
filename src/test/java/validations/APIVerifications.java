package validations;

import org.testng.Assert;
import io.restassured.response.Response;

public class APIVerifications {
	
	public void checkStatusCode(Response res, int statusCode){
		Assert.assertEquals(res.getStatusCode(), statusCode, "Status check failed!!");
	}
	
	public void checkContentType(Response res, String contentType){
		Assert.assertEquals(res.getContentType(), contentType, "ContentType check failed!!");
	}

}
