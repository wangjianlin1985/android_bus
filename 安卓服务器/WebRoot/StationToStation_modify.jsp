<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.StationToStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�BusStation��Ϣ
    List<BusStation> busStationList = (List<BusStation>)request.getAttribute("busStationList");
    StationToStation stationToStation = (StationToStation)request.getAttribute("stationToStation");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�վվ��ѯ</TITLE>
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
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="StationToStation/StationToStation_ModifyStationToStation.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="stationToStation.id" name="stationToStation.id" type="text" value="<%=stationToStation.getId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��ʼվ:</td>
    <td width=70%>
      <select name="stationToStation.startStation.stationId">
      <%
        for(BusStation busStation:busStationList) {
          String selected = "";
          if(busStation.getStationId() == stationToStation.getStartStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=busStation.getStationId() %>' <%=selected %>><%=busStation.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�յ�վ:</td>
    <td width=70%>
      <select name="stationToStation.endStation.stationId">
      <%
        for(BusStation busStation:busStationList) {
          String selected = "";
          if(busStation.getStationId() == stationToStation.getEndStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=busStation.getStationId() %>' <%=selected %>><%=busStation.getStationName() %></option>
      <%
        }
      %>
    </td>
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
