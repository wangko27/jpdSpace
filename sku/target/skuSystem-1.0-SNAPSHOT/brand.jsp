<%--
  Created by IntelliJ IDEA.
  User: jackchen
  Date: 7/8/13
  Time: 8:17 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>SKU查询</title>
    <link rel="stylesheet" href="css/sku.css">
    <script src="js/jquery-1.9.0.js"></script>
    <link rel="stylesheet" href="colorbox/colorbox.css"/>

    <script src="colorbox/jquery.colorbox.js"></script>
</head>
<body>
<div id="site-nav">
    <div id="header">
        <p class="nav-title">京拍档SKU搜索助手</p>
        <p class="nav-login">
        	<%
        		if(session.getAttribute("user")!=null && !"".equals(session.getAttribute("user"))){
        	%>
        		<%=session.getAttribute("user") %>|<a href="/loginout">退出</a>
        	<%
        		}else{
        	%>
        		<a href="/login.jsp">登录</a>|<a href="/register.jsp">注册</a>
        	<%		
        		}
        	%>
        </p>
    </div>
</div>
<div id="content">
    <div class="logo"><p><img src="images/logo.jpg"/></p>

        <p><img src="images/line.jpg"/></p>

        <p><img src="images/pinpai.jpg"/></p></div>
    <div style="clear:left"></div>
    <div class="form-content">

        <form method="post">
            <table border="0" cellpadding="0" cellspacing="10" width="400">
                <tr>
                    <td width="10px"><span class="start">*</span></td>
                    <td><input type="text" id="queryKey" name="queryKey" class="input-text w300" placeholder="关键字"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><label class="msg">在输入框中输入关键字</label></td>
                </tr>
                <tr>
                    <td><span class="start">*</span></td>
                    <td><input type="text" id="brand" name="brand" class="input-text w300" placeholder="brand"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label class="msg">在输入框中输入品牌</label></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="line"></div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="text" id="begin" name="begin" class="input-text" placeholder="开始页"/>&nbsp;
                        <input type="text" id="end" name="end" class="input-text" placeholder="结束页"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><label class="msg">不能查询100页后的数据，页码越长，等待时间越长</label></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                         <input type="button" class="button" onclick="findBrand()">

                        <a href="index.jsp">查询品牌位置</a>

                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div id="msg"></div>
                    </td>
                </tr>
            </table>
        </form>
	</div>
   </div>
	<div style="color: red; size: 25px">
		<label id="pageNum"></label> <label id="tr"></label> <label id="td"></label>

	</div>
<div id="footer">
    <p>有任何问题，请致电15652515060或QQ：953924393</p>

    <p>邮箱：jishu@jingpaidang.com</p>
</div>


	<script>
		function findBrand() {
			var queryKey = $("#queryKey").val();
			var brand = $("#brand").val();
			var begin = $("#begin").val();
			var end = $("#end").val();


			if (queryKey == null || $.trim(queryKey) == "" || brand == null
					|| $.trim(brand) == "") {
				$("#msg").text("请输入关键词和brand");
				return false;
			}
			$.colorbox({
				transition : 'none',
				overlayClose : false,
				href : 'colorbox/images/loadingAnimation.gif',
				title : '正在查找。。。',
				width : '200',
				height : '150',
				closeButton : false
			});

			$.post('/brand', {
				queryKey : queryKey,
				brand : brand,
				begin : begin,
				end : end
			}, function(data, status) {
				var json = eval('(' + data + ')');

				if (json.errorMsg != null) {
					$.colorbox({
						html : "<div style=\"text-align:center\">" + 查询出错
								+ "</div>",
						width : '200',
						height : '150'
					});

				} else {
					if (json.pageNum == 101) {
						$.colorbox({
							html : "<div style=\"text-align:center\">"
									+ "100页都查不到，放弃吧！" + "</div>",
							width : '200',
							height : '150'
						});

					} else {
						$.colorbox({
							html : "<div style=\"text-align:center\">第 "
									+ json.pageNum + " 页 <br>第 " + json.tr
									+ " 行<br>第 " + json.td + " 列</div>",
							width : '200',
							height : '150'
						});

					}
				}

			});

		}
	</script>

</body>
</html>