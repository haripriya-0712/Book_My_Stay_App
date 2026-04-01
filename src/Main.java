import java.util.*;
import java.util.stream.Collectors;

class Bogie {
    String name;
    int capacity;

    Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String toString() {
        return name + " -> " + capacity;
    }
}

public class TrainConsistUC8 {
    public static void main(String[] args) {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));

        System.out.println("Original Bogies:");
        bogies.forEach(System.out::println);
        List<Bogie> filteredBogies = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());
        System.out.println("\nFiltered Bogies (Capacity > 60):");
        filteredBogies.forEach(System.out::println);
    }
}