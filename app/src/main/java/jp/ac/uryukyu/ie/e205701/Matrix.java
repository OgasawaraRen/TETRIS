package jp.ac.uryukyu.ie.e205701;

/**
 * 行列の操作に関するクラス。
 */
public class Matrix {

    /**
     * 受け取った行列を90度横に倒した行列を返す。引数には正方行列を渡すことを想定。
     * 
     * @param matrix 変換する行列
     * @return 変換後の行列
     */
    public static int[][] rotate(int[][] matrix) {
        int[][] rotatedMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < rotatedMatrix.length; i++) {
            for (int j = 0; j < rotatedMatrix.length; j++) {
                rotatedMatrix[i][j] = matrix[rotatedMatrix.length - (j + 1)][i];
            }
        }

        return rotatedMatrix;
    }

    /**
     * 受け取った行列の転置行列を返す。
     * 
     * @param matrix 転置させる行列
     * @return 転置後の行列
     */
    public static int[][] transpose(int[][] matrix) {
        int[][] transposedMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < transposedMatrix.length; i++) {
            for (int j = 0; j < transposedMatrix.length; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }

        return transposedMatrix;
    }
}
