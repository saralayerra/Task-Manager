package com.example.taskmanager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(nextId.getAndIncrement(), "Learn Maven", false));
        tasks.add(new Task(nextId.getAndIncrement(), "Deploy app to EC2", false));
    }

    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title is required");
        }

        Task newTask = new Task(
                nextId.getAndIncrement(),
                task.getTitle().trim(),
                task.isCompleted()
        );
        tasks.add(newTask);
        return newTask;
    }
}
