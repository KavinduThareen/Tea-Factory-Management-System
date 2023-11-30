package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.LoginDetailsDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.StokeDeatailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StokeDetailModel {
    public boolean stokedetail(final StokeDeatailDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stoke_detailse VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPid());
        pstm.setString(2, dto.getLid());


        boolean isSaved = pstm.executeUpdate() >0;


        return isSaved;
    }

}
