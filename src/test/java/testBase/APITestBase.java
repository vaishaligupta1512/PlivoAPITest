package testBase;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import config.ConfigHandler;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import validations.APIVerifications;

public class APITestBase {
	private static Logger log = Logger.getLogger(APITestBase.class);

	protected String authID, authToken;
	public JsonPath js;
	public Response res;

	public APIVerifications apiVerify = new APIVerifications();
	public ConfigHandler config;

	@BeforeClass
	public void setUp() throws IOException {

		// Reading config.properties file
		config = new ConfigHandler();
		RestAssured.baseURI = config.getBaseURI();
		authID = config.getAuthID();
		authToken = config.getAuthToken();

		// Setting up log4j properties file path and configure the logger
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}
}
