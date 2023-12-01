package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.SalaryDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SalaryModel {
    public boolean salarySaved(final SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO salory VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getEmpId());
        pstm.setDate(3, dto.getDate());
        pstm.setString(4, dto.getCount());

        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }

    public boolean updateSalary(final SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE salory SET e_id = ?, Date = ?, s_count = ?  WHERE salory_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpId());
        pstm.setDate(2, dto.getDate());
        pstm.setString(3, dto.getCount());
        pstm.setString(4, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public List<SalaryDto> getAllSalary() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salory";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String empId = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String count = resultSet.getString(4);
          //  String delete = resultSet.getString(5);

            var dto = new SalaryDto(id,empId,date,count);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM salory WHERE salory_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT salory_id FROM salory ORDER BY salory_id DESC LIMIT 1";
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
            String[] split = currentCusId.split("S");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "S00" + id;
        }
        return "S001";
    }

    public SalaryDto searchPayment(String searchId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salory WHERE salory_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, searchId); // Use the parameter name instead of 'id'

        ResultSet resultSet = pstm.executeQuery();

        SalaryDto dto = null;

        if (resultSet.next()) {
            String resultId = resultSet.getString(1);  // Use a different variable name
            String empid = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String contac = resultSet.getString(4);


            dto = new SalaryDto(resultId, empid, date, contac);
        }
        return dto;
    }


}
