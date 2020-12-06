package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * is used in the InputNode enum.
 */
public interface INodeConfigReader {

  /**
   * This method allows access to all methods provided in the INodeConfigReader interface.
   *
   * @return INodeConfigReader
   */
  static INodeConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the current board width.
   *
   * @return the board width
   */
  int getBoardWidth();

  /**
   * Returns the current board height.
   *
   * @return the board height
   */
  int getBoardHeight();

}
