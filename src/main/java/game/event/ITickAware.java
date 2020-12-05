package game.event;

/**
 * This interface allows to implement an action to be taken by a specific tick frequency.
 */
public interface ITickAware {

  /**
   * The action implemented will be executed periodically, depending on the chosen tick frequency.
   */
  void onTick();

}
