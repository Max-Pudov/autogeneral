package au.com.autogeneral.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.autogeneral.dto.ToDoItem;
import au.com.autogeneral.dto.ToDoItemUpdateRequest;
import au.com.autogeneral.dto.TodoItemAddRequest;
import au.com.autogeneral.exception.TodoNotFoundException;
import au.com.autogeneral.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TodoController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TodoControllerTest {

  private static final String LONG_INPUT_51 = "111111111111111111111111111111111111111111111111111";

  @MockBean
  private TodoService todoService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @ParameterizedTest
  @ValueSource(strings = { "", LONG_INPUT_51})
  void addInvalidTodo(String name) throws Exception {
    //THEN
    mvc.perform(
        post("/test/1.0/todo")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new TodoItemAddRequest(name)))
    ).andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = { "", LONG_INPUT_51})
  void patchInvalidTodo(String name) throws Exception {
    //THEN
    mvc.perform(
        patch("/test/1.0/todo/1")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new ToDoItemUpdateRequest(name, true)))
    ).andExpect(status().isBadRequest());
  }

  @Test
  void addValidTodo() throws Exception {
    //GIVEN
    Instant createdAt = Instant.now();
    ToDoItem todoItem = new ToDoItem(1, "test todo", false, createdAt);

    TodoItemAddRequest testTodo = new TodoItemAddRequest("test todo");

    when(todoService.createTodo(testTodo)).thenReturn(todoItem);

    //WHEN
    String response = mvc.perform(
        post("/test/1.0/todo")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testTodo)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ToDoItem responseTodoItem = objectMapper.readValue(response, ToDoItem.class);

    //THEN
    assertThat(todoItem).isEqualTo(responseTodoItem);
  }

  @Test
  void getMissingTodo() throws Exception {
    when(todoService.getTodo(1)).thenThrow(new TodoNotFoundException(1));
    mvc.perform(get("/test/1.0/todo/1")).andExpect(status().isNotFound());
  }

  @Test
  void getTodo() throws Exception {
    //GIVEN
    ToDoItem todoItem = new ToDoItem(1, "test todo", false, Instant.now());
    when(todoService.getTodo(1)).thenReturn(todoItem);

    //WHEN
    String response = mvc.perform(get("/test/1.0/todo/1"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    //THEN
    ToDoItem resultToDoItem = objectMapper.readValue(response, ToDoItem.class);
    assertThat(resultToDoItem).isEqualTo(todoItem);
  }

  @Test
  void patchTodo() throws Exception {
    //GIVEN
    ToDoItem todoItem = new ToDoItem(1, "updated todo", true, Instant.now());
    ToDoItemUpdateRequest updateRequest = new ToDoItemUpdateRequest("updated todo", true);
    when(todoService.updateTodo(1, updateRequest))
        .thenReturn(todoItem);

    //WHEN
    String response = mvc.perform(patch("/test/1.0/todo/1")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    ToDoItem resultToDoItem = objectMapper.readValue(response, ToDoItem.class);

    //THEN
    assertThat(resultToDoItem).isEqualTo(todoItem);
  }
}
