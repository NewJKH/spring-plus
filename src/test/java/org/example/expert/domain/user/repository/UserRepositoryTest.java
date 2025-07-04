package org.example.expert.domain.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll에서 Autowired 사용 가능
public class UserRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    private final int TOTAL_USERS = 1_000_000;
    private final int BATCH_SIZE = 1000;

    @Test
    @Order(1)
    @Transactional
    @Commit
    void insertUsers() {
        for (long i = 0; i < TOTAL_USERS; i++) {
            String email = "test1234_" + i + "@test.com";
            String nickname = "user_" + i;

            User user = new User(i, email, nickname, email, UserRole.USER);
            em.persist(user);

            if (i % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }

        em.flush();
        em.clear();
    }

    @Test
    @Order(2)
    void searchNicknamePerformance() {
        long start = System.currentTimeMillis();
        Optional<User> result = userRepository.findByNickname("user_777777");
        long end = System.currentTimeMillis();

        System.out.println("개선2 시간 측정 값 : " + (end - start) + "ms");
        assertThat(result).isPresent();
    }
}