package neuralnet;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void mutationTest() {
        double[][] d = new double[][] {{1,1},{1,1},{1,1}};
        Matrix a = new Matrix(d);
        a.print();
        Matrix b = a.clone();
        b.print();
        assertEquals(a, b);
        b.randomize(0.9);
        b.print();
        assertNotEquals(a, b);
    }

    @Test
    public void transponseTest() {
        double[][] d = new double[][] {{2,1},{3,5},{7,4}};
        double[][] dt = new double[][] {{2,3,7},{1,5,4}};
        Matrix a = new Matrix(d);
        a.print();
        Matrix b = Matrix.transponse(a);
        b.print();
        Matrix c = new Matrix(dt);
        assertEquals(b, c);
    }

    @Test
    public void testMultiply() {
        double[][] d = new double[][] {{2,1},{1,0},{2,0}};
        double[][] c = new double[][] {{1},{2}};
        double[][] mul = new double[][] {{4},{1},{2}};
        Matrix a = new Matrix(d);
        a.print();
        Matrix b = new Matrix(c);
        b.print();
        Matrix m = Matrix.multiply(a, b);
        m.print();
        Matrix mt = new Matrix(mul);
        assertEquals(m, mt);
    }

    @Test
    public void testMatrix() {
        double[][] d = new double[][] {{0,1},{2,3},{4,5}};
        double[][] da = new double[][] {{0,2},{4,6},{8,10}};
        Matrix m = new Matrix(d);
        m.print();
        m.add(m);
        m.print();
        Matrix ma = new Matrix(da);
        assertEquals(m, ma);
    }



    public void randomizeTest() {
        Matrix m = new Matrix(2,3);
        m.print();
        m.randomize();
        m.print();
    }
}
