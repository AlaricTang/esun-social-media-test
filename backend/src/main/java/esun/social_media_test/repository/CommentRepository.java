package esun.social_media_test.repository;

import esun.social_media_test.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	/** 查出所有留言 by postId*/
    List<Comment> findByPostIdOrderByCreatedAtAsc(Integer postId);
}