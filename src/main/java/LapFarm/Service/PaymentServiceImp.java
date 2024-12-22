package LapFarm.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.PaymentDAO;
import LapFarm.DTO.PaymentDTO;
import LapFarm.Entity.PaymentEntity;

@Service
public class PaymentServiceImp implements IPaymentService {
	
	@Autowired
    private PaymentDAO paymentDAO;
    
    @Override
    public List<PaymentDTO> getPaymentsByUserId(int userId) {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        
        try {
            // Get payments from DAO
            List<PaymentEntity> payments = paymentDAO.getPaymentsByUserId(userId);
            
            // Convert entities to DTOs
            for (PaymentEntity payment : payments) {
                PaymentDTO dto = new PaymentDTO();
                dto.setIdPayment(payment.getIdPayment());
                dto.setOrderPayment(payment.getOrderPayment());
                dto.setUserPayment(payment.getUserPayment());
                dto.setTimePayment(payment.getTimePayment());
                dto.setStatePayment(payment.getStatePayment());
                dto.setPricePayment(payment.getPricePayment());
                dto.setMethodPayment(payment.getMethodPayment());
                
                paymentDTOs.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy thông tin thanh toán: " + e.getMessage());
        }
        
        return paymentDTOs;
    }
}
