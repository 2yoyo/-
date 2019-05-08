package com.qf.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.qf.service.impl.GoodsInfoServiceImpl;

/**
 * 购物车的类
 * 
 * @author Windows
 *
 */
public class ShopCar {
	// private List<GoodsInfo> list = new ArrayList<GoodsInfo>();
	// key:商品id
	// value:商品的数量
	private Map<Integer, Integer> shopCarMap = new HashMap<Integer, Integer>();

	/**
	 * 往购物车中添加商品
	 * 
	 * @param goodsInfoId
	 *            商品id
	 * @param num
	 *            数量
	 */
	public void add(Integer goodsInfoId, Integer num) {

		// 1.先判断商品是否存在
		if (shopCarMap.containsKey(goodsInfoId)) {
			
			// 2.如果存在先取出之前的数量和新的商品数量相加
			num += shopCarMap.get(goodsInfoId);
			
			// 3.判断商品的数量是否超过10个
			if(num >=10){
				num = 10;
			}
		}
		
		// 5.添加购物车中
		getShopCarMap().put(goodsInfoId, num);

	}

	/**
	 * 删除购物车中的商品
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		getShopCarMap().remove(id);
	}

	public void update(Integer id, Integer num) {
		this.shopCarMap.put(id, num); // key一直value被覆盖
	}

	// shopCarSize
	// ${sessionScope.shopCar.shopCarSize}
	public Integer getShopCarSize() {
		return getShopCarMap().keySet().size();
	}

	public static ShopCar getShopCartIns(HttpSession session) {
		// 1.先到session中获取购物车对象
		ShopCar shopCar = (ShopCar) session.getAttribute("shopCar");
		// ${user.username} pageContent-->req-->session-->application
		// ${sessionScope.user.username}
		// ShopCar shopCar = (ShopCar)session.getAttribute("user");//

		// 2.判断当前session是否有购物车对象
		if (shopCar == null) {
			shopCar = new ShopCar();
			// 新创建的购物车对象一定要反倒sessin中
			session.setAttribute("shopCar", shopCar);
		}

		// 3.返回购物车对象
		return shopCar;
	}

	public Map<Integer, Integer> getShopCarMap() {
		return shopCarMap;
	}

	public void setShopCarMap(Map<Integer, Integer> shopCarMap) {
		this.shopCarMap = shopCarMap;
	}

	public Integer getShopCarCount() {
		int count = 0;
		Set<Entry<Integer, Integer>> entrySet = shopCarMap.entrySet();
		for (Entry<Integer, Integer> entry : entrySet) {
			count += entry.getValue();
		}
		return count;
	}
	
	public Double getSumPrice(){
		double sum = 0.0;
		Set<Integer> idSet = this.shopCarMap.keySet();
		List<GoodsInfo> lists = new GoodsInfoServiceImpl().getGoodsInfoListByIds(idSet);
		for (GoodsInfo goodsInfo : lists) {
			
			// 根据商品id获得商品的数量
			Integer num = shopCarMap.get(goodsInfo.getId());
			
			sum +=goodsInfo.getGoods_price_off()*num;
		}
		return sum;
	}
	
}
