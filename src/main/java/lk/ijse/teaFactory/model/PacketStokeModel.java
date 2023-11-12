package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.PacketStokeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacketStokeModel {
    public boolean packetStokeSaved(PacketStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="INSERT INTO packet_stoke VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getCatagory());
        pstm.setString(3, dto.getWeigth());
        pstm.setString(4, dto.getDate());
        pstm.setString(5, "0");


        boolean isSaved = pstm.executeUpdate() >0;

        return isSaved;
    }

    public List<PacketStokeDto> loadAll () throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packet_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PacketStokeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){

            String id = resultSet.getString(1);
            String catagary = resultSet.getString(2);
            String weigth = resultSet.getString(3);
            String date = resultSet.getString(4);
            String isCompleted = resultSet.getString(5);



            var dto = new PacketStokeDto(id,catagary,weigth,date,isCompleted);
            dtoList.add(dto);
        }
        return dtoList;

    }
}
