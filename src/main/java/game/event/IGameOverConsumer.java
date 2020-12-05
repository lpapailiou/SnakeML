package game.event;

/**
 * A consumer allowing to consume a firede GameOver event.
 */
public interface IGameOverConsumer {

  /**
   * Handles a GameOver event.
   */
  void handle();

}
