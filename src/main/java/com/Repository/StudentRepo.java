package com.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

	@Query("SELECT s FROM Student s WHERE s.deleted = false")
	List<Student> findAllActive();

	Boolean existsByEmail(String email);

	Optional<Student> findByIdAndDeletedFalse(Long id);

	List<Student> findAllByDeletedFalse();

}
