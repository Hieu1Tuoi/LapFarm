package LapFarm.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();

		// Allow access to /login if the user is not logged in (no "admin" or "user" in
		// session)
		if (uri.contains("/login")) {
			if (session.getAttribute("admin") != null || session.getAttribute("user") != null) {
				// If the user is logged in, redirect them to the home page (or any other page)
				response.sendRedirect(request.getContextPath() + "/");
				return false; // Prevent further processing of the login request
			}
		}
		// Allow other requests to proceed as normal
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}