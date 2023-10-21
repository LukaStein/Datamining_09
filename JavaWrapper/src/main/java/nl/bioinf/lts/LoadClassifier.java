package nl.bioinf.lts;

import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public class LoadClassifier {

    public Classifier loadClassifier() {
        try {
            SerializationHelper reader = new SerializationHelper();
            String NAMECLASSIFIER = "bagging_randomforest.model";
            return (Classifier) reader.read(NAMECLASSIFIER);
        } catch (Exception e) {
            // file not found  && write log away
            throw new RuntimeException();
        }
    }
}

