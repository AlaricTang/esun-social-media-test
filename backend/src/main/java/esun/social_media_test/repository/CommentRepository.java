package esun.social_media_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import esun.social_media_test.dto.GetCommentDto;
import esun.social_media_test.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findByPostIdOrderByCreatedAtAsc(Integer postId);

	@Query("""
			SELECT c.commentId AS commentId,
			       c.userId AS userId,
			       u.userName AS userName,
			       c.postId AS postId,
			       c.content AS content,
			       c.createdAt AS createdAt
			FROM Comment c
			JOIN User u ON u.userId = c.userId
			WHERE c.postId = :postId
			ORDER BY c.createdAt ASC
			""")
	List<GetCommentDto> getCommentsWithUserByPostId(@Param("postId") Integer postId);
}
