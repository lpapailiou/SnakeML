package main.configuration;

import java.util.Set;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * is used in the GameAdapter class.
 */
public interface IGameAdapterConfigReader {

  /**
   * This method allows access to all methods provided in the IGameAdapterConfigReader interface.
   *
   * @return IGameAdapterConfigReader
   */
  static IGameAdapterConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the input node selection as a list of Integer. The input nodes are represented as enum
   * ordinals.
   *
   * @return the input node selection
   */
  Set<Integer> getInputNodeSelection();

}
