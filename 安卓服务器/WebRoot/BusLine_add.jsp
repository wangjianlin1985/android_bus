<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BusStation信息
    List<BusStation> busStationList = (List<BusStation>)request.getAttribute("busStationList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加公交线路</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var name = document.getElementById("busLine.name").value;
    if(name=="") {
        alert('请输入线路名称!');
        return false;
    }
    var startTime = document.getElementById("busLine.startTime").value;
    if(startTime=="") {
        alert('请输入首班车时间!');
        return false;
    }
    var endTime = document.getElementById("busLine.endTime").value;
    if(endTime=="") {
        alert('请输入末班车时间!');
        return false;
    }
    var company = document.getElementById("busLine.company").value;
    if(company=="") {
        alert('请输入所属公司!');
        return false;
    }
    var tjzd = document.getElementById("busLine.tjzd").value;
    if(tjzd=="") {
        alert('请输入途径站点!');
        return false;
    }
    var polylinePoints = document.getElementById("busLine.polylinePoints").value;
    if(polylinePoints=="") {
        alert('请输入地图线路坐标!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="BusLine/BusLine_AddBusLine.action" method="post" id="busLineAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>线路名称:</td>
    <td width=70%><input id="busLine.name" name="busLine.name" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>起点站:</td>
    <td width=70%>
      <select name="busLine.startStation.stationId">
      <%
        for(BusStation busStation:busStationList) {
      %>
          <option value='<%=busStation.getStationId() %>'><%=busStation.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到站:</td>
    <td width=70%>
      <select name="busLine.endStation.stationId">
      <%
        for(BusStation busStation:busStationList) {
      %>
          <option value='<%=busStation.getStationId() %>'><%=busStation.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>首班车时间:</td>
    <td width=70%><input id="busLine.startTime" name="busLine.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>末班车时间:</td>
    <td width=70%><input id="busLine.endTime" name="busLine.endTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>所属公司:</td>
    <td width=70%><input id="busLine.company" name="busLine.company" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>途径站点:</td>
    <td width=70%><textarea id="busLine.tjzd" name="busLine.tjzd" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>地图线路坐标:</td>
    <td width=70%><textarea id="busLine.polylinePoints" name="busLine.polylinePoints" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
