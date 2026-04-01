import java.util.LinkedList;

public class TrainConsistUC4 {
    public static void main(String[] args) {

        LinkedList<String> train = new LinkedList<>();
        train.add("Engine");
        train.add("Sleeper");
        train.add("AC");
        train.add("Cargo");
        train.add("Guard");

        System.out.println("Initial Train Consist:");
        System.out.println(train);
        train.add(2, "Pantry");

        System.out.println("\nAfter adding Pantry at position 2:");
        System.out.println(train);
        train.removeFirst();
        train.removeLast();
        System.out.println("\nFinal Train Consist:");
        System.out.println(train);
    }
}