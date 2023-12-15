/**
 * 
 */
package com.technoelevate.test.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;
import com.technoelevate.test.entity.Book;
import com.technoelevate.test.entity.Student;
import com.technoelevate.test.exception.BooKNotFound;
import com.technoelevate.test.repository.BookRepository;
import com.technoelevate.test.repository.StudentRepository;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StudentServiceImplTest {

	@Autowired
	private StudentServiceImpl service;

	@MockBean
	private StudentRepository studentRepository;
	
	@MockBean
	private BookRepository bookRepository;
	
	private BookDto bookDto;

	private StudentDto studentDto;

	private ObjectMapper objectMapper;
	
	private String bookObjStr;
	
	private String studentObjStr;
	
	private Book book;
	

	@BeforeEach
	void setUp() throws JsonProcessingException {
		bookDto = new BookDto(1l, "Assamese", Arrays.asList(new StudentDto(1l, "Sahid Alom", null)));
		book = new Book(1l, "Assamese", Arrays.asList(new Student(1l, "Sahid Alom", null)));
		studentDto = new StudentDto(1l, "Sahid Alom", Arrays.asList(new BookDto(1l, "Assamese", null)));
		objectMapper = new ObjectMapper();
		bookObjStr = objectMapper.writeValueAsString(bookDto);
		studentObjStr= objectMapper.writeValueAsString(studentDto);
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.service.StudentServiceImpl#saveStudent(com.technoelevate.test.dto.StudentDto)}.
	 */
	@Test
	void testSaveStudent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.service.StudentServiceImpl#getStudent(java.lang.Long)}.
	 */
	@Test
	void testGetStudent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.service.StudentServiceImpl#saveBook(java.lang.Long, com.technoelevate.test.dto.BookDto)}.
	 */
	@Test
	void testSaveBook() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.service.StudentServiceImpl#getBook(java.lang.Long)}.
	 */
	@Test
	void testGetBook() {
		Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		
		BookDto book2 = service.getBook(book.getBookId());
		
		assertEquals(book2.getBookId(),book.getBookId());
	}
	
	@Test
	void testGetBookWhenStudentEmpty() {
		book.setStudents(Collections.emptyList());
		Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		
		BookDto book2 = service.getBook(book.getBookId());
		
		assertEquals(book2.getBookId(),book.getBookId());
	}
	
	@Test
	void testGetBookWhenEmptyBook() {
		long bookId=1l;
		Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(()->service.getBook(bookId)).isInstanceOf(BooKNotFound.class);
	}
	
	@Test
	void testGetBookThrow() {
		long bookId=1l;
		Mockito.when(bookRepository.findById(Mockito.anyLong())).thenThrow(BooKNotFound.class);
		
		assertThatThrownBy(()->service.getBook(bookId)).isInstanceOf(BooKNotFound.class);
	}

	/**
	 * Test method for
	 * {@link com.technoelevate.test.service.StudentServiceImpl#StudentServiceImpl(StudentRepository, BookRepository)}.
	 */
	@Test
	void testStudentServiceImpl() {
		fail("Not yet implemented");
	}

}
