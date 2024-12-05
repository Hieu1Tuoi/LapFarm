package LapFarm.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.Controller.GlobalController;
import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.CartServiceImp;

public class GlobalInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		String uri = request.getRequestURI();
		if (uri.endsWith("/login")) {
			return true;
		}

		if (session.getAttribute("admin") == null) {
			response.sendRedirect(request.getContextPath() + "/error/403");
			return false;
		}
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