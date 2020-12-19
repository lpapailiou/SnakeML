package ai.data.storage;

/**
 * This class is used to signalize errors during the deserialization of the pre-trained
 * artificial networks.
 */
class DeserializationException extends RuntimeException {

  DeserializationException(String s, Throwable throwable) {
    super(s, throwable);
  }

  DeserializationException(String s) {
    super(s);
  }
}
