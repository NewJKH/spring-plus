package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.nickname = :nickname")
    Optional<User> findByNickname(@Param("nickname") String nickname);

    @Query(value = "SELECT id FROM users WHERE nickname = :nickname LIMIT 1", nativeQuery = true)
    Optional<Long> findIdFastest(@Param("nickname") String nickname);


    // CREATE INDEX idx_nickname_id ON users(nickname, id); 를 SQL 로 사용 후
    @Query(value = "SELECT * FROM users WHERE nickname = :nickname LIMIT 1", nativeQuery = true)
    Optional<User> findByNicknameFast(@Param("nickname") String nickname);

    @Query(value = "SELECT * FROM users WHERE id = :id LIMIT 1", nativeQuery = true)
    Optional<User> findByIdFast(@Param("id") Long id);
}
