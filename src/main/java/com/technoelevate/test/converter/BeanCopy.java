package com.technoelevate.test.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class BeanCopy {

	private static ObjectMapper mapper = new ObjectMapper();

	private static Gson gson = new Gson();

	public static <T> T jsonProperties(String json, Class<T> valueType) {
		try {
			return gson.fromJson(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonProperties(String json, TypeReference<T> valueTypeRef) {
		try {
			return gson.fromJson(json, valueTypeRef.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T, E> T objectProperties(E value, Class<T> valueType) {
		try {
			return gson.fromJson(mapper.writeValueAsString(value), valueType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T, E> T objectProperties(E value, TypeReference<T> valueTypeRef) {
		try {
			return gson.fromJson(mapper.writeValueAsString(value), valueTypeRef.getType());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private BeanCopy() {
	}
}
