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

		// ��װpage������
		userService.getUserPage(page);

		page.setUrl("UserServlet/getUserPage");
		// ��page�ŵ�req��������
		request.setAttribute("page", page);

		// ת������ʾҳ��
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

		// �ȸ����û����������ѯ�û�
		User user = userService.login(username, password);
		String contextPath = req.getContextPath();
		// �ж��û��Ƿ����
		if (user != null) {

			// �ж��û��Ƿ��ǹ���Ա
			if (user.getIsAdmin() == 1) {
				// ��user�ŵ�session��
				req.getSession().setAttribute("user", user);
				
				// ��ת����̨��ҳ
				try {
					resp.sendRedirect(contextPath+"/back/main.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					resp.getWriter().write("<script>alert('�����ǹ���Ա������ϵ����Ա������');location.href='"+contextPath+"/backLogin.jsp'</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.getWriter().write("<script>alert('�û�����������󣡣���');location.href='"+contextPath+"/backLogin.jsp'</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
