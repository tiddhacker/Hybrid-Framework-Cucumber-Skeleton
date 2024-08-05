package com.automation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import com.automation.constants.Constant;
//import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtils {
	
	public static void main(String[]args) throws ParseException, JSONException, IOException {
		
		JsonObject obj = getJsonObject("testJson");
		System.out.println(obj.toString());
		
	}

	public static JsonObject readJsonFile(String fileName) throws FileNotFoundException {
		File jsonInputFile;
		jsonInputFile = new File(Constant.JSON_FILE_DIR + fileName + ".json");
		InputStream is = new FileInputStream(jsonInputFile);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		JsonReader reader = Json.createReader(bReader);
		JsonObject jsonFileObject = reader.readObject();
		reader.close();
		return jsonFileObject;
	}

	public static synchronized JsonObject getJsonObject(String fileName) {

		JsonObject object = null;
		try {
			object = readJsonFile(fileName);

		} catch (Exception e) {
			Assert.fail("Failed to read JSON filename: " + fileName);
		}

		return object;
	}
	
	
}
