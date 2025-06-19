package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDTO;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void getEmptyTasksList() throws Exception {
        // Given
        when(dbService.getAllTasks()).thenReturn(Collections.emptyList());

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void getTaskList() throws Exception {
        // Given
        List<Task> tasks = List.of(
                new Task(1L, "test_title", "test_content"),
                new Task(2L, "test_title2", "test_content2")
        );

        List<TaskDTO> taskDTOs = List.of(
                new TaskDTO(1L, "test_title", "test_content"),
                new TaskDTO(2L, "test_title2", "test_content2")
        );

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDTOs);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("test_content")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("test_title2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("test_content2")));

    }

    @Test
    void getTaskById() throws Exception {
        // Given
        Task task = new Task(1L, "test_title", "test_content");
        TaskDTO taskDTO = new TaskDTO(1L, "test_title", "test_content");
        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDTO);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/v1/tasks/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test_content")));
    }

    @Test
    void testGetTaskWithWrongId() throws Exception {
        // Given
        when(dbService.getTask(999L)).thenThrow(TaskNotFoundException.class);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testCreateTask() throws Exception {
        // Given
        TaskDTO taskDTO = new TaskDTO(1L, "create_test_title", "create_test_content");
        Task task = new Task(1L, "create_test_title", "create_test_content");
        when(taskMapper.mapToTask(any(TaskDTO.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDTO);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(taskMapper, times(1)).mapToTask(any(TaskDTO.class));
        verify(dbService, times(1)).saveTask(task);
    }

    @Test
    void testUpdateTask() throws Exception {
        // Given
        TaskDTO taskDTO = new TaskDTO(1L, "update_test_title", "update_test_content");
        Task task = new Task(1L, "update_test_title", "update_test_content");
        when(taskMapper.mapToTask(any(TaskDTO.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDTO);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDTO);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("update_test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("update_test_content")));
    }

    @Test
    void testDeleteTask() throws Exception {
        // Given
        Long taskId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/{taskId}", taskId))
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(dbService, times(1)).deleteTask(taskId);
    }
}