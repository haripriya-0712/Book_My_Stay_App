import java.util.*;

class Booking {
    int roomNumber;
    String customerName;

    Booking(int roomNumber, String customerName) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
    }
}

public class Main {
    public static void main(String[] args) {

        Queue<Booking> queue = new LinkedList<>();
        Set<Integer> bookedRooms = new HashSet<>();

        // Add bookings
        if (!bookedRooms.contains(101)) {
            queue.add(new Booking(101, "Alice"));
            bookedRooms.add(101);
        }

        if (!bookedRooms.contains(102)) {
            queue.add(new Booking(102, "Bob"));
            bookedRooms.add(102);
        }

        // Process bookings (FIFO)
        while (!queue.isEmpty()) {
            Booking b = queue.poll();
            System.out.println("Room " + b.roomNumber + " booked by " + b.customerName);
        }
    }
}