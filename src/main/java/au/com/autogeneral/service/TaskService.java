package au.com.autogeneral.service;

import java.util.LinkedList;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final Map<Character, Character> MATCHING_BRACKETS = Map.of(
      '}', '{',
      ']', '[',
      ')', '('
  );

  public boolean isBalanced(String input) {
    LinkedList<Character> brackets = new LinkedList<>();
    for (char ch: input.toCharArray()) {
      switch(ch) {
        case '{':
        case '[':
        case '(':
          brackets.addLast(ch);
          break;
        case '}':
        case ']':
        case ')':
          if (!brackets.isEmpty() && brackets.getLast() == MATCHING_BRACKETS.get(ch)) {
            brackets.removeLast();
          } else {
            return false;
          }
        default:
          break;
      }
    }
    return brackets.isEmpty();
  }
}
