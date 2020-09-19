package main;

import game.element.Cell;
import game.Direction;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Config {

  public static final double GAME_OVER_FONT_SIZE = 100;
  public static final Paint GAME_OVER_FONT_COLOR = Color.RED;

  public static final Paint SCORE_FONT_COLOR = Color.WHITE;
  public static final double SCORE_FONT_SIZE_IN_PT = 30;

  public static final Color BACKGROUND_COLOR = Color.BURLYWOOD;
  public static final Paint SNAKE_BODY_COLOR = Color.SIENNA;

  // TODO: redesign: make width constant in cm, calculate px according to primary screen dpi
  public static final int CELL_SIZE_IN_PX = 100;
  public static final int NUMBER_OF_CELL_COLUMNS = 20;
  public static final int NUMBER_OF_CELL_ROWS = 20;

  public static final int INITIAL_SNAKE_SIZE = 3;
  public static final Direction INITIAL_DIRECTION = Direction.RIGHT;
  public static final Cell INITIAL_STARTING_POINT = new Cell(3, 3);

  public static final int SPEED_FACTOR = 1;

  public static final String TITLE_TEXT = "SNAKE GAME";
  public static final String GAME_OVER_TEXT = "GAME OVER";
  public static final Paint FOOD_COLOR = Color.RED;
}
