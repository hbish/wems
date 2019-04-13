<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <!DOCTYPE html>
<html lang="en"><!-- InstanceBegin template="/Templates/wems_login.dwt" codeOutsideHTMLIsLocked="false" -->
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
							{ bSortable: false },	// No sorting for this columns, as it only contains checkboxes
							{ sType: 'string' },
							{ bSortable: false },
							{ sType: 'numeric', bUseRendered: false, fnRender: function(obj) // Append unit and add icon
								{
									return '<small><img src="images/icons/fugue/image.png" width="16" height="16" class="picto"> '+obj.aData[obj.iDataColumn]+' Ko</small>';
								}
							},
							{ sType: 'date' },
							{ sType: 'numeric', bUseRendered: false, fnRender: function(obj) // Size is given as float for sorting, convert to format 000 x 000
								{
									return obj.aData[obj.iDataColumn].split('.').join(' x ');
								}
							},
							{ bSortable: false }	// No sorting for actions column
						],
						
						/*
						 * Set DOM structure for table controls
						 * @url http://www.datatables.net/examples/basic_init/dom.html
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
						  '    <li><a href="javascript:void(0)" onclick="$(this).getModalWindow().loadModalContent(\'ajax-modal.html\')">Load Ajax content</a></li>'+
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
			<li class="home"><a href="home.html" title="Home">Home</a>
			</li>
			<li class="estab"><a href="establishment.html" title="Establishment" onClick="document.location.href='establishment.html'">Establishment</a>
			</li>

			<li class="comments"><a href="notifications.html" title="Notifications" onClick="document.location.href='notifications.html'">Notifications</a>
               <ul>
					<li><a href="notifications.html" title="Notifications">Notifications</a></li>
			   </ul>
			</li>
            <li class="alarm"><a href="alarms.html" title="Alarms" onClick="document.location.href='alarms.html'">Alarms</a>
               <ul>

					<li><a href="alarms.html" title="Alarms">Alarms</a></li>
					<li><a href="new_alarm.html" title="New Alarm">New Alarm</a></li>
			   </ul>
            </li>
			<li class="stats"><a href="reports.html" title="Reports/Trends" onClick="document.location.href='reports.html'">Reports/Trends</a>
				<ul>
					<li><a href="reports.html" title="Reports">Reports</a></li>

					<li><a href="trends.html" title="Trends">Trends</a></li>
				</ul>
			</li>
			<li class="admin current"><a href="administration.html" title="Administration" onClick="document.location.href='administration.html'">Administration</a>
				<ul>
                    <li class="with-menu current"><a href="user_accounts.html" title="User">User Accounts</a>
						<div class="menu">

							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="user_accounts.html">List of Users</a>
								</li> 
                                <li class="icon_user current"><a href="new_user.html">New User</a>
								</li>
							</ul>
						</div>

					</li>
					<li class="with-menu"><a href="devices.html" title="Devices">Devices</a>
                        <div class="menu">
							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="devices.html">List of Devices</a>
							</ul>
						</div>
                    </li>
                    <li class="with-menu"><a href="user_groups.html" title="User Groups">User Groups</a>
                        <div class="menu">

							<img src="images/menu-open-arrow.png" width="16" height="16">
							<ul>
								<li class="icon_modify"><a href="user_groups.html">List of User Groups</a>
								</li> 
                                <li class="icon_users"><a href="new_user_group.html">New User Group</a>
								</li>
							</ul>
						</div>

                    </li>
				</ul>
			</li>
			<li class="users"><a href="profile.html" title="Profile" onClick="document.location.href='profile.html'">Profile</a>
				<ul>
					<li><a href="profile.html" title="Profile">Profile</a></li>
                    <li><a href="edit_profile.html" title="Edit Profile">Edit Profile</a></li>

				</ul>
			</li><!-- InstanceEndEditable -->
		</ul>
    </nav>
	<!-- End main nav -->
	
	<!-- Sub nav -->
<div id="sub-nav">
  <div class="container_12">
		
	<a href="help.html" title="Help" class="nav-button"><b>Help</b></a>

	
</div></div>
	<!-- End sub nav -->
	
	<!-- Status bar -->
<div id="status-bar"><div class="container_12">
	
		<ul id="status-infos">
			<li class="spaced">Logged as: <strong>Albert</strong></li>
			<li><a href="logout.html" class="button red" title="Logout"><span class="smaller">LOGOUT</span></a></li>
	  </ul>

		
		<ul id="breadcrumb">
			<li><a href="home.html" title="Home">Home</a></li>
			<!-- InstanceBeginEditable name="Breadcrumbs" -->
            <li><a href="administration.html" title="Administration">Administration</a></li>
            <li><a href="#" title="New User">New User</a></li>
            <!-- InstanceEndEditable -->
		</ul>

	
	</div></div>
	<!-- End status bar -->
	
	<div id="header-shadow"></div>
	<!-- End header -->
	

	
<!-- Content -->
<!-- InstanceBeginEditable name="Content" -->

<article class="container_12">
		
        <h1>New User</h1>
        <p>&nbsp;</p>
        
        <section class="grid_8">
			<div class="block-border">
			<!-- <form class="block-content form" id="simple_form" method="post" action="http://220.244.243.51:8080/WEMS_Servlets/AddNewUser">-->
				<!-- 'POST' and 'ACTION' methods have been removed from the HTML code above and are now used in Javascript,
				so that the next page is loaded only when the form has been validated. -->
				<form class="block-content form" name="simple_form" id="simple_form">
				<h1>New User Account</h1>

				
				<fieldset class="required">
                    <p class="inline-small-label">
					<label for="first-name">First Name</label>
					
					<input type="text" name="first-name" id="first-name" class="full-width" value="">
					
				    </p>
				    
                    <p class="inline-small-label">
					<label for="last-name">Last Name</label>
					<input type="text" name="last-name" id="last-name" class="full-width" value="">
					
					<p class="inline-small-label">
					<label for="last-name">Address</label>
					<input type="text" name="address" id="address" class="full-width" value="">
				    </p>
				    
                    <p class="inline-small-label">
					<label for="username">Username</label>
					<input type="text" name="username" id="username" class="full-width" value="">
				    </p>
				    
                    <p class="inline-small-label">
					<label for="pass">Password</label>
					<input type="password" name="pass" id="pass" class="full-width" value="">
				    </p> 
				    
                    <p class="inline-small-label">
					<label for="contact-number">Contact Number</label>
					<input type="text" name="contact-number" id="contact-number" class="full-width" value="">
				    </p>
				    
                    <p class="inline-small-label">
					<label for="email">Email</label>
					<input type="text" name="email" id="email" class="full-width" value="">
				    </p>
					
					<p class="inline-small-label">
						<label for="business-unit">User Group</label>
						<select name="business-unit" id="business-unit">
                            <option value="0">- Select One -</option>
							<option value="1">Audio Visual Team</option>
							<option value="2">Facilities Management Unit</option>
                            <option value="3">ABC</option>
                            <option value="4">Building X</option>
						</select>
					</p>
					
                    <p class="inline-small-label">
						<label for="user-access">User Type</label>
						<select name="user-access" id="user-access">
							<option value="0">- Select One -</option>
                            <option value="1">Administrator</option>
							<option value="2">Operator</option>
                            <option value="3">ABC</option>
						</select>
					</p>
				</fieldset>
				
				<SCRIPT language = JavaScript>
				var Message;
				var passwordMinNumOfChar = 4;
				var firstName = document.getElementById('first-name');
				var lastName = document.getElementById('last-name');
				var address = document.getElementById('address');
				var username = document.getElementById('username');
				var password = document.getElementById('pass');
				var contactNumber = document.getElementById('contact-number');
				var email = document.getElementById('email');
				var busUnit = document.getElementById('business-unit');
				var userAccess = document.getElementById('user-access');
//check if any textbox fields were left blank
function showAlert() {
	
	if(isNaN(contactNumber.value)) {
		alert("Please enter a valid contact number." );
	}

	else if(firstName.value == "" || lastName.value == "" || address.value == "" || username.value == "" || contactNumber.value == "" || email.value == "" || busUnit.value == 0 || userAccess.value == 0){
		alert("Please fill out every field.");
	}
	
	else if(CheckEmail() != "") {
		alert(Message);	
	}
	
	else if(password.value.length < passwordMinNumOfChar){
		alert("Please enter a password of at least " + passwordMinNumOfChar + " characters.");
	}
	
	else {
		//alert("submitting form..");
		document.simple_form.setAttribute("action", "http://220.244.243.51:8080/WEMS_Servlets/AddNewUser");
		document.simple_form.submit();
	}
}

function CheckEmail() {
	AtPos = email.value.indexOf("@");
	StopPos = email.value.lastIndexOf(".");
	Message = "";

	if (AtPos == -1 || StopPos == -1) {
	Message = "Not a valid email address";
	}

	if (StopPos < AtPos) {
	Message = "Not a valid email address";
	}

	if (StopPos - AtPos == 1) {
	Message = "Not a valid email address";
	}

	return Message;
	}

</SCRIPT>
				<fieldset class="grey-bg no-margin">

<!-- 					<button type="submit" class="float-right">Submit</button> -->


<p>
<button type="submit" class="float-right" onClick="showAlert();">Submit</button>
</p>
					
				</fieldset>
					
			</form></div>
		</section>   

        
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