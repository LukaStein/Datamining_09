package nl.bioinf.lts;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// User interface choosing what the program must do
public class Controller {
    private final ProcessCommandlineArguments processCLArguments = new ProcessCommandlineArguments();
    public String[] args;


    public void chooseOutput(String confusionMatrix, Instances predictions,
                             String summary) {
        CommandLine cmd = processCLArguments.commandLineParser(this.args);
        List<String> argList = cmd.getArgList();;
        if (argList.contains("help")) {
            this.printHelp();
        }
        if (argList.contains("predict")) {
            this.printPredictions(predictions);
        }
        if (argList.contains("accuracy")) {
            this.printConfusionMatrix(confusionMatrix, summary);
        }
        this.invalidOptionGiven(argList, cmd);
    }

    public void invalidOptionGiven(List<String> argList, CommandLine cmd) {
        List<String> availableOptions = new ArrayList<>();
        availableOptions.add("help");
        availableOptions.add("predict");
        availableOptions.add("accuracy");
        for (int index = 1; index < cmd.getArgs().length; index++) {
            if (!availableOptions.contains(argList.get(index))) {
                System.out.println("\nOption: " + this.args[index] + " does not exist.\n" +
                        "Choose from the following options: h predict accuracy");
            }
        }
    }

    public void printHelp(){
        System.out.println("\nUsage: Main.java filename.arff\n" +
                "[optional = help (for help)]\n" +
                "Choose the keywords below for certain output\n" +
                "-\taccuracy (output of confusion matrix and summary of accuracy)\n" +
                "-\tpredict (output of predictions)\n" +
                "-\te.g. (script.java filename.arff accuracy predict)" + "]");
    }

    private void printConfusionMatrix(String confusionMatrix, String summary){
        // Bepaal goed en fout
        System.out.println(confusionMatrix);
        System.out.println(summary);
    }

    private void printPredictions(Instances predictions){
        System.out.println(predictions);

    }
}
