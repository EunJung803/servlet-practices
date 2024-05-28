<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String row = request.getParameter("r");
	String col = request.getParameter("c");
	
	int nRow = Integer.parseInt(row);
	int nCol = Integer.parseInt(col);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" cellspacing="0">
		<%
			for(int i = 0; i < nRow; i++) {
		%>
			<tr>
				<%
					for(int j = 0; j < nCol; j++) {
				%>
					<td>Cell(<%=i %>, <%=j %>)</td>
				<%
					}
				%>
			</tr>
		<%
			}
		%>	
	</table>
</body>
</html>