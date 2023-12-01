package lk.ijse.teaFactory.model;

import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.PaseOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaseOrderModel {
    private static CusOrderModel cusModel = new CusOrderModel();
    private static PacketStokeModel packetStokeModel = new PacketStokeModel();
    private static OrderDetailModel orderDetailModel = new OrderDetailModel();
    public static boolean placeOrder(PaseOrderDto placeOrderDto) throws SQLException {

            String orderId = placeOrderDto.getid();
            String customerId = placeOrderDto.getCId();
            String category = placeOrderDto.getCatagary();
            double weight = placeOrderDto.getWeigth();
            LocalDate date = placeOrderDto.getDate();
            String description = placeOrderDto.getDescreption();
            double payment = placeOrderDto.getPayment();

            Connection connection = null;
            try {
                connection = DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                boolean isOrderSaved = cusModel.saveOrder(orderId,customerId,category,weight,date,description, String.valueOf(payment));
                if (isOrderSaved) {
                    boolean isUpdated = packetStokeModel.updateItem(placeOrderDto.getCartTmList());
                  //  boolean isUpdated = packetStokeModel.deleteItem(placeOrderDto.getCartTmList());

                    if (isUpdated  ) {
                       boolean isOrderDetailSaved = orderDetailModel.saveOrderDetails(placeOrderDto.getid(), placeOrderDto.getCartTmList());


                        if (isOrderDetailSaved) {
                            connection.commit();
                        }
                    }
                }
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
            return true;

    }
}


