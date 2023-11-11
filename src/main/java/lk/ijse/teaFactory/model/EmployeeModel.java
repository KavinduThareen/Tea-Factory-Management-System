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

        String sql =  "INSERT INTO employee VALUES(?, ?, ?, ?,?,? ,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getUId());
        pstm.setString(2,dto.getEmployeeId());
        pstm.setString(3,dto.getEmpGender());
        pstm.setString(4,dto.getEmpbd());
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
            String empbd = resultSet.getString(4);
            String employeeName = resultSet.getString(5);
            String empAddress = resultSet.getString(6);
            String empContac = resultSet.getString(7);


            var dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
