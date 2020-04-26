package au.com.autogeneral.exception;

public class TodoNotFoundException extends RuntimeException {

  private final int id;

  public TodoNotFoundException(int id) {
    this.id = id;
  }

  @Override
  public String getMessage() {
    return "Item with " + id + " not found";
  }
}