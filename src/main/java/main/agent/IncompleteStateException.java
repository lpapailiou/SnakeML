package main.agent;

/**
 * This class is used to signalize errors if an Agent has not been fully initialized
 * before it is being used.
 */
class IncompleteStateException extends RuntimeException {

  IncompleteStateException(String s) {
    super(s);
  }
}
