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
                Scanner sc = new Scanner(System.in);
                System.out.println("1. New Registration : ");
                System.out.println("2. Check Reservation : ");
                System.out.println("3. Room Details : ");
                System.out.println("4. Update Reservation : ");
                System.out.println("5. Exit : ");
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
                    newRegistration(connection, guest_name, room_num, contact_no);
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
                    System.out.print("Enter the reservation id : ");
                    int reservation_id = sc.nextInt();
                    checkReservation(connection,reservation_id);
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }
                } else if (choice == 3) {
                    System.out.println("---------------------------------Room Details ---------------------");
                    System.out.print("Enter the reservation_id : ");
                    int reservation_id = sc.nextInt();
                    getRoomNum(connection,reservation_id);
                } else if (choice == 4) {
                    System.out.println("---------------------------------Update Guest Reservation--------------------");
                    System.out.print("Enter the Guest Reservation Id : ");
                    int reservation_id = sc.nextInt();
                    updateReservation(connection,reservation_id);
                    System.out.print("Do you want to continue : (Y/N) -> ");
                    String yesNo = sc.next();
                    if (yesNo.equalsIgnoreCase("y")){
                        continue;
                    }else {
                        break;
                    }

                } else if(choice == 5){
                    break;
                }else{
                    System.out.println("Please choose valid choice.");
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //    --------------- ab hum har option ke liye methods bnayege ----------------------------------------
//        1. for registration
    public static void newRegistration(Connection connection, String guest_name, int room_no, String contact_no) throws SQLException {
        String Query = "INSERT INTO reservation(guest_name,room_num,contact_num) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(Query);
        ps.setString(1,guest_name);
        ps.setInt(2,room_no);
        ps.setString(3,contact_no);
        ps.executeUpdate();
    }
//    2. for check the guest reservation
    public  static  void  checkReservation(Connection connection, int reservation_id) throws SQLException{
        String query = "SELECT * FROM reservation WHERE reservation_id = ? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,reservation_id);
        ResultSet rs = ps.executeQuery(); // bcz we want to display data from the table
        if (rs.next()){
            System.out.println("Reservation Details : ");
            System.out.println("Reservation_Id : "+ rs.getInt("reservation_id"));
            System.out.println("Name : "+rs.getString("guest_name"));
            System.out.println("Room Num : "+rs.getInt("room_num"));
            System.out.println("Contact Num : "+rs.getString("contact_num"));
            System.out.println("Reservation Date : "+rs.getTimestamp("reservation_date"));
        }else {
            System.out.println("Guest does not exist in database.");
        }
    }
//   get room num
    public  static  void getRoomNum(Connection connection, int reservation_id) throws SQLException{
        String query = "SELECT * FROM reservation WHERE reservation_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,reservation_id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            System.out.println("Guest Name : "+rs.getString("guest_name"));
            System.out.println("Room Num : "+rs.getInt("room_num"));
            System.out.println("Reservation Date : "+rs.getTimestamp("reservation_date"));
        }else {
            System.out.println("Reservation id does not exist in database.");
        }
    }
// Update reservation --------------------
    public static void updateReservation(Connection connection, int reservation_id) throws SQLException {
        String tableQuery = "SELECT * FROM reservation WHERE reservation_id = ?";
        PreparedStatement tablePs = connection.prepareStatement(tableQuery);
        tablePs.setInt(1,reservation_id);
        ResultSet tableRs = tablePs.executeQuery();

        if (tableRs.next()){
            String updateQuery = "UPDATE reservation SET guest_name = ?, room_num = ?, contact_num = ? WHERE reservation_id = ?";
            PreparedStatement updatePs = connection.prepareStatement(updateQuery);

            Scanner sc = new Scanner(System.in);
            System.out.print("Update Guest Name : ");
            String newGuestName = sc.next();
            System.out.print("Update Guest Room Num : ");
            int newGuestRoomNum = sc.nextInt();
            System.out.print("Update Guest Contact Num : ");
            String newContactNum = sc.next();

//            System.out.println(newContactNum);
//            System.out.println(newGuestName);
//            System.out.println(newGuestName);

            updatePs.setString(1,newGuestName);
            updatePs.setInt(2,newGuestRoomNum);
            updatePs.setString(3,newContactNum);
            updatePs.setInt(4,reservation_id);

            updatePs.executeUpdate(); // now query updated.

            System.out.println("Data Updated Successfully.");
        }else {
            System.out.println("This Reservation Id Data Does Not Exist.");
        }
    }

}


