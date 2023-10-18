package nl.bioinf.lts;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class ClassifyData {
    public Instances classifyData(Classifier model, Instances data){
        try {
            // dataframe labeled
            Instances labeled = new Instances(data);
            for (int i = 0; i < data.numInstances(); i++) {
                // get one instance
                double label = model.classifyInstance(data.instance(i));
                labeled.instance(i).setClassValue(label);
            }
            return labeled;
        } catch(Exception e) {
            // exception occurred while predicting data && write log away
            throw new RuntimeException();
        }
    }
}
