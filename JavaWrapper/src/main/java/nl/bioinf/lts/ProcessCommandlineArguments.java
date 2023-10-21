package nl.bioinf.lts;

import org.apache.commons.cli.*;

import java.util.Arrays;

public class ProcessCommandlineArguments {

    public Options CommandlineOptions(){
        // new object
        Options options = new Options();
        // add argument options
        options.addOption("help", false, "Help message");
        options.addOption(new Option("predict", false, "Predictions output"));
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
}
