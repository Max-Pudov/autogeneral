package au.com.autogeneral.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.com.autogeneral.dto.BalanceTestResult;
import au.com.autogeneral.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

  private static final String LONG_INPUT_101 = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

  @SpyBean
  private TaskService taskService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @ParameterizedTest
  @ValueSource(strings = { "", LONG_INPUT_101})
  void testInvalidInput(String input) throws Exception {
    mvc.perform(get("/test/1.0/tasks/validateBrackets").param("input", input))
          .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @CsvSource({"[{()}], true", "a[]b{c(d)e}, true", "[{()}, false", "}%{, false"})
  void testValidInput(String input, boolean balanced) throws Exception {
    String response = mvc.perform(get("/test/1.0/tasks/validateBrackets").param("input", input))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    BalanceTestResult balanceTestResult = objectMapper.readValue(response, BalanceTestResult.class);
    assertThat(balanceTestResult.isBalanced()).isEqualTo(balanced);
  }
}
