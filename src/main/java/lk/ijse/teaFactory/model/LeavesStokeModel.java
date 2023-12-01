package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeavesStokeModel {
    public boolean addLeavesStoke(LeavesStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql =  "INSERT INTO leaves_stoke VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getWeigth());
        pstm.setDate(3, dto.getSDate());
        pstm.setDate(4, dto.getEDate());
      //  pstm.setString(5,"0");

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;

    }

/*
    public boolean addLeavesStoke2(LeavesStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql =  "INSERT INTO leaves_stoke VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getWeigth());
        pstm.setDate(3, dto.getSDate());
        pstm.setDate(4, dto.getEDate());
        pstm.setString(5,"0");

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;

    }

 */

    public static List<LeavesStokeDto> loadAll() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql =  "SELECT * FROM leaves_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<LeavesStokeDto> dtoList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String weigth = resultSet.getString(2);
            Date sDate = resultSet.getDate(3);
            Date eDate = resultSet.getDate(4);
         //   String isCompleted = resultSet.getString(5);

            var dto = new LeavesStokeDto(id,weigth,sDate,eDate);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public static boolean delete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM leaves_stoke WHERE leaves_s_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean update(final LeavesStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE leaves_stoke SET l_weigth = ?, l_suppli_date = ?, l_s_expiredate = ? WHERE leaves_s_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWeigth());
        pstm.setDate(2, dto.getSDate());
        pstm.setDate(3, dto.getEDate());
       // pstm.setString(4, "0");
        pstm.setString(4, dto.getId());


        return pstm.executeUpdate() > 0;
    }

    public static String generateNextLeavesId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT leaves_s_id FROM leaves_stoke ORDER BY  leaves_s_id DESC LIMIT 1";
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
            String[] split = currentCusId.split("L");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "L00" + id;
        }
        return "L001";
    }

    public LeavesStokeDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM leaves_stoke WHERE leaves_s_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        LeavesStokeDto dto = null;

        if(resultSet.next()) {
            String lid = resultSet.getString(1);
            String weigth = resultSet.getString(2);
            Date sdate = resultSet.getDate(3);
            Date edate = resultSet.getDate(4);
         //   String complete = resultSet.getString(5);

            dto = new LeavesStokeDto(lid,weigth,sdate,edate);
        }
        return dto;
    }

    public static boolean drop(String id, String weigth) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE leaves_stoke SET l_weigth = l_weigth - ? WHERE leaves_s_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, weigth);
        pstm.setString(2, id);

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

    public int stokeCount() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(l_weigth) AS total_weight FROM leaves_stoke";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                rowCount = resultSet.getInt("total_weight");
                System.out.println("Number of rows: " + rowCount);
            }
        }
        return rowCount;
    }

}
