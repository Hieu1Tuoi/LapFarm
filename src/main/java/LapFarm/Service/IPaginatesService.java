package LapFarm.Service;

import org.springframework.stereotype.Service;

import LapFarm.DTO.PaginatesDto;
@Service
public interface IPaginatesService {
	public PaginatesDto GetInfoPaginate(int totalData, int limit, int currentPage);
}
