package nl.bioinf.lts;
//javac -cp .:/commons/java/weka-3-8-5/weka.jar Main.java
//java -cp .:/commons/java/weka-3-8-5/weka.jar Main.java

import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.Arrays;
import java.util.List;
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
        // Type with more usable implementations than String[]
        String[] args = {"logPatientData.arff", "-accuracy"};
        List<String> argsList = Arrays.asList(args);
        // Instantiate controller
        Controller controller = new Controller();
        controller.userInterface(argsList);
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier("bagging_randomforest.model");
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        Instances data = loadFile.loadData(argsList.get(0)); // argsList.contains(*.arff) // "logPatientData.arff"
        // Voorspel labels
        ClassifyData classifying = new ClassifyData();
        Instances predictions = classifying.classifyData(model, data);
        // Constructing accuracy data
        ClassifierAccuracy accuracyAnnotations = new ClassifierAccuracy();
        accuracyAnnotations.confusionMatrix(data, predictions);
        String confusionMatrix = accuracyAnnotations.confusionMatrixToString();
        // summary goed en fout
        String summary = accuracyAnnotations.summary(predictions);
        // finally choose the users chosen output
        controller.chooseOutput(argsList, confusionMatrix, predictions, summary);
    }
}