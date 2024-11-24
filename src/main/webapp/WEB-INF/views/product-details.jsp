<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.nameProduct}</title>
    <!-- Add your CSS files here -->
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/font-awesome.min.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/slick.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/nouislider.min.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>

<body>
    <!-- HEADER -->
    <jsp:include page="/WEB-INF/views/include/header.jsp" />

    <!-- Product Section -->
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="section-title text-center">
                        <h3 class="title">${product.nameProduct}</h3>
                    </div>
                </div>
            </div>

            <div class="row">
                <!-- Product Image -->
                <div class="col-md-6">
			<div class="product-img">
			    <!-- Hiển thị ảnh chính -->
			    <c:choose>
			        <c:when test="${not empty product.images}">
			            <img src="${product.images[0].imageUrl}" alt="Main Product Image" class="main-image">
			        </c:when>
			        <c:otherwise>
			            <img src="/LapFarm/resources/img/default.jpg" alt="Default Image" class="main-image">
			        </c:otherwise>
			    </c:choose>
			
			    <%-- <!-- Hiển thị các thumbnail -->
			    <div class="product-thumbnails">
			        <c:forEach var="image" items="${product.images}">
			            <img src="${image.imageUrl}" alt="Thumbnail" class="thumbnail">
			        </c:forEach>
			    </div> --%>
			</div>
                </div>
                <!-- Product Info -->
                <div class="col-md-6">
                    <div class="product-body">
                        <p><strong>Brand:</strong> ${product.brand.nameBrand}</p>
                        <p><strong>Category:</strong> ${product.category.nameCategory}</p>
                        <p><strong>Description:</strong> ${product.description}</p>
                        <h4 class="product-price">$${product.salePrice}</h4>
                        <p><strong>Quantity Available:</strong> ${product.quantity}</p>

                        <!-- Rating -->
                        <div class="product-rating">
                            <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i>
                        </div>

                        <!-- Add to Cart Button -->
                        <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
                    </div>
                </div>
            </div>

            <!-- Reviews Section -->
            <div class="row">
                <div class="col-md-12">
                    <div class="section-title text-center">
                        <h3 class="title">Customer Reviews</h3>
                    </div>

                    <div class="reviews">
                        <c:forEach var="review" items="${product.reviews}">
                            <div class="review">
                                <div class="review-heading">
                                    <h5 class="name">${review.name}</h5>
                                    <p class="date">${review.date}</p>
                                    <div class="review-rating">
                                        <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i>
                                    </div>
                                </div>
                                <div class="review-body">
                                    <p>${review.comment}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Pagination for reviews -->
                    <ul class="reviews-pagination">
                        <li class="active">1</li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#"><i class="fa fa-angle-right"></i></a></li>
                    </ul>
                </div>
            </div>

            <!-- Review Form -->
            <div class="row">
                <div class="col-md-12">
                    <div class="section-title text-center">
                        <h3 class="title">Write a Review</h3>
                    </div>
                    <form class="review-form">
                        <input class="input" type="text" placeholder="Your Name">
                        <input class="input" type="email" placeholder="Your Email">
                        <textarea class="input" placeholder="Your Review"></textarea>
                        <div class="input-rating">
                            <span>Your Rating: </span>
                            <div class="stars">
                                <input id="star5" name="rating" value="5" type="radio"><label for="star5"></label>
                                <input id="star4" name="rating" value="4" type="radio"><label for="star4"></label>
                                <input id="star3" name="rating" value="3" type="radio"><label for="star3"></label>
                                <input id="star2" name="rating" value="2" type="radio"><label for="star2"></label>
                                <input id="star1" name="rating" value="1" type="radio"><label for="star1"></label>
                            </div>
                        </div>
                        <button class="primary-btn">Submit Review</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Related Products Section -->
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="section-title text-center">
                        <h3 class="title">Related Products</h3>
                    </div>
                </div>

                <!-- Example Product 1 -->
                <div class="col-md-3 col-xs-6">
                    <div class="product">
                        <div class="product-img">
                            <img src="/LapFarm/resources/img/product01.png" alt="">
                            <div class="product-label">
                                <span class="sale">-30%</span>
                            </div>
                        </div>
                        <div class="product-body">
                            <p class="product-category">Category</p>
                            <h3 class="product-name">
                                <a href="#">Product 1</a>
                            </h3>
                            <h4 class="product-price">
                                $980.00
                                <del class="product-old-price">$990.00</del>
                            </h4>
                            <div class="product-btns">
                                <button class="add-to-wishlist">
                                    <i class="fa fa-heart-o"></i>
                                </button>
                                <button class="add-to-compare">
                                    <i class="fa fa-exchange"></i>
                                </button>
                                <button class="quick-view">
                                    <i class="fa fa-eye"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- More Products here (you can repeat similar blocks) -->
            </div>
        </div>
    </div>

    <!-- NEWSLETTER -->
    <div id="newsletter" class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="newsletter">
                        <p>Sign Up for the <strong>NEWSLETTER</strong></p>
                        <form>
                            <input class="input" type="email" placeholder="Enter Your Email">
                            <button class="newsletter-btn">
                                <i class="fa fa-envelope"></i> Subscribe
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/include/footer.jsp" />

    <!-- Scripts -->
    <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/resources/js/slick.min.js' />"></script>
    <script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
    <script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
    <script src="<c:url value='/resources/js/main.js' />"></script>

</body>

</html>