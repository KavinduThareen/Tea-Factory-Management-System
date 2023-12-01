package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.SupOrderDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupOrderModel {
    public boolean SupOrderSaved(SupOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier_orders VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getSId());
        pstm.setDate(3, dto.getDate());
        pstm.setString(4, dto.getWeigth());
        pstm.setDouble(5,dto.getPayment());

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public List<SupOrderDto> loadAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier_orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupOrderDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String sId = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String weigth = resultSet.getString(4);
            double payment = resultSet.getDouble(5);

            var dto = new SupOrderDto(id, sId, date, weigth,payment);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier_orders WHERE s_orders_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean dropid(String id, String weigth) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier_orders SET sup_stoke_weigth = sup_stoke_weigth - ? WHERE s_orders_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, weigth);
        pstm.setString(2, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean update(final SupOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier_orders SET sup_id = ?, sup_date = ?, sup_stoke_weigth = ?,payment = ? WHERE s_orders_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSId());
        pstm.setDate(2, dto.getDate());
        pstm.setString(3, dto.getWeigth());
        pstm.setDouble(4, dto.getPayment());
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT s_orders_id FROM supplier_orders ORDER BY  s_orders_id DESC LIMIT 1";
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
            String[] split = currentCusId.split("s");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "s00" + id;
        }
        return "s001";
    }


    public static List<SupOrderDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier_orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<SupOrderDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new SupOrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    public SupOrderDto searchSuppli(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM supplier_orders WHERE s_orders_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        SupOrderDto dto = null;

        if(resultSet.next()) {
            String oid = resultSet.getString(1);
            String sid = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String weight = resultSet.getString(4);
            double payment = resultSet.getDouble(5);
            //   String complete = resultSet.getString(6);
            dto = new SupOrderDto(oid, sid, date , weight,payment);
        }
        return dto;
    }
}
