package main.configuration;

public interface ISnakeConfigReader {

  static ISnakeConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  int getSnakeTimeout();

}
