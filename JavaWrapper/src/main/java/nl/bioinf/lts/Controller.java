package nl.bioinf.lts;

import org.apache.commons.cli.CommandLine;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// User interface choosing what the program must do
public class Controller {
    private final ProcessCommandlineArguments processCLArguments = new ProcessCommandlineArguments();
    public String[] args;


    protected void chooseOutput() {
        CommandLine cmd = processCLArguments.commandLineParser(this.args);
        List<String> argList = cmd.getArgList();
        this.invalidOptionGiven(argList, cmd);

        boolean verifyHelp =  processCLArguments.helpOption(argList);
        if (verifyHelp){
            this.printHelp();
        }
        boolean verifyPredictionOption = processCLArguments.predictionOption(argList);
        ArrayList outputObjects = this.classification(verifyPredictionOption, argList);
        this.printPredictions(outputObjects.get(0).toString());

        boolean verifyAccuracy = processCLArguments.accuracyOption(argList);

        // Accuracy output only possible for training data set!
        if (verifyPredictionOption && verifyAccuracy){
            this.printAccuracy(outputObjects.get(1).toString(), outputObjects.get(2).toString());
        }



    }

    private void invalidOptionGiven(List<String> argList, CommandLine cmd) {
        List<String> availableOptions = new ArrayList<>();
        availableOptions.add("help");
        availableOptions.add("test");
        availableOptions.add("accuracy");
        availableOptions.add("training");
        for (int index = 1; index < cmd.getArgs().length; index++) {
            if (!availableOptions.contains(argList.get(index))) {
                System.err.println("\nOption: " + this.args[index] + " does not exist.\n" +
                        "Choose from the following options:" + availableOptions);
                System.exit(0);
            }
        }
    }

    private ArrayList<Object> classification(boolean verifyPredictionOption, List<String> argList){
        // Instantiate data object
        Instances data;
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier();
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        if(verifyPredictionOption){
            data = loadFile.loadTrainingData(argList.get(0)); // argsList.contains(*.arff) // "logPatientData.arff"
        } else {
            data = loadFile.loadTestData(argList.get(0)); // argsList.contains(*.arff) // "logPatientData.arff"
        }
        // Voorspel labels
        ClassifyData classifying = new ClassifyData();
        Instances predictions = classifying.classifyData(model, data);
        // Constructing accuracy data
        ClassifierAccuracy accuracyAnnotations = new ClassifierAccuracy();
        accuracyAnnotations.confusionMatrix(data, predictions);
        String confusionMatrix = accuracyAnnotations.confusionMatrixToString();
        // summary goed en fout
        String summary = accuracyAnnotations.summary(predictions);
        return new ArrayList<>(Arrays.asList(predictions, confusionMatrix, summary));
    }



    public void printHelp(){
        System.err.println("""

                Usage: Main.java filename.arff
                [optional = help (for help)]
                Choose the keywords below for certain output
                -\ttraining (Predicting training dataset)
                -\ttest (Predicting test dataset)
                -\taccuracy (output of confusion matrix and summary of accuracy)
                -\te.g. (script.java filename.arff accuracy predict)]""");
    }

    private void printAccuracy(String confusionMatrix, String summary){
        // Bepaal goed en fout
        System.out.println(confusionMatrix);
        System.out.println(summary);
    }

    private void printPredictions(String predictions){
        System.out.println(predictions);
    }

}
