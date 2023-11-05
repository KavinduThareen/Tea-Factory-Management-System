package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.registerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class registerModel {

public boolean registerUser(final registerDto dto) throws SQLException {
    Connection connection = DbConnection.getInstance().getConnection();

    String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";
    PreparedStatement pstm = connection.prepareStatement(sql);

    pstm.setString(1, dto.getUserid());
    pstm.setString(2,dto.getUsername());
    pstm.setString(3,dto.getContac());
    pstm.setString(4, dto.getPassword());


    boolean isSaved = pstm.executeUpdate() > 0;

    return isSaved;
}

/*
    public registerDto finduserName(String username) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE username = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,username);

        ResultSet resultSet = pstm.executeQuery();

        registerDto  dto =null;

        if (resultSet.next()){
            String l_username = resultSet.getString(1);

            dto =new registerDto(l_username);

        }


        return null;
    }
*/

}
