import java.util.LinkedHashSet;

public class TrainConsistUC5 {
    public static void main(String[] args) {
        LinkedHashSet<String> train = new LinkedHashSet<>();
        train.add("Engine");
        train.add("Sleeper");
        train.add("Cargo");
        train.add("Guard");
        train.add("Sleeper");
        System.out.println("Final Train Formation:");
        System.out.println(train);
    }
}