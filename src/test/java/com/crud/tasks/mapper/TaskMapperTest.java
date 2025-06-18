package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void testMapToTask() {
        // Given
        TaskDTO taskDTO = new TaskDTO(1L, "Test Task", "This is a test task");

        // When
        Task task = taskMapper.mapToTask(taskDTO);

        // Then
        assertNotNull(task);
        assertEquals(taskDTO.getId(), task.getId());
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getContent(), task.getContent());
    }

    @Test
    void testMapToTaskDto() {
        // Given
        Task task = new Task(1L, "Test Task", "This is a test task");

        // When
        TaskDTO taskDTO = taskMapper.mapToTaskDto(task);

        // Then
        assertNotNull(taskDTO);
        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getContent(), taskDTO.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        // Given
        List<Task> taskList = List.of(
                new Task(1L, "Task 1", "Content 1"),
                new Task(2L, "Task 2", "Content 2")
        );

        // When
        List<TaskDTO> taskDTOList = taskMapper.mapToTaskDtoList(taskList);

        // Then
        assertNotNull(taskDTOList);
        assertEquals(2, taskDTOList.size());
        assertEquals("Task 1", taskDTOList.get(0).getTitle());
        assertEquals("Content 1", taskDTOList.get(0).getContent());
    }
}