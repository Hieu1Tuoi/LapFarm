package LapFarm.Utils;

import org.apache.tomcat.jakartaee.commons.lang3.StringEscapeUtils;

public class XSSUtils {
	 // Kiểm tra chuỗi có chứa mã hóa HTML hay không
    private static boolean isHtmlEscaped(String input) {
        return input.contains("&lt;") || input.contains("&gt;") || input.contains("&amp;");
    }

    public static boolean containsXSS(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra các ký tự và mẫu thường được dùng trong XSS
        String[] patterns = {"<script>", "</script>", "javascript:", "onerror=", "onload=","alert(","&lt;","&amp;","&gt;"};
        for (String pattern : patterns) {
            if (value.toLowerCase().contains(pattern)) {
                return true;
            }
        }
        return value.matches(".*[<>&\"'].+");
    }
    
    // Phương thức mã hóa XSS
    public static String escapeXSS(String input) {
        if (input != null) {
            // Kiểm tra xem chuỗi đã mã hóa chưa
            if (!isHtmlEscaped(input)) {
                return StringEscapeUtils.escapeHtml4(input);
            }
        }
        return input;
    }
}
