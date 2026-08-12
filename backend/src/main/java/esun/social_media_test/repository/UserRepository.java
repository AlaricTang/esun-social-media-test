package esun.social_media_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import esun.social_media_test.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/** 註冊User Stored Procedure */
	@Modifying
	@Query(value = "CALL register_user(:mobile, :userName, :email, :passwordHash, :salt)", nativeQuery = true)
	void registerUser(
        @Param("mobile") String mobile,
        @Param("userName") String userName,
        @Param("email") String email,
        @Param("passwordHash") String passwordHash,
        @Param("salt") String salt
    );

	/** User查詢 by手機 Stored Procedure */
	// 2. 使用原生 CALL 語法呼叫依手機查詢 Stored Procedure
	@Query(value = "CALL get_user_by_mobile(:mobile)", nativeQuery = true)
	Optional<User> getUserByMobile(@Param("mobile") String mobile);

	// 補充：JPA 原生提供的方法，用作雙重驗證
	Optional<User> findByMobile(String mobile);
}