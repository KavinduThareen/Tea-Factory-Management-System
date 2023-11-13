package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CusOrderModel {

    public boolean cusOrdersSaved (CusOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getCId());
        pstm.setString(3, dto.getCatagary());
        pstm.setString(4, dto.getWeigth());
        pstm.setString(5, dto.getDate());
        pstm.setString(6, dto.getDescreption());
        pstm.setString(7,"0");

        boolean isSaved = pstm.executeUpdate() >0;


        return isSaved;
    }

    public List<CusOrderDto> loadAll() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CusOrderDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String cId = resultSet.getString(2);
            String catagary = resultSet.getString(3);
            String weigth = resultSet.getString(4);
            String date = resultSet.getString(5);
            String descreption = resultSet.getString(6);
            String isCompleted = resultSet.getString(7);

            var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,isCompleted);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM orders WHERE order_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean update (final CusOrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET cus_id = ?, o_catogary = ?, o_weigth, o_date, descrreption, isCompleted = ? WHERE order_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCId());
        pstm.setString(2, dto.getCatagary());
        pstm.setString(3, dto.getWeigth());
        pstm.setString(4, dto.getDate());
        pstm.setString(5, dto.getDescreption());
        pstm.setString(6, "0");
        pstm.setString(7, dto.getId());


        return pstm.executeUpdate() > 0;
    }

}
