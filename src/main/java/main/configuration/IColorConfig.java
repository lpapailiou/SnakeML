package main.configuration;

import javafx.scene.paint.Color;

public interface IColorConfig {

  int getBoardWidth();

  int getBoardHeight();

  Color getSnakeBodyColor();

  Color getFoodColor();

  Color getBackgroundColor();

  Color getScoreFontColor();

  Color getGameOverFontColor();

  Theme getTheme();

  void setTheme(Theme theme);
}
