package com.example.todo_api.service.task;

import com.example.todo_api.repository.task.TaskRecord;
import com.example.todo_api.repository.task.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskEntity find(long taskId) {
    return taskRepository.select(taskId)
        .map(record -> new TaskEntity(record.getId(), record.getTitle()))
        .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
  }

  public List<TaskEntity> find() {
    return taskRepository.selectList()
        .stream()
        .map(record -> new TaskEntity(record.getId(), record.getTitle()))
        .collect(Collectors.toList());
  }

  public TaskEntity create(String title) {
    var record = new TaskRecord(null, title);
    taskRepository.insert(record);

    return new TaskEntity(record.getId(), record.getTitle());
  }
}
