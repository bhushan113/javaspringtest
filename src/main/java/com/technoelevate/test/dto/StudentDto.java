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
public class StudentDto implements Serializable{
	private Long stdId;
	private String stdName;
	private List<BookDto> books;
	
	public StudentDto(String stdName, List<BookDto> books) {
		this.stdName = stdName;
		this.books = books;
	}
	
}
