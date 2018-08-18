<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的订单 - 新巴巴</title>
<meta name="Keywords" content="java,新巴巴java" />
<meta name="description"
	content="在新巴巴中找到了29910件java的类似商品，其中包含了“图书”，“电子书”，“教育音像”，“骑行运动”等类型的java的商品。" />
<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.common.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.order.css"
	media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript">
function check(){
	alert($("option:selected").val());
}
</script>
</head>
<body>
	<script type="text/javascript" src="/js/base-2011.js" charset="utf-8"></script>
	<!-- header start -->
	<jsp:include page="commons/header.jsp" />
	<!-- header end -->

	<div id="container">
		<div class="w">

			<div id="main">
				<div class="g-0">
					<div id="content" class="c-3-5">
						<div class="mod-main mod-comm" id="order01">
							<div class="m m3" id="safeinfo" style="display: none"></div>
							<div class="mt">
								<h3>
									我的订单
									<div class="layer-credit" id="creditPayShow"
										style="display: none">
										<a class="close" href="#none"
											clstag="click|keycount|orderinfo|baitiaoclose">关闭</a>
										<div class="cont">
											<span></span> <a class="go" href="#none" target="_blank"></a>
										</div>
									</div>
								</h3>
							</div>
							<div class="mc" id="order01">
								<dl>
									<dt>便利提醒：</dt>
									<dd id="ordercount-waitPay"
										clstag="click|keycount|orderinfo|waitPay">待付款(0)</dd>
									<dd id="ordercount-waitReceive"
										clstag="click|keycount|orderinfo|waitReceive">待确认收货(0)</dd>
									<dd id="ordercount-waitPick"
										clstag="click|keycount|orderinfo|waitPick">待评价(0)</dd>
								</dl>
							</div>
						</div>
						<div class="mod-main mod-comm lefta-box" id="order02">
							<div class="mt">
								<div class="extra-r">
									<div class="search-01">
										<input id="ip_keyword" name="" type="text" class="s-itxt"
											value="商品名称、商品编号、订单编号"
											onfocus="if (this.value==this.defaultValue) this.value=''"
											onblur="if (this.value=='') this.value=this.defaultValue"
											onkeydown="javascript:if(event.keyCode==13) OrderSearch('ip_keyword');">
											<!--input name="" type="button" value="查 询" class="btn-13" onclick="OrderSearch('ip_keyword')" clstag="click|keycount|orderinfo|search"/-->
											<a href="javascript:;" class="btn-13"
											onclick="OrderSearch('ip_keyword')"
											clstag="click|keycount|orderinfo|search">查 询</a>
									</div>
								</div>
							</div>
							<div class="mc">
								<table class="tb-void">
									<colgroup>
										<col width="290"/>
										<col width="90"/>
										<col width="110"/>
										<col width="100"/>
										<col width="100"/>
										<col width="130"/>
									</colgroup>
									<thead>
										<tr>
											<th>订单信息</th>
											<th>收货人</th>
											<th>订单金额</th>
											<th><select id="submitDate" name="" class="sele" onchange="check()">
													<option value="1" selected="">最近三个月</option>
													<option value="2">今年内</option>
													<option value="2013">2015年</option>
													<option value="2012">2014年</option>
													<option value="3">2014年以前</option>
											</select></th>
											<th><select id="orderState" name="" class="sele">
													<option value="4096" selected="">全部状态</option>
													<option value="1">等待付款</option>
													<option value="32">等待收货</option>
													<option value="128">等待评价</option>
													<option value="1024">已完成</option>
											</select></th>
											<th>操作</th>
										</tr>
									</thead>
									
									<c:forEach items="${orders }" var="order">
									<tbody id="tb-2538292730">

										<tr class="tr-th">
											<td colspan="6"><span class="tcol1"> 订单编号: <a
													name="orderIdLinks" id="idUrl2538292730" target="_blank"
													href="http://order.jd.com/normal/item.action?orderid=2538292730&amp;PassKey=769448C6BA99F1ADA8244BAE7BC60580"
													clstag="click|keycount|orderinfo|order_num">${order.id }</a>

											</span> <span class="tcol2"> 新巴巴 </span> <span class="tcol3">
													<a class="btn-im" onclick="getPamsForChat()" href="#none"
													title="联系客服"></a>
											</span></td>
										</tr>
										<tr id="track2538292730" oty="0,1,70" class="tr-td">
											<td>
												<div class="img-list">
													<a href="http://item.jd.com/1113410.html" class="img-box"
														clstag="click|keycount|orderinfo|order_product"
														target="_blank"> <img
														title="TP-LINK TL-WR885N 450M无线路由器（白）" width="50"
														height="50"
														src="/images/rBEhV1NXYLYIAAAAAADGO8-wV-UAAMfdgNi-BAAAMZT367.jpg"
														class="err-product"></a> 
													<a href="http://item.jd.com/1222567.html" class="img-box"
														clstag="click|keycount|orderinfo|order_product"
														target="_blank"> <img
														title="RND 扁平网线 超高速六类（CAT.6)  超薄扁平  线长为1米5 炫白色" width="50"
														height="50" src="/images/541fdd63Nab1b3c63.jpg"
														class="err-product"></a>
												</div>
											</td>
											<td><div class="u-name">上官云</div></td>
											<td>￥${order.orderPrice }<br> 货到付款<br></td>
											<td><span class="ftx-03">${order.createDate }<br>
														22:30:49</span> <input type="hidden" id="datasubmit-2538292730"
												value="2014-10-20 22:30:49"></td>

											<td><span class="ftx-03">已完成</span></td>

											<td id="operate2538292730" class="order-doi" width="100">

												<span id="pay-button-2538292730" state=""></span> <a
												href="javascript:void(0)"
												clstag="click|keycount|orderinfo|order_del"
												onclick="ensureMoveOrderToRecycle(2538292730,'397FF574E089D5409E6CC8EF67129D65');">删除</a></span><span
												id="doi2538292730"><br><a
														href="http://club.jd.com/JdVote/TradeComment.aspx?ruleid=2538292730&amp;ot=0&amp;payid=1&amp;shipmentid=70"
														target="_blank"
														clstag="click|keycount|orderinfo|order_comment">评价晒单</a>
											</td>
										</tr>
									</tbody>
									</c:forEach>
								</table>
							</div>
							<div class="mt10">
								<div class="pagin fr">
									<!--  <span class="text">共20条记录</span>    <span class="text">共1页</span> -->
									<span class="prev-disabled">上一页<b></b></span>

									<!-- <span class="prev-disabled">首页</span> -->
									<a class="current">1</a>
									<!-- <span class="next-disabled">末页</span>  -->
									<span class="next-disabled">下一页<b></b></span>

								</div>
								<div class="clr"></div>
							</div>
						</div>


					</div>
				</div>
				<jsp:include page="commons/left.jsp">
					<jsp:param name="page_index" value="1" />
				</jsp:include>
				<span class="clr"></span>
			</div>
		</div>
	</div>

	<!-- footer start -->
	<jsp:include page="commons/footer.jsp" />
	<!-- footer end -->
</body>
</html>