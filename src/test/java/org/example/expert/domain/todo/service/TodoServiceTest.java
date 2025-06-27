package org.example.expert.domain.todo.service;

import org.example.expert.client.WeatherClient;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @Mock
    WeatherClient weatherClient;
    @InjectMocks
    TodoService todoService;

    @Test
    @DisplayName("복잡한_테스트_이름_포함_성공")
    void getTodos() {
        //given
        TodoSearchResponse response = new TodoSearchResponse("할 일입니다", 1, 5);
        Page<TodoSearchResponse> dummyPage = new PageImpl<>(List.of(response));
        Pageable pageable = PageRequest.of(0,10);

        when(todoRepository.searchTodosByCondition("할 일", "관리", null, pageable))
                .thenReturn(dummyPage);

        //when
        Page<TodoSearchResponse> result = todoService.getTodos("할 일", "관리", null, 1, 10);

        //then
        assertEquals(1, result.getTotalElements());
        assertEquals("할 일입니다", result.getContent().get(0).getTitle());
    }
}