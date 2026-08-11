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
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer commentId;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "post_id", nullable = false)
	private Integer postId;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	/** */
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

}