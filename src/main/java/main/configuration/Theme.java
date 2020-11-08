package main.configuration;

import javafx.scene.paint.Color;

public enum Theme {

  CLASSIC(Color.LIME, Color.LIME, Color.RED, Color.RED, Color.BLACK, Color.WHITE, Color.RED, true,  "themeClassic.css"),
  SANDY(Color.SIENNA, Color.SIENNA, Color.RED, Color.RED, Color.BURLYWOOD, Color.RED, Color.RED, true, "themeSandy.css"),
  SUNSET(Color.web("#ff7582"), Color.web("#ff7582"), Color.web("#c56d86"), Color.MOCCASIN, Color.web("#2a3950"), Color.RED, Color.RED, true,  "themeSunset.css"),
  CANDY(Color.web("#ffa7a7"), Color.web("#ffa7a7"), Color.web("#ffcbcb"), Color.MOCCASIN, Color.web("#c9fdff"), Color.RED, Color.RED, true, "themeCandy.css"),
  MINIMAL(Color.BLACK, Color.DARKGRAY, Color.MEDIUMVIOLETRED, Color.MEDIUMVIOLETRED, Color.WHITE, Color.RED, Color.RED, false, "themeMinimal.css");

  private Color snakeBodyColor;
  private Color frameActiveColor;
  private Color frameInactiveColor;
  private Color foodColor;
  private Color backgroundColor;
  private Color scoreFontColor;   // TODO: remove if not used
  private Color gameOverFontColor;  // TODO: remove if not used
  private boolean darkTheme;
  private String css;

  Theme(Color snakeBodyColor, Color frameActiveColor, Color frameInactiveColor, Color foodColor, Color backgroundColor, Color scoreFontColor, Color gameOverFontColor, boolean darkTheme, String css) {
    this.snakeBodyColor = snakeBodyColor;
    this.frameActiveColor = frameActiveColor;
    this.frameInactiveColor = frameInactiveColor;
    this.foodColor = foodColor;
    this.backgroundColor = backgroundColor;
    this.scoreFontColor = scoreFontColor;
    this.gameOverFontColor = gameOverFontColor;
    this.darkTheme = darkTheme;
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

  public boolean isDarkTheme() {
    return darkTheme;
  }

  public String getCss() {
    return css;
  }

}
