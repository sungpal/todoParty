package com.sparta.todoparty.todo.repository;

import com.sparta.todoparty.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}