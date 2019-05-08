package com.qf.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class BackLoginFilter
 */
public class BackLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BackLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		
		// 1.用户当前登陆的用户
		Object user = req.getSession().getAttribute("user");
		
		String url = req.getRequestURL().toString();
		String action = url.substring(url.lastIndexOf("/")+1, url.length());
		
		// 2.判断用户是否登陆
		if(user != null || "backLogin".equals(action)){
			chain.doFilter(request, response);
		}else{
			resp.getWriter().write("<script>window.open('"+req.getContextPath()+"/backLogin.jsp','_parent')</script>");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
