package au.com.autogeneral.repository;

import au.com.autogeneral.domain.Todo;
import au.com.autogeneral.exception.TodoNotFoundException;
import java.sql.Timestamp;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert simpleJdbcInsert;

  public TodoRepository(DataSource dataSource) {
    this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
        .usingColumns("text", "is_completed", "created_at")
        .usingGeneratedKeyColumns("id")
        .withTableName("autogeneral.todo");
  }

  public Todo find(int id) {
    String sql =  "SELECT * FROM autogeneral.todo WHERE id = :id";
    ResultSetExtractor<Todo> rowMapper = rs -> {
      if (rs.next()) {
        return new Todo(
            rs.getInt("id"),
            rs.getString("text"),
            rs.getBoolean("is_completed"),
            rs.getTimestamp("created_at").toInstant()
        );
      } else {
        return null;
      }
    };
    return jdbcTemplate.query(sql, Map.of("id", id), rowMapper);
  }

  public Todo add(Todo todo) {
    Map<String, Object> parameters = Map.of(
        "text", todo.getText(),
        "is_completed", todo.isCompleted(),
        "created_at", Timestamp.from(todo.getCreatedAt())
    );
    int id = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
    todo.setId(id);
    return todo;
  }

  public int update(Todo todo) {
    String sql =
        "UPDATE autogeneral.todo "
      + "SET text = :text, is_completed = :is_completed "
      + "WHERE id = :id";
    return jdbcTemplate.update(sql, Map.of(
        "id", todo.getId(),
        "text", todo.getText(),
        "is_completed", todo.isCompleted()
    ));
  }
}
