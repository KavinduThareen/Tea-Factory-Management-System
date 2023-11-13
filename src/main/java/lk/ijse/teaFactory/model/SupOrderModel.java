package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.SupOrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupOrderModel {
    public boolean SupOrderSaved(SupOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier_orders VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getSId());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getWeigth());
        pstm.setString(5, "0");

        boolean isSaved = pstm.executeUpdate() > 0;
        return isSaved;
    }

    public List<SupOrderDto> loadAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupOrderDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String sId = resultSet.getString(2);
            String date = resultSet.getString(3);
            String weigth = resultSet.getString(4);
            String isCompleted = resultSet.getString(5);

            var dto = new SupOrderDto(id, sId, date, weigth, isCompleted);
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

    public boolean update(final SupOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET sup_id = ?, sup_date = ?, sup_stoke_weigth, deleted = ? WHERE s_orders_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSId());
        pstm.setString(2, dto.getDate());
        pstm.setString(3, dto.getWeigth());
        pstm.setString(4, "0");
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }
}
