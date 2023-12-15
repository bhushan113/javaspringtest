/**
 * 
 */
package com.technoelevate.test.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;
import com.technoelevate.test.service.StudentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

	@MockBean
	private StudentService studentService;

	@Autowired
	private MockMvc mockMvc;

	private BookDto bookDto;

	private StudentDto studentDto;

	private ObjectMapper objectMapper;
	
	private String bookObjStr;
	
	private String studentObjStr;

	@BeforeEach
	void setUp() throws JsonProcessingException {
		bookDto = new BookDto(1l, "Assamese", Arrays.asList(new StudentDto(1l, "Sahid Alom", null)));
		studentDto = new StudentDto(1l, "Sahid Alom", Arrays.asList(new BookDto(1l, "Assamese", null)));
		objectMapper = new ObjectMapper();
		bookObjStr = objectMapper.writeValueAsString(bookDto);
		studentObjStr= objectMapper.writeValueAsString(studentDto);
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.controller.StudentController#(com.technoelevate.test.dto.StudentDto)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddStudentStudentDto() throws Exception {
		Mockito.when(studentService.saveStudent(Mockito.any())).thenReturn(studentDto);

		mockMvc.perform(post("/api/v1/student").contentType(MediaType.APPLICATION_JSON)
				.content(studentObjStr)).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.error", Matchers.is(false)))
				.andExpect(jsonPath("$.message", Matchers.is("")))
				.andExpect(jsonPath("$.data").value(studentDto));
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.controller.StudentController#(java.lang.Long)}.
	 * 
	 * @throws Exception
	 * @throws JsonProcessingException
	 */
	@Test
	void testGetStudentLong() throws JsonProcessingException, Exception {
		Mockito.when(studentService.getStudent(Mockito.any())).thenReturn(studentDto);
		
		mockMvc.perform(get("/api/v1/student/{stdId}", studentDto.getStdId()))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.error", Matchers.is(false)))
			.andExpect(jsonPath("$.message", Matchers.is("")))
			.andExpect(jsonPath("$.data").value(studentDto));
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.controller.StudentController#(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetBook() throws Exception {
		Mockito.when(studentService.getBook(Mockito.anyLong())).thenReturn(bookDto);

		mockMvc.perform(get("/api/v1/book/{bookId}", bookDto.getBookId()))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.error", Matchers.is(false)))
				.andExpect(jsonPath("$.message", Matchers.is("")))
				.andExpect(jsonPath("$.data").value(bookDto));
//	        .andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh")));
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.controller.StudentController#addBook(java.lang.Long, com.technoelevate.test.dto.BookDto)}.
	 * 
	 * @throws Exception
	 * @throws JsonProcessingException
	 */
	@Test
	void testAddBook() throws JsonProcessingException, Exception {
		Mockito.when(studentService.saveBook(Mockito.any(), Mockito.any())).thenReturn(studentDto);
		
		mockMvc.perform(post("/api/v1/book/{bookId}", studentDto.getStdId()).contentType(MediaType.APPLICATION_JSON)
				.content(bookObjStr)).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.error", Matchers.is(false)))
				.andExpect(jsonPath("$.message", Matchers.is("")))
				.andExpect(jsonPath("$.data").value(studentDto));
	}

}
