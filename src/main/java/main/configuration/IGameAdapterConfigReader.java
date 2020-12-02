package main.configuration;

import java.util.Set;

public interface IGameAdapterConfigReader {

  static IGameAdapterConfigReader getInstance() {
    return Config.getInstance();
  }

  Set<Integer> getInputNodeSelection();

}
