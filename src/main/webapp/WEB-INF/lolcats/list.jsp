<%@ page import="java.util.List,com.codeforanyone.edujavajsp.model.LOLCat" %>
<html>
<head>
<title>LOLCats List</title></head>
<body>
<h1>LOLCats List</h1>

<p align="right"><a href="<%=request.getContextPath() %>/lolcats/manage/add">Add New Cat</a></p>


<!--  This page uses pure jsp for looping. An cleaner, more readable alternative is JSTL tags. -->

<%
	List<LOLCat> cats = (List<LOLCat>) request.getAttribute("cats");
	for (LOLCat cat : cats) {
		%>
		<div style="border: 1px solid #AAAAAA; padding: 15px; width: 300px;">
		<img src="<%=request.getContextPath() %>/lolcats/show/image?id=<%=cat.getId() %>" height=200>
		<br>Title: <%=cat.getTitle() %>
		<br>Filename: <%=cat.getFilename() %>
		<br>Format: <%=cat.getImageFormat() %>
		<br><a href="<%=request.getContextPath() %>/lolcats/manage/edit?id=<%=cat.getId() %>">Edit</a> 
		| <a href="<%=request.getContextPath() %>/lolcats/manage/delete?id=<%=cat.getId() %>">Delete</a> 
		</div><p></p>
		<% 
	}
	if (cats.isEmpty()) {
		%><p>There are no LOLCats in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/lolcats/manage/add">Add a New One</a>!</p><%
	}
%>

</body></html>
