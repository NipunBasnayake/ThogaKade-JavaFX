package service.custom.impl;

import model.OrderDetail;
import service.custom.OrderDetailService;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    private static OrderDetailServiceImpl orderDetailController;

    public static OrderDetailServiceImpl getInstance() {
        if (orderDetailController == null) {
            orderDetailController = new OrderDetailServiceImpl();
        }
        return orderDetailController;
    }

    @Override
    public List<OrderDetail> getOrderDetails() {
        String sql = "SELECT * FROM orderdetail";
        List<OrderDetail> orderDetails = new ArrayList<>();

        try (ResultSet res = CrudUtil.execute(sql)) {
            while (res.next()) {
                orderDetails.add(new OrderDetail(
                        res.getString("orderId"),
                        res.getString("itemCode"),
                        res.getInt("qty"),
                        res.getDouble("unitPrice")
                ));
            }
        } catch (SQLException e) {
            return null;
        }
        return orderDetails;
    }


    @Override
    public boolean addOrderDetail(ArrayList<OrderDetail> orderDetails) {
       for (OrderDetail orderDetail : orderDetails) {
           boolean isAdded = addOrderDetail(orderDetail);
           if (!isAdded) {
               return false;
           }
       }
       return true;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO orderdetail VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql, orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice());
        } catch (SQLException e) {
            return false;
        }
    }
}
