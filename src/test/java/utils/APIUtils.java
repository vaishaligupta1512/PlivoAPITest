package utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import org.apache.log4j.Logger;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIUtils {
	private static Logger log = Logger.getLogger(APIUtils.class);

	public static JsonPath responseToJsonPath(Response res) {
		return new JsonPath(res.asString());
	}
	
	public static double doubleRoundOff(double value, String format) {
		DecimalFormat df = new DecimalFormat(format);
		String roundOff = df.format(value);
		double rondedOffValue = 0.0D;
		try {
			rondedOffValue = (double)df.parse(roundOff);
		} catch (ParseException e) {
			log.error("Error rounding off the value.");
		}
		return rondedOffValue;
	}
}
