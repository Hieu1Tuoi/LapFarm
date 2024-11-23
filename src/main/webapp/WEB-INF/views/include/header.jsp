<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

<!-- HEADER -->
<header>
    <!-- TOP HEADER -->
    <div id="top-header">
        <div class="container">
            <ul class="header-links pull-left">
                <li><a href="#"><i class="fa fa-phone"></i> 0123456789</a></li>
                <li><a href="#"><i class="fa fa-envelope-o"></i> nhom8@email.com</a></li>
                <li><a href="#"><i class="fa fa-map-marker"></i> 97 Man Thiện</a></li>
            </ul>
            <ul class="header-links pull-right">
                <li><a href="#"><i class="fa fa-dollar"></i> USD</a></li>
                <li><a href="<c:url value='/login' />"><i class="fa fa-user-o"></i> My Account</a></li>
            </ul>
        </div>
    </div>
    <!-- /TOP HEADER -->

    <!-- MAIN HEADER -->
    <div id="header">
        <div class="container">
            <div class="row">
                <!-- LOGO -->
                <div class="col-md-3">
                    <div class="header-logo">
                        <a href="<c:url value='/home' />" class="logo">
                            <img src="<c:url value='/resources/img/logo.png' />" alt="">
                        </a>
                    </div>
                </div>
                <!-- /LOGO -->

                <!-- SEARCH BAR -->
                <div class="col-md-6">
                    <div class="header-search">
                        <form>
                            <select class="input-select">
                                <option value="0">All Categories</option>
                                <option value="1">Category 01</option>
                                <option value="2">Category 02</option>
                            </select>
                            <input class="input" placeholder="Search here">
                            <button class="search-btn">Search</button>
                        </form>
                    </div>
                </div>
                <!-- /SEARCH BAR -->

                <!-- ACCOUNT -->
                <div class="col-md-3 clearfix">
                    <div class="header-ctn">
                        <!-- Wishlist -->
                        <div>
                            <a href="#">
                                <i class="fa fa-heart-o"></i>
                                <span>Your Wishlist</span>
                                <div class="qty">2</div>
                            </a>
                        </div>
                        <!-- /Wishlist -->

                        <!-- Cart -->
                        <div class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                                <i class="fa fa-shopping-cart"></i>
                                <span>Your Cart</span>
                                <div class="qty">${cart.totalQuantity}</div>
                            </a>
                            <div class="cart-dropdown">
                                <c:choose>
                                    <c:when test="${not empty cart.items}">
                                        <div class="cart-list">
                                            <c:forEach var="item" items="${cart.items}">
                                                <div class="product-widget">
                                                    <div class="product-img">
                                                        <img src="${item.imageUrl}" alt="">
                                                    </div>
                                                    <div class="product-body">
                                                        <h3 class="product-name">
                                                            <a href="${item.productUrl}">${item.name}</a>
                                                        </h3>
                                                        <h4 class="product-price">
                                                            <span class="qty">${item.quantity}x</span>${item.price}
                                                        </h4>
                                                    </div>
                                                    <button class="delete">
                                                        <i class="fa fa-close"></i>
                                                    </button>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="cart-summary">
                                            <small>${cart.totalQuantity} Item(s) selected</small>
                                            <h5>SUBTOTAL: ${cart.totalPrice}</h5>
                                        </div>
                                        <div class="cart-btns">
                                            <a href="<c:url value='/cart/view' />">View Cart</a>
                                            <a href="<c:url value='/checkout' />">Checkout <i class="fa fa-arrow-circle-right"></i></a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-cart">
                                            <p>Your cart is currently empty.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <!-- /Cart -->

                        <!-- Menu Toggle -->
                        <div class="menu-toggle">
                            <a href="#">
                                <i class="fa fa-bars"></i>
                                <span>Menu</span>
                            </a>
                        </div>
                        <!-- /Menu Toggle -->
                    </div>
                </div>
                <!-- /ACCOUNT -->
            </div>
        </div>
    </div>
    <!-- /MAIN HEADER -->
</header>
<!-- /HEADER -->
