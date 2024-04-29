package org.hgy.todospringweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
	@Id
	@UuidGenerator
	private String id;
	
	@Column(nullable = false)
	private String username; // 아이디로 쓸 유저네임
	private String password;
	private String role;// admin or 일반사용자
	private String authProvider; // OAuth에서 사용할 유저정보 제공자: github
}
