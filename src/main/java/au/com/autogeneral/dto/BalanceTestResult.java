package au.com.autogeneral.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceTestResult {
  private String input;
  private boolean isBalanced;
}