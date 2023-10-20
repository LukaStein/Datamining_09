package nl.bioinf.lts;

import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public class LoadClassifier {
    private final String MODELCLASSIFIER = "bagging_randomforest.model";

    public Classifier loadClassifier(String MODELNAME) {
        try {
            SerializationHelper reader = new SerializationHelper();
            return (Classifier) reader.read(MODELNAME);
        } catch (Exception e) {
            // file not found  && write log away
            throw new RuntimeException();
        }
    }
}

