package com.qf.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qf.entity.Page;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.service.impl.UserServieImpl;

public class UserServlet extends DispatcherServlet {

	private IUserService userService = new UserServieImpl();

	public String getUserPage(Page<User> page, HttpServletRequest request) {

		// 封装page的数据
		userService.getUserPage(page);

		page.setUrl("UserServlet/getUserPage");
		// 把page放到req作用域中
		request.setAttribute("page", page);

		// 转发到显示页面
		return "forward:back/user/userinfo.jsp";
	}

	public String addUser(User user) {
		userService.addUser(user);
		return "redirect:UserServlet/getUserPage";
	}

	public String getUserById(Integer id, Integer page, HttpServletRequest request) {
		User user = userService.getUserById(id);
		request.setAttribute("user", user);
		request.setAttribute("page", page);
		return "forward:back/user/updateuser.jsp";
	}

	public String updateUser(User user, Integer page) {
		userService.updateUser(user);
		return "redirect:UserServlet/getUserPage?currentPage=" + page;
	}

	public String deleteUser(Integer id) {
		userService.deleteUser(id);
		return "redirect:UserServlet/getUserPage";
	}

	public void backLogin(String username, String password, HttpServletRequest req, HttpServletResponse resp) {

		// 先根据用户名和密码查询用户
		User user = userService.login(username, password);
		String contextPath = req.getContextPath();
		// 判断用户是否存在
		if (user != null) {

			// 判断用户是否是管理员
			if (user.getIsAdmin() == 1) {
				// 把user放到session中
				req.getSession().setAttribute("user", user);
				
				// 跳转到后台首页
				try {
					resp.sendRedirect(contextPath+"/back/main.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					resp.getWriter().write("<script>alert('您不是管理员，请联系管理员。。。');location.href='"+contextPath+"/backLogin.jsp'</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.getWriter().write("<script>alert('用户名或密码错误！！！');location.href='"+contextPath+"/backLogin.jsp'</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
