import java.util.*;

class BookingRequest {
    int id;
    String customerName;
    String roomType;

    public BookingRequest(int id, String customerName, String roomType) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class BookingSystem {
    private Queue<BookingRequest> queue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingSystem() {
        inventory.put("AC", 2);
        inventory.put("NON-AC", 2);
    }

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println("Request added: " + request.customerName + " (" + request.roomType + ")");
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }

    public synchronized void processBooking(BookingRequest request) {
        if (request == null) return;

        String type = request.roomType;

        if (inventory.containsKey(type) && inventory.get(type) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            System.out.println(Thread.currentThread().getName() +
                    " booked for " + request.customerName + " (" + type + ")");
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed for " + request.customerName + " (" + type + ")");
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

class BookingProcessor extends Thread {
    private BookingSystem system;

    public BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (system) {
                request = system.getRequest();
            }

            if (request == null) break;

            system.processBooking(request);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.addRequest(new BookingRequest(1, "Asha", "AC"));
        system.addRequest(new BookingRequest(2, "Ravi", "AC"));
        system.addRequest(new BookingRequest(3, "Kiran", "AC"));
        system.addRequest(new BookingRequest(4, "Meena", "NON-AC"));
        system.addRequest(new BookingRequest(5, "John", "NON-AC"));

        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        system.displayInventory();
    }
}
