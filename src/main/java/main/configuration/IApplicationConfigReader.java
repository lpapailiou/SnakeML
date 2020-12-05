package main.configuration;

public interface IApplicationConfigReader {

  static IApplicationConfigReader getInstance() {
    return Config.getInstance();
  }

  Mode getMode();

}
