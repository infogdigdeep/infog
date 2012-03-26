package com.infog.common.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	public static final String TEMPLATE = "/users.jrxml";
	
	public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String MEDIA_TYPE_PDF = "application/pdf";

	public static final String EXTENSION_TYPE_EXCEL = "xls";
	public static final String EXTENSION_TYPE_PDF = "pdf";


	@Autowired
	public JasperDatasourceService datasource;
	
	public ByteArrayOutputStream create (String reportType) throws Exception {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream templateStream = this.getClass().getResourceAsStream(TEMPLATE);
		JasperDesign jd = JRXmlLoader.load(templateStream);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, datasource.getDataSource());
		if (reportType.equals(EXTENSION_TYPE_EXCEL)) {
			createXLS(jp, bao);
		} else if (reportType.equals(EXTENSION_TYPE_PDF)) {
			createPdf(jp, bao);
		} else {
			throw new Exception ("Unknown Report Type");
		}
		return bao;
	}
	
	public void createPdf (JasperPrint jp, ByteArrayOutputStream bao) throws Exception {
		JRPdfExporter pdfExporter = new JRPdfExporter();
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bao);
		pdfExporter.exportReport();
	}
	
	public void createXLS (JasperPrint jp, ByteArrayOutputStream bao) throws Exception {
		JRXlsExporter xlsExporter = new JRXlsExporter();
		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bao);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		xlsExporter.exportReport();
	}
	
	public String getMediaType (String reportType) throws Exception {
		if (reportType.equals(EXTENSION_TYPE_EXCEL)) {
			return MEDIA_TYPE_EXCEL;
		} else if (reportType.equals(EXTENSION_TYPE_PDF)) {
			return MEDIA_TYPE_PDF;
		} else {
			throw new Exception ("Unknown Report Typre");
		}
		
	}
}
