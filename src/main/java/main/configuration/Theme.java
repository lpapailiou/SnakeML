package main.configuration;

import javafx.scene.paint.Color;

public enum Theme {

  CLASSIC(Color.LIME, Color.LIME, Color.RED, Color.RED, Color.BLACK, Color.WHITE, Color.RED, "themeClassic.css"),
  SANDY(Color.SIENNA, Color.SIENNA, Color.RED, Color.RED, Color.BURLYWOOD, Color.WHITE, Color.RED, "themeSandy.css"),
  MINIMAL(Color.BLACK, Color.DARKGRAY, Color.MEDIUMVIOLETRED, Color.MEDIUMVIOLETRED, Color.WHITE, Color.WHITE, Color.RED, "themeMinimal.css");

  private Color snakeBodyColor;
  private Color frameActiveColor;
  private Color frameInactiveColor;
  private Color foodColor;
  private Color backgroundColor;
  private Color scoreFontColor;
  private Color gameOverFontColor;
  private String css;

  Theme(Color snakeBodyColor, Color frameActiveColor, Color frameInactiveColor, Color foodColor, Color backgroundColor, Color scoreFontColor, Color gameOverFontColor, String css) {
    this.snakeBodyColor = snakeBodyColor;
    this.frameActiveColor = frameActiveColor;
    this.frameInactiveColor = frameInactiveColor;
    this.foodColor = foodColor;
    this.backgroundColor = backgroundColor;
    this.scoreFontColor = scoreFontColor;
    this.gameOverFontColor = gameOverFontColor;
    this.css = css;
  }

  public Color getSnakeBodyColor() {
    return snakeBodyColor;
  }

  public Color getFrameActiveColor() {
    return frameActiveColor;
  }

  public Color getFrameInactiveColor() {
    return frameInactiveColor;
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

  public String getCss() {
    return css;
  }

}
