package LapFarm.Bean;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

import java.io.File;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
@Service("mailer")
public class Mailer {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    
	@Autowired
	JavaMailSender mailer;
	
	public String VerifyCode(String to) {
		String code = generateRandomString();
		try {
			MimeMessage mail = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, false, "utf-8");

			helper.setFrom("n21dcat002@student.ptithcm.edu.vn");
			helper.setTo(to);
			helper.setSubject("Mã xác minh");
			String htmlContent = "<html><body>"
				    + "<h1>Chào bạn!</h1>"
				    + "<p>Sau đây là mã xác minh của bạn:</p>"
				    + "<h2>" + code + "</h2>"
				    + "<p>Mã xác minh này có hiệu lực trong 10 phút.</p>"
				    + "<p>Bạn đang xác minh địa chỉ e-mail của mình. Nếu bạn không yêu cầu, hãy bỏ qua thư này.</p>"
				    + "</body></html>";
				helper.setText(htmlContent, true);

			mailer.send(mail);
			System.out.println("Email đã được gửi thành công tới: " + to);

		} catch (MessagingException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Lỗi khi gửi email: " + ex.getMessage(), ex);
		}
		return code;
	}
	
	public String send(String to, ServletContext context) {
		String code = generateRandomString();
		try {
			MimeMessage mail = mailer.createMimeMessage();
			// Đặt multipart là true để hỗ trợ gửi file đính kèm
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");

			helper.setFrom("n21dcat002@student.ptithcm.edu.vn");
			helper.setTo(to);
			helper.setSubject("!!!");

			// Email content with HTML formatting
			String htmlContent = "<html><body>"
	                + "<h2>Chào bạn!</h2>"
	                + "<p>Sau đây là mã xác minh của bạn:</p>"
	                + "<h2>" + code + "</h2>"
	                + "<p>Mã xác minh này có hiệu lực trong 10 phút.</p>"
	                + "<p>Bạn đang xác minh địa chỉ e-mail của mình. Nếu bạn không yêu cầu, hãy bỏ qua thư này.</p>"
	                + "</body></html>";
			helper.setText(htmlContent, true);

			// File path for the attachment
			String filePath = context.getRealPath("/WEB-INF/resources/img/soicodoc.jpg");
			File file = new File(filePath);

			if (!file.exists()) {
				throw new RuntimeException("File không tồn tại: " + filePath);
			}

			// Attach the file to the email using File object directly
			helper.addAttachment("soicodoc.jpg", file);

			// Send the email
			mailer.send(mail);
			System.out.println("Email đã được gửi thành công tới: " + to);
			
			return "redirect:/home";
		} catch (MessagingException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Lỗi khi gửi email: " + ex.getMessage(), ex);
		}
	}
	
	public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        
        return sb.toString();
    }
}
