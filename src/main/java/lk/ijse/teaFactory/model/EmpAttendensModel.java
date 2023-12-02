package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EmpAttendensModel {

    public boolean markAttendent(String value, LocalDate date, LocalTime time) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        // Check if the value already exists in the database
        if (isValueExists(connection, value)) {
            System.out.println("Duplicate value found: " + value);

            // Delete the existing duplicate value
            if (deleteDuplicateValue(connection, value)) {
                System.out.println("Existing duplicate value deleted: " + value);
            } else {
                System.out.println("Failed to delete existing duplicate value: " + value);
                return false;  // You may choose to handle this case in your own way
            }
        }

        // Insert the new record into the database
        String sql = "INSERT INTO emp_attendens VALUES (?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, value);
            pstm.setString(2, String.valueOf(date));
            pstm.setString(3, String.valueOf(time));

            return pstm.executeUpdate() > 0;
        }
    }

    private boolean isValueExists(Connection connection, String value) throws SQLException {
        String query = "SELECT COUNT(*) FROM emp_attendens WHERE attendent = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private boolean deleteDuplicateValue(Connection connection, String value) throws SQLException {
        String deleteQuery = "DELETE FROM emp_attendens WHERE attendent = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, value);
            return deleteStatement.executeUpdate() > 0;
        }
    }

    public boolean delete() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM emp_attendens";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            return pstm.executeUpdate() > 0;
        }
    }

    public int empAttendes() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(attendent) AS total_attendent FROM emp_attendens";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                rowCount = resultSet.getInt("total_attendent");
                System.out.println("Number of rows: " + rowCount);
            }
        }
        return rowCount;
    }


}



   /* public boolean markAttendent(String value, LocalDate date, LocalTime time) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO emp_attendens VALUES(?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,value );
        pstm.setString(2, String.valueOf(date));
        pstm.setString(3, String.valueOf(time));

        boolean isSaved = pstm.executeUpdate() >0;
        return isSaved;
    }

    */


    // INSERT INTO emp_attendens VALUES(?, ?,?)

/*
    public int empAttendes() throws SQLException {
        int rowCount = 0;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(qr_value) AS total_weight FROM emp_attendens";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                rowCount = resultSet.getInt("total_weight");
                System.out.println("Number of rows: " + rowCount);
            }
        }
        return rowCount;
    }

 */


