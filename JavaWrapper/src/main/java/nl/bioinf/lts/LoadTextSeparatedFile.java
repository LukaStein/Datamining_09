package nl.bioinf.lts;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadTextSeparatedFile {
    private File tempObj;

    public String createTemporaryFileOfSingleInstance() {
        try{
            this.tempObj = File.createTempFile("singleInstanceTemp", ".arff");
            return this.tempObj.getName();
        } catch (IOException ioe) {
            throw new RuntimeException();
        }
    }

    public void writeInstanceToTempFile(String oneINSTANCE, StringBuilder body){
        try{
            FileWriter instantiateWriterObject = new FileWriter(this.tempObj.getName());
            instantiateWriterObject.write(String.valueOf(body));
            instantiateWriterObject.write(oneINSTANCE);
            instantiateWriterObject.close();
        } catch (IOException e) {
            System.err.println("Couldn't write to file.");
        }
    }

    public void deleteTemporaryFileOfSingleInstance() {
        this.tempObj.deleteOnExit();
    }

    // method that loads data with a class attribute present
    public Instances loadTrainingData(String FILENAME) {
        try {
            // load dataset
            ConverterUtils.DataSource reader = new ConverterUtils.DataSource(FILENAME);
            Instances data = reader.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e){
            // file not found  && write log away
            System.err.println("Check if the the correct dataset file is chosen for the type of predicting");
            throw new RuntimeException();
        }
    }

    // method that loads statusless data. A new status attribute is added at the end for predicting
    public Instances loadTestData(String FILENAME) {
        try {
            // load dataset
            ConverterUtils.DataSource reader = new ConverterUtils.DataSource(FILENAME);
            Instances data = reader.getDataSet();
//            @attribute status {sick,healthy}
            this.addClassAttribute(data);
            return data;
        } catch (Exception e){
            // file not found  && write log away
            System.err.println("Check if the the correct dataset is chosen for the type of predicting");
            throw new RuntimeException();
        }
    }

    private void addClassAttribute(Instances data){
        // prepare levels
        List<String> statusLabels = new ArrayList<>();
        statusLabels.add("healthy");
        statusLabels.add("sick");
        // instantiate status attribute
        Attribute status = new Attribute("status", statusLabels);
        // insert and assign class attribute
        data.insertAttributeAt(status, data.numAttributes());
        // position 24 attr. is position 23 due to starting index of zero.
        data.setClassIndex(data.numAttributes() - 1);
    }


    public StringBuilder chooseArffBody(boolean predictionOption) {
        try {
            StringBuilder body = new StringBuilder();
            InputStream getFile = getClass().getClassLoader().getResourceAsStream("arff_file_body.txt");
            assert getFile != null;
            Scanner readArffFileObj = new Scanner(getFile);
            while (readArffFileObj.hasNextLine()) {
                String bodyLine = readArffFileObj.nextLine();
                body.append(bodyLine).append("\n");
            }
            readArffFileObj.close();
            if (predictionOption) { // training format
                return new StringBuilder(body + """
                        @attribute status {sick,healthy}
                        
                        @data
                        """);
            } else { // test format
                return new StringBuilder(body + """
                        
                        @data
                        """);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
