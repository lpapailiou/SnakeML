package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the painter classes of ui.painter.
 */
public interface IPainterConfigReader {

  /**
   * This method allows access to all methods provided in the IPainterConfigReader interface.
   * @return IPainterConfigReader
   */
  static IPainterConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the current board width.
   * @return the board width
   */
  int getBoardWidth();

  /**
   * Returns the current board height.
   * @return the board height
   */
  int getBoardHeight();

  /**
   * Provides the currently selected theme.
   * @return the current theme
   */
  Theme getTheme();

}
