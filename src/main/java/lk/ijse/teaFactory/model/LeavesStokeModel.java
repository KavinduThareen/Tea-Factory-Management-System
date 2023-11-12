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
}
