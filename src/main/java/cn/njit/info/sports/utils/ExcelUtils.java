/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ExcelUtils
 * Author:   Administrator
 * Date:     2019/5/2 7:45
 */
package cn.njit.info.sports.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 解析Excel工具类
 *
 * @author Liuzw
 * @since 2019/5/2
 */
public class ExcelUtils {
  private ExcelUtils() {
  }

  //日志
  private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

  /**
   * 解析Excel(跳过第一行  --表头)
   *
   * @param file MultipartFile
   * @return 把一个工作区的内容放进List, 每一行为数组内容
   */
  public static List<String[]> getExcelData(MultipartFile file) {
    boolean checkFile = checkFile(file);
    if (!checkFile)
      return Collections.emptyList();
    //获得Workbook工作薄对象
    Workbook workbook = getWorkBook(file);
    //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
    List<String[]> list = new ArrayList<>();
    if (workbook != null) {
      for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
        //获得当前sheet工作表
        Sheet sheet = workbook.getSheetAt(sheetNum);
        if (sheet == null) {
          continue;
        }
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        //循环除了第一行的所有行
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
          //获得当前行
          Row row = sheet.getRow(rowNum);
          if (row == null) {
            continue;
          }
          //获得当前行的开始列
          int firstCellNum = row.getFirstCellNum();
          //获得当前行的列数
          int lastCellNum = row.getLastCellNum();
          String[] cells = new String[row.getLastCellNum()];
          //循环当前行
          for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            cells[cellNum] = getCellValue(cell);
          }
          list.add(cells);
        }
      }
    }
    return list;
  }

  /**
   * 检查文件是否可用
   *
   * @param file 文件MultipartFile
   */
  public static boolean checkFile(MultipartFile file) {
    //判断文件是否存在
    if (null == file) {
      logger.error("文件不存在！");
      return false;
    }
    String filename = file.getOriginalFilename();
    String suffixName = (filename.substring(filename.lastIndexOf('.'))).toLowerCase();
    if ((!suffixName.equals(".xlsx") && !suffixName.equals(".xls")) || file.getSize() > 1048576) {
      logger.error("该文件不是Excel文件");
      return false;
    }
    return true;
  }

  /**
   * 获取工作表
   *
   * @param file file
   * @return Workbook
   */
  private static Workbook getWorkBook(MultipartFile file) {
    //获得文件名
    String fileName = file.getOriginalFilename();
    //创建Workbook工作薄对象，表示整个excel
    Workbook workbook = null;
    try {
      //获取excel文件的io流
      InputStream is = file.getInputStream();
      //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
      if (fileName.endsWith("xls")) {
        //2003
        workbook = new HSSFWorkbook(is);
      } else if (fileName.endsWith("xlsx")) {
        //2007 及2007以上
        workbook = new XSSFWorkbook(is);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    return workbook;
  }

  /**
   * 获取单元格数据
   *
   * @param cell 单元格
   * @return 单元格数据
   */
  private static String getCellValue(Cell cell) {
    String cellValue = "";
    if (cell == null) {
      return cellValue;
    }
    //判断数据的类型
    switch (cell.getCellTypeEnum()) {
      case NUMERIC: //数字
        cellValue = stringDateProcess(cell);
        break;
      case STRING: //字符串
        cellValue = String.valueOf(cell.getStringCellValue());
        break;
      case BOOLEAN: //Boolean
        cellValue = String.valueOf(cell.getBooleanCellValue());
        break;
      case FORMULA: //公式
        cellValue = String.valueOf(cell.getCellFormula());
        break;
      case BLANK: //空值
        cellValue = "";
        break;
      case ERROR: //故障
        cellValue = "非法字符";
        break;
      default:
        cellValue = "未知类型";
        break;
    }
    return cellValue;
  }

  /**
   * 处理时间格式
   *
   * @param cell 单元格
   * @return 单元格数据
   */
  private static String stringDateProcess(Cell cell) {
    String result;
    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
      SimpleDateFormat sdf = null;
      if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
        sdf = new SimpleDateFormat("HH:mm");
      } else {// 日期
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      }
      Date date = cell.getDateCellValue();
      result = sdf.format(date);
    } else if (cell.getCellStyle().getDataFormat() == 58) {
      // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      double value = cell.getNumericCellValue();
      Date date = org.apache.poi.ss.usermodel.DateUtil
          .getJavaDate(value);
      result = sdf.format(date);
    } else {
      double value = cell.getNumericCellValue();
      CellStyle style = cell.getCellStyle();
      DecimalFormat format = new DecimalFormat();
      String temp = style.getDataFormatString();
      // 单元格设置成常规
      if (temp.equals("General")) {
        format.applyPattern("#");
      }
      result = format.format(value);
    }
    return result;
  }

}