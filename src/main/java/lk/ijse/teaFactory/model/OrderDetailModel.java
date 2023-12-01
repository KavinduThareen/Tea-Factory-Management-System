package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.tm.CartTm;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public class OrderDetailModel {
    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_detailse VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getItemId());
        pstm.setDouble(3, tm.getWeigth());
        pstm.setDouble(4, tm.getPayment());

        return pstm.executeUpdate() > 0;
    }

    public int ordersCount() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(weigth) AS total_weight FROM order_detailse";

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
