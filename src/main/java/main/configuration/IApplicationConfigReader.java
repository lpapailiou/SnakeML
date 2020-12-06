package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the ApplicationController class.
 */
public interface IApplicationConfigReader {

  /**
   * This method allows access to all methods provided in the IApplicationConfigReader interface.
   * @return IApplicationConfigReader
   */
  static IApplicationConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the currently selected mode (in terms of game logic).
   * @return the selected game mode
   */
  Mode getMode();

}
