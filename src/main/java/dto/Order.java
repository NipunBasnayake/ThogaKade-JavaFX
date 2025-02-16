package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private ArrayList<OrderDetail> orderDetails;
}
