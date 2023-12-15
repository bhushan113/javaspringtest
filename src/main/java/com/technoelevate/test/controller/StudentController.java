package com.technoelevate.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;
import com.technoelevate.test.response.StudentResponse;
import com.technoelevate.test.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@PostMapping("student")
	public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentDto studentDto) {
//		studentService.saveStudent(studentDto);?
		return ResponseEntity.ok(new StudentResponse(false, "",studentService.saveStudent(studentDto)));
	}

	@GetMapping("student/{stdId}")
	public ResponseEntity<StudentResponse>  getStudent(@PathVariable Long stdId) {
		return ResponseEntity.ok(new StudentResponse(false, "", studentService.getStudent(stdId)));
	}
	
	@GetMapping("book/{bookId}")
	public ResponseEntity<StudentResponse>  getBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(new StudentResponse(false, "", studentService.getBook(bookId)));
	}
	
	@PostMapping("book/{studentId}")
	public ResponseEntity<StudentResponse>  addBook(@PathVariable Long studentId, @RequestBody BookDto bookId) {
		return ResponseEntity.ok(new StudentResponse(false, "", studentService.saveBook(studentId, bookId)));
	}
}
