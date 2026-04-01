class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}

class PassengerBogie {
    String type;
    int capacity;

    PassengerBogie(String type, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.type = type;
        this.capacity = capacity;
    }

    public String toString() {
        return type + " -> " + capacity;
    }
}

public class TrainConsistUC14 {
    public static void main(String[] args) {

        try {
            PassengerBogie b1 = new PassengerBogie("Sleeper", 72);
            System.out.println(b1);

            PassengerBogie b2 = new PassengerBogie("AC Chair", 0);
            System.out.println(b2);

        } catch (InvalidCapacityException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}