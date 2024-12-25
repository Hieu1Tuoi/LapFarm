package LapFarm.Utils;

public final class ValidationUtils {
	// Validation lengths
	public static final int MAX_NAME_LENGTH = 50;
	public static final int MAX_PHONE_LENGTH = 11;
	public static final int MAX_ADDRESS_LENGTH = 200;
	public static final int MAX_EMAIL_LENGTH = 50;
	public static final int MAX_PASSWORD_LENGTH = 50;
	public static final int MAX_DESCRIPTION_LENGTH = 1000;

	// Error messages
	public static final String ERROR_NAME_LENGTH = "Họ tên không được vượt quá %d ký tự";
	public static final String ERROR_PHONE_FORMAT = "Số điện thoại không đúng định dạng";
	public static final String ERROR_EMAIL_FORMAT = "Email không đúng định dạng";
	public static final String ERROR_PASSWORD_FORMAT = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt!";
	// Add new constant for review
    public static final int MAX_REVIEW_WORDS = 500;
    public static final String ERROR_REVIEW_LENGTH = "Đánh giá không được vượt quá %d từ";
    public static final String ERROR_REVIEW_EMPTY = "Nội dung đánh giá không được để trống";

	private ValidationUtils() {
		// Private constructor to prevent instantiation
	}

	public static class ValidationResult {
		private final boolean valid;
		private final String message;

		public ValidationResult(boolean valid, String message) {
			this.valid = valid;
			this.message = message;
		}

		public boolean isValid() {
			return valid;
		}

		public String getMessage() {
			return message;
		}
	}

	// kiem tra phone
	public static ValidationResult validatePhone(String phone) {
		if (phone == null) {
			return new ValidationResult(false, "Số điện thoại không được để trống");
		}

		// Remove whitespace and dots if any
		phone = phone.replaceAll("[\\s\\.]", "");

		// Check length
		if (phone.length() > MAX_PHONE_LENGTH) {
			return new ValidationResult(false, "Số điện thoại không được vượt quá 11 ký tự");
		}

		// Regex for continuous phone number
		String phoneRegex = "^0\\d{9}$";
		if (!phone.matches(phoneRegex)) {
			return new ValidationResult(false, "Số điện thoại phải đúng 10 chữ số.");
		}
		return new ValidationResult(true, null);
	}

	// Kiem tra email
	public static ValidationResult validateEmail(String email) {
	    if (email == null) {
	        return new ValidationResult(false, "Email không được để trống");
	    }
	    // More strict email regex that requires proper domain format
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	    if (!email.matches(emailRegex)) {
	        return new ValidationResult(false, "Email không đúng định dạng");
	    }
	    if (email.length() > MAX_EMAIL_LENGTH) {
	        return new ValidationResult(false, "Email không được vượt quá " + MAX_EMAIL_LENGTH + " ký tự");
	    }
	    return new ValidationResult(true, null);
	}

	// Password Validation Methods
	public static ValidationResult validatePassword(String password, String confirmPassword) {
	    // Kiểm tra null
	    if (password == null) {
	        return new ValidationResult(false, "Mật khẩu không được để trống");
	    }

	    // Kiểm tra độ dài tối thiểu và tối đa
	    if (password.length() < 8) {
	        return new ValidationResult(false, "Mật khẩu phải có ít nhất 8 ký tự");
	    }
	    if (password.length() > MAX_PASSWORD_LENGTH) {
	        return new ValidationResult(false, "Mật khẩu không được vượt quá " + MAX_PASSWORD_LENGTH + " ký tự");
	    }

	    // Kiểm tra các yêu cầu về độ phức tạp của mật khẩu

	    // Kiểm tra mẫu regex tổng thể
	    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
	    if (!password.matches(passwordRegex)) {
	        return new ValidationResult(false, 
	            "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt!");
	    }

	    // Nếu tất cả điều kiện đều thỏa mãn
	    return new ValidationResult(true, null);
	}
	//Kiem tra review 
	public static ValidationResult validateReview(String review) {
        if (review == null || review.trim().isEmpty()) {
            return new ValidationResult(false, ERROR_REVIEW_EMPTY);
        }

        // Normalize the review text
        String normalizedReview = normalizeInput(review);

        // Count words by splitting on whitespace
        String[] words = normalizedReview.split("\\s+");
        
        if (words.length > MAX_REVIEW_WORDS) {
            return new ValidationResult(false, 
                String.format(ERROR_REVIEW_LENGTH, MAX_REVIEW_WORDS));
        }

        // Check for potential XSS or malicious content
        if (containsHtmlTags(normalizedReview)) {
            return new ValidationResult(false, 
                "Đánh giá không được chứa mã HTML hoặc script");
        }

        // Basic profanity check (you might want to expand this)
        if (containsProfanity(normalizedReview)) {
            return new ValidationResult(false, 
                "Đánh giá không được chứa từ ngữ không phù hợp");
        }

        return new ValidationResult(true, null);
    }

	// kiem tra do dai
	public static ValidationResult validateLength(String input, int maxLength, String fieldName) {
		if (input == null) {
			return new ValidationResult(false, fieldName + " không được để trống");
		}
		if (input.length() > maxLength) {
			return new ValidationResult(false, fieldName + " không được vượt quá " + maxLength + " ký tự");
		}
		return new ValidationResult(true, null);
	}

	// Input Sanitization Methods
	public static String normalizeInput(String input) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").replaceAll("\\s+", " ").trim();
	}
	private static boolean containsHtmlTags(String text) {
        return text.matches(".*<[^>]+>.*");
    }
	private static boolean containsProfanity(String text) {
        // Add your list of prohibited words here
        String[] profanityList = {"xxx", "yyy", "zzz"}; // Replace with actual prohibited words
        
        String lowerText = text.toLowerCase();
        for (String word : profanityList) {
            if (lowerText.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

	public static String sanitizeInput(String input) {
		if (input == null) {
			return null;
		}
		return input.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;")
				.replace("&", "&amp;").replace("(", "&#40;").replace(")", "&#41;").replace("/", "&#x2F;");
	}

	public static String safeSetString(String input, int maxLength) {
		if (input == null) {
			return null;
		}
		String normalized = normalizeInput(input);
		String sanitized = sanitizeInput(normalized);
		return sanitized.substring(0, Math.min(sanitized.length(), maxLength));
	}

}
