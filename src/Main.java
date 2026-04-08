import java.io.*;
import java.util.*;

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    int bookingId;
    String customerName;
    String roomType;

    public Booking(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Booking> bookings;
    Map<String, Integer> inventory;

    public SystemState(List<Booking> bookings, Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Data restored successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Corrupted data. Starting with clean state.");
        }

        return new SystemState(new ArrayList<>(), new HashMap<>());
    }
}

public class UseCase12DataPersistenceRecovery {

    static List<Booking> bookings = new ArrayList<>();
    static Map<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {

        SystemState state = PersistenceService.load();
        bookings = state.bookings;
        inventory = state.inventory;

        if (inventory.isEmpty()) {
            inventory.put("AC", 5);
            inventory.put("NON-AC", 5);
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY SYSTEM ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addBooking(sc);
                    break;

                case 2:
                    viewBookings();
                    break;

                case 3:
                    viewInventory();
                    break;

                case 4:
                    shutdown();
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);
    }

    static void addBooking(Scanner sc) {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Room Type (AC/NON-AC): ");
        String type = sc.nextLine().toUpperCase();

        if (!inventory.containsKey(type) || inventory.get(type) == 0) {
            System.out.println("Room not available!");
            return;
        }

        bookings.add(new Booking(id, name, type));
        inventory.put(type, inventory.get(type) - 1);

        System.out.println("Booking successful!");
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    static void viewInventory() {
        System.out.println("Room Availability:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    static void shutdown() {
        System.out.println("Saving data before exit...");

        SystemState state = new SystemState(bookings, inventory);
        PersistenceService.save(state);

        System.out.println("System shutdown complete.");
    }
}
