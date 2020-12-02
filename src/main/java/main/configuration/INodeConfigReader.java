package main.configuration;

public interface INodeConfigReader {

  static INodeConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

}
