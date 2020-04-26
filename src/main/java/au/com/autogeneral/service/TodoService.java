package au.com.autogeneral.service;

import au.com.autogeneral.domain.Todo;
import au.com.autogeneral.dto.ToDoItem;
import au.com.autogeneral.dto.ToDoItemUpdateRequest;
import au.com.autogeneral.dto.TodoItemAddRequest;
import au.com.autogeneral.exception.TodoNotFoundException;
import au.com.autogeneral.repository.TodoRepository;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  public ToDoItem createTodo(TodoItemAddRequest todoItemAddRequest) {
    Todo newTodo = todoRepository.add(convert(todoItemAddRequest));
    return convert(newTodo);
  }

  public ToDoItem getTodo(int id) {
    Todo todo = todoRepository.find(id);
    if (todo == null) {
      throw new TodoNotFoundException(id);
    }
    return convert(todo);
  }

  public ToDoItem updateTodo(int id, ToDoItemUpdateRequest toDoItemUpdateRequest) {
    Todo todo = todoRepository.find(id);
    if (todo == null) {
      throw new TodoNotFoundException(id);
    }
    todo.setText(toDoItemUpdateRequest.getText());
    todo.setCompleted(toDoItemUpdateRequest.isCompleted());
    if (todoRepository.update(todo) == 0) {
      throw new TodoNotFoundException(id);
    }
    return convert(todo);
  }

  private ToDoItem convert(Todo newTodo) {
    return new ToDoItem(
        newTodo.getId(),
        newTodo.getText(),
        newTodo.isCompleted(),
        newTodo.getCreatedAt()
    );
  }

  private Todo convert(TodoItemAddRequest todoItemAddRequest) {
    return new Todo(
        0,
        todoItemAddRequest.getName(),
        false,
        Instant.now()
    );
  }
}