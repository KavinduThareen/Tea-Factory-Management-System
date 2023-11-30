package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.LoginDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDetailModel {


    public boolean logdetail(final LoginDetailsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO login_details VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getInTime());
        pstm.setDate(3, dto.getDate());


        boolean isSaved = pstm.executeUpdate() >0;


        return isSaved;
    }


}
