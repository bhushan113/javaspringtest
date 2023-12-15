package com.technoelevate.test.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.test.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}