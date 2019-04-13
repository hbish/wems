package com.wems.config;

public class Config
{
    public boolean WIN7_UI_LOOK      = true; // Enable UI L&F for Win7 (For WinXP, best set false)
    public String SYS_USER_NAME      = "wems-server";
    public String SYS_USER_DESC      = "WEMS Administration for UTS FMU";
    public String SYS_USER_OWNER     = "UTS FMU/Service-Point";
    public String SYS_USER_SERIAL    = "0000-0000-0000-0000";
    public int    SYS_MAX_USERS      = 400;
    public int    SYS_MAX_LOG_ATTEMPT= 3; // Max Login Attempts
    public int    SYS_USER_ACCESS_0  = 0; // SuperAdmin Access Rights Type
    public int    SYS_USER_ACCESS_1  = 1; // Admin Access Rights Type
    public String SYS_EMAIL_CONTACT  = "admin@localhost";
    public String SYS_WEB_CONSOLE    = "http://localhost:8080/WEMS_Servlets/jsp/index.jsp";

    public String DB_JDBC_PROTOCOL   = "jdbc:mysql://";
    public String DB_JDBC_CLASS_NAME = "com.mysql.jdbc.Driver"; // Windows driver
    public String DB_HOST            = "localhost:3306";        // Host where the Database resides (Windows)
    public String DB_NAME            = "wemsdb";                // Name of the database
    public String DB_USER            = "root";                  // Database User
    public String DB_PASSWORD        = "root"; 	                // DB User Password

    // Note: JRXML Files for JasperViewer (Please modify Jasper/JRXML file fo image path)
    public int    REPORT_EXT_NO      = 6; // Max Chars for Extension (.JRXML - 6 or .JASPER - 7)
    public String REPORT_PATH_BASE   = "./reports/";
    public String REPORT_PATH_EXPORT = "./export/";
    public String REPORT_EVENT       = "report_event.jrxml";
    public String REPORT_SENSOR      = "report_device.jrxml";
    public String REPORT_USER        = "report_user.jrxml";
    public String REPORT_USERGROUP   = "report_usergroup.jrxml";
    public String REPORT_ALERT       = "report_alert.jrxml";
    public String[] SAVE_TYPE        = new String[]{"Save as HTML", "Save as PDF", "Save as EXCEL"};
    public String[] REPORT_LIST      = new String[]{"Events",                        "Sensor Device",                  "User",                         "User Group",                        "Alerts"};
    public String[] REPORT_PATH      = new String[]{REPORT_PATH_BASE  +REPORT_EVENT, REPORT_PATH_BASE  +REPORT_SENSOR, REPORT_PATH_BASE  +REPORT_USER, REPORT_PATH_BASE  +REPORT_USERGROUP, REPORT_PATH_BASE  +REPORT_ALERT};
    public String[] REPORT_EXPORT    = new String[]{REPORT_PATH_EXPORT+REPORT_EVENT, REPORT_PATH_EXPORT+REPORT_SENSOR, REPORT_PATH_EXPORT+REPORT_USER, REPORT_PATH_EXPORT+REPORT_USERGROUP, REPORT_PATH_EXPORT+REPORT_ALERT};

    // Resource Links:
    // http://www.velocityreviews.com/forums/t141237-send-smtp-mail-using-javamail-with-gmail-account.html
    // http://www.javapractices.com/topic/TopicAction.do?Id=144
    // http://www.java.net/node/697528
    public String MAIL_SMTP_HOST     = "localhost";
    public int    MAIL_SMTP_PORT     = 465;
    public String MAIL_FROM          = "admin@localhost";
    public String MAIL_SMTP_USER     = "admin@localhost";
    public String MAIL_SMTP_PASS     = "encXN.-bR";
    public String MAIL_SMTP_AUTH     = "true";
    public String MAIL_DEBUG         = "false";
    public int    MAIL_SMTP_SF_PORT  = 465;
    public String MAIL_SMTP_SF_CLASS = "javax.net.ssl.SSLSocketFactory";
    public String MAIL_SMTP_SF_FBACK = "false";

    // SMS Gateway Configuration
    public int    SMS_VALIDNUMBER    = 11; // i.e. in the form 61 XXX XXX XXX
    public int    SMS_MAXNUMBERS     = 10;
    public String SMS_HOST_USERNAME  = "";
    public String SMS_HOST_PASSWORD  = "";
    public String SMS_GATEWAY_URL_1  = "http://gateway.onewaysms.com.au:10001/api.aspx?apiusername=";
    public String SMS_GATEWAY_URL_2  = "&apipassword=";
    public String SMS_GATEWAY_URL_3  = "&mobileno=";
    public String SMS_GATEWAY_URL_4  = "&senderid=onewaysms&languagetype=1&message=";
}
