package main.configuration;

import javafx.scene.paint.Color;

public enum ColorScheme {

  NORMAL(Color.SIENNA, Color.RED, Color.BURLYWOOD, Color.WHITE, Color.RED);   // TODO: fancy naming for our color schemes?

  private Color snakeBodyColor;
  private Color foodColor;
  private Color backgroundColor;
  private Color scoreFontColor;
  private Color gameOverFontColor;

  ColorScheme(Color snakeBodyColor, Color foodColor, Color backgroundColor, Color scoreFontColor, Color gameOverFontColor) {
    this.snakeBodyColor = snakeBodyColor;
    this.foodColor = foodColor;
    this.backgroundColor = backgroundColor;
    this.scoreFontColor = scoreFontColor;
    this.gameOverFontColor = gameOverFontColor;
  }

  public Color getSnakeBodyColor() {
    return snakeBodyColor;
  }

  public Color getFoodColor() {
    return foodColor;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Color getScoreFontColor() {
    return scoreFontColor;
  }

  public Color getGameOverFontColor() {
    return gameOverFontColor;
  }

}
