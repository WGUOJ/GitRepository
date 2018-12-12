<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 页面顶部-->
<header id="top">
	<div id="logo" class="lf">
		<a href="http://localhost:18888/index.html"> <img src="http://139.129.241.42/images/portal/header/logo.png" alt="logo" />
		</a>
	</div>
	<div id="top_input" class="lf">
		<c:choose>
			<c:when test="${not empty q}">
				<input id="input" type="text" placeholder="${q}" />
			</c:when>
			<c:otherwise>
				<input id="input" type="text" placeholder="请输入您要搜索的内容" />
			</c:otherwise>
		</c:choose>
		   <div class="seek" tabindex="-1">
            <div class="actived" ><span>分类搜索</span> <img src="http://139.129.241.42/images/portal/header/header_normal.png" alt=""/></div>
            <div class="seek_content" >
                <div id="shcy" >生活餐饮</div>
                <div id="xxyp" >学习用品</div>
                <div id="srdz" >私人订制</div>
            </div>
        </div>
        
		<a href="javascript:void(0);" class="rt" onclick="search1()"><img id="search"
			src="http://139.129.241.42/images/portal/header/search.png" alt="搜索"/></a>
	</div>
	<div class="rt">
		<ul class="lf" id="iul">
			<li><a href="http://localhost:18888/collect/toMyCollect.html" title="我的收藏"> <img class="care"
					src="http://139.129.241.42/images/portal/header/care.png"
					alt="" />
			</a><b>|</b></li>
			<li><a href="http://localhost:18888/order/toMyOrder.html" title="我的订单"> <img class="order"
					src="http://139.129.241.42/images/portal/header/order.png" alt="" />
			</a><b>|</b></li>
			<li><a href="http://localhost:18888/cart/toCart.html" title="我的购物车"> <img class="shopcar"
					src="http://139.129.241.42/images/portal/header/shop_car.png" alt="" />
			</a><b>|</b></li>
			<li><a href="http://localhost:18888/lookforward.html">帮助</a><b>|</b></li>
		</ul>
	</div>
	<br />
</header>
	<nav id="nav">
		<ul>
			<li><a href="/index.html">首页</a></li>
			<li><a href="/food/toItemFood.html">生活餐饮</a></li>
			<li><a href="/toCate.html">学习用品</a></li>
			<li><a href="/lookforward.html">私人定制</a></li>
		</ul>
	</nav>
	<script src="/js/jquery-3.1.1.min.js"></script>
	<script src="/js/slide.js"></script>
	<script type="text/javascript">
		function logout() {
			var ticket = "${ticket}";
			$.ajax({
				url : '/payment/logout/'+ticket+'.html',
				type : 'post',
				dataType:'json',
				success:function(result) {
					if (result != null && result != "" && result != undefined) {
						if (result.status == 200) {
							alert(result.msg);
							window.location.href = "http://localhost:18882/sso/toLogin.html";
						}else {
							alert(result.msg);
						}
					}
				},
				error:function() {
					alert('退出失败！');
				}
			});
		}
	</script>
	<script>
	$('#nav>ul>li').click(function(){
        $(this).children().addClass('active');
        $(this).siblings().children().removeClass('active');
    })
	</script>
	
	<script src="/js/jquery.cookie.js"></script>
	<script src="/js/index.js"></script>
	<script type="text/javascript">
		$(function () {
			var ticket = "${ticket}";
			$.ajax({
				type:"POST",
				url:"/payment/checkLogin.html?ticket="+ticket,
				dataType:"json",
				success:function(result){
					var user = JSON.parse(result.data);
					if (result.status === 200) {
						$("#iul").append('<li><a href="http://localhost:18888/lookforward.html">'+user.username+'</a><b>|</b></li><li><a href="javascript:;" onclick="logout()">退出</a></li>');
					}else if(result.status === 500){
						$("#iul").append('<li><a href="http://localhost:18882/sso/toLogin.html">登录</a></li>');
					}
				},
				error:function(textStautus,XMLHttpRequest){
					alert("系统异常！");
				}
			});
		})
</script>
<script>
function search1(){
	var q=$("#input").val();
	console.log(111);
	window.location.href = "http://localhost:18888/search.html?q="+q;
}
</script>
<script type="text/javascript">   

    document.onkeydown=keyDownSearch;
   
    function keyDownSearch(e) { 
        var theEvent = e || window.event; 
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode; 
        if (code == 13) {  
        	search1();
            return false;
        }
        return true;
    }
</script>