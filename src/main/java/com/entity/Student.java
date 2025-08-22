package com.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "name should not null")
	private String name;

	@Min(value = 18)
	private int age;

	@Column(unique = true, nullable = false)
	@Email(message = "please enter valid email")
	private String email;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(name = "is_deleted")
	private Boolean deleted = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean delete) {
		this.deleted = delete;
	}

	public Student(Long id, @NotBlank(message = "name should not null") String name, @Min(18) int age,
			@Email(message = "please enter valid email") String email, LocalDateTime createdAt, LocalDateTime updatedAt,
			Boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", delete=" + deleted + "]";
	}

	public Student() {
		super();
	}

}