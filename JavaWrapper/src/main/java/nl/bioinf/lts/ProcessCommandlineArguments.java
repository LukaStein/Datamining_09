package nl.bioinf.lts;

import org.apache.commons.cli.*;
import java.util.List;

public class ProcessCommandlineArguments {

    public Options CommandlineOptions(){
        // new object
        Options options = new Options();
        // add argument options
        options.addOption("help", false, "Help message");
        options.addOption(new Option("file", false, "Receive file"));
        options.addOption(new Option("instance", false, "Receive instance"));
        options.addOption(new Option("training", false, "Predicting training dataset"));
        options.addOption(new Option("test", false, "Predicting test dataset"));
        options.addOption(new Option("accuracy", false, "Accuracy output"));
        options.addOption(new Option("save", false, "Write output to new file"));
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


    public boolean inputDataOption(List<String> argsList) {
        if (!argsList.contains("instance") && !argsList.contains("file")) {
            System.err.println("No input data type given.\n" +
                    "Choose \"instance\" or \"file\"" +
                    "Or approach \"help\" for detailed summary of available arguments");
            System.exit(0);
            return false;
        }
        else if (argsList.contains("instance")) {
            return false;
        } else {
            return (argsList.contains("file"));
        }
    }

    public boolean predictionOption(List<String> argsList) {
        if (!argsList.contains("test") && !argsList.contains("training")) {
            System.err.println("No prediction argument given.\n" +
                    "Choose \"test\" or \"training\"\n" +
                    "Or approach \"help\" for detailed summary of available arguments");
            System.exit(0);
            return false;
        }
        else if(argsList.contains("test")){
            return false;
        } else {
            return argsList.contains("training");
        }
    }

    public boolean helpOption(List<String> argsList) {
        return argsList.contains("help");
    }

    public boolean accuracyOption(List<String> argList){
        return argList.contains("accuracy");
    }

    public boolean saveOption(List<String> argsList){
        return argsList.contains("save");
    }
}


