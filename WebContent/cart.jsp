<%@ page import="com.cmpe.two81.viewobject.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%
    String URLRoot = request.getContextPath();
    Store store = (Store)request.getSession().getAttribute("store");
    User loggedUser = (User)request.getSession().getAttribute("loggedUser");
%>
<html>
<head>
    <meta charset="utf-8">
    <!-- replace with title from backend -->
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
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
                <li><% if(loggedUser != null) {
                %>
                    <a href="<%=URLRoot%>/logout">Logout</a></li>
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
            <%
                Cart cart = (Cart) request.getSession().getAttribute("savedCart");
                if(cart == null) {
            %>
                    <strong>Cart is empty!!</strong>
            <%
                }
                else {
                    List<ProductInfo> productInfos =  cart.getProductInfos();
                    if(productInfos == null || productInfos.size() == 0) {
            %>
                         <strong>Cart is empty!!</strong>
            <%
                    }
                    else {
            %>
                        <table id="basic_table"><tbody>
            <%
                        for(ProductInfo product : productInfos) {
            %>            
                              <tr><td><% if(product.getImageLink() != null) { %> 
                                    <img src="img/<%=product.getImageLink()%>" /><br>
                                    <strong><%=product.getProductName()%></strong><% } %></td>
                                  <td><form method="post" action="<%=URLRoot%>/cart">
                                     <input type="hidden" id="productId" name="productId" value="<%=product.getProductId()%>">
                                     <input type="hidden" id="actionId" name="actionId" value="REMOVE">
                                     <div class="input-append">
	                                     <input type="text" class="span1" id="qty" name="qty" value="<%=product.getQuantity()%>" readOnly>
	                                     <input type="submit" class="btn btn-warning" value="Remove from cart">
                                     </div>
                                     </form>
                                  </td>
                              </tr>
            <%
                        }
            %>
                        </tbody></table>
                        <table id="basic_table"><tbody>
                            <tr><td><form method="post" action="<%=URLRoot%>/order">
                                <input type="submit" class="btn btn-info" value="Place Order">
                            </form></td>
                            </tr>
                        </tbody></table>
            <%
                    }
                }
            %>

        </div>
    </div>
    <!-- Copyright Area -->
    <hr>
    <div class="footer">
        <p>&copy; Team 5 CMPE-281</p>
    </div>
</div>

<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>