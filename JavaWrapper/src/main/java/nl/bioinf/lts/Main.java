package nl.bioinf.lts;
//java -jar .\build\libs\ParkinsonDysphonia-ML0.43-SNAPSHOT.jar

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, starting WrapClassifier...");
            Main main = new Main();
            main.start(args);
            System.out.println("Closing WrapClassifier, goodbye...");
        } catch (RuntimeException e) {
            Controller controller = new Controller();
            e.printStackTrace();
//            controller.printHelp();
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