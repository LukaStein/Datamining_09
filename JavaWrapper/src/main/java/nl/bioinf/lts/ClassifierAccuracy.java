package nl.bioinf.lts;

import weka.core.Instances;
import java.text.DecimalFormat;
import java.util.Arrays;

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
    public void confusionMatrix(Instances data, Instances predictions){
        for (int logRow = 0; logRow < predictions.size(); logRow++){
            String[] firstInstanceData = data.get(logRow).toString().split(",");
            String[] firstInstancePred = predictions.get(logRow).toString().split(",");
            String dataClassifier = firstInstanceData[firstInstanceData.length-1];
            String predClassifier = firstInstancePred[firstInstancePred.length-1];
            if ((dataClassifier.equals(sick) && predClassifier.equals(sick))){
                TP += 1;
            }
            if ((dataClassifier.equals(sick) && predClassifier.equals(healthy))){
                FP += 1;
            }
            if ((dataClassifier.equals(healthy) && predClassifier.equals(healthy))){
                TN += 1;
            }
            if ((dataClassifier.equals(healthy) && predClassifier.equals(sick))){
                FN += 1;
            }
        }
    }


    public String confusionMatrixToString(){
        return "a   b   <-- classified as\n"
                + TP + " " + FN + " | " +  "a = sick\n"
                + FP + " " +  TN + " | " + "b = healthy";
    }
    public String summary(Instances predictions){
        double numInstances = predictions.numInstances();
        double correct = ((TP + TN)/numInstances) * 100;
        double incorrect = ((FP + FN)/numInstances) * 100;
        DecimalFormat twoDec = new DecimalFormat("##.00");
        return "\nSummary:\n" +
                "Correct:\t" + twoDec.format(correct) + " %\n" +
                "Incorrect:\t" + twoDec.format(incorrect) + " %";
    }
}
