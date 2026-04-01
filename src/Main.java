import java.util.*;

class Booking {
    int bookingId;
    String customerName;
    String roomType;
    String roomId;

    public Booking(int bookingId, String customerName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String toString() {
        return "ID: " + bookingId + ", Name: " + customerName +
               ", RoomType: " + roomType + ", RoomID: " + roomId;
    }
}

class BookingSystem {

    private Map<Integer, Booking> bookings = new HashMap<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Stack<String>> releasedRooms = new HashMap<>();

    private int roomCounter = 1;

    public BookingSystem() {
        inventory.put("AC", 2);
        inventory.put("NON-AC", 2);

        releasedRooms.put("AC", new Stack<>());
        releasedRooms.put("NON-AC", new Stack<>());
    }

    public void addBooking(int id, String name, String type) {

        if (!inventory.containsKey(type) || inventory.get(type) == 0) {
            System.out.println("Room not available");
            return;
        }

        String roomId;

        if (!releasedRooms.get(type).isEmpty()) {
            roomId = releasedRooms.get(type).pop();
        } else {
            roomId = type + "-" + roomCounter++;
        }

        Booking booking = new Booking(id, name, type, roomId);
        bookings.put(id, booking);

        inventory.put(type, inventory.get(type) - 1);

        System.out.println("Booking Confirmed: " + booking);
    }

    public void cancelBooking(int id) {

        if (!bookings.containsKey(id)) {
            System.out.println("Invalid Booking ID");
            return;
        }

        Booking booking = bookings.remove(id);

        releasedRooms.get(booking.roomType).push(booking.roomId);

        inventory.put(booking.roomType, inventory.get(booking.roomType) + 1);

        System.out.println("Booking Cancelled: " + booking);
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No active bookings");
            return;
        }

        for (Booking b : bookings.values()) {
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

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY ---");
            System.out.println("1. Add Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
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
                    System.out.print("Enter Booking ID to cancel: ");
                    int cancelId = sc.nextInt();
                    system.cancelBooking(cancelId);
                    break;

                case 3:
                    system.viewBookings();
                    break;

                case 4:
                    system.viewInventory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }
}
