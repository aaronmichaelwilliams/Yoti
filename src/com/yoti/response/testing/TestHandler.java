package com.yoti.response.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TestHandler {

	private static File testScenarios;
	private static Scanner scanner;
	private static StringBuffer results;

	public static void main(String[] args) throws FileNotFoundException {
		results = new StringBuffer();
		results.append("\"Test Scenario\",\"Test Result\",\"Username\",\"Password\",\"Method\",\"Expected Response\",\"Application\",\"Domain\",\"URL\",\"Actual Response\",\"Request Body\",\"Response Body\"\n");
		testScenarios = new File("C:\\Yoti Rest Example\\Tests.txt");
		scanner = new Scanner(testScenarios);
		while (scanner.hasNextLine()) {
			try {
				if (scanner.next().equals("Test:")) {
					TestScenario testScenario = new TestScenario();
					testScenario.setTestID(scanner.nextInt());
					scanner.next();
					testScenario.setUserName(scanner.next());
					scanner.next();
					testScenario.setPassword(scanner.next());
					scanner.next();
					testScenario.setMethod(scanner.next());
					scanner.next();
					testScenario.setExpectedResponse(scanner.nextInt());
					scanner.next();
					testScenario.setApplicationName(scanner.next());
					scanner.next();
					testScenario.setPermissions(collectPermissions(scanner.next()));
					scanner.next();
					testScenario.setURL(scanner.next());
					scanner.next();
					testScenario.setDomain(scanner.next());
					String jsonOutput = outputStreamCreator(
							testScenario.getApplicationName(),
							testScenario.getDomain(),
							testScenario.getPermissions());
					testScenario.setRequestBody(jsonOutput);
					HttpDriver restTester = restTestCreator(testScenario.getURL());
					setCredentials(restTester, testScenario.getUserName(),
							testScenario.getPassword());
					restTester.setOutPutStream(jsonOutput);
					testScenario
							.setActualResponse(getActualResponseCode(restTester));
					testScenario.setResponseBody(inputResponse(restTester));
					appendResults(testScenario.getTestID(),
							testScenario.getMethod(), testScenario.getUserName(),
							testScenario.getPassword(),
							testScenario.getExpectedResponse(),
							testScenario.getApplicationName(),
							testScenario.getURL(),
							testScenario.getDomain(),
							testScenario.getActualResponse(),
							testScenario.getRequestBody(),
							testScenario.getResponseBody());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		writeResultsToCSV();
	}

	private static HttpDriver restTestCreator(String URL) {
		HttpDriver restTester = new HttpDriver();
		try {
			restTester.setUrl(URL);
			restTester.openConnection();
			restTester.setRequestMethod("PUT");
			restTester.setRequestProperty("application/json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return restTester;
	}

	private static void setCredentials(HttpDriver restTester, String userName,
			String password) {
		restTester.setAutherizationCredentials(userName, password);
	}

	@SuppressWarnings("unchecked")
	private static JSONArray collectPermissions(String permissionsInput) {
		JSONArray permissionsArray = new JSONArray();
		String[] permissionsSplit = permissionsInput.split(",");
		for (int i = 0; i < permissionsSplit.length; i++) {
			permissionsArray.add(permissionsSplit[i]);
		}
		return permissionsArray;
	}

	@SuppressWarnings("unchecked")
	private static String outputStreamCreator(String applicationName,
			String domain, JSONArray permissions) {
		JSONObject jsonString = new JSONObject();
		jsonString.put("permissions", permissions);
		jsonString.put("domain", domain);
		jsonString.put("name", applicationName);
		
		return jsonString.toString();
	}

	private static String inputResponse(HttpDriver restTester) {
		String response = restTester.getInputStream();
		return response;
	}

	private static int getActualResponseCode(HttpDriver restTester) {
		int responseCode = 0;
		try {
			responseCode = restTester.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseCode;
	}

	private static void appendResults(int testId, String method,
			String userName, String password, int expectedResponseCode,
			String applicationName, String domain, String URL, int actualResponseCode,
			String requestBody, String responseBody) {
		String testScenarioResult;
		if (expectedResponseCode == actualResponseCode) {
			testScenarioResult = "PASS";
		} else {
			testScenarioResult = "FAIL";
		}
		results.append("\"" + testId + "\",\"" + testScenarioResult + "\",\""
				+ userName + "\",\"" + password + "\",\"" + method + "\",\""
				+ expectedResponseCode + "\",\"" + applicationName + "\",\""
				+ domain + "\",\""
				+ URL + "\",\"" + actualResponseCode + "\",\""
				+ requestBody.replace("\"", "'") + "\",\""
				+ responseBody.replace("\"", "'") + "\"\n");
	}

	private static void writeResultsToCSV() {
		try {
			File resultsFile = new File(
					"C:\\Yoti Rest Example\\RestTestResults.csv");
			PrintStream printStream = new PrintStream(resultsFile);
			printStream.print(results.toString().replace("\\/", "/"));
			printStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}