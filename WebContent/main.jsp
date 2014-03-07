<%@ page import="com.cmpe.two81.viewobject.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<%
	String URLRoot = request.getContextPath();
	Store store = (Store) request.getSession().getAttribute("store");
	User loggedUser = (User) request.getSession().getAttribute("loggedUser");
%>
<html>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<head>
<meta charset="utf-8">
<title><%=store.getName()%> Wireless Store</title>
<link rel="stylesheet" type="text/css"
	href="css/<%=store.getStyle__c()%>">
</head>
<body>
	<div id="main">
		<div id="header_nav">
			<div id="header_left">
				<h3><%=store.getName()%></h3>
			</div>
			<div id="header_right">
				<ul>
	                <li><a class="btn btn-large btn-primary disabled" href="<%=URLRoot%>/store">Home</a></li>
					<li>
						<%
							if (loggedUser != null) {
						%> <a href="<%=URLRoot%>/logout">Logout</a>
					</li>
					<%
						} else {
					%>
					<li><a href="<%=URLRoot%>/login.jsp">Login</a></li>
					<%
						}
					%>
					<li><a href="<%=URLRoot%>/cart.jsp">Cart</a></li>
				</ul>
			</div>
			<div class="span3">
				<ul class="nav nav-list">
					<li><a href="<%=store.getTwitter__c()%>">Twitter</a></li>
					<li><a href="<%=store.getFacebook__c()%>">Facebook</a></li>
				</ul>
			</div>
		</div>
		<!-- Navigation -->
		<div id="main_box">
			<div class="navbar" id="nav_box">
				<ul class="basictab">
					<%
						ProductType pType = (ProductType) request.getSession()
								.getAttribute("productType");
						List<ProductType> productTypes = (List<ProductType>) request.getSession().getAttribute("productTypes");
						if (productTypes != null && productTypes.size() > 0) {
							for (ProductType productType : productTypes) {
								if (productType == pType) {
					%>
					<li><a href="#"><%=productType.getTabName__c()%></a></li>
					<%
						} else {
					%>
					<li><a
						href="<%=URLRoot%>/productType?productTypeId=<%=productType.getID__c()%>"><%=productType.getTabName__c()%></a></li>
					<%
						}
							}
						}
					%>
					<li><a href="<%=URLRoot%>/productType?productTypeId=-1">Promotions</a></li>
				</ul>
			</div>
			<div id="content_box">
				<%
					String msg = (String) request.getSession().getAttribute("errMsg");
					if (msg != null) {
				%>
				<h3>
					<strong><%=msg%></strong>
				</h3>
				<%
					}
					if (pType == null) {
				%>
				<h2>ALL Promotions</h2>
				<%
					} else {
				%>
				<h2>
					ALL
					<%=pType.getTabName__c()%></h2>
				<%
					}
				%>
				<table id="basic_table">
					<tbody>
						<%
							if (pType == null) {
								List<Promotion> promotions = (List<Promotion>) request
										.getSession().getAttribute("promotions");
								if (promotions != null && promotions.size() > 0) {
									for (Promotion promotion : promotions) {
						%>
						<tr>
							<td><h3><%=promotion.getName()%></h3></td>
						</tr>
						<tr>
							<td><h3><%=promotion.getDescription__c()%></h3></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<%
							}
								}
							} else {
								List<Product> frontProducts = (List<Product>) request.getSession().getAttribute("frontProducts");
								if (frontProducts != null && frontProducts.size() > 0) {
									for (Product product : frontProducts) {
						%>
						<tr>
							<td>
								<%
									if (product.getSpecialNote__c() != null) {
								%>
								<div class="circleNotes"><%=product.getSpecialNote__c()%></div>
								<%
									}
								%> 
								<a href="<%=URLRoot%>/product?productId=<%=product.getId()%>">
								<%
									if (product.getImageLink__c() != null) {
								%> 
								<img src="img/<%=product.getImageLink__c()%>" /><br> 
								<%
 									}
 								%> 
 								<%=product.getName()%></a>
                                <% if(product.getTotalInventory__c()>0) {%>
                                <p><a class="btn" href="<%=URLRoot%>/product?productId=<%=product.getId()%>">Price: <%=product.getPrice__c()%></a></p>
                                <%} else{ %>
                                <p><a class="btn btn-danger">Out Of Stock</a></p>
                                <%}%> 								
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<%
							}
								}
							}
						%>
					</tbody>
				</table>
			</div>
			<!-- Copyright Area -->
		</div>
			<hr>
			<div class="footer">
				<p>&copy; Team 5 CMPE-281</p>
			</div>
	</div>
</body>
</html>