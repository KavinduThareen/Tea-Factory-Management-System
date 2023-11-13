package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    // not complet countiniue this

    public boolean customerSaved(final CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCusid());
        pstm.setString(2, dto.getEmpid());
        pstm.setString(3, dto.getCusname());
        pstm.setString(4, dto.getCusAddress());
        pstm.setString(5, dto.getCusCantac());
        pstm.setString(6,"0");

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
            String isCompleted = resultSet.getString(6);

            var dto = new CustomerDto(cusid,empId,cusname,cusaddress,contac,isCompleted);
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

        String sql = "UPDATE customer SET emp_id = ?, cus_name = ?, cus_address = ? ,cus_cantac = ?, isCompleted = ?   WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpid());
        pstm.setString(2, dto.getCusname());
        pstm.setString(3, dto.getCusAddress());
        pstm.setString(4, dto.getCusCantac());
        pstm.setString(5, "0");
        pstm.setString(6, dto.getCusid());

        return pstm.executeUpdate() > 0;
    }

}
