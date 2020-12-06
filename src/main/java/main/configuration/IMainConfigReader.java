package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * is used in the Main class.
 */
public interface IMainConfigReader {

  /**
   * This method allows access to all methods provided in the IMainConfigReader interface.
   *
   * @return IMainConfigReader
   */
  static IMainConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Provides the currently selected theme.
   *
   * @return the current theme
   */
  Theme getTheme();

}
