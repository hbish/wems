<!DOCTYPE html>
<html lang="en">
<head>

<!-- InstanceBeginEditable name="Title" -->
<title>WEMS Web-based Energy Management System</title>
<meta charset="utf-8">

<!-- Global stylesheets -->
<link href="jsp/css/reset.css" rel="stylesheet" type="text/css">
<link href="jsp/css/common.css" rel="stylesheet" type="text/css">
<link href="jsp/css/form.css" rel="stylesheet" type="text/css">
<link href="jsp/css/standard.css" rel="stylesheet" type="text/css">

<!-- Comment/uncomment one of these files to toggle between fixed and fluid layout -->
<link href="jsp/css/960.gs.css" rel="stylesheet" type="text/css">

<!-- WEMS Custom styles -->
<link href="jsp/css/wems.css" rel="stylesheet" type="text/css">

<!-- Custom styles -->
<link href="jsp/css/simple-lists.css" rel="stylesheet" type="text/css">
<link href="jsp/css/block-lists.css" rel="stylesheet" type="text/css">
<link href="jsp/css/planning.css" rel="stylesheet" type="text/css">
<link href="jsp/css/table.css" rel="stylesheet" type="text/css">
<link href="jsp/css/calendars.css" rel="stylesheet" type="text/css">
<link href="jsp/css/wizard.css" rel="stylesheet" type="text/css">
<link href="jsp/css/gallery.css" rel="stylesheet" type="text/css">

<!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon" href="wemsfavicon.ico">
<link rel="icon" type="image/png" href="wemsfavicon.png">

<!-- Generic libs -->
<script type="jsp/text/javascript" src="js/html5.js"></script>
<!-- this has to be loaded before anything else -->
<script type="jsp/text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="jsp/text/javascript" src="js/old-browsers.js"></script>
<!-- remove if you do not need older browsers detection -->

<!-- Template libs -->
<script type="jsp/text/javascript" src="js/jquery.accessibleList.js"></script>
<script type="jsp/text/javascript" src="js/searchField.js"></script>
<script type="jsp/text/javascript" src="js/common.js"></script>
<script type="jsp/text/javascript" src="js/standard.js"></script>
<!--[if lte IE 8]><script type="jsp/text/javascript" src="js/standard.ie.js"></script><![endif]-->
<script type="jsp/text/javascript" src="js/jquery.tip.js"></script>
<script type="jsp/text/javascript" src="js/jquery.hashchange.js"></script>
<script type="jsp/text/javascript" src="js/jquery.contextMenu.js"></script>
<script type="jsp/text/javascript" src="js/jquery.modal.js"></script>

<!-- Custom styles lib -->
<script type="jsp/text/javascript" src="js/list.js"></script>

<!-- Plugins -->
<script type="jsp/text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="jsp/text/javascript"
    src="js/jquery.datepick/jquery.datepick.min.js"></script>

<!-- InstanceBeginEditable name="Scripts" -->
<script type="jsp/text/javascript">
        
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
                list.push(false);   // Separator
                list.push({ text: 'Delete', link:'#', icon:'delete' });
                list.push({ text: 'Edit', link:'#', icon:'edit' });
            });
            
            // Extra options for the first one
            $('.favorites li:first').bind('contextMenu', function(event, list)
            {
                list.push(false);   // Separator
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
            $('.notsortable').each(function(i)
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
                            { bSortable: false },
                            { bSortable: false },
                            { bSortable: false },                       
                            { bSortable: false }    // No sorting for actions column
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
                            { bSortable: false }    // No sorting for actions column
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




<!-- InstanceEndEditable -->
</head>

<body>

    <!-- WEMS Logo -->
    <div class="logo">
        <div class="container_12">
            <div class="wems-logo">
                <img src="jsp/images/wems/WEMSLogo.png" width="174" height="60">
                <p>UTS Energy Management Project</p>
            </div>
        </div>
    </div>


    <!-- Main nav -->
    <nav id="main-nav">

        <ul class="container_12">
            <!-- InstanceBeginEditable name="Menu" -->
            <li class="home current"><a href="./index.jsp" title="Home">Home</a>
            </li>
            <li class="estab"><a href="jsp/go_green.jsp" title="Go Green">Go
                    Green</a></li>
            <!-- InstanceEndEditable -->
        </ul>
    </nav>
    <!-- End main nav -->

    <!-- Sub nav -->
    <div id="sub-nav">
        <div class="container_12">

            <a href="#" title="Help" class="nav-button"><b>Help</b></a>

            <form id="search-form" name="search-form" method="post"
                action="search.jsp">
                <input type="text" name="s" id="s" value="" title="Search admin..."
                    autocomplete="off">
            </form>

        </div>
    </div>
    <!-- End sub nav -->