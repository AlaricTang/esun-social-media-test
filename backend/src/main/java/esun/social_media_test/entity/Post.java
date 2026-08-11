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
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "image", length = 255)
	private String image;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

}