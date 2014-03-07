<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Wireless Store</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<%@ page import="com.cmpe.two81.viewobject.Store"%>
<%@ page import="java.util.List"%>
</head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<body>
	<%
    String URLRoot = request.getContextPath();
    List<Store> stores = (List<Store>) request.getSession().getAttribute("stores");
    request.getSession().removeAttribute("store");
    request.getSession().removeAttribute("serviceImpl");
    request.getSession().removeAttribute("loggedUser");
    request.getSession().removeAttribute("savedCart");
    request.getSession().removeAttribute("frontProducts");
    request.getSession().removeAttribute("productType");
    request.getSession().removeAttribute("promotions");
    request.getSession().removeAttribute("productTypes");
%>

	<div class="container">
		<h1>Wireless Store</h1>

		<div class="hero-unit">
			<h2>Select a Store</h2>

			<p>Select the store of your choice.You can always come to this
				page to visit another store.</p>
			<%
            if (stores.size() > 0) {
                for (int i = 0; i < stores.size(); i++) {
        %>
			<a href="<%=URLRoot%>/store?storeId=<%=stores.get(i).getID__c()%>"
				class="btn btn-large btn-success"><%=stores.get(i).getName()%> </a>
			<%
                }
            } else {
        %>
			<b>Sorry, No stores are currently set up.</b>
			<%
            }
        %>

		</div>

		<hr>
		<div class="footer">
			<p>&copy; Team 5 CMPE-281</p>
		</div>
	</div>
</body>
</html>