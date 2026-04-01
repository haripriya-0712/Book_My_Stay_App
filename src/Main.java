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

class AddOnService {
    String serviceName;
    int cost;

    public AddOnService(String serviceName, int cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String toString() {
        return serviceName + " (" + cost + ")";
    }
}

class AddOnServiceManager {

    private Map<Integer, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(int bookingId, AddOnService service) {
        serviceMap.putIfAbsent(bookingId, new ArrayList<>());
        serviceMap.get(bookingId).add(service);
    }

    public List<AddOnService> getServices(int bookingId) {
        return serviceMap.getOrDefault(bookingId, new ArrayList<>());
    }

    public int calculateTotalCost(int bookingId) {
        int total = 0;
        for (AddOnService s : getServices(bookingId)) {
            total += s.cost;
        }
        return total;
    }
}

class BookingSystem {

    private Map<Integer, Booking> bookings = new HashMap<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private AddOnServiceManager serviceManager = new AddOnServiceManager();

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
        bookings.put(id, booking);

        inventory.put(type, inventory.get(type) - 1);

        System.out.println("Booking Confirmed: " + booking);
    }

    public void addServiceToBooking(int id, String serviceName, int cost) {

        if (!bookings.containsKey(id)) {
            System.out.println("Invalid Booking ID");
            return;
        }

        serviceManager.addService(id, new AddOnService(serviceName, cost));

        System.out.println("Service added to booking " + id);
    }

    public void viewServices(int id) {

        if (!bookings.containsKey(id)) {
            System.out.println("Invalid Booking ID");
            return;
        }

        List<AddOnService> services = serviceManager.getServices(id);

        if (services.isEmpty()) {
            System.out.println("No services added");
            return;
        }

        for (AddOnService s : services) {
            System.out.println(s);
        }

        System.out.println("Total Add-On Cost: " + serviceManager.calculateTotalCost(id));
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available");
            return;
        }

        for (Booking b : bookings.values()) {
            System.out.println(b);
        }
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY ---");
            System.out.println("1. Add Booking");
            System.out.println("2. Add Service");
            System.out.println("3. View Services");
            System.out.println("4. View Bookings");
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
                    System.out.print("Enter Booking ID: ");
                    int bid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Service Name: ");
                    String sname = sc.nextLine();

                    System.out.print("Enter Cost: ");
                    int cost = sc.nextInt();

                    system.addServiceToBooking(bid, sname, cost);
                    break;

                case 3:
                    System.out.print("Enter Booking ID: ");
                    int vid = sc.nextInt();

                    system.viewServices(vid);
                    break;

                case 4:
                    system.viewBookings();
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
