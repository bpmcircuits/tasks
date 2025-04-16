package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping
    public TaskDto getTask(Long id) {
        return new TaskDto(1L, "Test title", "test content");
    }

    @DeleteMapping
    public void deleteTask(Long id) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited title", "Edited content");
    }

    @PostMapping
    public TaskDto createTask(TaskDto taskDto) {
        return null;
    }
}
