<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/include/common.jsp"%>
<c:if test="${account==null}">
	<%response.sendRedirect("error.jsp");%>
</c:if>
<html>
  <head>
      <title>集中监控系统监管中心</title>
      <script type="text/javascript" src="${pageContext.request.contextPath}/resource/map.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
  </head>
  <body style="TEXT-ALIGN: center;">
      <form action="" name="account">
          <input type="hidden" id="userName.account.info" value="${account.userName}">
      </form>
      <div id=top.mmcs
           style=" vertical-align: middle; MARGIN-RIGHT: auto;MARGIN-LEFT: auto;
                    top: 0px; left: 0px; width: 991px; height:127px;background-image: url(img/top.jpg);"></div>
      <div id=head.mmcs
           style=" vertical-align: middle; MARGIN-RIGHT: auto;MARGIN-LEFT: auto;
                    top: 0px; left: 0px; width: 991px; height:27px;"></div>
      <div id=body.mmcs
           style=" vertical-align: middle; MARGIN-RIGHT: auto;MARGIN-LEFT: auto;
                    top: 0px; left: 0px; width: 991px;height: auto;"></div>
      <div id=bottom.mmcs
           style=" vertical-align: middle; MARGIN-RIGHT: auto;MARGIN-LEFT: auto;
                    top: 0px; left: 0px; width: 999px;height: 70px;background-image: url(img/bottom.png);">
          <br>中华人民共和国公安部科技信息化局
          <br>技术支持:公安部第一研究所
      </div>
  </body>
</html>