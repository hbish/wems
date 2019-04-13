<!DOCTYPE html>
<html lang="en"><!-- InstanceBegin template="/templates/wems_loggedout.dwt" codeOutsideHTMLIsLocked="false" -->
<head>

	<!-- InstanceBeginEditable name="Title" --><title>WEMS Web-based Energy Management System</title><!-- InstanceEndEditable -->
	<meta charset="utf-8">
	
	<!-- Global stylesheets -->
	<link href="css/reset.css" rel="stylesheet" type="text/css">
	<link href="css/common.css" rel="stylesheet" type="text/css">
	<link href="css/form.css" rel="stylesheet" type="text/css">
	<link href="css/standard.css" rel="stylesheet" type="text/css">
	
	<!-- Comment/uncomment one of these files to toggle between fixed and fluid layout -->
	<link href="css/960.gs.css" rel="stylesheet" type="text/css">
	<!--<link href="css/960.gs.fluid.css" rel="stylesheet" type="text/css">-->
	
    <!-- WEMS Custom styles -->
    <link href="css/wems.css" rel="stylesheet" type="text/css">
    
	<!-- Custom styles -->
	<link href="css/simple-lists.css" rel="stylesheet" type="text/css">
	<link href="css/block-lists.css" rel="stylesheet" type="text/css">
	<link href="css/planning.css" rel="stylesheet" type="text/css">
	<link href="css/table.css" rel="stylesheet" type="text/css">
	<link href="css/calendars.css" rel="stylesheet" type="text/css">
	<link href="css/wizard.css" rel="stylesheet" type="text/css">
	<link href="css/gallery.css" rel="stylesheet" type="text/css">
	
	<!-- Favicon -->
	<link rel="shortcut icon" type="image/x-icon" href="wemsfavicon.ico">
	<link rel="icon" type="image/png" href="wemsfavicon.png">
	
	<!-- Generic libs -->
	<script type="text/javascript" src="js/html5.js"></script>				<!-- this has to be loaded before anything else -->
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/old-browsers.js"></script>		<!-- remove if you do not need older browsers detection -->
	
	<!-- Template libs -->
	<script type="text/javascript" src="js/jquery.accessibleList.js"></script>
	<script type="text/javascript" src="js/searchField.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/standard.js"></script>
	<!--[if lte IE 8]><script type="text/javascript" src="js/standard.ie.js"></script><![endif]-->
	<script type="text/javascript" src="js/jquery.tip.js"></script>
	<script type="text/javascript" src="js/jquery.hashchange.js"></script>
	<script type="text/javascript" src="js/jquery.contextMenu.js"></script>
	<script type="text/javascript" src="js/jquery.modal.js"></script>
	
	<!-- Custom styles lib -->
	<script type="text/javascript" src="js/list.js"></script>
	
	<!-- Plugins -->
	<script  type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	<script  type="text/javascript" src="js/jquery.datepick/jquery.datepick.min.js"></script>
	
	<!-- InstanceBeginEditable name="Scripts" -->
        <script type="text/javascript">
    		// Login modal
		function loginModal()
		{
			$.modal({
				content: '<div class="block-header">Please login</div>'+
'<form class="form with-margin" name="login-form" id="login-form" method="post" action="">'+
'<input type="hidden" name="a" id="a" value="send">'+
'<p class="inline-small-label">'+
'<label for="login"><span class="big">User name</span></label>'+
'<input type="text" name="login" id="login" class="full-width" value="">'+
'</p>'+
'<p class="inline-small-label">'+
'<label for="pass"><span class="big">Password</span></label>'+
'<input type="password" name="pass" id="pass" class="full-width" value="">'+
'</p>'+
				
'<button type="submit" class="float-right">Login</button>'+
'</form>'+

'<form class="form" id="password-recovery" method="post" action="">'+
'<fieldset class="grey-bg no-margin collapse">'+
'<legend><a href="#">Lost password?</a></legend>'+
'<p class="input-with-button">'+
'<label for="recovery-mail">Enter your e-mail address</label>'+
'<input type="text" name="recovery-mail" id="recovery-mail" value="">'+
'<button type="button">Send</button>'+
'</p>'+
'</fieldset>'+
'</form>',
				title: 'Login',
				maxWidth: 500,
				buttons: {
					'Cancel': function(win) { win.closeModal(); }
				}
			});
		}
	
	</script>
	<!-- InstanceEndEditable -->
</head>

<body>

    <!-- WEMS Logo -->
    <div class="logo"><div class="container_12">	
		<div class="wems-logo"><img src="images/wems/WEMSLogo.png" width="174" height="60"><p>UTS Energy Management Project</p></div>	
	</div></div>
    
    
    <!-- Main nav -->
	<nav id="main-nav">
		
		<ul class="container_12">
            <!-- InstanceBeginEditable name="Menu" -->
			<li class="home"><a href="index.jsp" title="Home">Home</a>
			</li>
			<li class="estab current"><a href="go_green.jsp" title="Go Green">Go Green</a>
			</li><!-- InstanceEndEditable -->
		</ul>
</nav>
	<!-- End main nav -->
	
	<!-- Sub nav -->
<div id="sub-nav">
  <div class="container_12">
		
	<a href="#" title="Help" class="nav-button"><b>Help</b></a>
	
<form id="search-form" name="search-form" method="post" action="search.jsp">
			<input type="text" name="s" id="s" value="" title="Search admin..." autocomplete="off">
	</form>
	
</div></div>
	<!-- End sub nav -->
	
	<!-- Status bar -->
<div id="status-bar"><div class="container_12">
	
		<ul id="status-infos">
			<li class="spaced">Not Logged In</li>
			<li><a href="#" class="button red" title="Logout" onClick="loginModal(); return false;"><span class="smaller">LOGIN</span></a></li>
	  </ul>
		
		<ul id="breadcrumb">
			<li><a href="index.jsp" title="Home">Home</a></li>
			<!-- InstanceBeginEditable name="Breadcrumbs" -->
            <li><a href="#" title="Go Green">Go Green</a></li>
            <!-- InstanceEndEditable -->
		</ul>
	
	</div></div>
	<!-- End status bar -->
	
	<div id="header-shadow"></div>
	<!-- End header -->
	

	
<!-- Content -->
<!-- InstanceBeginEditable name="Content" -->

<article class="container_12">
		<h1>Go Green</h1>
        <p>kad jaojfo jfoan poajofp jvn oajcoa oaopajd ojo ijosjskmug jop gh</p>
		<section class="grid_4">

		</section>
		
		<section class="grid_8">
			
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_6">
		</section>
		
		<section class="grid_6">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_4">
		</section>
		
		<section class="grid_8">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_12">
		</section>
		
		<section class="grid_12">
		</section>
		
		<section class="grid_12">
		</section>
		
		<section class="grid_12">
		</section>
		
		<section class="grid_4">
		</section>
		
		<section class="grid_8">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_12">
		</section>
		
		<section class="grid_4">
		</section>
		
		<section class="grid_4">
		</section>
		
		<section class="grid_4">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_6">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_6">
		</section>
		
		<section class="grid_6">
		</section>
		
		<div class="clear"></div>
		
		<div class="grid_4">
			
			<section class="with-margin">
				
			</section>
			
			<section>
				
			</section>
			
		</div>
		
		<section class="grid_8">
		</section>
		
		<div class="clear"></div>
		
		<section class="grid_4">
		</section>
		
		<section class="grid_8">
		</section>
		
		<div class="clear"></div>
		
</article>
<%@ include file="footer.jsp"%>