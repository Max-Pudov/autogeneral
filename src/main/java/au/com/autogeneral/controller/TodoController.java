package au.com.autogeneral.controller;

import static au.com.autogeneral.constants.ApplicationConstants.API_PREFIX;

import au.com.autogeneral.dto.BalanceTestResult;
import au.com.autogeneral.dto.ToDoItem;
import au.com.autogeneral.dto.ToDoItemUpdateRequest;
import au.com.autogeneral.dto.TodoItemAddRequest;
import au.com.autogeneral.error.ToDoItemNotFoundError;
import au.com.autogeneral.error.ToDoItemValidationError;
import au.com.autogeneral.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PREFIX)
@Validated
@Tag(name = "todo", description = "To Do List endpoints")
public class TodoController {

  @Autowired
  private TodoService todoService;

  @Operation(
      summary = "Create a to do item",
      tags = { "todo" }
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = @Content(schema = @Schema(implementation = BalanceTestResult.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Validation error",
          content = @Content(schema = @Schema(implementation = ToDoItemValidationError.class))
      )
  })
  @PostMapping(
      value = "/todo",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ToDoItem createTodo(@Valid @RequestBody TodoItemAddRequest todoRequestDto) {
    ToDoItem todo = todoService.createTodo(todoRequestDto);
    return todo;
  }

  @Operation(
      summary = "Create a to do item",
      tags = { "todo" }
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = @Content(schema = @Schema(implementation = BalanceTestResult.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Validation error",
          content = @Content(schema = @Schema(implementation = ToDoItemValidationError.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not Found Error",
          content = @Content(schema = @Schema(implementation = ToDoItemNotFoundError.class))
      ),
  })
  @GetMapping(path = "/todo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ToDoItem getTodo(@Parameter(description="id") @PathVariable("id") int id) {
    return todoService.getTodo(id);
  }

  @Operation(
      summary = "Modify an item",
      tags = { "todo" }
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = @Content(schema = @Schema(implementation = BalanceTestResult.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Validation error",
          content = @Content(schema = @Schema(implementation = ToDoItemValidationError.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not Found Error",
          content = @Content(schema = @Schema(implementation = ToDoItemNotFoundError.class))
      ),
  })
  @PatchMapping(
      path = "/todo/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ToDoItem patchTodo(
      @PathVariable("id") int id,
      @Valid @RequestBody ToDoItemUpdateRequest toDoItemUpdateRequest) {
    return todoService.updateTodo(id, toDoItemUpdateRequest);
  }
}