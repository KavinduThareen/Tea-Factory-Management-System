package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.loginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginModel {

    public loginDto finduserName() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String cus_username1 = null;
        String cus_username2 = null;

            String sql1 = "SELECT username,password FROM user";

            PreparedStatement pstm1 = connection.prepareStatement(sql1);


            ResultSet resultSet1 = pstm1.executeQuery();
            //  List<loginDto> dtoList = new ArrayList<>();

            loginDto dto = null;

            if (resultSet1.next()) {
                cus_username1 = resultSet1.getString(1);
                cus_username2 = resultSet1.getString(2);


                dto = new loginDto(cus_username1, cus_username2);
            //    dtoList.add(dto);
            }

            return dto;

        }



    public static String generateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT userid FROM user ORDER BY userid DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);

        }
        return currentCusId;
    }

    public static String password() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT password FROM user ORDER BY password DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);

        }
        return currentCusId;
    }

}
