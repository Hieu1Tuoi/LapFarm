package LapFarm.Service;

import LapFarm.DAO.OrdersDAO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.Entity.OrdersEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService  {

    @Autowired
    private OrdersDAO ordersDAO;

    @Transactional("transactionManager")
    public String processPayment(int orderId, byte paymentMethod, long paymentAmount) {
        // Fetch the order based on the orderId
        OrdersEntity order = ordersDAO.getOrderById(orderId);

        // Check if the order exists
        if (order == null) {
            return "Order not found";
        }

        // Check if the payment method matches and process the payment (this part can be connected to actual payment systems)
        if (paymentMethod == order.getPaymentMethod()) {
            // Assume the payment is successful if the correct payment method is used
            if (paymentAmount >= order.getTotalPrice()) {
                // Payment is successful, update the order status
                order.setState("Paid");  // Or "Completed", depending on the business logic
                ordersDAO.saveOrder(order);  // Update the order in the DB
                return "Payment successful, order status updated to 'Paid'.";
            } else {
                return "Insufficient payment amount.";
            }
        } else {
            return "Payment method mismatch.";
        }
    }



	
	
}