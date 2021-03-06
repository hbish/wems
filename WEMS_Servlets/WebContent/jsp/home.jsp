<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="javax.naming.InitialContext,java.util.*,com.wems.service.*,com.wems.data.*"%>
<jsp:useBean id="configBean" class="com.wems.service.ConfigurationBean"
	scope="session" />
<jsp:setProperty name="configBean" property="*" />
<!DOCTYPE html>
<html lang="en">
<!-- InstanceBegin template="/templates/wems_loggedin.dwt" codeOutsideHTMLIsLocked="false" -->
<head>

<!-- InstanceBeginEditable name="Title" -->
<title>WEMS Web-based Energy Management System</title>
<!-- InstanceEndEditable -->
<meta charset="utf-8">
<%
	ConfigurationBean configBean2 = null;
	try {
		InitialContext initialContext = new InitialContext();
		configBean2 = (ConfigurationBean) initialContext
				.lookup("java:global/WEMS_EAR/WEMS_EJB/ConfigurationBean!com.wems.service.ConfigurationBean");
	} catch (Exception exception) {
		//Do nothing
	}
%>
<%
	LoginBean loginBean = null;
	try {
		InitialContext initialContext = new InitialContext();
		loginBean = (LoginBean) initialContext
				.lookup("java:global/WEMS_EAR/WEMS_EJB/LoginBean!com.wems.service.LoginBean");
	} catch (Exception exception) {
		//Do nothing
	}
%>
<%
	String username = loginBean
			.getUserNameByIP(request.getRemoteAddr());
%>
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

<script type="text/javascript" src="js/html5.js"></script>
<!-- this has to be loaded before anything else -->
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/old-browsers.js"></script>
<!-- remove if you do not need older browsers detection -->

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
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/jquery.datepick/jquery.datepick.min.js"></script>

<script type="text/javascript" src="js/dynamicoptionlist.js"></script>



