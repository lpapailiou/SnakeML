package game.element;

import java.util.Objects;

public class Cell {   // TODO: make immutable? encapsulation?

  public int x;
  public int y;

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
    return new Cell(this.x, this.y);
  }

  @Override
  public String toString() { return "(" + x + ", " + y + ")"; }
}
