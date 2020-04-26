package au.com.autogeneral.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemUpdateRequest {

  @NotBlank
  @Size(min = 1, max = 50)
  private String text;

  @NotNull
  @JsonProperty("isCompleted")
  private boolean isCompleted;
}
