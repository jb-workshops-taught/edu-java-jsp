<html>
<head>
<title>JSP Example</title></head>
<body>
<h1>Random Number Example</h1>

  <img src="http://localhost:8080/imageservlet?imageid=5">

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

</body>
</html>