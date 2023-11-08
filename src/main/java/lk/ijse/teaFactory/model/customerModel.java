package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.customerDto;

import java.sql.Connection;
import java.sql.SQLException;

public class customerModel {

    // not complet countiniue this

    public boolean customerSaved(final customerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";


        return false;
    }
}
