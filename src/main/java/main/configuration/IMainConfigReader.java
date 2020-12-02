package main.configuration;

public interface IMainConfigReader {

  static IMainConfigReader getInstance() {
    return Config.getInstance();
  }

  Theme getTheme();

}
