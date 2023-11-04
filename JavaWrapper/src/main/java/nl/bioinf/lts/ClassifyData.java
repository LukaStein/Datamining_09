package nl.bioinf.lts;

import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class ClassifyData {
    public List<String> classifyData(Classifier model, Instances data){
        // dataframe labeled
        Instances labeled = new Instances(data);
        List<String> extractClasslabel = new ArrayList<String>();

        try {
            for (int i = 0; i < data.numInstances(); i++) {
                // get one instance
                double label = model.classifyInstance(data.instance(i));
                labeled.instance(i).setClassValue(label);
                extractClasslabel.add(data.classAttribute().value((int) label));
            }
            return extractClasslabel;
        } catch(Exception e) {
            // exception occurred while predicting data && write log away
            throw new RuntimeException();
        }
    }
}
