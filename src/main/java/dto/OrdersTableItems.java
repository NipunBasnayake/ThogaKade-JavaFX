package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersTableItems {
    private String orderId;
    private LocalDate date;
    private String customer;
    private String item;
    private int quantity;
    private double unitPrice;
    private double total;
}
