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

    public static boolean delete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM packet_stoke WHERE packet_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }


    public boolean update(final PacketStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packet_stoke SET s_catogary = ?, s_weigth = ?, s_expiredate = ?, isCompleted = ? WHERE packet_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCatagory());
        pstm.setString(2, dto.getWeigth());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, "0");
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT packet_id FROM packet_stoke ORDER BY packet_id DESC LIMIT 1";
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
            String[] split = currentCusId.split("P");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "P00" + id;
        }
        return "P001";
    }
}
