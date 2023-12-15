package com.technoelevate.test.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.technoelevate.test.converter.BeanCopy;

@SpringBootTest
class StudentTest {

	private String studentAsString = "{\"stdId\":1,\"stdName\":\"Sahid Alom\",\"books\":[{\"bookId\":1,\"bookName\":\"Assamese\"}]}";
	
	private Gson gson = new Gson();

	@Test
	@Order(1)
	void studentSerialisation() throws JsonProcessingException {
		Student student2 = BeanCopy.jsonProperties(studentAsString, Student.class);
		assertThat(gson.toJson(student2)).isIn(studentAsString);
	}

	@Test
	@Order(2)
	void studentDeSerialisation() {
		Student student2 = BeanCopy.jsonProperties(studentAsString, Student.class);
		assertThat(student2.getStdName()).isEqualTo("Sahid Alom");
	}
}
