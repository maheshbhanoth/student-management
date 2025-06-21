package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
