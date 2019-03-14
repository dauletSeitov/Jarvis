
package com.company;

import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

public class Start {

        private static final int SIZE = 100;
        NeuralNetwork neuralNetwork = new NeuralNetwork(10000, 60, 2, 0.01);


        public static void main(String[] args) throws IOException {

//                File folder = new File("/home/user/Desktop/1/cat");
//                File[] Filelist = folder.listFiles();
//
//                int i = 0;
//                for (File file: Filelist) {
//
//                        Files.move(file.toPath(), new File(file.getParent(), "cat_" + i +".jpg").toPath());
//                        i++;
//                }

                new Start().getFiles("/home/user/Desktop/1/mix");
        }

        private void getFiles(@NotNull String path){

                File folder = new File(path);
                List<File> Filelist = Arrays.asList(folder.listFiles());
                Collections.shuffle(Filelist);

                for (int i = 0; i < 10; i++) {
                        for (File file : Filelist) {
                                try {
                                        BufferedImage img = ImageIO.read(file);
                                        img = formatImage(img);
                                        pixelToText(img, file.getName());
                                } catch (Exception e) {
                                        System.out.println("file = " + file.getName());
                                        e.printStackTrace();
                                }
                        }
                }


                //test
                File files = new File("/home/user/Downloads/test");

                for (File file: files.listFiles()) {
                        try {
                                BufferedImage img = ImageIO.read(file);
                                img = formatImage(img);
                                test(img, file.getName());
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }


        }

        int iteration = 0 ;

        private void pixelToText(BufferedImage img, String fileName){

                int width = img.getWidth();
                int height = img.getHeight();

                // File outputfile = new File("/home/user/Desktop/1/boutrrrimage.jpg");


                double[] input = new double[10000];
                double[] target = {0.0, 1.0};

                if(fileName.startsWith("darth")){
                        target = new double[]{1.0, 0.0};
                }

                int index = 0;
                for (int i = 0; i < width; i++) {
                        for (int k = 0; k < height; k++) {

                                Color c = new Color(img.getRGB(i, k));
                                //System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
                                input[index++] = 1.01 * c.getRed() / 255;
                        }
                }

                double error = neuralNetwork.train(input, target);
                System.out.println(iteration + ") error = " + error);
                iteration++;
//                try {
//                        ImageIO.write(img, "jpg", outputfile);
//                } catch (IOException e) {
//                        e.printStackTrace();
//                }



        }


        private void test(BufferedImage img, String fileName){

                int width = img.getWidth();
                int height = img.getHeight();


                double[] input = new double[10000];
                double[] target = {0.0, 1.0};

                if(fileName.startsWith("d")){
                        target = new double[]{1.0, 0.0};
                }

                int index = 0;
                for (int i = 0; i < width; i++) {
                        for (int k = 0; k < height; k++) {

                                Color c = new Color(img.getRGB(i, k));
                                input[index++] = 1.01 * c.getRed() / 255;
                        }
                }

                double[] result = neuralNetwork.test(input);

                System.out.print (fileName);

                if( result[0] > 0.5 )
                        System.out.println("\tdog");
                else
                        System.out.println("\tcat");


        }

        private BufferedImage formatImage(BufferedImage img){
                return resize(img, SIZE, SIZE);
        }

        public static BufferedImage resize(BufferedImage img, int newW, int newH) {
                Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_BYTE_GRAY);

                Graphics2D g2d = dimg.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                return dimg;
        }

}
