<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.BusLine" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BusStation信息
    List<BusStation> busStationList = (List<BusStation>)request.getAttribute("busStationList");
    BusLine busLine = (BusLine)request.getAttribute("busLine");

%>
<HTML><HEAD><TITLE>查看公交线路</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><%=busLine.getLineId() %></td>
  </tr>

  <tr>
    <td width=30%>线路名称:</td>
    <td width=70%><%=busLine.getName() %></td>
  </tr>

  <tr>
    <td width=30%>起点站:</td>
    <td width=70%>
      <%=busLine.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到站:</td>
    <td width=70%>
      <%=busLine.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>首班车时间:</td>
    <td width=70%><%=busLine.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>末班车时间:</td>
    <td width=70%><%=busLine.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>所属公司:</td>
    <td width=70%><%=busLine.getCompany() %></td>
  </tr>

  <tr>
    <td width=30%>途径站点:</td>
    <td width=70%><%=busLine.getTjzd() %></td>
  </tr>

  <tr>
    <td width=30%>地图线路坐标:</td>
    <td width=70%><%=busLine.getPolylinePoints() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
