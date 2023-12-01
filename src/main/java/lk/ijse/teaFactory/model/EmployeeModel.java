package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.EmployeeDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean employeeSave( EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        boolean isSaved = false;

        String sql =  "INSERT INTO employee VALUES(?, ?, ?, ?,?,? ,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getUId());
        pstm.setString(2,dto.getEmployeeId());
        pstm.setString(3,dto.getEmpGender());
        pstm.setDate(4,dto.getEmpbd());
        pstm.setString(5,dto.getEmployeeName());
        pstm.setString(6,dto.getEmpAddress());
        pstm.setString(7,dto.getEmpContac());

        isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){

            String uId = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String empGender = resultSet.getString(3);
            Date empbd = resultSet.getDate(4);
            String employeeName = resultSet.getString(5);
            String empAddress = resultSet.getString(6);
            String empContac = resultSet.getString(7);

            var dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employeeid= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean update(final EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET user_id = ?, emp_gender = ?, emp_bd = ?,employee_name = ?,address = ?,contac = ? WHERE employeeid = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getUId());
        pstm.setString(2,dto.getEmpGender());
        pstm.setDate(3,dto.getEmpbd());
        pstm.setString(4,dto.getEmployeeName());
        pstm.setString(5,dto.getEmpAddress());
        pstm.setString(6,dto.getEmpContac());
        pstm.setString(7, dto.getEmployeeId());


        return pstm.executeUpdate() > 0;
    }

    public static List<EmployeeDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmployeeDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT employeeid FROM employee ORDER BY  employeeid DESC LIMIT 1";
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
            String[] split = currentCusId.split("E");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "E00" + id;
        }
        return "E001";
    }

    public EmployeeDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM employee WHERE employeeid = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String uId = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String empGender = resultSet.getString(3);
            Date empbd = resultSet.getDate(4);
            String employeeName = resultSet.getString(5);
            String empAddress = resultSet.getString(6);
            String empContac = resultSet.getString(7);
          //  String complete = resultSet.getString(8);
            dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac);
        }
        return dto;
    }

    public int empCount() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(employeeid) AS row_count FROM employee";

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

