package nl.bioinf.lts;

import org.apache.commons.cli.*;
import weka.core.Instances;

import java.util.Arrays;
import java.util.List;

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


    public boolean predictionOption(List<String> argsList){
        if(argsList.contains("test")){
            return false;
        } else return argsList.contains("training");
    }

    public boolean helpOption(List<String> argsList) {
        return argsList.contains("help");
    }

    public boolean accuracyOption(List<String> argList){
        return argList.contains("accuracy");
    }
}


