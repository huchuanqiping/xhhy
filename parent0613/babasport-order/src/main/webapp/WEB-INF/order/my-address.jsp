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
<link rel="stylesheet" type="text/css" href="/css/myjd.order.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/myjd.easebuy.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/colorbox.css"
	media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript">
function editAliasName(){
	$("#alias-form-137805074").show();
	
}
function saveAddessAlias(id,target){
	$("#alias-form-137805074").hide();
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
						<div id="addressList" class="mod-main mod-comm">
							<div class="mt">
								<a onclick="alertAddAddressDiag()" class="e-btn add-btn btn-5"
									href="javascript:;">新增收货地址</a> <span class="ftx-03">
									您已创建 <span id="addressNum_top" class="ftx-02">2</span>
									个收货地址，最多可创建 <span class="ftx-02">20</span> 个
								</span>
							</div>

							<div class="mc">
								<div class="sm easebuy-m " id="addresssDiv-137805074">
									<div class="smt">
										<h3>
											上官云 北京 <a onclick="modifyAliasTips(137805074,event);"
												id="alias-edit-137805074" class="alias-edit"
												href="javascript:editAliasName();"></a>
										</h3>
										<div class="extra">
											<a onclick="alertDelAddressDiag(137805074);" class="del-btn"
												href="#none">删除</a>
										</div>
										<div id="alias-form-137805074" class="alias-form hide">
											<div class="alias-new">
												<input type="text" class="ipt-text" id="ipt-text-137805074"
													value="上官云 北京"
													onblur="checkConsigneeAlias('ipt-text-137805074')"
													maxlength="20">
													<button type="button" class="btn-save"
														onclick="saveAddessAlias(137805074,event)">保存</button>
											</div>
											<div class="alias-common">
												<div class="ac-tip">建议填写常用名称：</div>
												<div class="ac-con">
													<a
														href="javascript:setAddressAilas(137805074,'home-137805074');"
														id="home-137805074" class="item">家里</a> <a
														href="javascript:setAddressAilas(137805074,'parentHome-137805074');"
														id="parentHome-137805074" class="item">父母家</a> <a
														href="javascript:setAddressAilas(137805074,'company-137805074');"
														id="company-137805074" class="item">公司</a>
												</div>
												<span class="error-msg" id="error_ipt-text-137805074"></span>
											</div>
										</div>
									</div>

									<div class="smc">
										<div class="items new-items">
											<div class="item-lcol">
												<div class="item">
													<span class="label">收货人：</span>
													<div class="fl">上官云</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">所在地区：</span>
													<div class="fl">北京昌平区六环以内</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">地址：</span>
													<div class="fl">建材城西路金燕龙办公楼一层</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">手机：</span>
													<div class="fl">156****8888</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">固定电话：</span>
													<div class="fl"></div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">电子邮箱：</span>
													<div class="fl"></div>
													<div class="clr"></div>
												</div>
											</div>

											<div class="item-rcol">
												<div class="extra">
													<a class="ml10 ftx-05"
														href="javascript:makeAddressAllDefault('137805074');">设为默认</a>
													<a class="ml10 ftx-05" href="javascript:;"
														onclick="alertUpdateAddressDiag(137805074);">编辑</a>
												</div>
											</div>
											<div class="clr"></div>
										</div>
									</div>
								</div>
								<div class="sm easebuy-m " id="addresssDiv-77187704">
									<div class="smt">
										<h3>
											上官云 <a onclick="modifyAliasTips(77187704,event);"
												id="alias-edit-77187704" class="alias-edit"
												href="javascript:;"></a>
										</h3>
										<div class="extra">
											<a onclick="alertDelAddressDiag(77187704);" class="del-btn"
												href="#none">删除</a>
										</div>
										<div id="alias-form-77187704" class="alias-form hide">
											<div class="alias-new">
												<input type="text" class="ipt-text" id="ipt-text-77187704"
													value="上官云"
													onblur="checkConsigneeAlias('ipt-text-77187704')"
													maxlength="20">
													<button type="button" class="btn-save"
														onclick="saveAddessAlias(77187704,event)">保存</button>
											</div>
											<div class="alias-common">
												<div class="ac-tip">建议填写常用名称：</div>
												<div class="ac-con">
													<a
														href="javascript:setAddressAilas(77187704,'home-77187704');"
														id="home-77187704" class="item">家里</a> <a
														href="javascript:setAddressAilas(77187704,'parentHome-77187704');"
														id="parentHome-77187704" class="item">父母家</a> <a
														href="javascript:setAddressAilas(77187704,'company-77187704');"
														id="company-77187704" class="item">公司</a>
												</div>
												<span class="error-msg" id="error_ipt-text-77187704"></span>
											</div>
										</div>
									</div>

									<div class="smc">
										<div class="items new-items">
											<div class="item-lcol">
												<div class="item">
													<span class="label">收货人：</span>
													<div class="fl">上官云</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">所在地区：</span>
													<div class="fl">北京市昌平区</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">地址：</span>
													<div class="fl">平西府修正健康大厦3楼</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">手机：</span>
													<div class="fl">136****9999</div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">固定电话：</span>
													<div class="fl"></div>
													<div class="clr"></div>
												</div>
												<div class="item">
													<span class="label">电子邮箱：</span>
													<div class="fl"></div>
													<div class="clr"></div>
												</div>
											</div>

											<div class="item-rcol">
												<div class="extra">
													<a class="ml10 ftx-05"
														href="javascript:makeAddressAllDefault('77187704');">设为默认</a>
													<a class="ml10 ftx-05" href="javascript:;"
														onclick="alertUpdateAddressDiag(77187704);">编辑</a>
												</div>
											</div>
											<div class="clr"></div>
										</div>
									</div>
								</div>
							</div>

							<div class="mt" id="addAddressDiv_bottom">
								<a onclick="alertAddAddressDiag()" class="e-btn add-btn btn-5"
									href="javascript:;">新增收货地址</a> <span class="ftx-03">
									您已创建 <span id="addressNum_botton" class="ftx-02">2</span>
									个收货地址，最多可创建 <span class="ftx-02">20</span> 个
								</span>
							</div>
						</div>


					</div>
				</div>
				<jsp:include page="commons/left.jsp">
			    	<jsp:param name="page_index" value="3"/>
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