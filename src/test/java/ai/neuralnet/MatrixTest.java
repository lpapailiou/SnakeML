package ai.neuralnet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MatrixTest {


  @Test
  public void biasTest() {
    double[][] d = new double[][]{{0, 1}, {1, 1}, {1, 1}};
    Matrix a = new Matrix(d);
    double[][] db = new double[][]{{1, 2}};
    Matrix b = new Matrix(db);
    double[][] de = new double[][]{{1, 3}, {2, 3}, {2, 3}};
    Matrix e = new Matrix(de);
    a.addBias(b);
    assertEquals(a, e);
  }

  @Test
  public void multiplicationTest() {
    double[][] d = new double[][]{{2, 1}, {1, 0}, {2, 0}};
    double[][] c = new double[][]{{1}, {2}};
    double[][] mul = new double[][]{{4}, {1}, {2}};
    Matrix a = new Matrix(d);
    Matrix b = new Matrix(c);
    Matrix m = Matrix.multiply(a, b);
    Matrix mt = new Matrix(mul);
    assertEquals(m, mt);
  }

  @Test
  public void sigmoidTest() {
    double[][] d = new double[][]{{0, -1}, {1, 2}};
    Matrix a = new Matrix(d);
    a.sigmoid();
    List<Double> list = Matrix.asList(a);
    assertEquals(0.5, Double.parseDouble(list.get(0) + ""), 0.001);
    assertEquals(0.25, Double.parseDouble(list.get(1) + ""), 0.05);
    assertEquals(0.75, Double.parseDouble(list.get(2) + ""), 0.2);
    assertEquals(0.85, Double.parseDouble(list.get(3) + ""), 0.1);
  }

  @Test
  public void utilitiesTest() {
    double[] d = new double[]{0.25, 5};
    Matrix m = Matrix.fromArray(d);
    List<Double> newList = Matrix.asList(m);
    List<Double> oldList = new ArrayList<>();
    for (double dbl : d) {
      oldList.add(dbl);
    }
    assertEquals(oldList, newList);
  }

  @Test
  public void randomizationTest() {
    double[][] d = new double[][]{{1, 1}, {1, 1}, {1, 1}};
    Matrix a = new Matrix(d);
    try {
      Matrix b = a.clone();
      Matrix c = a.clone();
      assertEquals(a, b);
      b.randomize(0.9);
      assertNotEquals(a, b);
      c.randomize();
      assertNotEquals(a, c);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }


  /*
   * The following tests are not used in this project, but have been kept for future usages
   * when the functions in the corresponding library class will be in use.
   */

//  @Test
//  public void diSigmoidTest() {
//    double[][] d = new double[][]{{0.25, 5}, {1, 2}};
//    Matrix a = new Matrix(d);
//    a = a.dsigmoid();
//    List<Double> list = Matrix.asList(a);
//    assertEquals(0.2, Double.parseDouble(list.get(0) + ""), 0.1);
//    assertEquals(-20, Double.parseDouble(list.get(1) + ""), 0.05);
//    assertEquals(0, Double.parseDouble(list.get(2) + ""), 0.05);
//    assertEquals(-2, Double.parseDouble(list.get(3) + ""), 0.05);
//  }

//  @Test
//  public void additionTest() {
//    double[][] d = new double[][]{{0, 1}, {2, 3}, {4, 5}};
//    double[][] da = new double[][]{{0, 2}, {4, 6}, {8, 10}};
//    Matrix m = new Matrix(d);
//    m.add(m);
//    Matrix ma = new Matrix(da);
//    assertEquals(m, ma);
//  }

//  @Test
//  public void mergeTest() {
//    double[][] d = new double[][]{{0, 1}, {2, 3}, {4, 5}};
//    double[][] dm = new double[][]{{1, 0}, {0, 5}, {6, 0}};
//    double[][] de = new double[][]{{-1, 1}, {2, -2}, {-2, 5}};
//    Matrix m = new Matrix(d);
//    Matrix mm = new Matrix(dm);
//    m = m.subtract(m, mm);
//    Matrix ma = new Matrix(de);
//    assertEquals(m, ma);
//  }

//  @Test
//  public void multiplicationScalarTest() {
//    double[][] d = new double[][]{{2, 1}, {1, 0}, {2, 0}};
//    double scalar = 2.5;
//    double[][] de = new double[][]{{5, 2.5}, {2.5, 0}, {5, 0}};
//    Matrix a = new Matrix(d);
//    a.multiply(scalar);
//    Matrix b = new Matrix(de);
//    assertEquals(a, b);
//  }

//  @Test
//  public void multiplicationElementwiseTest() {
//    double[][] d = new double[][]{{2, 1}, {1, 0}, {2, 0}};
//    double[][] dm = new double[][]{{2, 0}, {1, 0}, {0.5, 0}};
//    double[][] de = new double[][]{{4, 0}, {1, 0}, {1, 0}};
//    Matrix a = new Matrix(d);
//    Matrix b = new Matrix(dm);
//    Matrix e = new Matrix(de);
//    a.multiplyElementwise(b);
//    assertEquals(a, e);
//  }

//  @Test
//  public void subtractionTest() {
//    double[][] d = new double[][]{{0, 1}, {2, 3}, {4, 5}};
//    double[][] da = new double[][]{{0, 0}, {0, 0}, {0, 0}};
//    Matrix m = new Matrix(d);
//    m = m.subtract(m, m);
//    Matrix ma = new Matrix(da);
//    assertEquals(m, ma);
//  }

//  @Test
//  public void transponseTest() {
//    double[][] d = new double[][]{{2, 1}, {3, 5}, {7, 4}};
//    double[][] dt = new double[][]{{2, 3, 7}, {1, 5, 4}};
//    Matrix a = new Matrix(d);
//    Matrix b = Matrix.transpose(a);
//    Matrix c = new Matrix(dt);
//    assertEquals(b, c);
//  }

}
