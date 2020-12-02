package main.configuration;

public interface IPainterConfigReader {

  static IPainterConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  Theme getTheme();

}
