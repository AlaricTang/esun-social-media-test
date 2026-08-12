package esun.social_media_test.service.impl;

import java.util.List;
import java.util.Map;

public interface PostServiceImpl {

	/** 發布新文章 */
	void createPost(Integer userId, String content, String image);

	/** 取得所有文章 */
	List<Map<String, Object>> getAllPosts();
}
