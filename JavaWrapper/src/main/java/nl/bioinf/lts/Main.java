package nl.bioinf.lts;
//javac -cp .:/commons/java/weka-3-8-5/weka.jar Main.java
//java -cp .:/commons/java/weka-3-8-5/weka.jar Main.java

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, starting WrapClassifier...");
            Main main = new Main();
            main.start(args);
            System.out.println("Closing WrapClassifier, goodbye...");
        } catch (RuntimeException e) {
            Controller controller = new Controller();
            controller.printHelp();
        }
    }

    private void start(String[] args) {

        // Instantiate controller && pass args to controller
        Controller controller = new Controller();
        controller.args = args;
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier();
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        Instances data = loadFile.loadTrainingData(args[0]); // argsList.contains(*.arff) // "logPatientData.arff"
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
        controller.chooseOutput(confusionMatrix, predictions, summary);
    }
}