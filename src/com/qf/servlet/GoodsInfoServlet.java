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

		// 封装page对象
		gfService.getPage(page);

		// 设置url
		page.setUrl("GoodsInfoServlet/getGoodsInfoPage");

		// 把page放到req中
		req.setAttribute("page", page);

		return "forward:back/goods/goodsList.jsp";
	}

	public String toAddGoodsInfo(HttpServletRequest req) {

		// 1.查询所有的商品类别
		List<GoodsType> gtList = gtService.getAllGoodTypeList();

		// 2.把gtTypes放到req中
		req.setAttribute("gtList", gtList);

		// 3.跳转到添加页面
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

		// 1.创建一个文件上传的工程
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建一个上传文件的核心对象
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 3.解析req对象
			List<FileItem> parseRequest = upload.parseRequest(req);

			// 创建一个Map用来装表单中的数据
			Map<String, Object> map = new HashMap<String, Object>();
			// 5.遍历集合
			for (FileItem fileItem : parseRequest) {
				// 获元素的name属性
				String fieldName = fileItem.getFieldName(); 
				// 判断是否是二进制文件
				if (fileItem.isFormField()) { 
					String value = fileItem.getString("utf-8"); // 元素对应的value值
					map.put(fieldName, value);
				} else {
					// 数据表中要存在图片名称，要把图片名称放到map中
					String name = fileItem.getName();
					
					// 判断用户是否上传图片
					if (name != null && !"".equals(name)) {
						map.put(fieldName, name);

						// 把图片上传到指定目录
						InputStream ips = null;
						FileOutputStream ops = null;
						try {
							// 1.先获取上传文件的流
							ips = fileItem.getInputStream();

							// 2.获取输出文件路径
							String realPath = req.getServletContext().getRealPath("images");

							// 3.创建一个输出流
							ops = new FileOutputStream(realPath + File.separator + name);

							// 4.拷贝图片
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
