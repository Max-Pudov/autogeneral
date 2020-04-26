package au.com.autogeneral.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import au.com.autogeneral.domain.Todo;
import au.com.autogeneral.dto.ToDoItem;
import au.com.autogeneral.dto.ToDoItemUpdateRequest;
import au.com.autogeneral.dto.TodoItemAddRequest;
import au.com.autogeneral.exception.TodoNotFoundException;
import au.com.autogeneral.repository.TodoRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

  @Mock
  private TodoRepository repository;

  @InjectMocks
  private TodoService todoService;

  @Test
  void testCreateTodo() {
    //GIVEN
    Todo todo = createTodoMock(1, "test todo");
    when(repository.add(any())).thenReturn(todo);

    //WHEN
    ToDoItem todoItem = todoService.createTodo(new TodoItemAddRequest("test todo"));

    //THEN
    compareTodos(todo, todoItem);
  }

  @Test
  public void testGet() {
    //GIVEN
    Todo todo = createTodoMock(1, "test todo");
    when(repository.find(1)).thenReturn(todo);

    //WHEN
    ToDoItem todoItem = todoService.getTodo(1);

    //THEN
    compareTodos(todo, todoItem);
  }

  @Test
  public void testUpdate() {
    //GIVEN
    Todo oldTodo = createTodoMock(1, "test todo");
    Todo updatedTodo = createTodoMock(1, "update todo");
    updatedTodo.setCreatedAt(oldTodo.getCreatedAt());

    when(repository.find(1)).thenReturn(oldTodo);
    when(repository.update(updatedTodo)).thenReturn(1);

    ToDoItemUpdateRequest update = new ToDoItemUpdateRequest("update todo", true);

    //WHEN
    ToDoItem todoItem = todoService.updateTodo(1, update);

    //THEN
    compareTodos(updatedTodo, todoItem);
  }

  @Test
  public void testGetWithWrongId() {
    //GIVEN
    when(repository.find(0)).thenReturn(null);

    //THEN
    assertThrows(TodoNotFoundException.class, () -> todoService.getTodo(0));
  }

  @Test
  public void testUpdateWrongId() {
    //GIVEN
    Todo todo = createTodoMock(0, "unknown todo");
    when(repository.find(0)).thenReturn(null);
    ToDoItemUpdateRequest update = new ToDoItemUpdateRequest("update todo", true);

    //THEN
    assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(0, update));
  }

  private void compareTodos(Todo todo, ToDoItem todoItem) {
    assertThat(todo.getId()).isEqualTo(todoItem.getId());
    assertThat(todo.getText()).isEqualTo(todoItem.getText());
    assertThat(todo.isCompleted()).isEqualTo(todoItem.isCompleted());
    assertThat(todo.getCreatedAt()).isEqualTo(todoItem.getCreatedAt());
  }

  private Todo createTodoMock(int i, String s) {
    return new Todo(i, s, true, Instant.now());
  }
}
