<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<footer id="footer">
	<!-- top footer -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-3 col-xs-6">
					<div class="footer">
						<h3 class="footer-title">About Us</h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
							sed do eiusmod tempor incididunt ut.</p>
						<ul class="footer-links">
							<li><a href="https://maps.app.goo.gl/aCnkqxqmdPdnekNb7"><i
						class="fa fa-map-marker"></i> 97 Man Thiện</a></li>
							<li><a href="#"><i class="fa fa-phone"></i>0123456789</a></li>
							<li><a href="https://mail.google.com/mail/?view=cm&to=nhom8@gmail.com" target="_blank"><i
						class="fa fa-envelope-o"></i> nhom8@email.com</a></li>
						
						</ul>
					</div>
				</div>

				<div class="col-md-3 col-xs-6">
					<div class="footer">
						<h3 class="footer-title">DANH MỤC</h3>
						<c:forEach var="cate" items="${categories}">
							<ul class="footer-links">
								<li><a
									href="products-category?idCategory=${cate.encryptedId}">${cate.nameCategory}</a></li>
									<br>
							</ul>
						</c:forEach>
					</div>
				</div>

				<div class="clearfix visible-xs"></div>

				<div class="col-md-3 col-xs-6">
					<div class="footer">
						<h3 class="footer-title">Information</h3>
						<ul class="footer-links">
							<li><a href="#">About Us</a></li>
							<li><a href="#">Contact Us</a></li>
							<li><a href="#">Privacy Policy</a></li>
							<li><a href="#">Orders and Returns</a></li>
							<li><a href="#">Terms & Conditions</a></li>
						</ul>
					</div>
				</div>

				<div class="col-md-3 col-xs-6">
					<div class="footer">
						<h3 class="footer-title">Service</h3>
						<ul class="footer-links">
							<li><a href="<c:url value='/login' />">My Account</a></li>
							<li><a href="#">View Cart</a></li>
							<li><a href="#">Wishlist</a></li>
							<li><a href="#">Track My Order</a></li>
							<li><a href="#">Help</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /top footer -->

</footer>
