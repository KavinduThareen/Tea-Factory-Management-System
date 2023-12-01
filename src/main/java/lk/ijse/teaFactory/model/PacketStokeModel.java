
package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CartTm;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacketStokeModel {
    public boolean packetStokeSaved(PacketStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO packet_stoke VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getCatagory());
        pstm.setString(3, dto.getWeigth());
        pstm.setDate(4, dto.getDate());

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public List<PacketStokeDto> loadAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packet_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PacketStokeDto> dtoList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String catagory = resultSet.getString(2);
            String weigth = resultSet.getString(3);
            Date date = resultSet.getDate(4);

            var dto = new PacketStokeDto(id, catagory, weigth, date);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<PacketStokeDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM  packet_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PacketStokeDto> itemList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            itemList.add(new PacketStokeDto(
                   resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            ));
        }

        return itemList;
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

        String sql = "UPDATE packet_stoke SET s_catogary = ?, s_weigth = ?, s_expiredate = ? WHERE packet_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCatagory());
        pstm.setString(2, dto.getWeigth());
        pstm.setDate(3, dto.getDate());
        pstm.setString(4, dto.getId());

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

    private static String splitOrderId(String currentCusId) {
        if (currentCusId != null) {
            String[] split = currentCusId.split("P");
            int id = Integer.parseInt(split[1]);
            id++;
            return "P00" + id;
        }
        return "P001";
    }

    public static List<PacketStokeDto> loadAllcatagary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packet_stoke";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PacketStokeDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new PacketStokeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean updateItem(List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getItemId(), String.valueOf(tm.getWeigth()))) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String code, String qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packet_stoke SET s_weigth = s_weigth - ? WHERE packet_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }

    public PacketStokeDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM packet_stoke WHERE packet_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        PacketStokeDto dto = null;

        if(resultSet.next()) {
            String lid = resultSet.getString(1);
            String catgary = resultSet.getString(2);
            String weigth = resultSet.getString(3);
            java.util.Date edate = resultSet.getDate(4);


            dto = new PacketStokeDto(lid,catgary,weigth, (Date) edate);
        }
        return dto;
    }

    public int stokeCount() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(s_weigth) AS total_weight FROM packet_stoke";

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
