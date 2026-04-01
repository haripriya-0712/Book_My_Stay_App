import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Booking {
    int bookingId;
    String customerName;
    String roomType;

    public Booking(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String toString() {
        return "ID: " + bookingId + ", Name: " + customerName + ", Room: " + roomType;
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();

    public BookingSystem() {
        inventory.put("AC", 2);
        inventory.put("NON-AC", 2);
    }

    public void validate(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type");
        }

        if (inventory.get(type) <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }
    }

    public void addBooking(int id, String name, String type) {
        try {
            validate(type);

            Booking booking = new Booking(id, name, type);
            bookings.add(booking);

            inventory.put(type, inventory.get(type) - 1);

            System.out.println("Booking successful: " + booking);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available");
            return;
        }

        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    public void viewInventory() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Booking ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type (AC/NON-AC): ");
                    String type = sc.nextLine().toUpperCase();

                    system.addBooking(id, name, type);
                    break;

                case 2:
                    system.viewBookings();
                    break;

                case 3:
                    system.viewInventory();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 4);
    }
}
