<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="javax.naming.InitialContext, java.util.*, com.wems.service.*, com.wems.entity.*, com.wems.data.*" %>
<!DOCTYPE html>
<html lang="en"><!-- InstanceBegin template="/templates/wems_loggedin.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
<meta http-equiv="Refresh" content="6,URL=http://220.244.243.51:8080/WEMS_Servlets/jsp/alarms.jsp">

<% LocationBean locBean = null;
try {
	InitialContext initialContext = new InitialContext();
	locBean = (LocationBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/LocationBean!com.wems.service.LocationBean");
} catch (Exception exception) {
//Do nothing
}%>
<% AlarmsBean alarmBean = null;
try {
	InitialContext initialContext = new InitialContext();
	alarmBean = (AlarmsBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/AlarmsBean!com.wems.service.AlarmsBean");
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
<% ConfigurationBean configBean = null;
try {
    InitialContext initialContext = new InitialContext();
    configBean = (ConfigurationBean) initialContext.lookup("java:global/WEMS_EAR/WEMS_EJB/ConfigurationBean!com.wems.service.ConfigurationBean");
} catch (Exception exception) {
//Do nothing
}%>
<%String username = loginBean.getUserNameByIP(request.getRemoteAddr());%>
<% String deleteid;%>
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
		
		// Demo modal
		function openModal()
		{
			$.modal({
				content: '<p>This is an example of modal window. You can open several at the same time (click button below!), move them and resize them.</p>'+
						  '<p>The plugin provides several other functions to control them, try below:</p>'+
						  '<ul class="simple-list with-icon">'+
						  '    <li><a href="javascript:void(0)" onclick="$(this).getModalWindow().setModalTitle(\'\')">Remove title</a></li>'+
						  '    <li><a href="javascript:void(0)" onclick="$(this).getModalWindow().setModalTitle(\'New title\')">Change title</a></li>'+
						  '    <li><a href="javascript:void(0)" onclick="$(this).getModalWindow().loadModalContent(\'ajax-modal.jsp\')">Load Ajax content</a></li>'+
						  '</ul>',
				title: 'Example modal window',
				maxWidth: 500,
				buttons: {
					'Open new modal': function(win) { openModal(); },
					'Close': function(win) { win.closeModal(); }
				}
			});
		}
	
	</script>
    
    
    
    
    
    
    
    
    
    <script type="text/javascript">
    		// Login modal
		function deleteAlarm(alarmID)
		{
			$.modal({
				content: '<div class="block-header">Confirm Delete</div>'+
				'<form class="form with-margin" name="delete-alarm-form" id="delete-alarm-form" method="post" action="http://220.244.243.51:8080/WEMS_Servlets/DeleteAlarm">'+
'<input type="hidden" name="alarmID" id="alarmID" value="' + alarmID + '">' +
'<p>Are you sure you want to delete this alarm?</p>'+
'<button type="submit" class="float-right">Delete</button>'+
'<button type="button" onclick="$(this).getModalWindow().closeModal();">Cancel</button>'+
'</form>',
				title: '',
				maxWidth: 500,
				buttons: {
				}
			});
		}
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
			<li class="notification"><a href="notifications.jsp" title="Notifications" onClick="document.location.href='notifications.jsp'">Notifications</a>
               <ul>
					<li><a href="notifications.jsp" title="Notifications">Notifications</a></li>
			   </ul>
			</li>
            <li class="alarm current"><a href="alarms.jsp" title="Alarms" onClick="document.location.href='alarms.jsp'">Alarms</a>
               <ul>
					<li class="current"><a href="alarms.jsp" title="Alarms">Alarms</a></li>
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
            <li><a href="#" title="Alarms">Alarms</a></li>
            <!-- InstanceEndEditable -->
		</ul>
	
	</div></div>
	<!-- End status bar -->
	
	<div id="header-shadow"></div>
	<!-- End header -->
	

	
<!-- Content -->
<!-- InstanceBeginEditable name="Content" -->

<article class="container_12">
		
        <h1>Alarms</h1>
        <%UserDataClass user = configBean.getUserByUsername(username); %>
        <p>Showing all alarm configuration settings for <strong><%=user.getFirstName()%></strong> from <strong><%=user.getUserGroup() %></strong> group.</p>
        
        <!-- All Alarms -->
        <section class="grid_12">
			<div class="block-border"><form class="block-content form" id="table_form" method="post" action="">
				<h1>All Alarms</h1>
			
				<table class="table sortable no-margin" cellspacing="0" width="100%">
				
					<thead>
						<tr>
                            <th scope="col"><span class="column-sort">
									<a href="#" title="Sort up" class="sort-up"></a>
									<a href="#" title="Sort down" class="sort-down"></a>
								</span>Priority</th>
							<th scope="col"><span class="column-sort">
									<a href="#" title="Sort up" class="sort-up"></a>
									<a href="#" title="Sort down" class="sort-down"></a>
								</span>Building</th>
							<th scope="col">
								Level
							</th>
                            <th scope="col">
								Room
							</th>
                            <th scope="col">
								ID
							</th>
							<th scope="col">
								<span class="column-sort">
									<a href="#" title="Sort up" class="sort-up"></a>
									<a href="#" title="Sort down" class="sort-down"></a>
								</span>
								Type
							</th>
                            <th scope="col">
								Parameter
							</th>
                            <th scope="col">
								Trigger
							</th>
							<th scope="col" class="table-actions">Actions</th>
						</tr>
					</thead>
					
					<tbody>
						<%List<AlertSettingDataClass> userAlarms = alarmBean.getUsersAlarms(username);%>
						<%for(int i = 0; i < userAlarms.size();i++){ %>
						<tr>
							<td><%=userAlarms.get(i).getPriority()%></td>
							<td><%=userAlarms.get(i).getBuilding()%></td>
							<td><%=userAlarms.get(i).getLevel()%></td>
                            <td><%=userAlarms.get(i).getRoom()%></td>
							<td><%=userAlarms.get(i).getDeviceId()%></td>
                            <td><%=userAlarms.get(i).getDeviceType()%></td>
							<td><%=userAlarms.get(i).getParameterName()%></td>
                            <td><%=userAlarms.get(i).getTrigger()%></td>
							
							<td class="table-actions">
								<a href="view_alarm.jsp?id=<%=userAlarms.get(i).getAlertId()%>" title="View" class="with-tip"><img src="images/icons/fugue/magnifier.png" width="16" height="16"></a>
                                <a href="edit_alarm.jsp?id=<%=userAlarms.get(i).getAlertId()%>" title="Modify" class="with-tip"><img src="images/icons/fugue/pencil.png" width="16" height="16"></a>
								<a href="#" title="Delete" class="with-tip" onClick="deleteAlarm(<%=Integer.toString(userAlarms.get(i).getAlertId())%>)"><img src="images/icons/fugue/cross-circle.png" width="16" height="16"></a>
								
							</td>
							
						</tr>
						<%}%>
					</tbody>
				
				</table>
					
			</form></div>
		</section>
		
		<div class="clear"></div>
		
</article>
<%@ include file="footer.jsp" %>