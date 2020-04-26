package au.com.autogeneral.controller;

import static au.com.autogeneral.constants.ApplicationConstants.API_PREFIX;

import au.com.autogeneral.dto.BalanceTestResult;
import au.com.autogeneral.error.ToDoItemValidationError;
import au.com.autogeneral.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PREFIX)
@Validated
@Tag(name = "tasks", description = "General algorithmic tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Operation(
      summary = "Checks if brackets in a string are balanced",
      description = "Brackets in a string are considered to be balanced if the following criteria are met:\n"
      + "        - For every opening bracket (i.e., **`(`**, **`{`**, or **`[`**), there is a matching closing bracket (i.e., **`)`**, **`}`**, or **`]`**) of the same type (i.e., **`(`** matches **`)`**, **`{`** matches **`}`**, and **`[`** matches **`]`**). An opening bracket must appear before (to the left of) its matching closing bracket. For example, **`]{}[`** is not balanced.\n"
      + "        - No unmatched braces lie between some pair of matched bracket. For example, **`({[]})`** is balanced, but **`{[}]`** and **`[{)]`** are not balanced.",
      tags = { "tasks" }
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "successful operation",
          content = @Content(schema = @Schema(implementation = BalanceTestResult.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "successful operation",
          content = @Content(schema = @Schema(implementation = ToDoItemValidationError.class))
      )
  })
  @GetMapping(
      path = "/tasks/validateBrackets",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public BalanceTestResult validateBrackets(
      @Parameter(description="Input string (max length 100)")
      @RequestParam("input")
      @NotBlank
      @Size(min = 1, max = 100)
      String input
  ) {
    return new BalanceTestResult(input, taskService.isBalanced(input));
  }
}
