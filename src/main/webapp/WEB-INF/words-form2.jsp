<html>
<head>
<title>Crazy Words - More Words</title></head>
<body>
<h1>Crazy Words - More Words</h1>

<p>Please enter 2 verbs.</p>

<form method="get" action="/crazywords/results">
<input type="hidden" name="noun1" value="<%= request.getAttribute("noun1") %>">
<input type="hidden" name="noun2" value="<%= request.getAttribute("noun2") %>">

<p>Verb: <input type="text" name="verb1"></p>
<p>Verb: <input type="text" name="verb2"></p>

<p><input type=submit value="Next"></p>

</form>

</body>
</html>