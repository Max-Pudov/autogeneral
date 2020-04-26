package au.com.autogeneral.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemNotFoundError {
  private String[] details;
  private String name;
}