package service;

import entities.Booking;
import entities.Room;
import entities.User;
import enums.RoomType;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Service {
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public Service() {
        rooms = new ArrayList<>();
        users = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            if (roomPricePerNight < 0) {
                throw new IllegalArgumentException("Room price cannot be negative");
            }

            // Find existing room
            Room existingRoom = findRoomByNumber(roomNumber);
            if (existingRoom != null) {
                // Update existing room without affecting bookings
                existingRoom.setRoomType(roomType);
                existingRoom.setRoomPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully");
            } else {
                // Create new room
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            // Validate dates
            if (checkIn.after(checkOut)) {
                throw new IllegalArgumentException("Check-in date must be before check-out date");
            }

            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                throw new IllegalArgumentException("User not found: " + userId);
            }

            if (room == null) {
                throw new IllegalArgumentException("Room not found: " + roomNumber);
            }

            // Calculate nights and total cost
            int nights = calculateNights(checkIn, checkOut);
            int totalCost = nights * room.getRoomPricePerNight();

            // Check user balance
            if (user.getBalance() < totalCost) {
                System.out.println("Booking failed: Insufficient balance for User " + userId +
                        ". Required: " + totalCost + ", Available: " + user.getBalance());
                return;
            }

            // Check room availability
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                System.out.println("Booking failed: Room " + roomNumber + " is not available for the specified period");
                return;
            }

            // Create booking and update user balance
            Booking booking = new Booking(userId, roomNumber, checkIn, checkOut,
                    room.getRoomType(), room.getRoomPricePerNight(), user.getBalance());
            bookings.add(booking);
            user.setBalance(user.getBalance() - totalCost);

            System.out.println("Booking successful for User " + userId + ", Room " + roomNumber +
                    " for " + nights + " nights. Total cost: " + totalCost);

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public void setUser(int userId, int balance) {
        try {
            if (balance < 0) {
                throw new IllegalArgumentException("User balance cannot be negative");
            }

            User existingUser = findUserById(userId);
            if (existingUser != null) {
                existingUser.setBalance(balance);
                System.out.println("User " + userId + " balance updated to " + balance);
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created with balance " + balance);
            }
        } catch (Exception e) {
            System.out.println("Error setting user: " + e.getMessage());
        }
    }

    public void printAll() {
        System.out.println("\n=== ALL ROOMS AND BOOKINGS (Latest to Oldest) ===");

        // Print rooms (latest to oldest)
        System.out.println("\n--- ROOMS ---");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        // Print bookings (latest to oldest)
        System.out.println("\n--- BOOKINGS ---");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
    }

    public void printAllUsers() {
        System.out.println("\n=== ALL USERS (Latest to Oldest) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    // Helper methods
    private User findUserById(int userId) {
        return users.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
    }

    private Room findRoomByNumber(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
    }

    private int calculateNights(Date checkIn, Date checkOut) {
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                // Check for date overlap
                if (!(checkOut.before(booking.getCheckIn()) || checkIn.after(booking.getCheckOut()))) {
                    return false;
                }
            }
        }
        return true;
    }
}
