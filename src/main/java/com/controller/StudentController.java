package com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.StudentService;
import com.entity.Student;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/student")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping
	public ResponseEntity<Student> cerateStudent(@Valid @RequestBody Student stundent) {
		Student student = studentService.create(stundent);
		return ResponseEntity.ok(student);
	}

	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents() {
		return ResponseEntity.ok(studentService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@Valid @PathVariable Long id) {
		return ResponseEntity.ok(studentService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
		return ResponseEntity.ok(studentService.updateStudent(id, student));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}

}
