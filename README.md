# Hotel Reservation System 🏨

**Skypay Technical Test 2** - A simplified hotel reservation system implemented in Java.

## 📋 Overview

This project implements a hotel reservation system that manages three main entities:
- **Users** (with balance management)
- **Rooms** (with types and pricing)
- **Bookings** (with date validation and availability checking)

## 🏗️ Architecture

```
src/
├── entities/
│   ├── User.java          # User entity with balance
│   ├── Room.java          # Room entity with type and pricing
│   └── Booking.java       # Booking entity with date management
├── enums/
│   └── RoomType.java      # Room types (STANDARD, JUNIOR, SUITE)
├── service/
│   └── Service.java       # Main service handling all operations
└── Main.java              # Test runner with specified test cases
```

## ✨ Features

- ✅ User creation and balance management
- ✅ Room creation with type and pricing
- ✅ Booking validation (balance, availability, dates)
- ✅ Room updates without affecting existing bookings
- ✅ Complete booking history preservation
- ✅ Exception handling for all edge cases

## 🚀 How to Run

### Prerequisites
- Java 11 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Option 1: Using IDE
1. Clone the repository
2. Open the project in your IDE
3. Run `Main.java`

### Option 2: Command Line
```bash
# Clone the repository
git clone https://github.com/ziliwalid/TestSkyPayHotel.git
cd TestSkyPayHotel

# Compile
javac -d out -sourcepath src src/*.java src/entities/*.java src/enums/*.java src/service/*.java

# Run
java -cp out Main
```

## 📊 Test Results

The system executes the following test scenario:

1. **Create 3 rooms:** Standard (1000/night), Junior (2000/night), Suite (3000/night)
2. **Create 2 users:** User 1 (5000 balance), User 2 (10000 balance)
3. **Execute booking attempts:**
   - ❌ User 1 → Room 2 (7 nights) - Insufficient balance
   - ❌ User 1 → Room 2 (invalid dates) - Date validation error
   - ✅ User 1 → Room 1 (1 night) - Success
   - ❌ User 2 → Room 1 (2 nights) - Room unavailable (conflict)
   - ✅ User 2 → Room 3 (1 night) - Success
4. **Update Room 1** to suite type with new pricing
5. **Verify** that existing bookings preserve original data

### Expected Output
```
=== BOOKING ATTEMPTS ===
1. User 1 booking Room 2: Booking failed: Insufficient balance
2. User 1 booking Room 2: Booking failed: Check-in date must be before check-out date
3. User 1 booking Room 1: Booking successful for 1 nights. Total cost: 1000
4. User 2 booking Room 1: Booking failed: Room not available
5. User 2 booking Room 3: Booking successful for 1 nights. Total cost: 3000

Final balances: User 1: 4000, User 2: 7000
```

## 🏛️ Key Design Decisions

### 1. **Booking Data Preservation**
When `setRoom()` updates a room, existing bookings maintain their original data (snapshot approach):
- ✅ **Business Logic:** Matches real hotel practices (price honored at booking time)
- ✅ **Performance:** O(1) data access vs O(n) historical calculations
- ✅ **Data Integrity:** Immutable booking records prevent corruption

### 2. **Exception Handling**
Comprehensive validation for:
- Negative balances and prices
- Invalid date ranges
- Non-existent users/rooms
- Insufficient funds
- Room availability conflicts

### 3. **Data Ordering**
All print functions display data from latest to oldest as specified.
