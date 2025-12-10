import java.io.*;
import java.util.*;

// ----- Room Class -----
class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isBooked;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isBooked() { return isBooked; }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " | Category: " + category + " | Price: ₹" + price + " | " + (isBooked ? "Booked" : "Available");
    }
}

// ----- Reservation Class -----
class Reservation {
    private String guestName;
    private int roomNumber;
    private double amountPaid;
    private Date date;

    public Reservation(String guestName, int roomNumber, double amountPaid) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.amountPaid = amountPaid;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return date + " | Guest: " + guestName + " | Room: " + roomNumber + " | Paid: ₹" + amountPaid;
    }
}

// ----- Hotel System -----
class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private final String FILE_NAME = "bookings.txt";

    public Hotel() {
        loadRooms();
        loadBookingsFromFile();
    }

    private void loadRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 3000));
        rooms.add(new Room(202, "Deluxe", 3000));
        rooms.add(new Room(301, "Suite", 5500));
        rooms.add(new Room(302, "Suite", 5500));
    }

    public void displayAvailableRooms() {
        System.out.println("\n----- Available Rooms -----");
        for (Room r : rooms) {
            if (!r.isBooked()) {
                System.out.println(r);
            }
        }
    }

    public void viewAllRooms() {
        System.out.println("\n----- All Rooms -----");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    public void bookRoom(String guestName, int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                if (r.isBooked()) {
                    System.out.println("❌ Room already booked.");
                    return;
                }

                r.setBooked(true);
                Reservation reservation = new Reservation(guestName, roomNumber, r.getPrice());
                reservations.add(reservation);

                saveBookingToFile(reservation);

                System.out.println("✅ Booking successful for " + guestName + ", Room " + roomNumber +
                        "\nPayment of ₹" + r.getPrice() + " received.");
                return;
            }
        }
        System.out.println("❌ Invalid Room Number");
    }

    public void cancelReservation(int roomNumber) {
        Iterator<Reservation> iterator = reservations.iterator();

        while (iterator.hasNext()) {
            Reservation res = iterator.next();
            if (res.toString().contains("Room: " + roomNumber)) {
                iterator.remove();
                for (Room r : rooms) {
                    if (r.getRoomNumber() == roomNumber) {
                        r.setBooked(false);
                        break;
                    }
                }
                System.out.println("🗑 Reservation for Room " + roomNumber + " cancelled.");
                saveAllBookings();
                return;
            }
        }

        System.out.println("❌ No reservation found for that room!");
    }

    public void viewBookings() {
        System.out.println("\n----- Booking Records -----");
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    private void saveBookingToFile(Reservation reservation) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(reservation);
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    private void loadBookingsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("(Loaded previous booking) " + line);
            }
        } catch (IOException e) {
            System.out.println("No previous booking history found.");
        }
    }

    private void saveAllBookings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Reservation r : reservations) {
                writer.println(r);
            }
        } catch (IOException e) {
            System.out.println("Error updating booking file");
        }
    }
}

// ----- Main Application -----
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();
        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. View All Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View All Bookings");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> hotel.displayAvailableRooms();
                case 2 -> hotel.viewAllRooms();
                case 3 -> {
                    System.out.print("Enter Guest Name: ");
                    String guestName = sc.nextLine();
                    System.out.print("Enter Room Number to Book: ");
                    int roomNum = sc.nextInt();
                    hotel.bookRoom(guestName, roomNum);
                }
                case 4 -> {
                    System.out.print("Enter Room Number to Cancel: ");
                    int roomNum = sc.nextInt();
                    hotel.cancelReservation(roomNum);
                }
                case 5 -> hotel.viewBookings();
                case 0 -> System.out.println("Thank you for using the Hotel Reservation System!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}
