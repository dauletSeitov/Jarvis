package com.company;

public class Utils {

        public static double[] multiplyMatrix(double[][] m1, double[] m2) {
                int m1ColLength = m1[0].length; // m1 columns length
                int m2RowLength = m2.length;    // m2 rows length
                if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
                int mRRowLength = m1.length;    // m result rows length
                double[] mResult = new double[mRRowLength];
                for(int i = 0; i < mRRowLength; i++) {         // rows from m1
//                        for(int j = 0; j < mRColLength; j++) {     // columns from m2
                                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                                        mResult[i] += m1[i][k] * m2[k];
                                }
//                        }
                }
                return mResult;
        }

        public static double[][] multiplyMatrix(double[] m1, double[] m2) {

//                if(m1.length != m2.length)
//                        throw new RuntimeException("could not multiply m1 = " + m1.length + "m2 " + m2.length);

                double[][] result = new double[m1.length][m2.length];
                int outI = 0, outK = 0;
                for(int i = 0; i < m1.length; i++) {
                        for(int k = 0; k < m2.length; k++) {
                                result[outI][outK++] = m1[i] * m2[k];
                        }
                        outI++;
                        outK = 0;
                }
                return result;
        }


        public static double activationFunction(double value){
                return 1/(1 + Math.pow(Math.E, -1 * value));
        }

        public static double[] minusMatrix(double[] m1, double[] m2){
                if (m1.length != m2.length)
                        throw new RuntimeException("m1 length is " + m1.length + " m2 length is " + m2.length);

                double[] result = new double[m1.length];
                for (int i = 0; i < m1.length; i++) {
                        result[i] = m1[i] - m2[i];
                }
                return result;
        }

        public static double[][] transposeMatrix(double[][] matrix){

                double[][] result = new double[matrix[0].length][matrix.length];

                for (int i = 0; i < matrix.length; i++) {
                        for (int k = 0; k < matrix[0].length; k++) {
                                result[k][i] = matrix[i][k];
                        }
                }
                return result;
        }

        public static double[] oneMinusMatrix(double[] matrix){

                double[] result = new double[matrix.length];

                for (int i = 0; i < matrix.length; i++) {
                      result[i] = 1 - matrix[i];
                }
                return result;
        }

        public static double[] elementwiseMultiplication(double[] m1, double[] m2, double[] m3){

                if (m1.length != m2.length || m2.length != m3.length)
                        throw new RuntimeException("m1 length is " + m1.length + " m2 length is " + m2.length + " m3 length is " + m3.length);
                double [] result = new double[m1.length];
                for (int i = 0; i < m1.length; i++) {
                        result[i] = m1[i] * m2[i] * m3[i];
                }
                return result;
        }


        public static double[][] scalarMultiplication(double[][] matrix, double scalar){

                double [][] result = new double[matrix.length][matrix[0].length];
                for (int i = 0; i < matrix.length; i++) {
                        for (int k = 0; k < matrix[0].length; k++) {
                                result[i][k] = matrix[i][k] * scalar;
                        }
                }
                return result;
        }

        public static double[][] sumMatrix(double[][] matrix1, double[][] matrix2){

                double [][] result = new double[matrix1.length][matrix1[0].length];
                for (int i = 0; i < matrix1.length; i++) {
                        for (int k = 0; k < matrix1[0].length; k++) {
                                result[i][k] = matrix1[i][k] + matrix2[i][k];
                        }
                }
                return result;
        }


}
