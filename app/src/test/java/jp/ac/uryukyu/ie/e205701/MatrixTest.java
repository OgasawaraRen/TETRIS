package jp.ac.uryukyu.ie.e205701;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    @Test
    void rotateTest() {
        int[][] beforeMatrix = { { 1, 0, 0 }, 
                                 { 0, 1, 0 }, 
                                 { 0, 0, 1 } };

        int[][] correctMatrix = { { 0, 0, 1 }, 
                                  { 0, 1, 0 }, 
                                  { 1, 0, 0 } };

        int[][] rotatedMatrix = Matrix.rotate(beforeMatrix);

        assertArrayEquals(rotatedMatrix, correctMatrix);

        int[][] beforeMatrix2 = { { 0, 0, 0, 0 }, 
                                  { 1, 1, 1, 1 }, 
                                  { 0, 0, 0, 0 }, 
                                  { 0, 0, 0, 0 } };

        int[][] correctMatrix2 = { { 0, 0, 1, 0 }, 
                                   { 0, 0, 1, 0 }, 
                                   { 0, 0, 1, 0 }, 
                                   { 0, 0, 1, 0 } };

        int[][] rotatedMatrix2 = Matrix.rotate(beforeMatrix2);

        assertArrayEquals(rotatedMatrix2, correctMatrix2);

    }
}
