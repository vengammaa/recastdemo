package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.recast.jpa.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

	@Query("SELECT t FROM PasswordResetToken t WHERE t.pwChanged = false and t.token = :token")
	public PasswordResetToken findActiveTokenByToken(@Param("token") String token);

	@Query("SELECT t FROM PasswordResetToken t WHERE t.token = :token")
	public PasswordResetToken findTokenByToken(@Param("token") String token);

	@Query("SELECT t FROM PasswordResetToken t WHERE t.pwChanged = false and t.user.userName = :userName")
	public List<PasswordResetToken> findActiveTokensByUser(@Param("userName") String userName);

}
