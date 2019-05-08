package com.qf.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.qf.entity.GoodsInfo;
import com.qf.entity.GoodsType;
import com.qf.entity.Page;
import com.qf.service.IGoodsInfoService;
import com.qf.service.IGoodsTypeService;
import com.qf.service.impl.GoodsInfoServiceImpl;
import com.qf.service.impl.GoodsTypeServiceImpl;

public class GoodsInfoServlet extends DispatcherServlet {

	private IGoodsInfoService gfService = new GoodsInfoServiceImpl();
	private IGoodsTypeService gtService = new GoodsTypeServiceImpl();

	public String getGoodsInfoPage(Page<GoodsInfo> page, HttpServletRequest req) {

		// ��װpage����
		gfService.getPage(page);

		// ����url
		page.setUrl("GoodsInfoServlet/getGoodsInfoPage");

		// ��page�ŵ�req��
		req.setAttribute("page", page);

		return "forward:back/goods/goodsList.jsp";
	}

	public String toAddGoodsInfo(HttpServletRequest req) {

		// 1.��ѯ���е���Ʒ���
		List<GoodsType> gtList = gtService.getAllGoodTypeList();

		// 2.��gtTypes�ŵ�req��
		req.setAttribute("gtList", gtList);

		// 3.��ת�����ҳ��
		return "forward:back/goods/goodsadd.jsp";
	}

	public String addGoodsInfo(HttpServletRequest req) {
		GoodsInfo goodsInfo = uploadFile(req);
		gfService.add(goodsInfo);
		return "redirect:GoodsInfoServlet/getGoodsInfoPage";
	}

	public String getGoodsInfoById(Integer id, HttpServletRequest request) {
		GoodsInfo goodsInfo = gfService.getById(id);
		List<GoodsType> gtList = gtService.getAllGoodTypeList();
		request.setAttribute("gtList", gtList);
		request.setAttribute("goodsInfo", goodsInfo);
		return "forward:back/goods/goodupdate.jsp";
	}

	public String udpateGoodsInfo(HttpServletRequest request) {
		GoodsInfo goodsInfo = uploadFile(request);
		gfService.update(goodsInfo);
		return "redirect:GoodsInfoServlet/getGoodsInfoPage";
	}

	public GoodsInfo uploadFile(HttpServletRequest req) {
		GoodsInfo goodsInfo = new GoodsInfo();

		// 1.����һ���ļ��ϴ��Ĺ���
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.����һ���ϴ��ļ��ĺ��Ķ���
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 3.����req����
			List<FileItem> parseRequest = upload.parseRequest(req);

			// ����һ��Map����װ���е�����
			Map<String, Object> map = new HashMap<String, Object>();
			// 5.��������
			for (FileItem fileItem : parseRequest) {
				// ��Ԫ�ص�name����
				String fieldName = fileItem.getFieldName(); 
				// �ж��Ƿ��Ƕ������ļ�
				if (fileItem.isFormField()) { 
					String value = fileItem.getString("utf-8"); // Ԫ�ض�Ӧ��valueֵ
					map.put(fieldName, value);
				} else {
					// ���ݱ���Ҫ����ͼƬ���ƣ�Ҫ��ͼƬ���Ʒŵ�map��
					String name = fileItem.getName();
					
					// �ж��û��Ƿ��ϴ�ͼƬ
					if (name != null && !"".equals(name)) {
						map.put(fieldName, name);

						// ��ͼƬ�ϴ���ָ��Ŀ¼
						InputStream ips = null;
						FileOutputStream ops = null;
						try {
							// 1.�Ȼ�ȡ�ϴ��ļ�����
							ips = fileItem.getInputStream();

							// 2.��ȡ����ļ�·��
							String realPath = req.getServletContext().getRealPath("images");

							// 3.����һ�������
							ops = new FileOutputStream(realPath + File.separator + name);

							// 4.����ͼƬ
							IOUtils.copy(ips, ops);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							IOUtils.closeQuietly(ips);
							IOUtils.closeQuietly(ops);
						}
					}

				}
			}

			BeanUtils.populate(goodsInfo, map);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return goodsInfo;
	}
}
