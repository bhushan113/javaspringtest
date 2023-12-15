package com.technoelevate.test.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@SuppressWarnings("serial")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student_info")
@JsonInclude(value = Include.NON_DEFAULT)
public class Student implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stdId;
	private String stdName;

	@ManyToMany( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "student_book", joinColumns = @JoinColumn(name = "stdId", referencedColumnName = "stdId"), inverseJoinColumns = @JoinColumn(name = "bookId", referencedColumnName = "bookId"))
	@JsonManagedReference
	
	private List<Book> books;

}
