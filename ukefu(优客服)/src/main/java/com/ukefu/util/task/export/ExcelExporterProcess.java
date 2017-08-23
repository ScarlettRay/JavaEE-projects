package com.ukefu.util.task.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.ukefu.webim.web.model.MetadataTable;
import com.ukefu.webim.web.model.SysDic;
import com.ukefu.webim.web.model.TableProperties;
import com.ukefu.webim.web.model.UKeFuDic;

@SuppressWarnings("deprecation")
public class ExcelExporterProcess {
	private HSSFWorkbook  wb; 
	private Sheet sheet; 
	private CellStyle firstStyle = null ;
	
	private int rowNum ;
	
	private List<Map<String ,Object>> values ;
	private MetadataTable table ;
	private OutputStream output ;
	
	public ExcelExporterProcess(List<Map<String ,Object>> values , MetadataTable table , OutputStream output) {
		this.values = values ;
		this.table = table ;
		this.output = output;
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		firstStyle = createFirstCellStyle();
		createHead() ;
	}
	public void process() throws IOException{
		createContent();
		if(table!=null){
			for(TableProperties tp : table.getTableproperty()){
				sheet.autoSizeColumn(table.getTableproperty().indexOf(tp)) ;
			}
			wb.write(this.output);
		}
	}
	
	/**
	 * 构建头部
	 */
	private void createHead(){
		Row row = sheet.createRow(rowNum);
		if(table!=null && table.getTableproperty()!=null){
			for(TableProperties tp : table.getTableproperty()){
				Cell cell2 = row.createCell(table.getTableproperty().indexOf(tp)); 
				cell2.setCellStyle(firstStyle); 
				cell2.setCellValue(new HSSFRichTextString(tp.getName()));
			}
		}
		rowNum ++ ;
	}
	
	private CellStyle createContentStyle(){
		CellStyle cellStyle = wb.createCellStyle(); 
		
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
		cellStyle.setWrapText(false);// 指定单元格自动换行 

		// 设置单元格字体 
		Font font = wb.createFont(); 
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 200); 
		cellStyle.setFont(font); 
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		return cellStyle ;
	}
	
	/**
	 * 首列样式
	 * @return
	 */
	private CellStyle createFirstCellStyle(){
		CellStyle cellStyle = baseCellStyle();
		Font font = wb.createFont();
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 180);
		cellStyle.setFont(font);
		
		cellStyle.setWrapText(false);
		
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
	
		return cellStyle;
	}
	
	
	private synchronized void createContent(){
		CellStyle cellStyle = createContentStyle() ;
		if(table!=null && table.getTableproperty()!=null){
			for(Map<String , Object> value:values){
				Row row2 = sheet.createRow(rowNum);
				for(TableProperties tp : table.getTableproperty()){
					Cell cell2 = row2.createCell(table.getTableproperty().indexOf(tp)); 
					cell2.setCellStyle(cellStyle); 
					if(value.get(tp.getFieldname())!=null){
						if(tp.isSeldata()){
							SysDic sysDic = UKeFuDic.getInstance().getDicItem(String.valueOf(value.get(tp.getFieldname()))) ;
							cell2.setCellValue(new HSSFRichTextString(sysDic.getName()));
						}else{
							cell2.setCellValue(new HSSFRichTextString(String.valueOf(value.get(tp.getFieldname()))));
						}
					}
				}
				rowNum ++ ;
			}
		}
	}
	
	
	private CellStyle baseCellStyle(){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 

		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
				
		cellStyle.setWrapText(true);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font font = wb.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setFontName("宋体"); 
		font.setFontHeight((short) 200); 
		cellStyle.setFont(font); 
		
		return cellStyle;
	}
}
