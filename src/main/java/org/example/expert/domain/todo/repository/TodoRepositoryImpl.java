package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId){
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        return Optional.ofNullable(jpaQueryFactory
                .select(todo)
                .from(todo)
                .join(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId)).fetchOne());
    }

    public Page<TodoSearchResponse> searchTodosByCondition(String title, String managerName, LocalDateTime createdAt, Pageable pageable) {
        QTodo todo = QTodo.todo;
        QManager qManager = QManager.manager;
        QComment qComment = QComment.comment;

        BooleanBuilder where = new BooleanBuilder();
        if (title != null && !title.isBlank()) {
            where.and(todo.title.contains(title));
        }
        if (managerName != null && !managerName.isBlank()) {
            where.and(qManager.user.nickname.contains(managerName));
        }
        if (createdAt != null) {
            where.and(todo.createdAt.goe(createdAt));
        }

        List<TodoSearchResponse> results = jpaQueryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        qManager.countDistinct().intValue(),
                        qComment.countDistinct().intValue()
                ))
                .from(todo)
                .leftJoin(todo.managers,qManager)
                .leftJoin(todo.comments,qComment)
                .where(where)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results,pageable,results.size());

    }
}
