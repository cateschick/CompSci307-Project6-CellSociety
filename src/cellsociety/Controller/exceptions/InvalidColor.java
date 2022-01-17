package cellsociety.Controller.exceptions;

/**
 * Represents an incorrect color passed into the parser
 *
 * @author Kyle White
 */
public class InvalidColor extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public InvalidColor(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create exception based on a caught exception with a different message.
   */
  public InvalidColor(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create exception based on a caught exception, with no additional message.
   */
  public InvalidColor(Throwable cause) {
    super(cause);
  }
}

