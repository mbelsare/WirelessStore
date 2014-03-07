<%@ page import="com.cmpe.two81.viewobject.ProductType" %>
<%@ page import="com.cmpe.two81.viewobject.Store" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%
    String URLRoot = request.getContextPath();
    Store store =  (Store)request.getSession().getAttribute("store");
%>
<html>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<head>
    <meta charset="utf-8">
    <title><%=store.getName()%> Wireless Store</title>
    <link rel="stylesheet" type="text/css" href="css/<%=store.getStyle__c()%>">
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
                <li><a href="<%=URLRoot%>/login.jsp">Login</a></li>
                <li><a href="<%=URLRoot%>/cart.jsp">Cart</a></li>
            </ul>
        </div>
        <div class="span4">
            <ul class="nav nav-list">
                <li><a href="<%=store.getTwitter__c()%>">Twitter</a></li>
                <li><a href="<%=store.getFacebook__c()%>">Facebook</a></li>
            </ul>
        </div>
    </div>
    <!-- Navigation -->
    <div id="main_box">
        <div id="nav_box">
            <ul class="basictab">
                <%
                    List<ProductType> productTypes =  (List<ProductType>)request.getSession().getAttribute("productTypes");
                    if(productTypes != null && productTypes.size() > 0){
                        for(ProductType productType : productTypes) {
                %>
                            <li><a href="<%=URLRoot%>/productType?productTypeId=<%=productType.getID__c()%>"><%=productType.getTabName__c()%></a></li>
                <%
                        }
                    }
                %>
                <li><a href="<%=URLRoot%>/productType?productTypeId=-1">Promotions</a></li>
            </ul>
        </div>
        <div id="content_box">
            <form method="post" action="<%=URLRoot%>/signin">
            		<div class="control-group">
						<label style="color:black;padding:5px" class="control-label" for="username">UserName</label>
						<div class="controls">
							<input class="input-medium" type="text" id="username" name="username" value="manoj">
						</div>
					</div>
            		<div class="control-group">
						<label style="color:black;padding:5px" class="control-label" for="password">Password</label>
						<div class="controls">
							<input class="input-medium" type="password" id="password" name="password" value="manoj">
						</div>
					</div>
	                <input type="submit" class="btn" value="SignIn">
            </form>
        </div>
    </div>
    <!-- Copyright Area -->
    <hr>
    <div class="footer">
        <p>&copy; Team 5 CMPE-281</p>
    </div>
</div>
</body>
</html>