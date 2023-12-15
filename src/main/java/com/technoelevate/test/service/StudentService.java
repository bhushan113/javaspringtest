package com.technoelevate.test.service;

import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;

public interface StudentService {
	StudentDto saveStudent(StudentDto studentDto);
	
	StudentDto getStudent(Long stdId);
	
	BookDto getBook(Long bookId);
    StudentDto saveBook(Long stdId,BookDto bookDto);
}
