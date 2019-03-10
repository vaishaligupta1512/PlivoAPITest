package tests;

import java.text.ParseException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import requests.MessageAPIRequests;
import testBase.APITestBase;
import utils.APIUtils;

public class MessageAPITest extends APITestBase {

	private static Logger log = Logger.getLogger(MessageAPITest.class);

	String senderNumber, receiverNumber, msgUUID, msgRate, initialCashCredit;
	MessageAPIRequests msgRequest = new MessageAPIRequests();

	@Test(description="Get the initial Cash Credit in Account.")
	public void getInitialCashCredit() {
		res = msgRequest.getAccountDetails(authID, authToken);
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 200);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		initialCashCredit = js.get("cash_credits");
		log.info("Initial cash credits: " + initialCashCredit);
	}

	@Test(description="Get two numbers to send text message.")
	public void getNumbers() {
		res = msgRequest.getAccountNumbers(authID, authToken);
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 200);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		senderNumber = js.get("objects[0].number");
		receiverNumber = js.get("objects[1].number");
		log.info("senderNumber: " + senderNumber + " receiverNumber: " + receiverNumber);
	}

	@Test(dependsOnMethods = { "getNumbers" }, description = "Text message sent from sender to receiver.")
	public void sendSMS() {
		String msgText = "Hi, text from Plivo";
		res = msgRequest.sendTextMessage(authID, authToken, senderNumber, receiverNumber, msgText);
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 202);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		String status = js.get("message");
		msgUUID = js.get("message_uuid[0]");
		log.info("Message status: " + status + " and Message UUID: " + msgUUID);
		res = msgRequest.getMessageDetails(authID, authToken, msgUUID);
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 200);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		msgRate = js.get("total_rate");
		log.info("Message sending charges: " + msgRate);
	}

	@Test(dependsOnMethods = { "sendSMS" }, description = "Check text message charges for particular country.")
	public void getMsgPricing() {
		res = msgRequest.getCountryPricing(authID, authToken, "US");
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 200);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		String priceRate = js.get("message.outbound.rate");
		Assert.assertEquals(msgRate, priceRate, "Mismatch in pricing and message charges!!");
	}

	@Test(dependsOnMethods = { "getInitialCashCredit", "getMsgPricing" }, description="Check that correct amount is deducted from Account.")
	public void validateCashCreditDeduction() throws ParseException {
		res = msgRequest.getAccountDetails(authID, authToken);
		js = APIUtils.responseToJsonPath(res);
		apiVerify.checkStatusCode(res, 200);
		apiVerify.checkContentType(res, ContentType.JSON.toString());
		String afterCashCredit = js.get("cash_credits");
		log.info("After msg cash credits: " + afterCashCredit);
		double initialCash = Double.parseDouble(initialCashCredit);
		double afterCash = Double.parseDouble(afterCashCredit);
		double amountDeducted = initialCash - afterCash;
		double amountDeductedroundedOff = APIUtils.doubleRoundOff(amountDeducted, "0.00000");
		log.info("Amount deducted: " + amountDeductedroundedOff);
		Assert.assertEquals(Double.parseDouble(msgRate), amountDeductedroundedOff, "Mismatch in Cash credits deductions!!");
	}
}
