package nl.bioinf.lts;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadTextSeparatedFile {
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
}
