package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean employeeSave( EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        boolean isSaved = false;

        String sql =  "INSERT INTO employee VALUES(?, ?, ?, ?,?,? ,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getUId());
        pstm.setString(2,dto.getEmployeeId());
        pstm.setString(3,dto.getEmpGender());
        pstm.setString(4,dto.getEmpbd());
        pstm.setString(5,dto.getEmployeeName());
        pstm.setString(6,dto.getEmpAddress());
        pstm.setString(7,dto.getEmpContac());
        pstm.setString(8, "0");



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
            String empbd = resultSet.getString(4);
            String employeeName = resultSet.getString(5);
            String empAddress = resultSet.getString(6);
            String empContac = resultSet.getString(7);
            String delete = resultSet.getString(8);


            var dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac,delete);
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

        String sql = "UPDATE customer SET user_id = ?, emp_gender = ?, emp_bd = ?,employee_name = ?,address = ?,contac = ?,delet = ?  WHERE employeeid = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getUId());
        pstm.setString(2,dto.getEmpGender());
        pstm.setString(3,dto.getEmpbd());
        pstm.setString(4,dto.getEmployeeName());
        pstm.setString(5,dto.getEmpAddress());
        pstm.setString(6,dto.getEmpContac());
        pstm.setString(7,"0");
        pstm.setString(8, dto.getEmployeeId());


        return pstm.executeUpdate() > 0;
    }




}
