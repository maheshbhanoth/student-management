package com.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Repository.StudentRepo;
import com.entity.Student;

@Service
public class StudentService {

	private final StudentRepo studentRepo;

	public StudentService(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

	public Student create(Student student) {
		return studentRepo.save(student);
	}

	public List<Student> getAll() {
		return studentRepo.findAll();
	}

	public Student getById(Long id) {
		return studentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException(("no stundent found with the given id:" + id)));
	}

	public Student updateStudent(Long id, Student updatedStudent) {
		Student existing = studentRepo.getById(id);
		existing.setName(updatedStudent.getName());
		existing.setAge(updatedStudent.getAge());
		existing.setEmail(updatedStudent.getEmail());

		return studentRepo.save(updatedStudent);
	}

	public void deleteStudent(Long id) {
		studentRepo.deleteById(id);
	}

}
