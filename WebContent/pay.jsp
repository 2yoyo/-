<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>结算页面</title>
<base href="<%=request.getContextPath()+"/"%>">
		<link href="AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />

		<link href="basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="css/cartstyle.css" rel="stylesheet" type="text/css" />

		<link href="css/jsstyle.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
		<script type="text/javascript">
			
			$(function(){
				
				
				// 1.先获取默认的地址的元素Id
				var deEleId = $("#del_1");
				
				// 2.打钩
				deEleId.attr("class","user-addresslist defaultAddr");
				
				// 3.默认的标签
				$("#del_1 .address-left").append('<ins class="deftip">默认地址</ins>');
				
				
				$("ul[class='addressChoose'] li").each(function(){
						$(this).unbind();
						$(this).click(function(){
							$("ul[class='addressChoose'] li").removeClass("defaultAddr");
							$(this).addClass("defaultAddr");
							var shouhuoren = $("li[class='user-addresslist defaultAddr'] span[class='buy-user']").html();
							var dianhua = $("li[class='user-addresslist defaultAddr'] span[class='buy-phone']").html();
							var dizhi = $("li[class='user-addresslist defaultAddr'] span[class='buy--address-detail']").html();
							$("#buy_user").val(shouhuoren);
							$("#phone").val(dianhua);
							$("#address").val(dizhi);
							
							$("#send").html(dizhi);
							$("#person").html(shouhuoren);
							$("#tel").html(dianhua);	
						})
					})
					
					$("ul[class='op_express_delivery_hot'] li").each(function(){
						$(this).unbind();
						$(this).click(function(){
							var express = $(this).children("span").attr("id");
							$("#express").val(express);
							$("ul[class='op_express_delivery_hot'] li").removeClass("selected");
							$(this).addClass("selected");
						})
					})
					
					$("ul[class='pay-list'] li").each(function(){
						$(this).unbind();
						$(this).click(function(){
							var paytype = $(this).children("span").attr("id");
							$("#paytype").val(paytype);
							$("ul[class='pay-list'] li").removeClass("selected");
							$(this).addClass("selected");
						})
					})
					
				
				$("#go").click(function(){
					$("#form").submit();
				})
				
			})
			
		
			
		</script>
		
	</head>

	<body>

	<%@include file="head.jsp" %>
			<div class="concent">
				<!--地址 -->
				<div class="paycont">
					<div class="address">
						<h3>确认收货地址 </h3>
						<div class="control">
							<div class="tc-btn createAddr theme-login am-btn am-btn-danger"><a href="addAddress.jsp" style="color: white;">使用新地址</a></div>
						</div>
						<div class="clear"></div>
						<ul class="addressChoose">
							
								<!-- 默认地址：class="user-addresslist defaultAddr" -->
								<c:forEach items="${addressList}" var="address">
									<li class="user-addresslist" id="del_${address.isdefault}">
										<div class="address-left" >
											<div class="user DefaultAddr">
												 <span class="buy-address-detail"></span>
		                  						 <span class="">收货人</span>
												 <span class="buy-user">${address.shouhuoren}</span>
											</div>
											<div class="user DefaultAddr">
												 <span class="buy-address-detail"></span>
		                  						 <span class="">电话</span>
												 <span class="buy-phone">${address.phone }</span>
											</div>
												
											<div class="default-address DefaultAddr">
												<span class="buy-line-title buy-line-title-type">收货地址：</span>
												<span class="buy--address-detail">${address.address}</span>
											</div>
