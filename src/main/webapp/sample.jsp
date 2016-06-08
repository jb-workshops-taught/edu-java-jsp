<html>
<head>
<title>JSP Example</title></head>
<body>
<h1>Random Number Example</h1>

  <img src="<%=request.getContextPath() %>/lolcats/show/image?id=1" width="200">

  <%
    double num = Math.random();
    if (num > 0.95) {
  %>
      <h2>You'll have a luck day!</h2><p>(<%= num %>)</p>
  <%
    } else {
  %>
      <h2>Well, life goes on ... </h2><p>(<%= num %>)</p>
  <%
    }
  %>
  
  <!-- This is an html comment block.  The sample.jsp is served 
  to the web browser directly, without a servlet, so it lives under the webapp/ directory.
  In contrast, the jsp's that are used from a servlet live under webapp/WEB-INF/ directory,
  where the web browser cannot create a URL to load the jsp directly without its data prefilled. -->

</body>
</html>