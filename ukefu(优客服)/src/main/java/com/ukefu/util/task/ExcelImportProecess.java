package com.ukefu.util.task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.UKTools;
import com.ukefu.webim.web.model.SysDic;
import com.ukefu.webim.web.model.TableProperties;
import com.ukefu.webim.web.model.UKeFuDic;

public class ExcelImportProecess extends DataProcess{
	private DecimalFormat format = new DecimalFormat("###");
	
	public ExcelImportProecess(DSDataEvent event){
		super(event);
	}
	
	@Override
	public void process() {
		processExcel(event);
	}
	
	private void processExcel(final DSDataEvent event){
		InputStream is = null;  
    	try {
    		event.getDSData().getReport().setTableid(event.getDSData().getTask().getId());
    		if(event.getDSData().getUser()!=null){
    			event.getDSData().getReport().setUserid(event.getDSData().getUser().getId());
    			event.getDSData().getReport().setUsername(event.getDSData().getUser().getUsername());
    		}
    		
            try {  
                is = new FileInputStream(event.getDSData().getFile());  
            } catch (FileNotFoundException ex) {  
                ex.printStackTrace();
            }  
            boolean isExcel2003 = true;  
            if (isExcel2007(event.getDSData().getFile().getName())) {  
                isExcel2003 = false;  
            }
            
            Workbook wb = null;  
            try {  
                wb = isExcel2003 ? new HSSFWorkbook(is) : new XSSFWorkbook(is);  
            } catch (IOException ex) {  
                ex.printStackTrace();
            }  
            Sheet sheet = wb.getSheetAt(0);  
            Row titleRow = sheet.getRow(0);
            Row valueRow = sheet.getRow(1);
            int totalRows = sheet.getPhysicalNumberOfRows(); 
            int colNum = titleRow.getPhysicalNumberOfCells();
            for(int i=2 ; i<totalRows && valueRow == null ; i++){
            	valueRow = sheet.getRow(i);
            	if(valueRow !=null){
            		break ;
            	}
            }
            /**
             * 需要检查Mapping 是否存在
             */
            long start = System.currentTimeMillis() ;
            
            for(int i=1 ; i<totalRows; i++){
            	Row row = sheet.getRow(i) ;
            	if(row!=null){
					Object data = event.getDSData().getClazz().newInstance() ;
					Map<Object, Object> values = new HashMap<Object , Object>() ;
					for(int col=0 ; col<colNum ; col++){
						Cell value = row.getCell(col) ;
						Cell title = titleRow.getCell(col) ;
						String titleValue = getValue(title) ;
						TableProperties tableProperties = getTableProperties(event, titleValue);
						if(tableProperties!=null && value!=null){
							String valuestr = getValue(value) ;
							if(tableProperties.isSeldata()){
								SysDic sysDic = UKeFuDic.getInstance().getDicItem(valuestr) ;
								if(sysDic!=null){
									values.put(tableProperties.getFieldname(), sysDic.getName()) ;
								}else{
									values.put(tableProperties.getFieldname(), valuestr) ;
								}
							}else{
								values.put(tableProperties.getFieldname(), valuestr) ;
							}
							event.getDSData().getReport().setBytes(event.getDSData().getReport().getBytes() + valuestr.length());
							event.getDSData().getReport().getAtompages().incrementAndGet() ;
						}
					}
					values.put("orgi", event.getOrgi()) ;
					if(values.get("id")==null){
						values.put("id", UKTools.getUUID()) ;
					}
					if(event.getValues()!=null && event.getValues().size() > 0){
						values.putAll(event.getValues());
					}
					UKTools.populate(data, values);
					event.getDSData().getProcess().process(data);
            	}
			}
            
            event.setTimes(System.currentTimeMillis() - start);
            event.getDSData().getReport().setEndtime(new Date());
            event.getDSData().getReport().setStatus(UKDataContext.TaskStatusType.END.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(event.getDSData().getFile().exists()){
				event.getDSData().getFile().delete() ;
			}
		}
	}
	
	private TableProperties getTableProperties(DSDataEvent event , String title){
		TableProperties tableProperties = null ; 
		for(TableProperties tp : event.getDSData().getTask().getTableproperty()){
			if(tp.getName().equals(title) || tp.getFieldname().equals(title)){
				tableProperties = tp ; break ;
			}
		}
		return tableProperties;
	}
	
	private boolean isExcel2007(String fileName) {  
        return fileName.matches("^.+\\.(?i)(xlsx)$");  
    } 
	@SuppressWarnings("deprecation")
	private String getValue(Cell cell){
		String strCell = "";
		if(cell!=null){
			short dt = cell.getCellStyle().getDataFormat() ;
	        switch (cell.getCellType()) {
		        case HSSFCell.CELL_TYPE_STRING:
		            strCell = cell.getStringCellValue();
		            break;
		        case HSSFCell.CELL_TYPE_BOOLEAN:
		            strCell = String.valueOf(cell.getBooleanCellValue());
		            break;
		        case HSSFCell.CELL_TYPE_BLANK:
		            strCell = "";
		            break;
		        case HSSFCell.CELL_TYPE_NUMERIC:
		        	if (HSSFDateUtil.isCellDateFormatted(cell)) {
		        		SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                strCell = sdf.format(cell.getDateCellValue());  
		        	} else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                strCell = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value));  
		            }else{
		            	
		            	if (HSSFDateUtil.isCellDateFormatted(cell)) {
		            		SimpleDateFormat sdf = null;  
			                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
			                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			                } else {// 日期  
			                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			                }  
			                strCell = sdf.format(cell.getDateCellValue());  
			        	}else{
			            	boolean isNumber = isNumberFormat(dt) ;
			        		if(isNumber){
			        			DecimalFormat numberFormat = getNumberFormat(cell.getCellStyle().getDataFormatString()) ;
			        			if(numberFormat!=null){
			        				strCell = String.valueOf(numberFormat.format(cell.getNumericCellValue()));
			        			}else{
			        				strCell = String.valueOf(cell.getNumericCellValue());
			        			}
			        		}else{
			        			strCell = String.valueOf(format.format(cell.getNumericCellValue())) ;
			        		}
			        	}
	                }
		            break;
		        case HSSFCell.CELL_TYPE_FORMULA: {
	                // 判断当前的cell是否为Date
		        	boolean isNumber = isNumberFormat(dt) ;
		        	try{
		        		if(isNumber){
		        			strCell = String.valueOf(cell.getNumericCellValue());
			        	}else{
			        		strCell = "";
			        	}
		        	}catch(Exception ex){
		        		ex.printStackTrace();
		        		strCell = cell.getRichStringCellValue().getString();  
		        	}
	                break;
	            }
		        default:
		            strCell = "";
		            break;
	        }
	        if (strCell.equals("") || strCell == null) {
	            return "";
	        }
		}
        return strCell;
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	private String getDataType(Cell cell){
		String dataType = "string";
		if(cell!=null){
			short dt = cell.getCellStyle().getDataFormat() ;
	        switch (cell.getCellType()) {
		        case HSSFCell.CELL_TYPE_STRING:
		        	dataType = "string";
		            break;
		        case HSSFCell.CELL_TYPE_BOOLEAN:
		        	dataType = "number";
		            break;
		        case HSSFCell.CELL_TYPE_BLANK:
		        	if (HSSFDateUtil.isCellDateFormatted(cell)) {
		        		if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
		        			dataType = "datetime" ;  
		                } else {// 日期  
		                	dataType = "datetime" ;  
		                }  
		        		
		        	} else if (cell.getCellStyle().getDataFormat() == 58){
		        		dataType = "datetime" ;
		        	}else{
		        		boolean isNumber = isNumberFormat(dt) ;
		        		if(isNumber){
		        			dataType = "number";
		        		}else{
		        			dataType = "string";
		        		}
		        	}
		            break;
		        case HSSFCell.CELL_TYPE_NUMERIC:
		        	if (HSSFDateUtil.isCellDateFormatted(cell)) {
		        		if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
		        			dataType = "datetime" ;  
		                } else {// 日期  
		                	dataType = "datetime" ;  
		                }  
		        		
		        	} else if (cell.getCellStyle().getDataFormat() == 58){
		        		dataType = "datetime" ;
		        	}else{
		        		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			        		if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
			        			dataType = "datetime" ;  
			                } else {// 日期  
			                	dataType = "datetime" ;  
			                }  
			        	}else{
			        		boolean isNumber = isNumberFormat(dt) ;
			        		if(isNumber){
			        			dataType = "number";
			        		}else{
			        			dataType = "string";
			        		}
			        	}
	                }
		            break;
		        case HSSFCell.CELL_TYPE_FORMULA: {
	                // 判断当前的cell是否为Date
		        	boolean isNumber = isNumberFormat(dt) ;
	        		if(isNumber){
	        			dataType = "number";
	        		}else{
	        			dataType = "string";
	        		}
	                break;
	            }
		        default:
		        	dataType = "string";
		            break;
	        }
	       
		}
        return dataType;
	}
	
	private DecimalFormat getNumberFormat(String dataformat){
		DecimalFormat numberFormat = null ;
		int index = dataformat.indexOf("_") > 0 ?  dataformat.indexOf("_") : dataformat.indexOf(";") ;
		if(index > 0){
			String format = dataformat.substring( 0 , index) ;
			if(format.matches("[\\d.]{1,}")){
				numberFormat = new DecimalFormat(format);
			}
		}
		
		return numberFormat ;
	}
	
	private boolean isNumberFormat(short dataType){
		boolean number = false ;
		switch(dataType){
			case 180 : number = true  ; break; 
			case 181 : number = true  ; break; 
			case 182 : number = true  ; break;
			case 178 : number = true  ; break;
			case 177 : number = true  ; break;
			case 176 : number = true  ; break;
			case 183 : number = true  ; break; 
			case 185 : number = true  ; break; 
			case 186 : number = true  ; break;
			case 179 : number = true  ; break;
			case 187 : number = true  ; break; 
			case 7 : number = true  ; break; 
			case 8 : number = true  ; break; 
			case 44 : number = true  ; break; 
			case 10 : number = true  ; break; 
			case 12 : number = true  ; break; 
			case 13 : number = true  ; break; 
			case 188 : number = true  ; break; 
			case 189 : number = true  ; break; 
			case 190 : number = true  ; break; 
			case 191 : number = true  ; break; 
			case 192 : number = true  ; break; 
			case 193 : number = true  ; break; 
			case 194 : number = true  ; break; 
			case 11 : number = true  ; break; 

		}
		return number ;
	}


}
