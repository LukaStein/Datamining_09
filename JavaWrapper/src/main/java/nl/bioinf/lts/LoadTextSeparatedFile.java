package nl.bioinf.lts;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class LoadTextSeparatedFile {
    public Instances loadData(String FILENAME) {
        try {
            // load dataset
            ConverterUtils.DataSource reader = new ConverterUtils.DataSource(FILENAME);
            Instances data = reader.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e){
            // file not found  && write log away
            throw new RuntimeException();
        }
    }
}
