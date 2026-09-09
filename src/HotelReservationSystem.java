import javax.management.Query;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;
import java.util.SortedMap;

public class HotelReservationSystem {
    private final static String URL = "jdbc:mysql://localhost:3306/hotel_db";
    private final static String USER = "root";
    private final static String PASS = "ikramsql@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } // driver loaded successfully
        try {
            // connection created successfully.
            Connection connection = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("------------------Hotel Reservation System------------------");
            while(true) {
                System.out.println();
                Scanner sc = new Scanner(System.in);
                System.out.println("1. New Registration : ");
                System.out.println("2. Check Reservation : ");
                System.out.println("3. Room Details : ");
                System.out.println("4. Update Reservation : ");
                System.out.println("5. Check The Total Guest : ");
                System.out.println("6. Remove The Guest : ");
                System.out.println("7. Exit : ");
                System.out.print("Choose the number : ");
                int choice = sc.nextInt();

                if (choice == 1) {
                    System.out.println("----------------------------Enter the Guest Details.----------------------------------------");
                    System.out.print("Guest Name : ");
                    String guest_name = sc.next();
                    System.out.print("Room No : ");
                    int room_num = sc.nextInt();
                    System.out.print("Contact No : ");
                    String contact_no = sc.next();
                    Methods.newRegistration(connection, guest_name, room_num, contact_no);
                    System.out.println("Guest Data Updated Successfully.");
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }

                } else if (choice == 2) {
                    System.out.println("------------------------Reservation Details------------------------");
                    System.out.print("Enter The Guest Id : ");
                    int reservation_id = sc.nextInt();
                    Methods.checkReservation(connection,reservation_id);
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }
                } else if (choice == 3) {
                    System.out.println("---------------------------------Room Details ---------------------");
                    System.out.print("Enter The Guest Id : ");
                    int reservation_id = sc.nextInt();
                    Methods.getRoomNum(connection,reservation_id);
                } else if (choice == 4) {
                    System.out.println("---------------------------------Update Guest Reservation--------------------");
                    System.out.print("Enter The Guest Reservation Id : ");
                    int reservation_id = sc.nextInt();
                    Methods.updateReservation(connection,reservation_id);
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }

                } else if (choice == 5){
                    System.out.println("-----------------------------------Total Guest---------------------------------");
                    System.out.println();
                    Methods.getTotalEmployee(connection);
//                    System.out.println();
                } else if (choice == 6) {
                    System.out.println("--------------------------------Delete Guest Data------------------------------");
                    System.out.println();
                    System.out.print("Enter The Guest Id : ");
                    int guestId = sc.nextInt();
                    Methods.delGuest(connection,guestId);
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }
                } else if(choice == 7){
                    break;
                }else{
                    System.out.println("Please choose valid choice.");
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}


