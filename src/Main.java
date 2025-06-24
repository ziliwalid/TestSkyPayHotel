import enums.RoomType;
import service.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("=== HOTEL RESERVATION SYSTEM TEST ===\n");

            // Create 3 rooms
            System.out.println("Creating rooms...");
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setRoom(3, RoomType.SUITE, 3000);

            // Create 2 users
            System.out.println("\nCreating users...");
            service.setUser(1, 5000);
            service.setUser(2, 10000);

            System.out.println("\n=== BOOKING ATTEMPTS ===");

            // Test cases as specified in the document
            Date checkIn1 = sdf.parse("30/06/2026");
            Date checkOut1 = sdf.parse("07/07/2026");
            System.out.println("\n1. User 1 booking Room 2 from 30/06/2026 to 07/07/2026:");
            service.bookRoom(1, 2, checkIn1, checkOut1);

            Date checkIn2 = sdf.parse("07/07/2026");
            Date checkOut2 = sdf.parse("30/06/2026");
            System.out.println("\n2. User 1 booking Room 2 from 07/07/2026 to 30/06/2026:");
            service.bookRoom(1, 2, checkIn2, checkOut2);

            Date checkIn3 = sdf.parse("07/07/2026");
            Date checkOut3 = sdf.parse("08/07/2026");
            System.out.println("\n3. User 1 booking Room 1 from 07/07/2026 to 08/07/2026:");
            service.bookRoom(1, 1, checkIn3, checkOut3);

            Date checkIn4 = sdf.parse("07/07/2026");
            Date checkOut4 = sdf.parse("09/07/2026");
            System.out.println("\n4. User 2 booking Room 1 from 07/07/2026 to 09/07/2026:");
            service.bookRoom(2, 1, checkIn4, checkOut4);

            Date checkIn5 = sdf.parse("07/07/2026");
            Date checkOut5 = sdf.parse("08/07/2026");
            System.out.println("\n5. User 2 booking Room 3 from 07/07/2026 to 08/07/2026:");
            service.bookRoom(2, 3, checkIn5, checkOut5);

            System.out.println("\n6. Updating Room 1 to suite type with price 10000:");
            service.setRoom(1, RoomType.SUITE, 10000);

            // Print final results
            service.printAll();
            service.printAllUsers();

        } catch (Exception e) {
            System.out.println("Error in test execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
