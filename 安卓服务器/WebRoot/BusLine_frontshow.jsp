<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.BusLine" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ page import="com.chengxusheji.domain.BusStation" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�BusStation��Ϣ
    List<BusStation> busStationList = (List<BusStation>)request.getAttribute("busStationList");
    BusLine busLine = (BusLine)request.getAttribute("busLine");

%>
<HTML><HEAD><TITLE>�鿴������·</TITLE>
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
    <td width=30%>��¼���:</td>
    <td width=70%><%=busLine.getLineId() %></td>
  </tr>

  <tr>
    <td width=30%>��·����:</td>
    <td width=70%><%=busLine.getName() %></td>
  </tr>

  <tr>
    <td width=30%>���վ:</td>
    <td width=70%>
      <%=busLine.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�յ�վ:</td>
    <td width=70%>
      <%=busLine.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�װ೵ʱ��:</td>
    <td width=70%><%=busLine.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>ĩ�೵ʱ��:</td>
    <td width=70%><%=busLine.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>������˾:</td>
    <td width=70%><%=busLine.getCompany() %></td>
  </tr>

  <tr>
    <td width=30%>;��վ��:</td>
    <td width=70%><%=busLine.getTjzd() %></td>
  </tr>

  <tr>
    <td width=30%>��ͼ��·����:</td>
    <td width=70%><%=busLine.getPolylinePoints() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
