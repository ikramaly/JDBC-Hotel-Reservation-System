import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Methods {

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
            System.out.println("This reservation id doest not exist in record.");
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
            System.out.println("Reservation id does not exist in record.");
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
            System.out.println("This Reservation Id Data Does Not Exist In Record.");
        }
    }
    //    ---------------------Count the total emp in databases--------------------------------
    public static void getTotalEmployee(Connection connection) throws SQLException{
        String query = "SELECT reservation_id FROM reservation";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        int totalGuest = 0;
        while (rs.next()){
            totalGuest+=1;
        }
        System.out.println("Total Guest Available In The Hotel : "+totalGuest);
    }
//    ---------------------------- delete employee-----------------------------

    public static  void delGuest(Connection connection,int reservation_id) throws SQLException{
        String query = "DELETE FROM reservation WHERE reservation_id = ?";
        PreparedStatement ps = connection.prepareCall(query);
        ps.setInt(1,reservation_id);
        int rowAffected = ps.executeUpdate(); // if query will be successfully it will return 1 it means table row affected.
        if (rowAffected==1){
            System.out.println("Guest Data Deleted Successfully.");
        }else {
            System.out.println("Reservation id "+reservation_id+" does not exist.");
        }
    }
}
