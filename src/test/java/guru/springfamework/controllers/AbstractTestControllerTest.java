package guru.springfamework.controllers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTestControllerTest {
	public static String asJsonString(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static JsonNode readJsonNode(String jsonString) throws IOException {
		return new ObjectMapper().readTree(jsonString);
	}
}
