package com.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.Repository.StudentRepo;
import com.entity.Student;
import com.exception.DuplicateEmailException;
import com.exception.StudentNotFoundException;

import jakarta.validation.Valid;

@Service
@Transactional
@Validated
public class StudentService {

	private final StudentRepo studentRepo;

	public StudentService(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Transactional
	public Student create(Student student) {
		if(studentRepo.existsByEmail(student.getEmail())) {
			throw new DuplicateEmailException("Email already exists: " +student.getEmail());
		}
		Student add = new Student();
		add.setName(student.getName());
		add.setEmail(student.getEmail());
		add.setAge(student.getAge());

		Student saved = studentRepo.save(add);

		return saved;
	}

	public List<Student> getAll(){
		return studentRepo.findAllByDeletedFalse();
	}

	public Student getById(Long id) {
		return studentRepo.findByIdAndDeletedFalse(id)
				.orElseThrow(() -> new StudentNotFoundException(("no stundent found with the given id:" + id)));
	}

	public Student updateStudent(@Valid Long id, Student updatedStudent) {
		Student existing = studentRepo.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

		existing.setName(updatedStudent.getName());
		existing.setAge(updatedStudent.getAge());
		existing.setEmail(updatedStudent.getEmail());
		existing.setUpdatedAt(LocalDateTime.now());

		return studentRepo.save(existing);
	}

	public void deleteStudent(Long id) {
		Student student = studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found"));
		student.setDeleted(true);
		studentRepo.save(student);

	}

}
