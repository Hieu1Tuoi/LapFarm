package LapFarm.Service;

import LapFarm.DAO.ReviewDAO;
import LapFarm.Entity.ReviewEntity;
import LapFarm.Utils.XSSUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;  // Inject ReviewDAO để thao tác với cơ sở dữ liệu

    // Phương thức lưu đánh giá vào cơ sở dữ liệu
    public void saveReview(ReviewEntity reviewEntity) {
    	 if (XSSUtils.containsXSS(reviewEntity.getComment())) {
             throw new IllegalArgumentException("Đánh giá chứa nội dung không hợp lệ và không được phép lưu.");
         }
    	  reviewDAO.saveReview(reviewEntity); 
    }
    
}
