package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StokeDetailModel {
    LeavesStokeDto a = new LeavesStokeDto();
    PacketStokeDto b = new PacketStokeDto();

    public void saveId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stoke_detailse (packet_id, leaves_id) VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,b.getId());
        pstm.setString(2,a.getId());

        // Execute the query
        pstm.executeUpdate();

        // Close the PreparedStatement and Connection


    }
}
