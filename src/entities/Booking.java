package entities;

import enums.RoomType;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private int nights;

    // Store room and user data at booking time
    private RoomType roomType;
    private int roomPricePerNight;
    private int userBalance;

    private static int bookingCounter = 1;

    //ctor
    public Booking(int userId, int roomNumber, Date checkIn, Date checkOut,
                   RoomType roomType, int roomPricePerNight, int userBalance) {
        this.bookingId = bookingCounter++;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomType = roomType;
        this.roomPricePerNight = roomPricePerNight;
        this.userBalance = userBalance;
        this.nights = calculateNights(checkIn, checkOut);
        this.totalCost = nights * roomPricePerNight;
    }

    private int calculateNights(Date checkIn, Date checkOut) {
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // Getters
    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getRoomNumber() { return roomNumber; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public int getTotalCost() { return totalCost; }
    public int getNights() { return nights; }
    public RoomType getRoomType() { return roomType; }
    public int getRoomPricePerNight() { return roomPricePerNight; }
    public int getUserBalance() { return userBalance; }

    @Override
    public String toString() {
        return "Booking{bookingId=" + bookingId + ", userId=" + userId + ", roomNumber=" + roomNumber +
                ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", nights=" + nights +
                ", totalCost=" + totalCost + ", roomType=" + roomType.getType() +
                ", roomPricePerNight=" + roomPricePerNight + ", userBalanceAtBooking=" + userBalance + "}";
    }
}