<!-- InstanceBeginEditable name="Scripts" -->
<script type="text/javascript">
		
		$(document).ready(function()
		{
			/*
			 * Example context menu
			 */
			
			// Context menu for all favorites
			$('.favorites li').bind('contextMenu', function(event, list)
			{
				var li = $(this);
				
				// Add links to the menu
				if (li.prev().length > 0)
				{
					list.push({ text: 'Move up', link:'#', icon:'up' });
				}
				if (li.next().length > 0)
				{
					list.push({ text: 'Move down', link:'#', icon:'down' });
				}
				list.push(false);	// Separator
				list.push({ text: 'Delete', link:'#', icon:'delete' });
				list.push({ text: 'Edit', link:'#', icon:'edit' });
			});
			
			// Extra options for the first one
			$('.favorites li:first').bind('contextMenu', function(event, list)
			{
				list.push(false);	// Separator
				list.push({ text: 'Settings', icon:'terminal', link:'#', subs:[
					{ text: 'General settings', link: '#', icon: 'blog' },
					{ text: 'System settings', link: '#', icon: 'server' },
					{ text: 'Website settings', link: '#', icon: 'network' }
				] });
			});
			
			/*
			 * Table sorting
			 */
			
			// A small classes setup...
			$.fn.dataTableExt.oStdClasses.sWrapper = 'no-margin last-child';
			$.fn.dataTableExt.oStdClasses.sInfo = 'message no-margin';
			$.fn.dataTableExt.oStdClasses.sLength = 'float-left';
			$.fn.dataTableExt.oStdClasses.sFilter = 'float-right';
			$.fn.dataTableExt.oStdClasses.sPaging = 'sub-hover paging_';
			$.fn.dataTableExt.oStdClasses.sPagePrevEnabled = 'control-prev';
			$.fn.dataTableExt.oStdClasses.sPagePrevDisabled = 'control-prev disabled';
			$.fn.dataTableExt.oStdClasses.sPageNextEnabled = 'control-next';
			$.fn.dataTableExt.oStdClasses.sPageNextDisabled = 'control-next disabled';
			$.fn.dataTableExt.oStdClasses.sPageFirst = 'control-first';
			$.fn.dataTableExt.oStdClasses.sPagePrevious = 'control-prev';
			$.fn.dataTableExt.oStdClasses.sPageNext = 'control-next';
			$.fn.dataTableExt.oStdClasses.sPageLast = 'control-last';
			
			// Apply to table
			$('.sortable').each(function(i)
			{
				// DataTable config
				var table = $(this),
					oTable = table.dataTable({
						/*
						 * We set specific options for each columns here. Some columns contain raw data to enable correct sorting, so we convert it for display
						 * @url http://www.datatables.net/usage/columns
						 */
						aoColumns: [
							{ bSortable: false },  // No sorting for this column
							{ sType: 'string' },
							{ bSortable: false },
							{ sType: 'string' },
							{ sType: 'string' },						
							{ bSortable: false }	// No sorting for actions column
						],
						
						/*
						 * Set DOM structure for table controls
						 * @url http://www.datatables.net/examples/basic_init/dom.jsp
						 */
						sDom: '<"block-controls"<"controls-buttons"p>>rti<"block-footer clearfix"lf>',
						
						/*
						 * Callback to apply template setup
						 */
						fnDrawCallback: function()
						{
							this.parent().applyTemplateSetup();
						},
						fnInitComplete: function()
						{
							this.parent().applyTemplateSetup();
						}
					});
				
				// Sorting arrows behaviour
				table.find('thead .sort-up').click(function(event)
				{
					// Stop link behaviour
					event.preventDefault();
					
					// Find column index
					var column = $(this).closest('th'),
						columnIndex = column.parent().children().index(column.get(0));
					
					// Send command
					oTable.fnSort([[columnIndex, 'asc']]);
					
					// Prevent bubbling
					return false;
				});
				table.find('thead .sort-down').click(function(event)
				{
					// Stop link behaviour
					event.preventDefault();
					
					// Find column index
					var column = $(this).closest('th'),
						columnIndex = column.parent().children().index(column.get(0));
					
					// Send command
					oTable.fnSort([[columnIndex, 'desc']]);
					
					// Prevent bubbling
					return false;
				});
			});
			
			/*
			 * Datepicker
			 * Thanks to sbkyle! http://themeforest.net/user/sbkyle
			 */
			$('.datepicker').datepick({
				alignment: 'bottom',
				showOtherMonths: true,
				selectOtherMonths: true,
				renderer: {
					picker: '<div class="datepick block-border clearfix form"><div class="mini-calendar clearfix">' +
							'{months}</div></div>',
					monthRow: '{months}', 
					month: '<div class="calendar-controls" style="white-space: nowrap">' +
								'{monthHeader:M yyyy}' +
							'</div>' +
							'<table cellspacing="0">' +
								'<thead>{weekHeader}</thead>' +
								'<tbody>{weeks}</tbody></table>', 
					weekHeader: '<tr>{days}</tr>', 
					dayHeader: '<th>{day}</th>', 
					week: '<tr>{days}</tr>', 
					day: '<td>{day}</td>', 
					monthSelector: '.month', 
					daySelector: 'td', 
					rtlClass: 'rtl', 
					multiClass: 'multi', 
					defaultClass: 'default', 
					selectedClass: 'selected', 
					highlightedClass: 'highlight', 
					todayClass: 'today', 
					otherMonthClass: 'other-month', 
					weekendClass: 'week-end', 
					commandClass: 'calendar', 
					commandLinkClass: 'button',
					disabledClass: 'unavailable'
				}
			});
		});
	
	</script>
<!-- InstanceEndEditable -->

</head>

