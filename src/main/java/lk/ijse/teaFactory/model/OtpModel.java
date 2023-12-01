package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.OtpDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OtpModel {

    public boolean save(OtpDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Otp VALUES(?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, dto.getOtp());
        boolean isSaved = pstm.executeUpdate() >0;

        return isSaved;
    }

    public int load() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Otp";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        int otp = 0;

        while (resultSet.next()) {
             otp = resultSet.getInt(1);
        }
        return otp;
    }

    public static boolean delete(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Otp WHERE otp = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, id);

        return pstm.executeUpdate() > 0;
    }
}
