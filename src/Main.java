import java.util.*;

class GoodsBogie {
    String type;
    String cargo;

    GoodsBogie(String type, String cargo) {
        this.type = type;
        this.cargo = cargo;
    }

    public String toString() {
        return type + " -> " + cargo;
    }
}

public class TrainConsistUC12 {
    public static void main(String[] args) {
        List<GoodsBogie> bogies = new ArrayList<>();
        bogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        bogies.add(new GoodsBogie("Open", "Coal"));
        bogies.add(new GoodsBogie("Box", "Grain"));

        System.out.println("Goods Bogies:");
        bogies.forEach(System.out::println);
        boolean isSafe = bogies.stream()
                .allMatch(b ->
                        !b.type.equals("Cylindrical") ||
                                b.cargo.equals("Petroleum")
                );
        System.out.println("\nSafety Compliance Status:");
        if (isSafe) {
            System.out.println("Train is SAFE for operation");
        } else {
            System.out.println("Train is UNSAFE - Invalid cargo detected");
        }
    }
}