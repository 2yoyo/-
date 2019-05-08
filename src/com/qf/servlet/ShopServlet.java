package com.qf.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.qf.entity.Address;
import com.qf.entity.GoodsInfo;
import com.qf.entity.GoodsType;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import com.qf.entity.ShopCar;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.IGoodsInfoService;
import com.qf.service.IGoodsTypeService;
import com.qf.service.IOrderService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.GoodsInfoServiceImpl;
import com.qf.service.impl.GoodsTypeServiceImpl;
import com.qf.service.impl.OrderServiceImpl;

public class ShopServlet extends DispatcherServlet {

	private IGoodsTypeService gtService = new GoodsTypeServiceImpl();
	private IGoodsInfoService giService = new GoodsInfoServiceImpl();
	private IAddressService addressService = new AddressServiceImpl();
	private IOrderService orderService = new OrderServiceImpl();

	/**
	 * ��ѯ��ҳ�е�����
	 * 
	 * @return
	 */
	public String getAll(HttpServletRequest req) {

		// ��ѯ���е���Ʒ�б�
		List<GoodsType> gtList = gtService.getAllGoodTypeList();

		// ��ѯ���е���Ʒ��Ϣ
		List<GoodsInfo> giList = giService.getAllGoodsInfoList();

		// �Ѽ������req��
		req.setAttribute("gtList", gtList);
		req.setAttribute("giList", giList);

		// ��ת����ҳչʾ
		return "forward:index.jsp";
	}

	public String getGoodsInfoById(Integer id, HttpServletRequest req) {
		GoodsInfo goodsInfo = giService.getById(id);
		req.setAttribute("goodsInfo", goodsInfo);
		// �ص�������Ʒչʾҳ��
		return "forward:introduction.jsp";
	}

	public void addShopCar(Integer id, Integer num, HttpServletRequest req) {

		// 1.�õ����ﳵ����
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());

		// 2.��ӹ��ﳵ
		shopCar.add(id, num);
	}

	public String getGoodsInfoListByIds(HttpServletRequest request) {

		// 1.�Ȼ�ȡ���ﳵ����
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());

		// 2.��ȡ���ﳵ�����е�map
		Map<Integer, Integer> shopCarMap = shopCar.getShopCarMap();

		// 3.�ٻ�ȡ���е���Ʒid
		Set<Integer> keySet = shopCarMap.keySet();

		if (!keySet.isEmpty()) {
			// 4.������Ʒid��ѯ��Ʒ����
			List<GoodsInfo> giList = giService.getGoodsInfoListByIds(keySet);

			// 5.�Ѽ��Ϸ��뵽req��
			request.setAttribute("giList", giList);
		}

		// 6.��ת�����ﳵҳ��
		return "forward:shopcat.jsp";
	}
	
	public void updateShopCarNum(Integer id,Integer num,HttpServletRequest req){
		
		// 1.��ȡ���ﳵ����
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());
		
		// 2.�޸Ĺ��ﳵ�е�����
		shopCar.update(id,num);
	}
	
	public String deleteShopCarById(Integer id,HttpServletRequest req){
		
		// 1.��ȡ���ﳵ����
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());
		
		// 2.�޸Ĺ��ﳵ�е�����
		shopCar.delete(id);
		
		return "redirect:ShopServlet/getGoodsInfoListByIds";
	}
	
	public String toPay(HttpServletRequest request){
		//���ж��Ƿ��¼
		User user = (User) request.getSession().getAttribute("customer");
		if (user==null) {
			user = new User();
			user.setId(20);
			//return "forward:login.jsp";
		}
		//�����û�ID��ѯ��ַ
		List<Address> addresses = addressService.getAddressListByUserId(user.getId());
		
		//��ѯ���ﳵ��Ϣ
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());
		//�жϹ��ﳵ���Ƿ�����Ʒ
		Set<Integer> keySet = shopCar.getShopCarMap().keySet();
		if (!keySet.isEmpty()) {
			List<GoodsInfo> goodsInfoList = giService.getGoodsInfoListByIds(keySet);
			request.setAttribute("goodsInfoList", goodsInfoList);
		}
		//�Ѽ��Ϸŵ�req��
		request.setAttribute("addressList", addresses);
		return "forward:pay.jsp";
		
	}
	
	
	public String pay(HttpServletRequest request){
		//���ж��Ƿ��¼
		User user = (User) request.getSession().getAttribute("customer");
		if (user==null) {
			user = new User();
			user.setId(20);
			//return "forward:login.jsp";
		}
		//��ȡ�û���Ϣ
		String shouhuoren = request.getParameter("shouhuoren");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String express = request.getParameter("express");
		String bank = request.getParameter("bank");
				
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());
		//��װ������������
		Order order = new Order();
		order.setO_orderdate(new Date());
		order.setO_paycount(shopCar.getShopCarCount()); //������
		order.setO_paytype(bank); //֧��
		order.setO_sendtype(express); //���
		order.setO_shperson(shouhuoren);
		order.setO_shphone(phone);
		order.setUserid(user.getId());
		//���붩��
		int orderId = orderService.add(order);//��Ҫ���ض���ID
		// ��װ�����������
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		//��ȡ���е���ƷID
		Set<Integer> keySet = shopCar.getShopCarMap().keySet();
		//��ȡ��ƷID��ѯ��Ʒ����
		List<GoodsInfo> goodsInfos = giService.getGoodsInfoListByIds(keySet);
		for(GoodsInfo goodsInfo:goodsInfos){
			OrderDetail orderDetail = new OrderDetail();
			//��Ʒ����
			Integer goodsnum = shopCar.getShopCarMap().get(goodsInfo.getId());
			orderDetail.setGoods_date(new Date());
			orderDetail.setGoods_description(goodsInfo.getGoods_description());
			orderDetail.setGoods_total_price(goodsInfo.getGoods_price_off()*goodsnum);
			orderDetail.setGoodsid(goodsInfo.getId()); // ��ƷId
			orderDetail.setGoodsname(goodsInfo.getGoods_name());
			orderDetail.setGoodsnum(goodsnum);
			orderDetail.setGoodspic(goodsInfo.getGoods_pic());
			orderDetail.setGoodsprice(goodsInfo.getGoods_price());
			orderDetail.setO_orderid(orderId); // ?
		
			// ��ӵ�������
			orderDetails.add(orderDetail);
		}
		
		// ���붩������
		
		// ֧���ɹ�����չ��ﳵ
		// ��ת���ɹ�ҳ��
		// Ҫ��ʾ�ܽ��ջ��˵���Ϣ
		// ��������(����)
		request.setAttribute("sumPrice", shopCar.getSumPrice());
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		return "forward:success.jsp";
		
		
		
	}
}
