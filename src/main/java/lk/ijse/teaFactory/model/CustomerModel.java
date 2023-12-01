package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.CustomerDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public boolean customerSaved(final CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCusid());
        pstm.setString(2, dto.getEmpid());
        pstm.setString(3, dto.getCusname());
        pstm.setString(4, dto.getCusAddress());
        pstm.setString(5, dto.getCusCantac());

        boolean isSaved = pstm.executeUpdate() >0;

        return isSaved;
    }

    public List<CustomerDto> loadAll() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cusid = resultSet.getString(1);
            String empId = resultSet.getString(2);
            String cusname = resultSet.getString(3);
            String cusaddress = resultSet.getString(4);
            String contac = resultSet.getString(5);
           // String isCompleted = resultSet.getString(6);

            var dto = new CustomerDto(cusid,empId,cusname,cusaddress,contac);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean delete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateCustomer(final CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET emp_id = ?, cus_name = ?, cus_address = ? ,cus_cantac = ?  WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpid());
        pstm.setString(2, dto.getCusname());
        pstm.setString(3, dto.getCusAddress());
        pstm.setString(4, dto.getCusCantac());
     //   pstm.setString(5, "0");
        pstm.setString(5, dto.getCusid());

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextcusId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT customer_id FROM customer ORDER BY  customer_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);
            return splitOrderId(currentCusId);
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentCusId) {    //O008
        if (currentCusId != null) {
            String[] split = currentCusId.split("C");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "C00" + id;
        }
        return "C001";
    }

    public static List<CustomerDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<CustomerDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );

            dtoList.add(dto);
        }
        return dtoList;
    }

    public CustomerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String empid = resultSet.getString(2);
            String name = resultSet.getString(3);
            String address = resultSet.getString(4);
            String cantac = resultSet.getString(5);
            dto = new CustomerDto(cus_id, empid, name , address,cantac);
        }
        return dto;
    }

     public int cusCount() throws SQLException {
         int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

         String sql = "SELECT COUNT(customer_id) AS row_count FROM customer";

         try (PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()) {

             if (resultSet.next()) {
                  rowCount = resultSet.getInt("row_count");
                 System.out.println("Number of rows: " + rowCount);
             }
         }
         return rowCount;
     }


}
