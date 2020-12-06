package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * is used in the Generation class.
 */
public interface IGenerationConfigReader {

  /**
   * This method allows access to all methods provided in the IGenerationConfigReader interface.
   *
   * @return IGenerationConfigReader
   */
  static IGenerationConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Gets the currently set population size.
   *
   * @return the population size
   */
  int getPopulationSize();

}
