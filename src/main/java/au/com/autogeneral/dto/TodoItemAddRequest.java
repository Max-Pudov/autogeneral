package au.com.autogeneral.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoItemAddRequest {

  @NotBlank
  @Size(min = 1, max = 50)
  private String name;
}
