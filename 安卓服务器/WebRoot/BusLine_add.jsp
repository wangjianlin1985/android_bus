<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�BusStation��Ϣ
    List<BusStation> busStationList = (List<BusStation>)request.getAttribute("busStationList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӹ�����·</TITLE> 
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
/*��֤��*/
function checkForm() {
    var name = document.getElementById("busLine.name").value;
    if(name=="") {
        alert('��������·����!');
        return false;
    }
    var startTime = document.getElementById("busLine.startTime").value;
    if(startTime=="") {
        alert('�������װ೵ʱ��!');
        return false;
    }
    var endTime = document.getElementById("busLine.endTime").value;
    if(endTime=="") {
        alert('������ĩ�೵ʱ��!');
        return false;
    }
    var company = document.getElementById("busLine.company").value;
    if(company=="") {
        alert('������������˾!');
        return false;
    }
    var tjzd = document.getElementById("busLine.tjzd").value;
    if(tjzd=="") {
        alert('������;��վ��!');
        return false;
    }
    var polylinePoints = document.getElementById("busLine.polylinePoints").value;
    if(polylinePoints=="") {
        alert('�������ͼ��·����!');
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
    <td width=30%>��·����:</td>
    <td width=70%><input id="busLine.name" name="busLine.name" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>���վ:</td>
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
    <td width=30%>�յ�վ:</td>
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
    <td width=30%>�װ೵ʱ��:</td>
    <td width=70%><input id="busLine.startTime" name="busLine.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ĩ�೵ʱ��:</td>
    <td width=70%><input id="busLine.endTime" name="busLine.endTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������˾:</td>
    <td width=70%><input id="busLine.company" name="busLine.company" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>;��վ��:</td>
    <td width=70%><textarea id="busLine.tjzd" name="busLine.tjzd" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ͼ��·����:</td>
    <td width=70%><textarea id="busLine.polylinePoints" name="busLine.polylinePoints" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
