package com.technoelevate.test.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.test.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}