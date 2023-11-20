package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.PaseOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaseOrderModel {
    private static CusOrderModel cusModel = new CusOrderModel();
    private static PacketStokeModel packetStokeModel = new PacketStokeModel();
    private static OrderDetailModel orderDetailModel = new OrderDetailModel();

    public static boolean placeOrder(PaseOrderDto placeOrderDto) throws SQLException {

        Connection connection = null;

        try  {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String orderId = placeOrderDto.getid();
            String customerId = placeOrderDto.getCId();
            String category = placeOrderDto.getCatagary();
            String weight = placeOrderDto.getWeigth();
            String date = placeOrderDto.getDate();
            String description = placeOrderDto.getDescreption();
            double payment = placeOrderDto.getPayment();

            System.out.println("Placing order: " + placeOrderDto);

            // Save order details
            boolean isOrderSaved = cusModel.saveOrder(orderId, customerId, category, weight, date, description, String.valueOf(payment));

            if (isOrderSaved) {
                // Update packet stoke
                boolean isUpdated = packetStokeModel.updateItem(placeOrderDto.getCartTmList());

                // Save order details
                boolean isOrderDetailSaved = orderDetailModel.saveOrderDetails(orderId, placeOrderDto.getCartTmList());

                if (isUpdated && isOrderDetailSaved) {
                    connection.commit();
                    System.out.println("Order successfully placed.");
                } else {
                    connection.rollback();
                    System.out.println("Failed to update packet stoke or save order details. Rolling back.");
                }
            } else {
                connection.rollback();
                System.out.println("Failed to save order. Rolling back.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception
        } finally {
            connection.setAutoCommit(true); // Set auto-commit back to true
        }
        return true;
    }
}


