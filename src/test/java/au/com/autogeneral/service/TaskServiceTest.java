package au.com.autogeneral.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @InjectMocks
  private TaskService taskService;

  @ParameterizedTest
  @ValueSource(strings = {"{%}", "[a]", "(123)", "b{3(4[6]7)8}c"})
  void testValidBrackets(String input) {
    assertThat(taskService.isBalanced(input)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(strings = {"{%", "[b(]c", "d(e)f[", "g{([])}]h", "{[}]"})
  void testInvalidBrackets(String input) {
    assertThat(taskService.isBalanced(input)).isFalse();
  }
}