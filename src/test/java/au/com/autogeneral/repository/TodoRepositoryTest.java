package au.com.autogeneral.repository;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.autogeneral.config.RepositoryTestConfig;
import au.com.autogeneral.domain.Todo;
import java.time.Instant;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(value = {RepositoryTestConfig.class})
public class TodoRepositoryTest {

  @Autowired
  private DataSource dataSource;

  private TodoRepository eventsRepository;

  @BeforeEach
  void init() {
    eventsRepository = new TodoRepository(dataSource);
  }

  @Test
  public void testAddAndFindTodo() {
    //GIVEN
    Todo draft = new Todo(0, "my todo", false, Instant.parse("2017-10-13T01:50:58.735Z"));

    //WHEN
    Todo createdTodo = eventsRepository.add(draft);
    Todo foundTodo = eventsRepository.find(createdTodo.getId());

    //THEN
    assertThat(createdTodo).isEqualTo(foundTodo);
  }

  @Test
  public void testUpdateTodo() {
    //GIVEN
    Todo draft = new Todo(0, "my todo", false, Instant.parse("2017-10-13T01:50:58.735Z"));
    Todo todo = eventsRepository.add(draft);
    todo.setCompleted(true);
    todo.setText("updated todo");

    //WHEN
    eventsRepository.update(todo);
    Todo updatedTodo = eventsRepository.find(todo.getId());

    //THEN
    assertThat(updatedTodo.getText()).isEqualTo("updated todo");
    assertThat(updatedTodo.isCompleted()).isEqualTo(true);
  }

  @Test
  public void testGetWithWrongId() {
    assertThat(eventsRepository.find(0)).isNull();
  }

  @Test
  public void testUpdateWrongId() {
    Todo updatedTodo = new Todo(0, "unknown todo", true, Instant.now());
    assertThat(eventsRepository.update(updatedTodo)).isZero();
  }
}