package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;
import com.wems.database.*;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.*;

/**
 * Write a description of class ReportViewer here.
 */
public class ReportViewer
{
    /* Instance variables */
    static Config config;
    /* JasperPrint is the object contains report after result filling process */
    JasperPrint jasperPrint;
    /* JasperReport is the object that holds our compiled jrxml file */
    JasperReport jasperReport;
    /* JRViewer is the encapsulated Viewer Object to display the report */
    JRViewer viewer;
    
    /**
     * Constructor for objects of class ReportViewer
     */
    public ReportViewer(Config config)
    {
        this.config = config;
    }
    
    public JPanel displayReport(String reportPath, String reportExport, String reportFormat) throws Exception
    {
        // connection is the data source we used to fetch the data from
        Connection connection = establishConnection();
        
        // jasperParameter is a Hashmap contains the parameters passed from application to the jrxml layout
        HashMap jasperParameter = new HashMap();
        
        // jrxml compiling process (Only used of JRXML Files, if compiled this step can be skipped)
        jasperReport = JasperCompileManager.compileReport(reportPath);
        jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
        
        // filling report with data from data source (Using precompiled report)
        //jasperPrint = JasperFillManager.fillReport(reportPath, jasperParameter, connection);
        
        // Export File
        String exportFile = reportExport;
        for (int i=0; i<config.REPORT_EXT_NO; i++)
        {
            exportFile = removeCharAt(exportFile, exportFile.length()-1);
        }
        exportFile += " " + new java.util.Date().toString();
        exportFile = exportFile.replaceAll(":", "-");
        switch (reportFormat)
        {
            case "Save as HTML":
            {
                exportFile += ".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, exportFile);
                break;
            }
            case "Save as PDF":
            {
                exportFile += ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, exportFile);
                break;
            }
            case "Save as EXCEL":
            {
                exportFile += ".xls";
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, exportFile);
                exporter.exportReport();
                break;
            }
        }
        
        viewer = new JRViewer(jasperPrint);
        viewer.setPreferredSize(new Dimension(680, 480));
        
        // embed JasperReport viewer into JPanel
        JPanel panel = new JPanel();
        panel.add(viewer);
        panel.setVisible(true);
        return panel;
    }
    
    private String removeCharAt(String s, int pos)
    {
        return s.substring(0,pos)+s.substring(pos+1);
    }
    
    private static Connection establishConnection()
    {
        try
        {
            return DBConnection.getDBConnection(config);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }
}