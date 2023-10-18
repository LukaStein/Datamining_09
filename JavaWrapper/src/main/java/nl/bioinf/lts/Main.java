package nl.bioinf.lts;
//javac -cp .:/commons/java/weka-3-8-5/weka.jar Main.java
//java -cp .:/commons/java/weka-3-8-5/weka.jar Main.java

import weka.classifiers.Classifier;
import weka.core.Instances;
// Options CommandLine CommandLinePartser -> command line interface

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, starting WrapClassifier...");
            Main main = new Main();
            main.start();
            System.out.println("Closing WrapClassifier, goodbye...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start() throws Exception {
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier("NB_iris.model");
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        Instances data = loadFile.loadData("iris.arff");
        // Voorspel labels
        ClassifyData classifying = new ClassifyData();
        Instances predictions = classifying.classifyData(model, data);
    }
}