import java.util.*;

class Room {
    String type;
    int price;
    String amenities;

    public Room(String type, int price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String toString() {
        return "Type: " + type + ", Price: " + price + ", Amenities: " + amenities;
    }
}

class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public Inventory() {
        availability.put("AC", 2);
        availability.put("NON-AC", 0);
    }

    public int getAvailableCount(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return availability.keySet();
    }
}

class SearchService {

    private Map<String, Room> roomDetails = new HashMap<>();
    private Inventory inventory;

    public SearchService(Inventory inventory) {
        this.inventory = inventory;

        roomDetails.put("AC", new Room("AC", 2000, "WiFi, TV, AC"));
        roomDetails.put("NON-AC", new Room("NON-AC", 1000, "Fan, TV"));
    }

    public void searchRooms() {

        boolean found = false;

        for (String type : inventory.getRoomTypes()) {

            int count = inventory.getAvailableCount(type);

            if (count > 0) {
                System.out.println(roomDetails.get(type));
                System.out.println("Available: " + count);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available");
        }
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        SearchService searchService = new SearchService(inventory);

        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- ROOM SEARCH ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    searchService.searchRooms();
                    break;

                case 2:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 2);
    }
}
