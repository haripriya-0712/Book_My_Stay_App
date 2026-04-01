import java.util.*;

class BookingRequest {
    int bookingId;
    String customerName;
    String roomType;

    public BookingRequest(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

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

class BookingService {

    private Queue<BookingRequest> queue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    private int roomCounter = 1;

    public BookingService() {
        inventory.put("AC", 2);
        inventory.put("NON-AC", 2);

        allocatedRooms.put("AC", new HashSet<>());
        allocatedRooms.put("NON-AC", new HashSet<>());
    }

    public void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println("Request added: " + request.customerName);
    }

    public void processRequests() {

        while (!queue.isEmpty()) {

            BookingRequest req = queue.poll();
            String type = req.roomType;

            if (!inventory.containsKey(type) || inventory.get(type) == 0) {
                System.out.println("Booking failed for " + req.customerName);
                continue;
            }

            String roomId;

            do {
                roomId = type + "-" + roomCounter++;
            } while (allocatedRooms.get(type).contains(roomId));

            allocatedRooms.get(type).add(roomId);
            inventory.put(type, inventory.get(type) - 1);

            Booking booking = new Booking(req.bookingId, req.customerName, type, roomId);

            System.out.println("Booking Confirmed: " + booking);
        }
    }

    public void viewInventory() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }

    public void viewAllocatedRooms() {
        System.out.println("Allocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + ": " + allocatedRooms.get(type));
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        service.addRequest(new BookingRequest(1, "Asha", "AC"));
        service.addRequest(new BookingRequest(2, "Ravi", "AC"));
        service.addRequest(new BookingRequest(3, "Kiran", "AC"));
        service.addRequest(new BookingRequest(4, "Meena", "NON-AC"));

        service.processRequests();

        service.viewInventory();
        service.viewAllocatedRooms();
    }
}
