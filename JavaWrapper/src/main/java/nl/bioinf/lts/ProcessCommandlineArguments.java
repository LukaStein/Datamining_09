package nl.bioinf.lts;

import org.apache.commons.cli.*;
import weka.core.Instances;

import java.util.Arrays;

public class ProcessCommandlineArguments {

    public Options CommandlineOptions(){
        // new object
        Options options = new Options();
        // add argument options
        options.addOption("help", false, "Help message");
        options.addOption(new Option("training", false, "Predicting training dataset"));
        options.addOption(new Option("test", false, "Predicting test dataset"));
        options.addOption(new Option("accuracy", false, "Accuracy output"));
        return options;
    }

    public CommandLine commandLineParser(String[] args) {
        try{
            Options options = this.CommandlineOptions();
            CommandLineParser parser = new DefaultParser();
            return parser.parse(options, args); // known as cmd in Controller
        } catch (ParseException pe){
            throw new RuntimeException("Parsing failed" + pe);
        }
    }


    public void predictionType(String[] args){
        LoadTextSeparatedFile loadFile = new LoadTextSeparatedFile();
        if(args[0].equals("training")){
            Instances data = loadFile.loadTrainingData(args[0]); // argsList.contains(*.arff) // "logPatientData.arff"
        }
        if(args[0].equals("test")){
            Instances data = loadFile.loadTestData(args[0]); // argsList.contains(*.arff) // "logPatientData.arff"
        }
    }
}
