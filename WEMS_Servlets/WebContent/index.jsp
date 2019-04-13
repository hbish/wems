<%@ include file="header.jsp"%>

<div id="status-bar">
	<div class="container_12">

		<ul id="status-infos">
			<li class="spaced">Not Logged In</li>
			<li><a href="#" class="button red" title="Logout"
				onClick="loginModal(); return false;"><span class="smaller">LOGIN</span></a></li>
		</ul>

		<ul id="breadcrumb">
			<li><a href="index.jsp" title="Home">Home</a></li>
		</ul>

	</div>
</div>

<div id="header-shadow"></div>
<!-- End header -->

<article class="container_12">


	<p>
		Access logged in pages <a href="jsp/home.jsp">here</a>.
	</p>
	
	<p>
	<form method="get" action="XMLServlet">
	  <input type="text" name="data"></input>
	  <button type="submit"> GET </button>
	</form>
	
		<form method="post" action="XMLServlet">
	  <input type="text" name="data"></input>
	  	  <button type="submit"> POST </button>
	</form>
	</p>

</article>


<%@ include file="footer.jsp"%>