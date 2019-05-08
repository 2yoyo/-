package com.qf.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.qf.service.impl.GoodsInfoServiceImpl;

/**
 * ���ﳵ����
 * 
 * @author Windows
 *
 */
public class ShopCar {
	// private List<GoodsInfo> list = new ArrayList<GoodsInfo>();
	// key:��Ʒid
	// value:��Ʒ������
	private Map<Integer, Integer> shopCarMap = new HashMap<Integer, Integer>();

	/**
	 * �����ﳵ�������Ʒ
	 * 
	 * @param goodsInfoId
	 *            ��Ʒid
	 * @param num
	 *            ����
	 */
	public void add(Integer goodsInfoId, Integer num) {

		// 1.���ж���Ʒ�Ƿ����
		if (shopCarMap.containsKey(goodsInfoId)) {
			
			// 2.���������ȡ��֮ǰ���������µ���Ʒ�������
			num += shopCarMap.get(goodsInfoId);
			
			// 3.�ж���Ʒ�������Ƿ񳬹�10��
			if(num >=10){
				num = 10;
			}
		}
		
		// 5.��ӹ��ﳵ��
		getShopCarMap().put(goodsInfoId, num);

	}

	/**
	 * ɾ�����ﳵ�е���Ʒ
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		getShopCarMap().remove(id);
	}

	public void update(Integer id, Integer num) {
		this.shopCarMap.put(id, num); // keyһֱvalue������
	}

	// shopCarSize
	// ${sessionScope.shopCar.shopCarSize}
	public Integer getShopCarSize() {
		return getShopCarMap().keySet().size();
	}

	public static ShopCar getShopCartIns(HttpSession session) {
		// 1.�ȵ�session�л�ȡ���ﳵ����
		ShopCar shopCar = (ShopCar) session.getAttribute("shopCar");
		// ${user.username} pageContent-->req-->session-->application
		// ${sessionScope.user.username}
		// ShopCar shopCar = (ShopCar)session.getAttribute("user");//

		// 2.�жϵ�ǰsession�Ƿ��й��ﳵ����
		if (shopCar == null) {
			shopCar = new ShopCar();
			// �´����Ĺ��ﳵ����һ��Ҫ����sessin��
			session.setAttribute("shopCar", shopCar);
		}

		// 3.���ع��ﳵ����
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
			
			// ������Ʒid�����Ʒ������
			Integer num = shopCarMap.get(goodsInfo.getId());
			
			sum +=goodsInfo.getGoods_price_off()*num;
		}
		return sum;
	}
	
}
