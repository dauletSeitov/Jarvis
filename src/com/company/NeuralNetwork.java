
package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class NeuralNetwork {

        private double lr;
        private double[][] wih;
        private double[][] who;

        public NeuralNetwork(int inodes, int hnodes, int onodes, double lr) {   //inodes - input nodes, hnodes - hidden nodes, onodes - output nodes, lr - learning rate 0 > lr < 1

                if(inodes == 0 || hnodes == 0||  onodes ==0 || lr == 0)
                        throw new RuntimeException("invalid input params: inodes = " + inodes + " hnodes = " + hnodes + " onodes = " + onodes + " lr = " + lr);

                this.lr = lr;
                this.wih = new double[hnodes][inodes];
                this.who = new double[onodes][hnodes];

                for (int i = 0; i < hnodes; i++) {      //init weights
                        for (int k = 0; k < inodes; k++) {
                                wih[i][k] = Math.random() - 0.5;
                        }
                }

                for (int i = 0; i < onodes; i++) {      //init weights
                        for (int k = 0; k < hnodes; k++) {
                                who[i][k] = Math.random() - 0.5 ;
                        }
                }
        }

        public static void main(String[] args) {


                double[] data = {1.0, 0.0, 1.0};        //training data
                double[] data2 = {0.0, 1.0, 1.0};
                double[] target = {1.0, 0.0};
                double[] target2 = {0.0, 1.0};

                NeuralNetwork neuralNetwork = new NeuralNetwork(3, 4, 2, 0.3);


                for (int i = 0; i < 100; i++) {        //training
                        neuralNetwork.train(data, target);
                        neuralNetwork.train(data2, target2);

                }


                double[] res = neuralNetwork.test(data);        //test 1
                System.out.println("\ntarget = " + Arrays.toString(target));
                System.out.println("res = " + Arrays.toString(res));
                System.out.println("err = " + Arrays.toString(Utils.minusMatrix(target, res)));

                double[] res2 = neuralNetwork.test(data2);      //test 2
                System.out.println("\ntarget2 = " + Arrays.toString(target2));
                System.out.println("res = " + Arrays.toString(res2));
                System.out.println("err = " + Arrays.toString(Utils.minusMatrix(target2, res2)));


        }

        public void saveWeights (String path) throws IOException {

                Objects.requireNonNull(path,"could not save path is null");

                FileOutputStream fileOutputStream = new FileOutputStream(path);
                ObjectOutputStream objectWriter = new ObjectOutputStream(fileOutputStream);

                objectWriter.writeObject(wih);
                objectWriter.writeObject(who);

                objectWriter.close();
        }


        public void loadWeights(String path) throws IOException, ClassNotFoundException {

                Objects.requireNonNull(path,"could not save path is null");

                FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

                wih = (double[][]) inputStream.readObject();
                who = (double[][]) inputStream.readObject();

                inputStream.close();



        }


        public double[] test(double[] inputs) {        //test method

                double[] hiddenInputs = Utils.multiplyMatrix(this.wih, inputs);

                double[] hiddenOutputs = Arrays.stream(hiddenInputs).map(Utils::activationFunction).toArray();

                double[] finalInputs = Utils.multiplyMatrix(this.who, hiddenOutputs);

                return Arrays.stream(finalInputs).map(Utils::activationFunction).toArray();
        }

        public double train(double[] inputs, double[] target) {  //training method

                double[] hiddenInputs = Utils.multiplyMatrix(this.wih, inputs);

                double[] hiddenOutputs = Arrays.stream(hiddenInputs).map(Utils::activationFunction).toArray();

                double[] finalInputs = Utils.multiplyMatrix(this.who, hiddenOutputs);

                double[] finalOutputs = Arrays.stream(finalInputs).map(Utils::activationFunction).toArray();

                double[] outputErrors = Utils.minusMatrix(target, finalOutputs);

                double[] hiddenErrors = Utils.multiplyMatrix(Utils.transposeMatrix(who), outputErrors);

                this.who = evaluateWeights(outputErrors, hiddenOutputs, finalOutputs, who);
                this.wih = evaluateWeights(hiddenErrors, inputs, hiddenOutputs, wih);

                //System.out.println("who = " + Arrays.deepToString(who));
                //System.out.println("wih = " + Arrays.deepToString(wih));

                return Arrays.stream(outputErrors).sum();
        }




        private double[][] evaluateWeights(double[] errors, double[] previousOutputs, double[] afterOutputs, double[][] weights) {


                double[] doubles = Utils.elementwiseMultiplication(errors, afterOutputs, Utils.oneMinusMatrix(afterOutputs));

                double[][] doubles1 = Utils.multiplyMatrix(doubles, previousOutputs);

                double[][] evalOutWeights = Utils.scalarMultiplication(doubles1, lr);

                return Utils.sumMatrix(weights, evalOutWeights);
        }


}














