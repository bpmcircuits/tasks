package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMapper {

    public Task mapToTask(final TaskDTO taskDto) {
        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getContent());
    }

    public TaskDTO mapToTaskDto(final Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getContent());
    }

    public List<TaskDTO> mapToTaskDtoList(final List<Task> taskList) {
        return taskList.stream()
                .map(this::mapToTaskDto)
                .toList();
    }
}
