package LapFarm.Service;

import LapFarm.DAO.ReviewDAO;
import LapFarm.Entity.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;  // Inject ReviewDAO để thao tác với cơ sở dữ liệu

    // Phương thức lưu đánh giá vào cơ sở dữ liệu
    public void saveReview(ReviewEntity reviewEntity) {
        reviewDAO.saveReview(reviewEntity);  // Gọi ReviewDAO để lưu dữ liệu
    }
}
