package esun.social_media_test.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name", nullable = false, length = 50)
	private String userName;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "mobile", nullable = false, unique = true, length = 20)
	private String mobile;

	@Column(name = "salt", length = 64)
	private String salt;

	@Column(name = "biography", columnDefinition = "TEXT")
	private String biography;

	@Column(name = "cover_image", length = 255)
	private String coverImage;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

}
