package esun.social_media_test.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import esun.social_media_test.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	/** 發文 Stored Procedure */
	@Modifying
    @Query(value = "CALL create_post(:userId, :content, :image)", nativeQuery = true)
    void createPost(
        @Param("userId") Integer userId,
        @Param("content") String content,
        @Param("image") String image
    );

	/** 取得所有發文 Stored Procedure */
	@Query(value = "CALL get_all_posts()", nativeQuery = true)
	List<Map<String, Object>> getAllPostsWithUser();
}