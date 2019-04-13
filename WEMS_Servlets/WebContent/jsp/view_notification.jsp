<%@page import="com.wems.data.AlertLogDataClass"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.naming.InitialContext, java.util.*, com.wems.service.*, com.wems.entity.*" %>
<jsp:useBean id="configBean" class="com.wems.service.ConfigurationBean" scope="session"/>
<jsp:setProperty name="configBean" property="*"/>
<%boolean foundUser;%>
<%String userName;%>
<%foundUser=false; %>
<%User user = new User(); %>

<!DOCTYPE html>
<html lang="en"><!-- InstanceBegin template="/templates/wems_loggedin.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
<% ConfigurationBean configBean2 = null;
try {
	InitialContext initialContext = new InitialContext();
	configBean2 = (ConfigurationBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/ConfigurationBean!com.wems.service.ConfigurationBean");
} catch (Exception exception) {
//Do nothing
}%>
<% LoginBean loginBean = null;
try {
	InitialContext initialContext = new InitialContext();
	loginBean = (LoginBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/LoginBean!com.wems.service.LoginBean");
} catch (Exception exception) {
//Do nothing
}%>
<% AlarmsBean alarmsBean = null;
try {
	InitialContext initialContext = new InitialContext();
	alarmsBean = (AlarmsBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/AlarmsBean!com.wems.service.AlarmsBean");
} catch (Exception exception) {
//Do nothing
}%>
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
	
    <script type="text/javascript" src="js/dynamicoptionlist.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    
    

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
							{ sType: 'string' },
							{ sType: 'string' },
							{ bSortable: false },  // No sorting for this column							
							{ bSortable: false },
							{ sType: 'string' },
							{ bSortable: false },
							{ bSortable: false },
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

<!-- InstanceBeginEditable name="Body" --><body><!-- InstanceEndEditable -->

	
	<!-- WEMS Logo -->
  <div class="logo"><div class="container_12">	
		<div class="wems-logo"><img src="images/wems/WEMSLogo.png" width="174" height="60"><p>UTS Energy Management Project</p></div>	
	</div></div>

    <!-- Main nav -->
	<nav id="main-nav">
		
		<ul class="container_12">
            <!-- InstanceBeginEditable name="Menu" -->
			<li class="home"><a href="home.jsp" title="Home">Home</a>
			</li>
			<li class="estab"><a href="establishment.jsp" title="Establishment" onClick="document.location.href='establishment.jsp'">Establishment</a>
			</li>
			<li class="notification current"><a href="notifications.jsp" title="Notifications" onClick="document.location.href='notifications.jsp'">Notifications</a>
               <ul>
					<li class="current"><a href="notifications.jsp" title="Notifications">Notifications</a></li>
			   </ul>
			</li>
            <li class="alarm"><a href="alarms.jsp" title="Alarms" onClick="document.location.href='alarms.jsp'">Alarms</a>
               <ul>
					<li><a href="alarms.jsp" title="Alarms">Alarms</a></li>
					<li><a href="new_alarm.jsp" title="New Alarm">New Alarm</a></li>
			   </ul>
            </li>

			<li class="admin"><a href="administration.jsp" title="Administration" onClick="document.location.href='administration.jsp'">Administration</a>
				<ul>
                    <li class="with-menu"><a href="#" title="User">User Accounts</a>
						<div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="user_accounts.jsp">List of Users</a>
								</li> 
                                <li class="icon_user"><a href="new_user.jsp">New User</a>
								</li>
							</ul>
						</div>
					</li>
                    <li><a href="new_location.jsp" title="Location">New Location</a></li>
					<li class="with-menu"><a href="#" title="Devices">Devices</a>
                        <div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="devices.jsp">List of Devices</a>
								</li> 
							</ul>
						</div>
                    </li>
                    <li><a href="user_groups.jsp" title="User Groups">User Groups</a></li>
				</ul>
			</li>
			<li class="users"><a href="profile.jsp" title="Profile" onClick="document.location.href='profile.jsp'">Profile</a>
				<ul>
					<li><a href="profile.jsp" title="Profile">Profile</a></li>
                    <li><a href="edit_profile.jsp" title="Edit Profile">Edit Profile</a></li>
				</ul>
			</li><!-- InstanceEndEditable -->
		</ul>
    </nav>
	<!-- End main nav -->
	
	<!-- Sub nav -->
<div id="sub-nav">
  <div class="container_12">
			
</div></div>
	<!-- End sub nav -->
	
	<!-- Status bar -->
<div id="status-bar"><div class="container_12">
	
		<ul id="status-infos">
			<li class="spaced">LOGGED IN AS: <strong><%=loginBean.getUserNameByIP(request.getRemoteAddr())%></strong></li>
			
			<input type="button" class="button red" value="LOGOUT" onclick="OpenLogoutScriptlet()"/>
			
<SCRIPT language = JavaScript>
function OpenLogoutScriptlet() {
	window.location = "http://220.244.243.51:8080/WEMS_Servlets/Logout?user-name=<%=loginBean.getUserNameByIP(request.getRemoteAddr())%>";
}
</SCRIPT>
	  </ul>
		
		<ul id="breadcrumb">
			<li><a href="home.jsp" title="Home">Home</a></li>
			<!-- InstanceBeginEditable name="Breadcrumbs" -->
            <li><a href="notifications.jsp" title="Notifications">Notifications</a></li>
            <li><a href="#" title="View Notification">View Notification</a></li>
            <!-- InstanceEndEditable -->
		</ul>
	
	</div></div>
	<!-- End status bar -->
	
	<div id="header-shadow"></div>
	<!-- End header -->
	

	
<!-- Content -->
<!-- InstanceBeginEditable name="Content" -->

<article class="container_12">
		
        <h1>View Notification</h1>
        <p>&nbsp;</p>
        
        
     

        
        <!-- All Notifications -->
        <section class="grid_12">
			<div class="block-border"><form class="block-content form" id="table_form" method="post" action="">
				<h1>Notification</h1>
                <div class="no-margin">
				<table class="table no-margin" cellspacing="0" width="100%">
				
					<thead>
						<tr>
							<th scope="col">
								Setting
							</th>
                            <th scope="col">
								Value
							</th>
						</tr>
					</thead>
					
					<tbody>
						<%
						AlertLogDataClass alertLog = alarmsBean.getAlertLogByID(Integer.parseInt(request.getParameter("id")));
						%>
						<tr>
							<td>Priority</td>
							<td><%=alertLog.getImportance() %></td>
						</tr>
                        <tr>
							<td>Building</td>
							<td><%=alertLog.getBuilding() %></td>
						</tr>
                        <tr>
							<td>Level</td>
							<td><%=alertLog.getFloor() %></td>
						</tr>
                        <tr>
							<td>Room</td>
							<td><%=alertLog.getRoom() %></td>
						</tr>
                        <tr>
							<td>Device ID</td>
							<td><%=alertLog.getDevice() %></td>
						</tr>
                         <tr>
							<td>Parameter</td>
							<td><%=alertLog.getParameterName() %></td>
						</tr>
                        <tr>
							<td>Trigger</td>
							<td><%=alertLog.getTrigger() %></td>
						</tr>
                        <tr>
							<td>Notification</td>
							<td><%=alarmsBean.getSensorDataDump(Integer.parseInt(request.getParameter("id"))) %></td>
						</tr>
                        
				
					</tbody>
				
				</table>
            </div>	
			</form></div>
		</section>
        
</article>
<%@ include file="footer.jsp"%>