package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.loginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {

    public loginDto finduserName() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String cus_username1=null;
        String cus_username2=null;

        String sql1 = "SELECT username,password FROM user";

        PreparedStatement pstm1 = connection.prepareStatement(sql1);

        ResultSet resultSet1 = pstm1.executeQuery();

        loginDto dto = null;

        if (resultSet1.next()) {
            cus_username1= resultSet1.getString("username");
            cus_username2= resultSet1.getString("password");

            dto = new loginDto(cus_username1,cus_username2);
        }

        return dto;

    }
}
