package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public boolean saveOrderDetails(String orderId, List<CusOrderTm> cartTmList) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            for (CusOrderTm tm : cartTmList) {
                if (!saveOrderDetail(connection, orderId, tm)) {
                    return false;
                }
            }
            return true;
        } finally {
            connection.close(); // Close the connection in the finally block
        }
    }

    private boolean saveOrderDetail(Connection connection, String orderId, CusOrderTm tm) throws SQLException {
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, orderId);
            pstm.setString(2, tm.getId());
            pstm.setString(3, tm.getWeigth()); // Assuming tm.getWeigth() returns a String
            pstm.setDouble(4, tm.getPayment());

            return pstm.executeUpdate() > 0;
        }
    }


}
