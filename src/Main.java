import java.util.*;

class Reservation {
    int bookingId;
    String customerName;
    String roomType;

    public Reservation(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String toString() {
        return "ID: " + bookingId + ", Name: " + customerName + ", Room: " + roomType;
    }
}

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added: " + r);
    }

    public void viewRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests");
            return;
        }

        System.out.println("Booking Requests in Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    public void processNextRequest() {
        if (queue.isEmpty()) {
            System.out.println("No requests to process");
            return;
        }

        Reservation r = queue.poll();
        System.out.println("Processing request: " + r);
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- BOOK MY STAY ---");
            System.out.println("1. Add Booking Request");
            System.out.println("2. View Requests");
            System.out.println("3. Process Next Request");
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

                    System.out.print("Enter Room Type: ");
                    String type = sc.nextLine();

                    queue.addRequest(new Reservation(id, name, type));
                    break;

                case 2:
                    queue.viewRequests();
                    break;

                case 3:
                    queue.processNextRequest();
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
