import java.util.*;

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

class BookingHistory {
    private List<Booking> history = new ArrayList<>();

    public void add(Booking booking) {
        history.add(booking);
    }

    public List<Booking> getAll() {
        return history;
    }
}

class ReportService {

    public void generateReport(List<Booking> bookings) {

        if (bookings.isEmpty()) {
            System.out.println("No booking history available");
            return;
        }

        int total = bookings.size();
        int acCount = 0;
        int nonAcCount = 0;

        for (Booking b : bookings) {
            if (b.roomType.equals("AC")) acCount++;
            else if (b.roomType.equals("NON-AC")) nonAcCount++;
        }

        System.out.println("\n--- BOOKING REPORT ---");
        System.out.println("Total Bookings: " + total);
        System.out.println("AC Bookings: " + acCount);
        System.out.println("NON-AC Bookings: " + nonAcCount);
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private BookingHistory history = new BookingHistory();

    public BookingSystem() {
        inventory.put("AC", 2);
        inventory.put("NON-AC", 2);
    }

    public void addBooking(int id, String name, String type) {

        if (!inventory.containsKey(type) || inventory.get(type) == 0) {
            System.out.println("Room not available");
            return;
        }

        Booking booking = new Booking(id, name, type);
        inventory.put(type, inventory.get(type) - 1);

        history.add(booking);

        System.out.println("Booking Confirmed: " + booking);
    }

    public void viewHistory() {
        List<Booking> list = history.getAll();

        if (list.isEmpty()) {
            System.out.println("No booking history");
            return;
        }

        for (Booking b : list) {
            System.out.println(b);
        }
    }

    public void generateReport() {
        ReportService rs = new ReportService();
        rs.generateReport(history.getAll());
    }

    public void viewInventory() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
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
                    system.viewHistory();
                    break;

                case 3:
                    system.generateReport();
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
