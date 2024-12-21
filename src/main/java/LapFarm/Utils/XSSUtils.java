package LapFarm.Utils;

import org.apache.tomcat.jakartaee.commons.lang3.StringEscapeUtils;

public class XSSUtils {
	 // Kiểm tra chuỗi có chứa mã hóa HTML hay không
    private static boolean isHtmlEscaped(String input) {
        return input.contains("&lt;") || input.contains("&gt;") || input.contains("&amp;");
    }

    public static boolean containsXSS(String input) {
        if (input == null) {
            return false;
        }
        // Kiểm tra các ký tự đặc biệt XSS cơ bản như <, >, &, ", '
        return input.contains("<") || input.contains(">") || input.contains("&") || input.contains("\"") || input.contains("'");
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
