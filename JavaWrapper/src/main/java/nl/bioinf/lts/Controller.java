package nl.bioinf.lts;

import weka.core.Instances;
import java.util.List;

// User interface choosing what the program must do
public class Controller {

    public void userInterface(List<String> argsList){
        if (argsList.contains("-h")){
            System.out.println("Usage: Main.java filename.arff\n" +
                    "[optional = -h (for help)]\n" +
                    "Choose the keywords below for certain output" +
        "-accuracy (output of confusion matrix and summary of accuracy\n" +
                    "-predict (output of predictions)" +
                    "e.g. (script.java filename.arff -Accuracy -Predict)" + "]");
        }
    }

    public void chooseOutput(List<String> argsList, String confusionMatrix, Instances predictions, String summary){
        if (argsList.contains("-predict")){
            printPredictions(predictions);
        }
        if (argsList.contains("-accuracy")){
            printConfusionMatrix(confusionMatrix, summary);
        }
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
