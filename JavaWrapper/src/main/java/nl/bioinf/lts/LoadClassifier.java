package nl.bioinf.lts;

import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

import java.io.InputStream;

public class LoadClassifier {

    public Classifier loadClassifier() {
        try {
            String NAMECLASSIFIER = "bagging_randomforest.model";
            InputStream CLASSIFIER = getClass().getClassLoader().getResourceAsStream(NAMECLASSIFIER);
            return (Classifier) SerializationHelper.read(CLASSIFIER);
        } catch (Exception e) {
            // file not found  && write log away
            throw new RuntimeException();
        }
    }
}

