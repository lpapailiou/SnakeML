package ai.data.storage;

public class DeserializationException extends RuntimeException {

  public DeserializationException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public DeserializationException(String s) {
    super(s);
  }
}
