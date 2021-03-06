package com.sf.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.sf.service.FileTraverser;

public class SfUtils {
	
	static Logger logger = Logger.getLogger(SfUtils.class.getName());

	/**
	 * 
	 * @param sheet
	 * @param cellReference
	 * @return
	 */
	public static Cell getCell(XSSFSheet sheet, CellReference cellReference) {
		Row row = sheet.getRow(cellReference.getRow());
		return row.getCell(cellReference.getCol());
	}
	
	public static Cell getHSSFCell(HSSFSheet sheet, CellReference cellReference) {
		Row row = sheet.getRow(cellReference.getRow());
		return row.getCell(cellReference.getCol());
	}

	/**
	 * 
	 * @param sheet
	 * @param cellReference
	 * @return
	 */
	public static String getCellValueasString(XSSFSheet sheet, CellReference cellReference) {
		String cellValue = getCell(sheet, cellReference).getStringCellValue();
		return cellValue;
	}
	
	public static String getHSSFCellValueasString(HSSFSheet sheet, CellReference cellReference) {
		String cellValue = getHSSFCell(sheet, cellReference).getStringCellValue();
		return cellValue;
	}

	/**
	 * 
	 * @param sheet
	 * @param cellReference
	 * @return
	 */
	public static Double getCellValueasNumber(XSSFSheet sheet, CellReference cellReference) {
		Double cellValue = getCell(sheet, cellReference).getNumericCellValue();
		return cellValue;
	}
	
	public static Double getHSSFCellValueasNumber(HSSFSheet sheet, CellReference cellReference) {
		Double cellValue = getHSSFCell(sheet, cellReference).getNumericCellValue();
		return cellValue;
	}

	/**
	 * 
	 * @param accName
	 * @return
	 * @throws IOException
	 */
	public static Properties accountTemplatePropertyLoader(String accName) throws IOException {

		FileReader accountPropertyMapping = new FileReader("resource/accountPropertyMapping.properties");
		Properties p = new Properties();
		p.load(accountPropertyMapping);

		if (accName != null) {

			String templatePropertyName = "resource/" + p.getProperty(accName);

			logger.info("Property File Name : " + templatePropertyName + " for Account Name : " + accName);

			FileReader templatePropertyMapping = new FileReader(templatePropertyName);
			Properties templateProperty = new Properties();
			templateProperty.load(templatePropertyMapping);

			return templateProperty;
		}

		return null;

	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties accountTemplateMappingPropertyLoader() throws IOException {
		
		FileReader accountTemplate = new FileReader("resource/accountTemplateMapping.properties");
		Properties p = new Properties();
		p.load(accountTemplate);
		
		return p;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties accountMappingPropertyLoader() throws IOException {

		FileReader accountMappings = new FileReader("resource/accountMappings.properties");
		Properties p = new Properties();
		p.load(accountMappings);

		return p;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties projectMappingPropertyLoader() throws IOException {

		FileReader projectMappings = new FileReader("resource/projectMappings.properties");
		Properties p = new Properties();
		p.load(projectMappings);

		return p;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Properties accountContactMappingPropertyLoader() throws IOException {

		FileReader accountContactMapping = new FileReader("resource/accountContactMapping.properties");
		Properties p = new Properties();
		p.load(accountContactMapping);

		return p;
	}


	/**
	 * 	// GEtting the value for external Id
	 * @param propertyName
	 * @param write
	 * @param updatedValue
	 * @return
	 */
	public static Integer valueStorePropertyLoader(String propertyName, Boolean write, String updatedValue) {
		String value = "";
		Integer intValue = null;
		// FileReader valueStore = null;
		FileInputStream valueStoreIn = null;
		Properties p = new Properties();
		try {
			valueStoreIn = new FileInputStream("resource/valueStore.properties");
			p.load(valueStoreIn);

		} catch (FileNotFoundException e) {
			logger.debug("resource/valueStore.properties File not found"+ e.getMessage());
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.debug("Error in file resource/valueStore.properties"+ e.getMessage());
			logger.error(e.getStackTrace());
		}
		if (!write) {
			value = p.getProperty(propertyName);
		} else {
			try {
				valueStoreIn.close();

				FileOutputStream valueStoreOut = new FileOutputStream("resource/valueStore.properties");
				p.setProperty(propertyName, updatedValue);
				p.store(valueStoreOut, null);
				valueStoreOut.close();
				logger.info(propertyName+ " Value updated with updated value =: "+ updatedValue);;
			} catch (IOException e) {
				logger.debug("Error in file resource/valueStore.properties "+ e.getMessage());
				logger.error(e.getStackTrace());
			}
		}

		if (!value.isEmpty()) {
			intValue = Integer.parseInt(value);
		}
		
		return intValue;
	}

	/**
	 * 
	 * @return
	 */
	public static String getExternalIdValue() {

		FileTraverser.externalIdCounter = FileTraverser.externalIdCounter + 1;
		return FileTraverser.externalIdCounter.toString();

	}

	/**
	 * 
	 * @param accountName
	 * @return
	 */
	public static String getAccountId(String accountName) {
		String accountId = "";
		try {
			Properties property = SfUtils.accountMappingPropertyLoader();
			accountName = accountName.trim().replace(" ", "_");
			accountId = property.getProperty(accountName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accountId;
	}

	/**
	 * 
	 * @param accountName
	 * @return
	 */
	public static String getContactId(String accountName) {
		String accountId = "";
		try {
			Properties property = SfUtils.accountContactMappingPropertyLoader();
			accountName = accountName.trim().replace(" ", "_");
			accountId = property.getProperty(accountName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accountId;
	}
}
