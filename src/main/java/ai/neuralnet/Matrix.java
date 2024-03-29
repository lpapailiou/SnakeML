package ai.neuralnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class to build the layers of the neural network.
 */
public class Matrix implements Serializable, Cloneable {

  private static final long serialVersionUID = 2L;
  private final double[][] data;
  private final int rows;
  private final int cols;

  /**
   * The constructor to create a randomized matrix for given type.
   *
   * @param rows the row count of the matrix
   * @param cols the column count of the matrix
   */
  Matrix(int rows, int cols) {
    data = new double[rows][cols];
    this.rows = rows;
    this.cols = cols;
  }

  /**
   * Constructor used for testing
   *
   * @param input the input 2d array to be converted to a matrix
   */
  Matrix(double[][] input) {
    data = input;
    rows = input.length;
    cols = input[0].length;
  }

  void addBias(Matrix m) {
    if (cols != m.cols) {
      throw new IllegalArgumentException("wrong input matrix dimensions!");
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] += m.data[0][j];
      }
    }
  }

  static Matrix merge(Matrix a, Matrix b) {
    if (a.rows != b.rows || a.cols != b.cols) {
      throw new IllegalArgumentException(
          "wrong input matrix dimensions! " + a.getType() + " vs. " + b.getType());
    }
    for (int i = 0; i < a.rows; i++) {
      for (int j = 0; j < a.cols; j++) {
        a.data[i][j] = (a.data[i][j] + b.data[i][j]) / 2;
      }
    }
    return a;
  }

  static Matrix multiply(Matrix a, Matrix b) {
    if (a.cols != b.rows) {
      throw new IllegalArgumentException(
          "wrong input matrix dimensions for multiplication! " + a.getType() + " " + b.getType());
    }
    Matrix tmp = new Matrix(a.rows, b.cols);
    for (int i = 0; i < tmp.rows; i++) {
      for (int j = 0; j < tmp.cols; j++) {
        double sum = 0;
        for (int k = 0; k < a.cols; k++) {
          sum += a.data[i][k] * b.data[k][j];
        }
        tmp.data[i][j] = sum;
      }
    }
    return tmp;
  }

  void sigmoid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = 1 / (1 + Math.exp(-data[i][j]));
      }
    }
  }

  private String getType() {
    return "(" + rows + ", " + cols + ")";
  }

  int getRows() {
    return rows;
  }

  int getCols() {
    return cols;
  }

  static Matrix fromArray(double[] arr) {
    Matrix tmp = new Matrix(arr.length, 1);
    for (int i = 0; i < arr.length; i++) {
      tmp.data[i][0] = arr[i];
    }
    return tmp;
  }

  static List<Double> asList(Matrix m) {
    List<Double> tmp = new ArrayList<>();
    for (int i = 0; i < m.rows; i++) {
      for (int j = 0; j < m.cols; j++) {
        tmp.add(m.data[i][j]);
      }
    }
    return tmp;
  }

  void randomize() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        data[i][j] = Math.random() * 2 - 1;
      }
    }
  }

  void randomize(double factor) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (Math.random() < 0.5) {
          data[i][j] = data[i][j] + (Math.random() * 2 - 1) * factor;
        }
      }
    }
  }

  @Override
  protected Matrix clone() throws CloneNotSupportedException {
    super.clone();
    Matrix m = new Matrix(rows, cols);
    for (int i = 0; i < m.rows; i++) {
      if (m.cols >= 0) {
        System.arraycopy(this.data[i], 0, m.data[i], 0, m.cols);
      }
    }
    return m;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        sb.append(data[i][j]).append("  ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Integer.parseInt(rows + "" + cols);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Matrix)) {
      return false;
    }
    return this.toString().equals(o.toString());
  }



  /*
   * The following functions are not used in this project, but have been kept for future usages
   * as a library.
   */

//  void add(Matrix m) {
//      if (cols != m.cols || rows != m.rows) {
//          throw new IllegalArgumentException("wrong input matrix dimensions for addition!");
//      }
//      for (int i = 0; i < rows; i++) {
//          for (int j = 0; j < cols; j++) {
//              data[i][j] += m.data[i][j];
//          }
//      }
//  }

//  static Matrix subtract(Matrix a, Matrix b) {
//      if (a.cols != b.cols) {
//          throw new IllegalArgumentException("wrong input matrix dimensions! " + a.getType() + " vs. " + b.getType());
//      }
//      for (int i = 0; i < a.rows; i++) {
//          for (int j = 0; j < a.cols; j++) {
//              a.data[i][j] -= b.data[i][j];
//          }
//      }
//      return a;
//  }

//  void multiply(double scalar) {
//      for (int i = 0; i < rows; i++) {
//          for (int j = 0; j < cols; j++) {
//              data[i][j] *= scalar;
//          }
//      }
//  }
//
//  void multiplyElementwise(Matrix m) {
//      if (cols != m.cols || rows != m.rows) {
//          throw new IllegalArgumentException("wrong input matrix dimensions!");
//      }
//      for (int i = 0; i < rows; i++) {
//          for (int j = 0; j < cols; j++) {
//              data[i][j] *= m.data[i][j];
//          }
//      }
//  }

//  static Matrix transpose(Matrix m) {
//      Matrix tmp = new Matrix(m.cols, m.rows);
//      for (int i = 0; i < m.rows; i++) {
//          for (int j = 0; j < m.cols; j++) {
//              tmp.data[j][i] = m.data[i][j];
//          }
//      }
//      return tmp;
//  }

//  Matrix dsigmoid() {
//      Matrix tmp = new Matrix(rows, cols);
//      for (int i = 0; i < rows; i++) {
//          for (int j = 0; j < cols; j++) {
//              tmp.data[i][j] = data[i][j] * (1 - data[i][j]);
//          }
//      }
//      return tmp;
//  }

}
