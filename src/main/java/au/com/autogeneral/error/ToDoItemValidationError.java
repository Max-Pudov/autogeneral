package au.com.autogeneral.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemValidationError {
  private ValidationErrorDetails[] details;
  private String name;
}