package LapFarm.Service;

import java.util.List;

import LapFarm.DTO.PaymentDTO;

public interface IPaymentService {
	List<PaymentDTO> getPaymentsByUserId(int userId);

}
