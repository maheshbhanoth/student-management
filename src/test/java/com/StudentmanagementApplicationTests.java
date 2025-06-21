package com;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Repository.StudentRepo;
import com.Service.StudentService;
import com.entity.Student;

@ExtendWith(MockitoExtension.class)
class StudentmanagementApplicationTests {

	@Mock
	private StudentRepo studentRepo;

	@InjectMocks
	private StudentService studentService;

	private Student sampleStudent;

	@BeforeEach
	public void setup() {
		sampleStudent = new Student(1L, "Alice", 20, "alice@example.com");
	}

	@Test
	public void getByStudentIdTest() {
		when(studentRepo.findById(1L)).thenReturn(Optional.of(sampleStudent));
		Student result=studentService.getById(1L);
		
		assertThat(result.getId()).isEqualTo(1L);
		verify(studentRepo).findById(1L);
		
	}

	@Test
	public void createStudentTest() {

		when(studentRepo.save(any(Student.class))).thenReturn(sampleStudent);
		
		Student result=studentService.create(new Student(null,"Alice", 20, "alice@example.com"));
		
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("Alice");
		verify(studentRepo, times(1)).save(any(Student.class));
	}

	@Test
	public void getAllStudents() {
		when(studentRepo.findAll()).thenReturn(List.of(sampleStudent));
		
		List<Student> list=studentService.getAll();
		
		assertThat(list).hasSize(1).contains(sampleStudent);
		verify(studentRepo).findAll();
	}

	@Test
	public void getStudentByIdMissing() {
		when(studentRepo.findById(2L)).thenReturn(Optional.empty());
		
		assertThatThrownBy(()-> studentService.getById(2L))
		.isInstanceOf(RuntimeException.class)
		.hasMessageContaining("no student found");
	}

	@Test
	public void deleteStudentTest() {
		doNothing().when(studentRepo).deleteById(1L);
		studentService.deleteStudent(1L);
		verify(studentRepo).deleteById(1L);
	}
}
