package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplingDetailModel {
    public boolean detail(String sid, String id, Date sDate) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO suppling_detailse VALUES(?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,sid );
        pstm.setString(2, id);
        pstm.setDate(3, sDate);

        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }


}
