package com.example.todo_api.controller.task;

import com.example.todo_api.service.task.TaskEntity;
import com.example.todo_api.service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDTO;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDTO;
import java.net.URI;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

  private final TaskService taskService;

  @Override
  public ResponseEntity<TaskDTO> showTask(Long taskId) {
    var entity = taskService.find(taskId);

    var dto = toTaskDTO(entity);

    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<TaskDTO> createTask(TaskForm form) {
    var entity = taskService.create(form.getTitle());
    var dto = toTaskDTO(entity);
    return ResponseEntity.created(URI.create("/tasks/" + dto.getId())).body(dto);
  }

  @Override
  public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset) {
    var entityList = taskService.find(limit, offset);
    var dtoList = entityList.stream()
        .map(this::toTaskDTO)
        .collect(Collectors.toList());

    var pageDTO = new PageDTO();
    pageDTO.setLimit(limit);
    pageDTO.setOffset(offset);
    pageDTO.setSize(dtoList.size());

    var dto = new TaskListDTO();
    dto.setPage(pageDTO);
    dto.setResults(dtoList);
    return ResponseEntity.ok(dto);
  }

  /**
   * TaskDTOに変換します
   *
   * @param taskEntity taskForm or taskRecord
   * @return taskDTO
   */
  private TaskDTO toTaskDTO(TaskEntity taskEntity) {
    var taskDTO = new TaskDTO();
    taskDTO.setId(taskEntity.getId());
    taskDTO.setTitle(taskEntity.getTitle());
    return taskDTO;
  }
}