<!-- InstanceBeginEditable name="Body" -->
<body>
	<!-- InstanceEndEditable -->


	<!-- WEMS Logo -->
	<div class="logo">
		<div class="container_12">
			<div class="wems-logo">
				<img src="images/wems/WEMSLogo.png" width="174" height="60">
				<p>UTS Energy Management Project</p>
			</div>
		</div>
	</div>

	<!-- Main nav -->
	<nav id="main-nav">
		<%
			UserDataClass user = configBean2.getUserByUsername(username);
		%>
		<ul class="container_12">
			<!-- InstanceBeginEditable name="Menu" -->
			<li class="home current"><a href="home.jsp" title="Home">Home</a>
			</li>
			<li class="estab"><a href="establishment.jsp"
				title="Establishment"
				onClick="document.location.href='establishment.jsp'">Establishment</a>
			</li>
			<li class="notification"><a href="notifications.jsp"
				title="Notifications"
				onClick="document.location.href='notifications.jsp'">Notifications</a>
				<ul>
					<li><a href="notifications.jsp" title="Notifications">Notifications</a></li>
				</ul></li>
			<li class="alarm"><a href="alarms.jsp" title="Alarms"
				onClick="document.location.href='alarms.jsp'">Alarms</a>
				<ul>
					<li><a href="alarms.jsp" title="Alarms">Alarms</a></li>
					<li><a href="new_alarm.jsp" title="New Alarm">New Alarm</a></li>
				</ul></li>
			<li class="admin"><a href="administration.jsp"
				title="Administration"
				onClick="document.location.href='administration.jsp'">Administration</a>
				<ul>
					<li class="with-menu"><a href="user_accounts.jsp" title="User">User
							Accounts</a>
						<div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="user_accounts.jsp">List
										of Users</a></li>
								<li class="icon_user"><a href="new_user.jsp">New User</a></li>
							</ul>
						</div></li>
					<li><a href="new_location.jsp" title="Location">New
							Location</a></li>
					<li class="with-menu"><a href="devices.jsp" title="Devices">Devices</a>
						<div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="devices.jsp">List of
										Devices</a></li>
							</ul>
						</div></li>
					<li class="with-menu"><a href="user_groups.jsp"
						title="User Groups">User Groups</a>
						<div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="user_groups.jsp">List
										of User Groups</a></li>
								<li class="icon_users"><a href="new_user_group.jsp">New
										User Group</a></li>
							</ul>
						</div></li>
				</ul>
			<li class="users"><a href="profile.jsp" title="Profile"
				onClick="document.location.href='profile.jsp'">Profile</a>
				<ul>
					<li><a href="profile.jsp" title="Profile">Profile</a></li>
					<li><a href="edit_profile.jsp" title="Edit Profile">Edit
							Profile</a></li>
				</ul></li>
			<!-- InstanceEndEditable -->
			<!-- InstanceEndEditable -->
		</ul>
	</nav>
	<!-- End main nav -->
	<!-- Sub nav -->

	<div id="sub-nav">
		<div class="container_12"></div>
	</div>
	<!-- End sub nav -->

	<!-- Status bar -->
	<div id="status-bar">
		<div class="container_12">

			<ul id="status-infos">
				<li class="spaced">LOGGED IN AS: <strong><%=username%></strong></li>

				<input type="button" class="button red" value="LOGOUT"
					onclick="OpenLogoutScriptlet()" />

				<SCRIPT language=JavaScript>
function OpenLogoutScriptlet() {
	window.location = "http://220.244.243.51:8080/WEMS_Servlets/Logout?user-name=<%=loginBean.getUserNameByIP(request.getRemoteAddr())%>";
}
</SCRIPT>
			</ul>

			<ul id="breadcrumb">
				<li><a href="home.jsp" title="Home">Home</a></li>
			</ul>


		</div>
	</div>
	<!-- End status bar -->

	<div id="header-shadow"></div>
	<!-- End header -->



	<!-- Content -->
	<!-- InstanceBeginEditable name="Content" -->

	<article class="container_12">


		<section class="grid_12">
			<div class="block-border">
				<div class="block-content">
					<h1>Welcome To WEMS</h1>
					<div><p>WEMS Web console is an energy management solution that enables 
					the user to monitor the power usage of devices, allow for real-time fault handling, 
					generate reports, and add, edit or delete devices in the user's organisation. 
					This system uses current technologies such as HTML5, Glassfish and JavaScript to provide concurrent 
					performance, in a hope of reducing power bills, carbon footprint and maintenance costs.</p></div>


				</div>
			</div>
		</section>


	</article>

	<%@ include file="footer.jsp"%>