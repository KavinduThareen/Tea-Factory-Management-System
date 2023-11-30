package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StokeDetailModel {

    public void saveId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stoke_detailse (p_stoke_id) VALUES (?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);

            // Execute the query
            pstm.executeUpdate();
        } // The try-with-resources statement automatically closes the PreparedStatement and Connection
    }

    public void saveId2(LeavesStokeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stoke_detailse (l_id) VALUES (?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1,dto.getId());
            System.out.println(dto.getId());

            // Execute the query
            pstm.executeUpdate();
        } // The try-with-resources statement automatically closes the PreparedStatement and Connection
    }

}
