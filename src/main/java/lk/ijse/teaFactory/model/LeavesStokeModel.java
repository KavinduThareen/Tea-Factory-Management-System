package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeavesStokeModel {

    public boolean addLeavesStoke(LeavesStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql =  "INSERT INTO leaves_stoke VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getWeigth());
        pstm.setString(3, dto.getSDate());
        pstm.setString(4, dto.getEDate());
        pstm.setString(5,"0");

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;

    }

    public List<LeavesStokeDto> loadAll() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql =  "SELECT * FROM leaves_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<LeavesStokeDto> dtoList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String weigth = resultSet.getString(2);
            String sDate = resultSet.getString(3);
            String eDate = resultSet.getString(4);
            String isCompleted = resultSet.getString(5);

            var dto = new LeavesStokeDto(id,weigth,sDate,eDate,isCompleted);
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

        String sql = "UPDATE leaves_stoke SET l_weigth = ?, l_suppli_date = ?, l_s_expiredate = ?, isCompleted = ? WHERE leaves_s_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getWeigth());
        pstm.setString(2, dto.getSDate());
        pstm.setString(3, dto.getEDate());
        pstm.setString(4, "0");
        pstm.setString(5, dto.getId());


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
}
