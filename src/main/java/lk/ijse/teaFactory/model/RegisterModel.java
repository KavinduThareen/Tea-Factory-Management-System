package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.RegisterDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterModel {

public boolean registerUser( RegisterDto dto) throws SQLException {
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

    public static List<RegisterDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<RegisterDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new RegisterDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    public static String generateNextUserId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT userid FROM user ORDER BY userid DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);
            return splitOrderId(currentCusId);
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentCusId) {    //O008
        if (currentCusId != null) {
            String[] split = currentCusId.split("U");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "U00" + id;
        }
        return "U001";
    }

    public static String username() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT username FROM user ORDER BY username DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);

        }
        return currentCusId;
    }

}
