package com;

import static org.assertj.core.api.Assertions.assertThat; 
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
		sampleStudent = new Student(1L, "Alice", 20, "alice@example.com", LocalDateTime.now(), LocalDateTime.now(),
				false);
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
		
		Student result=studentService.create(sampleStudent);
		
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("Alice");
		verify(studentRepo, times(1)).save(any(Student.class));
	}

	@Test
	public void updateStudentSuccessTest() {
	    Long studentId = 1L;

	    Student existingStudent = new Student();
	    existingStudent.setId(studentId);
	    existingStudent.setName("Mahesh");
	    existingStudent.setAge(20);
	    existingStudent.setEmail("mahesh@gmail.com");

	    Student updatedStudent = new Student();
	    updatedStudent.setName("Mahi");
	    updatedStudent.setAge(22);
	    updatedStudent.setEmail("mahi@gmail.com");

	    when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));
	    when(studentRepo.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

	    Student result = studentService.updateStudent(studentId, updatedStudent);

	    assertThat(result.getName()).isEqualTo("Mahi");
	    assertThat(result.getAge()).isEqualTo(22);
	    assertThat(result.getEmail()).isEqualTo("mahi@gmail.com");
	    assertThat(result.getUpdatedAt()).isNotNull();

	    verify(studentRepo).findById(studentId);
	    verify(studentRepo).save(existingStudent);
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
		.hasMessageContaining("no stundent found with the given id:2");
	}

	@Test
	public void deleteStudentTest() {
		Student student = new Student(1L, "Mahesh", 23, "mahesh@gmail.com", LocalDateTime.now(), LocalDateTime.now(),
				false);
		when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
		when(studentRepo.save(any(Student.class))).thenReturn(student);
		studentService.deleteStudent(1L);

		assertThat(student.isDeleted()).isTrue();
		verify(studentRepo).findById(1L);
		verify(studentRepo).save(student);
	}
	
	@Test
	public void deleteStudentMissingTest(){
		when(studentRepo.findById(2L)).thenReturn(Optional.empty());
		assertThatThrownBy(()-> studentService.deleteStudent(2L))
		.isInstanceOf(RuntimeException.class)
		.hasMessageContaining("Student not found");
		verify(studentRepo, times(0)).save(any(Student.class));
	}
}
