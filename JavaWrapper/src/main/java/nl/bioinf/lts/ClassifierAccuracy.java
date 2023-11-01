package nl.bioinf.lts;

import weka.core.Instance;
import weka.core.Instances;
import java.text.DecimalFormat;
import java.util.List;

//   a   b   <-- classified as
//        144   3 |   a = sick
//        12  36 |   b = healthy
public class ClassifierAccuracy {
    public int TP = 0;
    private int FP = 0;
    private int TN = 0;
    private int FN = 0;
    private String sick = "sick";
    private String healthy = "healthy";


    public void confusionMatrix(Instances data, List<String> predictionLabel){
        for (int row = 0; row < data.size(); row++){
            Instance supervisedInstance = data.instance(row);
            double classIndex = supervisedInstance.classValue();
            String supervisedLabel = data.classAttribute().value((int) classIndex);
            if ((supervisedLabel.equals(sick) && predictionLabel.get(row).equals(sick))){
                TP += 1;
            }
            if ((supervisedLabel.equals(sick) && predictionLabel.get(row).equals(healthy))){
                FP += 1;
            }
            if ((supervisedLabel.equals(healthy) && predictionLabel.get(row).equals(healthy))){
                TN += 1;
            }
            if ((supervisedLabel.equals(healthy) && predictionLabel.get(row).equals(sick))){
                FN += 1;
            }
        }
    }


    public String confusionMatrixToString(){
        return "\n\na   b   <-- classified as\n"
                + TP + " " + FN + " | " +  "a = sick\n"
                + FP + " " +  TN + " | " + "b = healthy";
    }
    public String summary(List<String> predictions){
        double numInstances = predictions.size();
        double correct = ((TP + TN)/numInstances) * 100;
        double incorrect = ((FP + FN)/numInstances) * 100;
        DecimalFormat twoDec = new DecimalFormat("##.00");
        return "\nSummary:\n" +
                "Correct:\t" + twoDec.format(correct) + " %\n" +
                "Incorrect:\t" + twoDec.format(incorrect) + " %\n";
    }
}
