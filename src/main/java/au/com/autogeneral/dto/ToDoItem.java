package au.com.autogeneral.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItem {
  private int id;
  private String text;
  @JsonProperty("isCompleted")
  private boolean isCompleted;
  private Instant createdAt;
}