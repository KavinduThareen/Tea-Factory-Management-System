package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StokeDetailModel {
    public boolean detail(String pid, String lid, Date date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO stoke_detailse VALUES(?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,pid );
        pstm.setString(2, lid);
        pstm.setDate(3, date);

        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }


}
