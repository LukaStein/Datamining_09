package nl.bioinf.lts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputFile {

    public void createOutputFile(String FILENAME){
        try {
            File instantiateFileObject = new File(FILENAME);
            if (instantiateFileObject.createNewFile()){
                System.out.println("File:\t-" + instantiateFileObject.getName() + "- Created successfully");
            } else {
                System.err.println("\nFile with specified name already exist.");
            }
        } catch (IOException e) {
            System.err.println("\nCouldn't make file.");
        }
    }

    public void writeOutputToNewFile(ArrayList<String> outputVar, String FILENAME){
        try{
            FileWriter instantiateWriterObject = new FileWriter(FILENAME);
            for (String outputobj : outputVar) {
                instantiateWriterObject.write(outputobj + "\n");
            }
            instantiateWriterObject.close();
            System.out.println("File:\t-" + FILENAME + "- Written output successfully" );
        } catch (IOException e) {
            System.err.println("Couldn't write to file.");
        }
    }

}
