package main.configuration;

import javafx.scene.paint.Color;

public enum Theme {

  CLASSIC(Color.LIME, Color.LIME, Color.RED, Color.RED, Color.BLACK, true,  "themeClassic.css"),
  SANDY(Color.web("#93826c"), Color.web("#93826c"), Color.web("#85a39f"), Color.web("#85a39f"), Color.web("#E7D1B5"), true, "themeSandy.css"),
  SUNSET(Color.web("#ff7582"), Color.web("#ff7582"), Color.web("#c56d86"), Color.MOCCASIN, Color.web("#2a3950"), true,  "themeSunset.css"),
  CANDY(Color.web("#ffa7a7"), Color.web("#ffa7a7"), Color.web("#ffcbcb"), Color.MOCCASIN, Color.web("#c9fdff"), true, "themeCandy.css"),    // TODO: rename to cancer?
  MINIMAL(Color.BLACK, Color.DARKGRAY, Color.MEDIUMVIOLETRED, Color.MEDIUMVIOLETRED, Color.WHITE, false, "themeMinimal.css"),
  YB(Color.web("#F9CC11"), Color.web("#F9CC11"), Color.web("#F9CC11"), Color.web("#ffffff"), Color.BLACK, true,  "themeYoungBoys.css");

  private Color snakeBodyColor;
  private Color frameActiveColor;
  private Color frameInactiveColor;
  private Color foodColor;
  private Color backgroundColor;
  private boolean darkTheme;
  private String css;

  Theme(Color snakeBodyColor, Color frameActiveColor, Color frameInactiveColor, Color foodColor, Color backgroundColor, boolean darkTheme, String css) {
    this.snakeBodyColor = snakeBodyColor;
    this.frameActiveColor = frameActiveColor;
    this.frameInactiveColor = frameInactiveColor;
    this.foodColor = foodColor;
    this.backgroundColor = backgroundColor;
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

  public boolean isDarkTheme() {
    return darkTheme;
  }

  public String getCss() {
    return css;
  }

}
