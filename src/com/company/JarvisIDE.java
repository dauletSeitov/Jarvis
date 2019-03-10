package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class JarvisIDE {

        public static void main(String[] args) throws IOException { // arg[0] - path, arg[1] - path


                System.out.println((int)0.53);
                if(args.length < 2)
                        throw new RuntimeException("incorrect input params!");

                NeuralNetwork neuralNetwork = new NeuralNetwork(784, 100, 10, 0.5);

                BufferedReader br = new BufferedReader(new FileReader(args[0]));
                String line = null;
                int lineNumber = 0;
                while ((line = br.readLine()) != null){

                        Data data = convertData(line);
                        double error = neuralNetwork.train(data.inputs, data.target);

                        if(lineNumber % 10 == 0)
                                System.out.println("lineNumber = " + lineNumber + " \terror = " + Math.abs(error));

                        lineNumber++;

                       // if(lineNumber  == 20000)break;

                }

                br.close();


                BufferedReader out = new BufferedReader(new FileReader(args[1]));

                int k = 0 ;
                int right = 0;
                int incorrect = 0;
                while ((line = out.readLine()) != null){
                        Data data = convertData(line);

                        double[] result = neuralNetwork.test(data.inputs);

                        for (int i = 0; i < result.length; i++) {
                                if(result[i] > 0.5)
                                        if( Math.round(result[i]) == data.target[i]) {
                                                System.out.println("ok");
                                                right++;
                                        }
                                        else {
                                                System.out.println("i ERROR result: " + Arrays.toString(result) + "\ntarget: " + Arrays.toString(data.target));
                                                incorrect++;
                                        }
                                        }
                }

                System.out.println((100 * right)/(right+incorrect));

                out.close();
        }


        private static Data convertData(String line){

                String[] lineArray = line.split(",");

                Data data = new Data();

                for (int i = 1; i < lineArray.length; i++) {
                        data.inputs[i-1] = 1.01 * Double.valueOf(lineArray[i]) / 255;

                }

                String target = lineArray[0];

                data.target[Integer.valueOf(target)] = 1;

                return data;
        }
}


class Data {

        double[] inputs = new double[784];
        double[] target = new double[10];
}
