package com.technoelevate.test.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class BookDto implements Serializable{
	private Long bookId;
	private String bookName;
	private List<StudentDto> students;
	
	@Override
	public String toString() {
		return "BookDto [bookId=" + bookId + ", bookName=" + bookName + "]";
	}

	public BookDto(String bookName, List<StudentDto> students) {
		this.bookName = bookName;
		this.students = students;
	}
	
	
}
