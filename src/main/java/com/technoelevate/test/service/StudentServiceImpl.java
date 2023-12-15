package com.technoelevate.test.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technoelevate.test.converter.BeanCopy;
import com.technoelevate.test.dto.BookDto;
import com.technoelevate.test.dto.StudentDto;
import com.technoelevate.test.entity.Book;
import com.technoelevate.test.entity.Student;
import com.technoelevate.test.exception.BooKNotFound;
import com.technoelevate.test.repository.BookRepository;
import com.technoelevate.test.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	private final BookRepository bookRepository;

	@Override
	public StudentDto saveStudent(StudentDto studentDto) {
		return BeanCopy.objectProperties(studentRepository.save(BeanCopy.objectProperties(studentDto, Student.class)),
				StudentDto.class);
	}

	@Override
	public StudentDto getStudent(Long stdId) {
		Student student = studentRepository.findById(stdId).orElseThrow();
		return BeanCopy.objectProperties(student, StudentDto.class);
	}
	
	@Transactional
	@Override
	public StudentDto saveBook(Long stdId,BookDto bookDto) {
		
		Student student = studentRepository.findById(stdId).orElseThrow();
		Book book = BeanCopy.objectProperties(bookDto, Book.class);
		student.getBooks().add(book);
		return BeanCopy.objectProperties(student, StudentDto.class);
	}

	@Override
	public BookDto getBook(Long bookId) {
		try {
			Book book = bookRepository.findById(bookId).orElseThrow(()->new BooKNotFound("Book Not Found!!!"));
			BookDto bookDto = BeanCopy.objectProperties(book, BookDto.class);
			if (!book.getStudents().isEmpty())
				bookDto.setStudents(book.getStudents().stream().map(std -> {
					std.setBooks(null);
					return BeanCopy.objectProperties(std, StudentDto.class);
				}).collect(Collectors.toList()));
			return bookDto;
		} catch (BooKNotFound e) {
			throw e;
		}
	}

}