package org.example.expert.domain.log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "log")
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue
    private Long id; // 로그 ID
    private String message; // 로그 내용
    private final LocalDateTime createdAt = LocalDateTime.now(); // 로그 시간

    public Log(String message) {
        this.message = message;
    }
}
