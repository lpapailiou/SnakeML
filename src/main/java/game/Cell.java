package game;

import java.util.Objects;

/**
 * Instances of this class serve as coordinates. They are used as elements of the game and mark specific
 * locations on the 'game board'.
 */
public class Cell implements Cloneable{

  public final int x;     // the immutable x coordinate
  public final int y;     // the immutable y coordinate

  /**
   * The constructor will create a coordinate of an x and y value. After initialization,
   * the coordinate will be immutable.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return x == cell.x &&
        y == cell.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public Cell clone() {
    try {
      super.clone();
      return new Cell(this.x, this.y);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String toString() { return "(" + x + ", " + y + ")"; }
}
