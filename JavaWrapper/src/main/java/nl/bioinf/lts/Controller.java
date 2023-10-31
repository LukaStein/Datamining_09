package nl.bioinf.lts;

import org.apache.commons.cli.CommandLine;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// User interface choosing what the program must do
public class Controller {
    private final ProcessCommandlineArguments processCLArguments = new ProcessCommandlineArguments();
    public String[] args;


    protected void chooseOutput() {
        CommandLine cmd = processCLArguments.commandLineParser(this.args);
        List<String> argList = cmd.getArgList();
        this.invalidOptionGiven(argList, cmd);

        boolean verifyHelp = processCLArguments.helpOption(argList);
        if (verifyHelp) {
            this.printHelp();
        }
        boolean verifyPredictionOption = processCLArguments.predictionOption(argList);
        ArrayList<String> outputObjects = this.classification(verifyPredictionOption, argList);
        this.printPredictions(outputObjects.get(0).toString());

        boolean verifyAccuracy = processCLArguments.accuracyOption(argList);

        // Accuracy output only possible for training data set!
        if (verifyPredictionOption && verifyAccuracy) {
            this.printAccuracy(outputObjects.get(1), outputObjects.get(2));
        }
        if (!verifyPredictionOption && verifyAccuracy) {
            System.err.println("Accuracy can only be calculated for training data." +
                    "\nPlease only give \"test\" as argument for predicting on testing data without a class attribute.");
        }

        boolean verifySaving = processCLArguments.saveOption(argList);
        if (verifySaving) {
            this.writeOutputAway(outputObjects);
        }
    }

    private void invalidOptionGiven(List<String> argList, CommandLine cmd) {
        List<String> availableOptions = new ArrayList<>();
        availableOptions.add("help");
        availableOptions.add("test");
        availableOptions.add("accuracy");
        availableOptions.add("training");
        availableOptions.add("save");
        if (argList.contains("save")){
            availableOptions.add(argList.get(argList.size() - 1));
        }
        for (int index = 1; index < cmd.getArgs().length; index++) {
            if (!availableOptions.contains(argList.get(index))) {
                System.err.println("\nOption: " + this.args[index] + " does not exist.\n" +
                        "Choose from the following options:" + availableOptions);
                System.exit(0);
            }
        }
    }

    private ArrayList<String> classification(boolean verifyPredictionOption, List<String> argList) {
        // Instantiate data object
        Instances data;
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier();
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        if (verifyPredictionOption) { // training
            data = loadFile.loadTrainingData(argList.get(0));
            // Voorspel labels
            ClassifyData classifying = new ClassifyData();
            List<String> predictions = classifying.classifyData(model, data);
            // Constructing accuracy data
            ClassifierAccuracy accuracyAnnotations = new ClassifierAccuracy();
            accuracyAnnotations.confusionMatrix(data, predictions);
            String confusionMatrix = accuracyAnnotations.confusionMatrixToString();
            // summary goed en fout
            String summary = accuracyAnnotations.summary(predictions);
            String predLabels = this.formatPredictions(predictions);
            return new ArrayList<>(Arrays.asList(predLabels, confusionMatrix, summary));
        } else { // test
            data = loadFile.loadTestData(argList.get(0));
            // Voorspel labels
            ClassifyData classifying = new ClassifyData();
            List<String> predictions = classifying.classifyData(model, data);
            String predLabels = formatPredictions(predictions);
            return new ArrayList<>(Collections.singletonList(predLabels));
        }
    }


    public void printHelp() {
        System.err.println("""

                Usage: Main.java filename.arff
                [optional = help (for help)]
                Choose the keywords below for certain output
                -\ttraining (Predicting training dataset)
                -\ttest (Predicting test dataset)
                -\taccuracy (Output of confusion matrix and summary of accuracy)
                -\tsave filename.txt (Save output of program run to a new specified file)
                -\te.g. (script.java filename.arff accuracy predict)]""");
    }

    private String formatPredictions(List<String> predictions){
        String predLabels = """
                    """;
        for (String label : predictions) {
            predLabels = predLabels + label + "\n";
        }
        return predLabels;
    }

    private void printAccuracy(String confusionMatrix, String summary) {
        // Bepaal goed en fout
        System.out.println(confusionMatrix);
        System.out.println(summary);
    }

    private void printPredictions(String predictions) {
        System.out.println(predictions); // TODO: output onder elkaar
    }

    private void writeOutputAway(ArrayList<String> outputObj) {
        if (!this.args[this.args.length - 1].isEmpty()) {
            OutputFile outputObject = new OutputFile();
            outputObject.createOutputFile(this.args[this.args.length - 1]);
            outputObject.writeOutputToNewFile(outputObj, this.args[this.args.length - 1]);
        }
    }
}




