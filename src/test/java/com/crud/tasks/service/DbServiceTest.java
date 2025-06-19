package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testGetEmptyTaskList() {
        // Given
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Task> tasks = dbService.getAllTasks();

        // Then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testGetTaskById() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "title", "description");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task foundTask = dbService.getTask(1L);

        // Then
        assertNotNull(foundTask);
        assertEquals(task.getTitle(), foundTask.getTitle());
    }

    @Test
    void testExceptionWithTaskNotFound() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        // When Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));
    }

    @Test
    void testSavingTask() {
        // Given
        Task task = new Task(1L, "title", "description");
        when(taskRepository.save(task)).thenReturn(task);

        // When
        Task savedTask = dbService.saveTask(task);

        // Then
        assertNotNull(savedTask);
        assertEquals(task.getTitle(), savedTask.getTitle());
    }

    @Test
    void testDeleteTask() {
        // Given
        Long taskId = 1L;

        // When
        dbService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}