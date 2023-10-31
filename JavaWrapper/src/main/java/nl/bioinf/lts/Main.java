package nl.bioinf.lts;
//javac -cp .:/commons/java/weka-3-8-5/weka.jar Main.java
//java -cp .:/commons/java/weka-3-8-5/weka.jar Main.java


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, starting WrapClassifier...");
            Main main = new Main();
            main.start(args);
            System.out.println("Closing WrapClassifier, goodbye...");
        } catch (RuntimeException e) {
            Controller controller = new Controller();
            controller.printHelp();
        }
    }

    private void start(String[] args) {

        // Instantiate controller && pass args to controller
        Controller controller = new Controller();
        controller.args = args;
        // Laad data
        controller.chooseOutput();
    }
}