package com.bezkoder.springjwt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.Task;
import com.bezkoder.springjwt.repository.TaskRepository;
import com.bezkoder.springjwt.repository.ProcessRepository;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class TaskController {
    @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private TaskRepository taskRepository;


  @GetMapping("/process/{processId}/tasks")
  public ResponseEntity<List<Task>> getAllCommentsByTutorialId(@PathVariable(value = "processId") Long processId) {
    if (!processRepository.existsById(processId)) {
      throw new ResourceNotFoundException("Not found Tutorial with id = " + processId);
    }

    List<Task> tasks = taskRepository.findByProcessId(processId);
    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity<Task> getTasksByProcessId(@PathVariable(value = "id") Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

    return new ResponseEntity<>(task, HttpStatus.OK);
  }


  @PostMapping("/process/{processId}/tasks")
  public ResponseEntity<List<Task>> createTasks(@PathVariable(value = "processId") Long processId,
      @RequestBody List<Task> taskRequests) {
    List<Task> tasks = processRepository.findById(processId).map(process -> {
      for(Task taskRequest : taskRequests) {
        taskRequest.setProcess(process);
        taskRepository.save(taskRequest);
      }
      return taskRequests;
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + processId));
  
    return new ResponseEntity<>(tasks, HttpStatus.CREATED);
  }
  
//update task by id
  @PutMapping("/tasks/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task taskRequest) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("TaskId " + id + "not found"));

    task.setTaskName(taskRequest.getTaskName());
    task.setTaskDescription(taskRequest.getTaskDescription());
    task.setTaskWla(taskRequest.getTaskWla());
    task.setTaskOwner(taskRequest.getTaskOwner());

    return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
  }


  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
    taskRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/processes/{processId}/tasks")
  public ResponseEntity<List<Task>> deleteAllTasksOfTutorial(@PathVariable(value = "processId") Long processId) {
    if (!processRepository.existsById(processId)) {
      throw new ResourceNotFoundException("Not found Tutorial with id = " + processId);
    }

    taskRepository.deleteByProcessId(processId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