<!-- 												<ins class="deftip">默认地址</ins> -->
										</div>
										<div class="new-addr-btn">
											<span class="new-addr-bar hidden">|</span>
											<a href="#">编辑</a>
											<span class="new-addr-bar">|</span>
											<a href="javascript:void(0);" onclick="delClick(this);">删除</a>
										</div>
								</li>
								</c:forEach>
						</ul>

						<div class="clear"></div>
					</div>
					<!--物流 -->
					<div class="logistics">
						<h3>选择物流方式</h3>
						<ul class="op_express_delivery_hot">
							<li data-value="yuantong" class="OP_LOG_BTN "><i class="c-gap-right" style="background-position:0px -468px"></i>圆通<span id="圆通"></span></li>
							<li data-value="shentong" class="OP_LOG_BTN "><i class="c-gap-right" style="background-position:0px -1008px"></i>申通<span id="申通"></span></li>
							<li data-value="yunda" class="OP_LOG_BTN "><i class="c-gap-right" style="background-position:0px -576px"></i>韵达<span id="韵达"></span></li>
							<li data-value="zhongtong" class="OP_LOG_BTN op_express_delivery_hot_last"><i class="c-gap-right" style="background-position:0px -324px"></i>中通<span id="中通"></span></li>
							<li data-value="shunfeng" class="OP_LOG_BTN  op_express_delivery_hot_bottom"><i class="c-gap-right" style="background-position:0px -180px"></i>顺丰<span id="顺丰"></span></li>
						</ul>
					</div>
					<div class="clear"></div>

					<!--支付方式-->
					<div class="logistics">
						<h3>选择支付方式</h3>
						<ul class="pay-list">
							<li class="pay card"><img src="images/wangyin.jpg" />银联<span id="银联"></span></li>
							<li class="pay qq"><img src="images/weizhifu.jpg" />微信<span id="微信"></span></li>
							<li class="pay taobao"><img src="images/zhifubao.jpg" />支付宝<span id="支付宝"></span></li>
						</ul>
					</div>
					<div class="clear"></div>

					<!--订单 -->
					<div class="concent">
						<div id="payTable">
							<h3>确认订单信息</h3>
							<div class="cart-table-th">
								<div class="wp">

									<div class="th th-item">
										<div class="td-inner">商品信息</div>
									</div>
									<div class="th th-price">
										<div class="td-inner">单价</div>
									</div>
									<div class="th th-amount">
										<div class="td-inner">数量</div>
									</div>
									<div class="th th-sum">
										<div class="td-inner">金额</div>
									</div>
									<div class="th th-oplist">
										<div class="td-inner">配送方式</div>
									</div>

								</div>
							</div>
							<div class="clear"></div>
							
					<c:forEach items="${goodsInfoList}" var="goodsInfo">
							<!-- 购物车 -->
							<tr class="item-list">
								<div class="bundle  bundle-last">

									<div class="bundle-main">
										<ul class="item-content clearfix">
											<div class="pay-phone">
												<li class="td td-item">
													<div class="item-pic">
														<a href="#" class="J_MakePoint">
															<img src="images/${goodsInfo.goods_pic}" style="width: 80px; height: 80px" class="itempic J_ItemImg"></a>
													</div>
													<div class="item-info">
														<div class="item-basic-info">
															<a href="#" class="item-title J_MakePoint" data-point="tbcart.8.11">${goodsInfo.goods_description}</a>
														</div>
													</div>
												</li>						
												<li class="td td-price">
													<div class="item-price price-promo-promo">
														<div class="price-content">
															<div class="price-line">
																<em class="price-original">${goodsInfo.goods_price_off}</em>
															</div>
															<div class="price-line"> 
																<em class="J_Price price-now" id="priceNow" tabindex="0">${goodsInfo.goods_price}</em>
															</div>
														</div>
													</div>
												</li>
											</div>
											<li class="td td-amount">
												<div class="amount-wrapper ">
													<div class="item-amount ">
														<span class="phone-title">购买数量</span>
														<div class="sl">
															<input class="min am-btn" name="" type="button" value="-" id="jian"/> 
															<input class="text_box" id="count" type="text" value="${sessionScope.shopCar.shopCarMap[goodsInfo.id]}" 
													          style="width: 30px; text-align: center;"/> 
													          <input class="add am-btn" name="" type="button" value="+"  id="jia"/>
														 <input type="hidden"  value="goodsId">
														</div>
													</div>
												</div>
											</li>
											<li class="td td-sum">
												<div class="td-inner">
													<em tabindex="0" class="J_ItemSum number" id="singlePrice"><fmt:formatNumber type="number" value="${sessionScope.shopCar.shopCarMap[goodsInfo.id]*goodsInfo.goods_price_off}" pattern="#.##"/></em>
												</div>
											</li>
											<li class="td td-oplist">
												<div class="td-inner">
													<span class="phone-title">配送方式</span>
													<div class="pay-logis">
														快递<b class="sys_item_freprice">10</b>元
													</div>
												</div>
											</li>

										</ul>
										<div class="clear"></div>

									</div>
							</tr>
							
							</c:forEach>
							
							<div class="clear"></div>
							</div>


							<!--信息 -->
							<div class="order-go clearfix">
								<div class="pay-confirm clearfix">
									<div class="box">
										<div tabindex="0" id="holyshit267" class="realPay"><em class="t">实付款：</em>
											<span class="price g_price ">
                                    <span>¥</span> <em class="style-large-bold-red " id="J_ActualFee"><fmt:formatNumber value="${sessionScope.shopCar.sumPrice}" pattern="#.##" /></em>
											</span>
										</div>

										<div id="holyshit268" class="pay-address">

											<p class="buy-footer-address">
												<span class="buy-line-title buy-line-title-type">寄送至：</span>
												<span class="buy--address-detail" id="send">
								  						
												</span>
											</p>
											<p class="buy-footer-address">
												<span class="buy-line-title">收货人：</span>
												<span class="buy-address-detail">   
                                         <span class="buy-user" id="person">艾迪 </span>
												<span class="buy-phone" id="tel">15871145629</span>
												</span>
											</p>
										</div>
									</div>

									<div id="holyshit269" class="submitOrder">
										<div class="go-btn-wrap">
											<a id="go" class="btn-go" tabindex="0" title="点击此按钮，提交订单">提交订单</a>
										</div>
										<form action="ShopServlet/pay" method="post" id="form">
											<input type="hidden" name="shouhuoren" value="" id="buy_user"/>
											<input type="hidden" name="phone" value="" id="phone"/>
											<input type="hidden" name="address" value="" id="address"/>
											<input type="hidden" name="express" value="" id="express"/>
											<input type="hidden" name="bank" value="" id="paytype"/>
										</form>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>

						<div class="clear"></div>
					</div>
				</div>
				<div class="footer">
					<div class="footer-hd">
						<p>
							<a href="#">恒望科技</a>
							<b>|</b>
							<a href="#">商城首页</a>
							<b>|</b>
							<a href="#">支付宝</a>
							<b>|</b>
							<a href="#">物流</a>
						</p>
					</div>
					<div class="footer-bd">
						<p>
							<a href="#">关于恒望</a>
							<a href="#">合作伙伴</a>
							<a href="#">联系我们</a>
							<a href="#">网站地图</a>
							<em>© 2015-2025 Hengwang.com 版权所有</em>
						</p>
					</div>
				</div>
			</div>
			<div class="theme-popover-mask"></div>
			<div class="theme-popover">

				<!--标题 -->
				<div class="am-cf am-padding">
					<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">新增地址</strong> / <small>Add address</small></div>
				</div>
				<hr/>

				<div class="am-u-md-12">
					<form class="am-form am-form-horizontal">

						<div class="am-form-group">
							<label for="user-name" class="am-form-label">收货人</label>
							<div class="am-form-content">
								<input type="text" id="user-name" placeholder="收货人">
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-form-label">手机号码</label>
							<div class="am-form-content">
								<input id="user-phone" placeholder="手机号必填" type="email">
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-form-label">所在地</label>
							<div class="am-form-content address">
								<select data-am-selected>
									<option value="a">浙江省</option>
									<option value="b">湖北省</option>
								</select>
								<select data-am-selected>
									<option value="a">温州市</option>
									<option value="b">武汉市</option>
								</select>
								<select data-am-selected>
									<option value="a">瑞安区</option>
									<option value="b">洪山区</option>
								</select>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-intro" class="am-form-label">详细地址</label>
							<div class="am-form-content">
								<textarea class="" rows="3" id="user-intro" placeholder="输入详细地址"></textarea>
								<small>100字以内写出你的详细地址...</small>
							</div>
						</div>

						<div class="am-form-group theme-poptit">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<div class="am-btn am-btn-danger">保存</div>
								<div class="am-btn am-btn-danger close">取消</div>
							</div>
						</div>
					</form>
				</div>

			</div>

			<div class="clear"></div>
	</body>

</html>