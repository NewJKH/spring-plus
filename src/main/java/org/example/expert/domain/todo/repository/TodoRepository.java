package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Query("SELECT t " +
            "FROM Todo t " +
            "LEFT JOIN FETCH t.user u " +
            "WHERE t.modifiedAt >= :startAt AND t.modifiedAt <= :endAt "+
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(@Param("startAt") LocalDateTime startAt,
                                              @Param("endAt") LocalDateTime endAt,
                                              Pageable pageable);

    @Query("SELECT t " +
            "FROM Todo t " +
            "LEFT JOIN FETCH t.user u " +
            "WHERE t.weather = :weather AND t.modifiedAt >= :startAt AND t.modifiedAt <= :endAt " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findByWeatherOrderByModifiedAtDesc(@Param("weather") String weather,
                                                  @Param("startAt")  LocalDateTime startAt,
                                                  @Param("endAt") LocalDateTime endAt,
                                                  Pageable pageable);
}
