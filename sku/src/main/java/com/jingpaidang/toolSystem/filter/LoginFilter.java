package com.jingpaidang.toolSystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	 }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();

		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();
		// 从session里用户信息
		String userEmail = (String) session.getAttribute("user");
		// 登陆页面无需过滤
		if (path.indexOf("/login.jsp") > -1 || path.indexOf("/css/")>-1 || path.indexOf("/colorbox/")>-1||path.indexOf("/images/")>-1||
				path.indexOf("/js/")>-1 || path.indexOf("/login")>-1 || path.indexOf("/register")>-1|| path.indexOf("/isUserEmailExist")>-1) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}


        // 判断如果没有取到用户信息,就跳转到登陆页面
        if (userEmail == null || "".equals(userEmail)) {
            // 跳转到登陆页面
            servletResponse.sendRedirect("/login.jsp");
        } else {
            // 已经登陆,继续此次请求
            if(path.equals("/add.jsp") || path.equals("/addSku")) {
                if("everyone@jingpaidang.com".equals(userEmail)) {
                    chain.doFilter(request, response);

                    return ;
                }  else {
                    servletResponse.sendRedirect("/login.jsp");
                }

            }  else {
                chain.doFilter(request, response);

            }
		}
	}
}
