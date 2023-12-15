package com.technoelevate.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.technoelevate.test.converter.BeanCopy;
import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;
import com.technoelevate.test.entity.Student;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private StudentDto studentDto;
	
	private BookDto bookDto;
	
	@BeforeEach
	void setUp() throws JsonProcessingException {
		studentDto = new StudentDto("Sahin Alom", Arrays.asList(new BookDto("Assamese", null)));
		bookDto = new BookDto(1l, "Assamese", Arrays.asList(new StudentDto(1l, "Sahid Alom", null)));
	}
	
	@Test
	@Rollback(false)
	@Order(1)
	void addStudentTest() {
		Student student = BeanCopy.objectProperties(studentDto, Student.class);
		Student student2 = entityManager.persist(student);
		assertThat(student2.getStdId()).isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(2)
	void findByIdTest() {
		Student student = BeanCopy.objectProperties(studentDto, Student.class);
		Student student2 = entityManager.persist(student);
		Student student3 = studentRepository.findById(student2.getStdId()).orElseThrow();
		assertThat(student3).isIn(student2);
	}

}
