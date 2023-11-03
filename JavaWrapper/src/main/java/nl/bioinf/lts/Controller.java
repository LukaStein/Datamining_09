package nl.bioinf.lts;

import org.apache.commons.cli.CommandLine;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.*;

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
        boolean verifyInputOption = processCLArguments.inputDataOption(argList);
        boolean verifyPredictionOption = processCLArguments.predictionOption(argList);
        ArrayList<String> outputObjects = this.classification(verifyPredictionOption, verifyInputOption);
        this.printPredictions(outputObjects.get(0) + "\n");

        boolean verifyAccuracy = processCLArguments.accuracyOption(argList);

        // Accuracy output only possible for training data set!
        if (verifyPredictionOption && verifyAccuracy) {
            this.printAccuracy(outputObjects.get(1), outputObjects.get(2));
        }
        if (!verifyPredictionOption && verifyAccuracy) {
            System.err.println("""
                    \n
                    Accuracy can only be calculated for training data.
                    Please only give "test" as argument for predicting on testing data without a class attribute.""");
        }

        boolean verifySaving = processCLArguments.saveOption(argList);
        if (verifySaving) {
            this.writeOutputAway(outputObjects);
        }
    }

    private void invalidOptionGiven(List<String> argList, CommandLine cmd) {
        List<String> availableOptions = new ArrayList<>();
        availableOptions.add("help");
        availableOptions.add("instance");
        availableOptions.add("file");
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

    private String dataOptionVerified() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter instance (one) or filename:");
        System.out.print("> ");
        String inputObj = scanner.nextLine();
        System.out.println("Received argument: " + inputObj);
        scanner.close();
        if (inputObj.isEmpty()) {
            System.err.println("No file or instance given. Consult help for more details");
            System.exit(0);
        }
        return inputObj;
    }

    private ArrayList<String> classification(boolean verifyPredictionOption,
                                             boolean verifyInputOption) {
        // Instantiate data object
        Instances data;
        // Instantiate FILENAME object
        String FILENAME = "";
        // Laad model
        LoadClassifier loadModel = new LoadClassifier();
        Classifier model = loadModel.loadClassifier();
        // Laad data
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();

        if (verifyInputOption) { // file
            FILENAME = this.dataOptionVerified();
        } else { // instance
            String oneINSTANCE = this.dataOptionVerified();
            StringBuilder body = loadFile.chooseArffBody(verifyPredictionOption);
            String tempFilename = loadFile.createTemporaryFileOfSingleInstance();
            loadFile.writeInstanceToTempFile(oneINSTANCE, body);
            FILENAME = tempFilename;
        }

        if (verifyPredictionOption) { // training
            data = loadFile.loadTrainingData(FILENAME);
//            loadFile.deleteTemporaryFileOfSingleInstance();
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
            data = loadFile.loadTestData(FILENAME);
//            loadFile.deleteTemporaryFileOfSingleInstance();
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
        System.out.println("Prediction(s):\n" + predictions);
    }

    private void writeOutputAway(ArrayList<String> outputObj) {
        if (!this.args[this.args.length - 1].isEmpty()) {
            OutputFile outputObject = new OutputFile();
            outputObject.createOutputFile(this.args[this.args.length - 1]);
            outputObject.writeOutputToNewFile(outputObj, this.args[this.args.length - 1]);
        }
    }
}




