package com.qf.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DispatcherServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取调用action
		String actionName = getActionName(request.getRequestURL().toString());

		// 根据actionName获取方法对象(不考虑方法的重载问题)
		Method actionMethod = getMethodByActionName(actionName);

		if (actionMethod != null) {

			//处理方法的参数(?)
			Object[] param = methodParam(request, response, actionMethod);

			try {
				//调用方法
				Object result = actionMethod.invoke(this, param);
				
				if(result != null && !"".equals(result.toString().trim())){
					//响应客户端
					responseClient(result.toString(), request, response);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private void responseClient(String result, HttpServletRequest request, HttpServletResponse response) {
		String[] split = result.split(":");
		String type = split[0];
		String page = split[1]; 
		if ("forward".equals(type)) { 
			try {
				request.getRequestDispatcher("/"+page).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("redirect".equals(type)) {
			try {
				// 重定向加/代表的站点的个目录
				response.sendRedirect(request.getContextPath()+"/"+page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理方法的参数
	 * 
	 * @param request
	 *            req对象
	 * @param response
	 * @param actionMethod
	 *            被调用的方法
	 * @return 方法实际参数
	 */
	private Object[] methodParam(HttpServletRequest request, HttpServletResponse response, Method actionMethod) {

		//创建一个数组用来装方法的实参
		Object[] param = new Object[actionMethod.getParameterCount()];

		//获取到方法的参数
		Parameter[] parameters = actionMethod.getParameters();

		//循环给参数给值
		for (int i = 0; i < parameters.length; i++) {

			//方法上面是否接受req和resp对象
			String simpleName = parameters[i].getType().getSimpleName();
			if ("HttpServletRequest".equals(simpleName)) {
				param[i] = request;
			} else if ("HttpServletResponse".equals(simpleName)) {
				param[i] = response;
			} else {

				//获取形参的名称
				String name = parameters[i].getName();

				//根据形参名称到req中取值
				String value = request.getParameter(name);

				if (value != null) {
					//把value值放到数组中
					pushParam(param, i, value, simpleName);

				} else {
					try {
						//当成自定义对象处理
						Object obj = parameters[i].getType().newInstance();

						//获取表单数据
						Map<String, String[]> parameterMap = request.getParameterMap();

						//把parameterMap的数据拷贝到obj对象中
						BeanUtils.populate(obj, parameterMap);
						param[i] = obj;
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			}
		}

		return param;
	}

	/**
	 * 根据方法的参数类型赋值
	 * 
	 * @param param
	 *            实参的数组
	 * @param i
	 *            方法参数下标
	 * @param value
	 *            从req中获取到的值
	 * @param simpleName
	 *            参数的类型名称
	 */
	private void pushParam(Object[] param, int index, String value, String simpleName) {
		if ("Integer".equals(simpleName)) {
			param[index] = Integer.parseInt(value);
		} else if ("Double".equals(simpleName)) {
			param[index] = Double.parseDouble(value);
		} else {
			param[index] = value;
		}
	}

	/**
	 * 根据actionName获取method对象
	 * 
	 * @param actionName
	 *            方法名称
	 * @return mehod对象
	 */
	private Method getMethodByActionName(String actionName) {

		// 获取所有的方法
		Method[] methods = this.getClass().getDeclaredMethods();

		// 循环判断
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.equals(actionName)) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 * 从地址栏中截取action的名称
	 * 
	 * @param url
	 *            地址栏
	 * @return
	 */
	private String getActionName(String url) {
		int lastIndexOf = url.lastIndexOf("/");
		return url.substring(lastIndexOf + 1, url.length());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
