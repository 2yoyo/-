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

		// ��ȡ����action
		String actionName = getActionName(request.getRequestURL().toString());

		// ����actionName��ȡ��������(�����Ƿ�������������)
		Method actionMethod = getMethodByActionName(actionName);

		if (actionMethod != null) {

			//�������Ĳ���(?)
			Object[] param = methodParam(request, response, actionMethod);

			try {
				//���÷���
				Object result = actionMethod.invoke(this, param);
				
				if(result != null && !"".equals(result.toString().trim())){
					//��Ӧ�ͻ���
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
				// �ض����/�����վ��ĸ�Ŀ¼
				response.sendRedirect(request.getContextPath()+"/"+page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �������Ĳ���
	 * 
	 * @param request
	 *            req����
	 * @param response
	 * @param actionMethod
	 *            �����õķ���
	 * @return ����ʵ�ʲ���
	 */
	private Object[] methodParam(HttpServletRequest request, HttpServletResponse response, Method actionMethod) {

		//����һ����������װ������ʵ��
		Object[] param = new Object[actionMethod.getParameterCount()];

		//��ȡ�������Ĳ���
		Parameter[] parameters = actionMethod.getParameters();

		//ѭ����������ֵ
		for (int i = 0; i < parameters.length; i++) {

			//���������Ƿ����req��resp����
			String simpleName = parameters[i].getType().getSimpleName();
			if ("HttpServletRequest".equals(simpleName)) {
				param[i] = request;
			} else if ("HttpServletResponse".equals(simpleName)) {
				param[i] = response;
			} else {

				//��ȡ�βε�����
				String name = parameters[i].getName();

				//�����β����Ƶ�req��ȡֵ
				String value = request.getParameter(name);

				if (value != null) {
					//��valueֵ�ŵ�������
					pushParam(param, i, value, simpleName);

				} else {
					try {
						//�����Զ��������
						Object obj = parameters[i].getType().newInstance();

						//��ȡ������
						Map<String, String[]> parameterMap = request.getParameterMap();

						//��parameterMap�����ݿ�����obj������
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
	 * ���ݷ����Ĳ������͸�ֵ
	 * 
	 * @param param
	 *            ʵ�ε�����
	 * @param i
	 *            ���������±�
	 * @param value
	 *            ��req�л�ȡ����ֵ
	 * @param simpleName
	 *            ��������������
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
	 * ����actionName��ȡmethod����
	 * 
	 * @param actionName
	 *            ��������
	 * @return mehod����
	 */
	private Method getMethodByActionName(String actionName) {

		// ��ȡ���еķ���
		Method[] methods = this.getClass().getDeclaredMethods();

		// ѭ���ж�
		for (int i = 0; i < methods.length; i++) {
			String name = methods[i].getName();
			if (name.equals(actionName)) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 * �ӵ�ַ���н�ȡaction������
	 * 
	 * @param url
	 *            ��ַ��
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
