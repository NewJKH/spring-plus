package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

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
}
