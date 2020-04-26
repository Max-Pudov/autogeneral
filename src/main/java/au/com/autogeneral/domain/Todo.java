package au.com.autogeneral.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
  private int id;
  private String text;
  private boolean isCompleted;
  private Instant createdAt;
}
