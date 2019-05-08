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
	 * 查询首页中的数据
	 * 
	 * @return
	 */
	public String getAll(HttpServletRequest req) {

		// 查询所有的商品列表
		List<GoodsType> gtList = gtService.getAllGoodTypeList();

		// 查询所有的商品信息
		List<GoodsInfo> giList = giService.getAllGoodsInfoList();

		// 把集合添加req中
		req.setAttribute("gtList", gtList);
		req.setAttribute("giList", giList);

		// 跳转到首页展示
		return "forward:index.jsp";
	}

	public String getGoodsInfoById(Integer id, HttpServletRequest req) {
		GoodsInfo goodsInfo = giService.getById(id);
		req.setAttribute("goodsInfo", goodsInfo);
		// 回到单个商品展示页面
		return "forward:introduction.jsp";
	}

	public void addShopCar(Integer id, Integer num, HttpServletRequest req) {

		// 1.得到购物车对象
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());

		// 2.添加购物车
		shopCar.add(id, num);
	}

	public String getGoodsInfoListByIds(HttpServletRequest request) {

		// 1.先获取购物车对象
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());

		// 2.获取购物车对象中的map
		Map<Integer, Integer> shopCarMap = shopCar.getShopCarMap();

		// 3.再获取所有的商品id
		Set<Integer> keySet = shopCarMap.keySet();

		if (!keySet.isEmpty()) {
			// 4.根据商品id查询商品对象
			List<GoodsInfo> giList = giService.getGoodsInfoListByIds(keySet);

			// 5.把集合放入到req中
			request.setAttribute("giList", giList);
		}

		// 6.跳转到购物车页面
		return "forward:shopcat.jsp";
	}
	
	public void updateShopCarNum(Integer id,Integer num,HttpServletRequest req){
		
		// 1.获取购物车对象
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());
		
		// 2.修改购物车中的数量
		shopCar.update(id,num);
	}
	
	public String deleteShopCarById(Integer id,HttpServletRequest req){
		
		// 1.获取购物车对象
		ShopCar shopCar = ShopCar.getShopCartIns(req.getSession());
		
		// 2.修改购物车中的数量
		shopCar.delete(id);
		
		return "redirect:ShopServlet/getGoodsInfoListByIds";
	}
	
	public String toPay(HttpServletRequest request){
		//先判断是否登录
		User user = (User) request.getSession().getAttribute("customer");
		if (user==null) {
			user = new User();
			user.setId(20);
			//return "forward:login.jsp";
		}
		//根据用户ID查询地址
		List<Address> addresses = addressService.getAddressListByUserId(user.getId());
		
		//查询购物车信息
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());
		//判断购物车中是否有商品
		Set<Integer> keySet = shopCar.getShopCarMap().keySet();
		if (!keySet.isEmpty()) {
			List<GoodsInfo> goodsInfoList = giService.getGoodsInfoListByIds(keySet);
			request.setAttribute("goodsInfoList", goodsInfoList);
		}
		//把集合放到req中
		request.setAttribute("addressList", addresses);
		return "forward:pay.jsp";
		
	}
	
	
	public String pay(HttpServletRequest request){
		//先判断是否登录
		User user = (User) request.getSession().getAttribute("customer");
		if (user==null) {
			user = new User();
			user.setId(20);
			//return "forward:login.jsp";
		}
		//获取用户信息
		String shouhuoren = request.getParameter("shouhuoren");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String express = request.getParameter("express");
		String bank = request.getParameter("bank");
				
		ShopCar shopCar = ShopCar.getShopCartIns(request.getSession());
		//封装到订单对象中
		Order order = new Order();
		order.setO_orderdate(new Date());
		order.setO_paycount(shopCar.getShopCarCount()); //总数量
		order.setO_paytype(bank); //支付
		order.setO_sendtype(express); //快递
		order.setO_shperson(shouhuoren);
		order.setO_shphone(phone);
		order.setUserid(user.getId());
		//插入订单
		int orderId = orderService.add(order);//需要返回订单ID
		// 封装订单详情对象
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		//获取所有的商品ID
		Set<Integer> keySet = shopCar.getShopCarMap().keySet();
		//获取商品ID查询商品对象
		List<GoodsInfo> goodsInfos = giService.getGoodsInfoListByIds(keySet);
		for(GoodsInfo goodsInfo:goodsInfos){
			OrderDetail orderDetail = new OrderDetail();
			//商品数量
			Integer goodsnum = shopCar.getShopCarMap().get(goodsInfo.getId());
			orderDetail.setGoods_date(new Date());
			orderDetail.setGoods_description(goodsInfo.getGoods_description());
			orderDetail.setGoods_total_price(goodsInfo.getGoods_price_off()*goodsnum);
			orderDetail.setGoodsid(goodsInfo.getId()); // 商品Id
			orderDetail.setGoodsname(goodsInfo.getGoods_name());
			orderDetail.setGoodsnum(goodsnum);
			orderDetail.setGoodspic(goodsInfo.getGoods_pic());
			orderDetail.setGoodsprice(goodsInfo.getGoods_price());
			orderDetail.setO_orderid(orderId); // ?
		
			// 添加到集合中
			orderDetails.add(orderDetail);
		}
		
		// 插入订单详情
		
		// 支付成功后清空购物车
		// 跳转到成功页面
		// 要显示总金额，收货人的信息
		// 订单管理(周六)
		request.setAttribute("sumPrice", shopCar.getSumPrice());
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		return "forward:success.jsp";
		
		
		
	}
}
