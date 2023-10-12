package com.ruoyi.common.utils.excel;


import com.ruoyi.common.exception.ExcelException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelUtils {

    /**
     * 读取excle文件
     * 不含sheet页名称
     *
     * @param file   excel文件
     * @param rowNum 开始读取的行数的下标 从0开始
     * @param colNum 读取的列数的下标 从0开始
     * @return
     * @throws IOException
     */
    public static List<List<Map<String, String>>> readExcel(MultipartFile file, int rowNum, int colNum) throws IOException {
        List<List<Map<String, String>>> pageList = new ArrayList<List<Map<String, String>>>();
        InputStream inputStream = null;
        //创建新excel文档
        inputStream = file.getInputStream();
        Workbook book = null;
        try {
            if (file.getOriginalFilename().contains(".xlsx")) {
                book = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().contains(".xls")) {
                book = new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new ExcelException("请使用 .xls或.xlsx 为后缀的excel 版本 或直接下载模板");
        }
        for (int pageNum = 0; pageNum < book.getNumberOfSheets(); pageNum++) {
            //获取指定索引的页
            Sheet sheet = book.getSheetAt(pageNum);
            if (sheet == null) {
                continue;
            }
            List<Map<String, String>> list = new ArrayList<>();
            for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> map = new HashMap<>();
                //根据索引获取具体的行,第一行为表头所以索引从1开始
                Row row = sheet.getRow(i);
                //如果这行对象不为空就进行读取
                if (row != null) {
                    for (int j = 0; j < colNum; j++) {
                        MergedResult mergedRegion = isMergedRegion(sheet, i, j);
                        if (mergedRegion.isMerged()) {
                            int firstRow = mergedRegion.getFirstRow();
                            int firstColumn = mergedRegion.getFirstColumn();
                            if (firstRow == i && firstColumn == j) {
                                Cell cell = row.getCell(j);
                                String cellStr = getExcelCellValue(cell);
                                map.put("cell" + j, cellStr);
                            } else if (firstRow == i && firstColumn != j) {
                                String cellStr = map.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            } else {
                                Map<String, String> mapCell = list.get(firstRow - rowNum);
                                String cellStr = mapCell.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            }
                        } else {
                            Cell cell = row.getCell(j);
                            String cellStr = getExcelCellValue(cell);
                            map.put("cell" + j, cellStr);
                        }
                    }
                    map.put("cell" + colNum, i + "");
                }
                list.add(map);
            }
            pageList.add(list);
        }
        return pageList;
    }

    /**
     * 读取excle文件
     * 含sheet页名称
     *
     * @param file   excel文件
     * @param rowNum 开始读取的行数的下标 从0开始
     * @param colNum 读取的列数
     * @return cell+i 存放单元格值 i从0开始；cell_index 存放 行数从0开始
     * @throws IOException
     */

    public static List<ExcelDataInfo> readExcelInfo(MultipartFile file, int rowNum, int colNum) throws IOException {

        List<ExcelDataInfo> pageList = new ArrayList<ExcelDataInfo>();
        InputStream inputStream = null;
        //创建新excel文档
        inputStream = file.getInputStream();
        Workbook book = null;
        try {
            if (file.getOriginalFilename().contains(".xlsx")) {
                book = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().contains(".xls")) {
                book = new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new ExcelException("请使用 .xls或.xlsx 为后缀的excel 版本 或直接下载模板");
        }
        for (int pageNum = 0; pageNum < book.getNumberOfSheets(); pageNum++) {
            //获取指定索引的页
            Sheet sheet = book.getSheetAt(pageNum);
            if (sheet == null) {
                continue;
            }
            ExcelDataInfo excelDataInfo = new ExcelDataInfo();
            String sheetName = sheet.getSheetName();
            List<Map<String, String>> list = new ArrayList<>();
            for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> map = new HashMap<>();
                //根据索引获取具体的行,第一行为表头所以索引从1开始
                Row row = sheet.getRow(i);
                //如果这行对象不为空就进行读取
                if (row != null) {
                    for (int j = 0; j < colNum; j++) {
                        MergedResult mergedRegion = isMergedRegion(sheet, i, j);
                        if (mergedRegion.isMerged()) {
                            int firstRow = mergedRegion.getFirstRow();
                            int firstColumn = mergedRegion.getFirstColumn();
                            if (firstRow == i && firstColumn == j) {
                                Cell cell = row.getCell(j);
                                String cellStr = getExcelCellValue(cell);
                                map.put("cell" + j, cellStr);
                            } else if (firstRow == i && firstColumn != j) {
                                String cellStr = map.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            } else {
                                Map<String, String> mapCell = list.get(firstRow - rowNum);
                                String cellStr = mapCell.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            }
                        } else {
                            Cell cell = row.getCell(j);
                            String cellStr = getExcelCellValue(cell);
                            map.put("cell" + j, cellStr);
                        }
                    }
                    map.put("cell_index", i + "");
                }
                list.add(map);
            }
            excelDataInfo.setSheetName(sheetName);
            excelDataInfo.setDataList(list);
            pageList.add(excelDataInfo);
        }
        return pageList;
    }

    /**
     * 读取本地excle文件
     * 含sheet页名称
     *
     * @param file   excel文件
     * @param rowNum 开始读取的行数的下标 从0开始
     * @param colNum 读取的列数
     * @return cell+i 存放单元格值 i从0开始；cell_index 存放 行数从0开始
     * @throws IOException
     */

    public static List<ExcelDataInfo> readExcelLocalhostInfo(File file, int rowNum, int colNum) throws IOException {
        List<ExcelDataInfo> pageList = new ArrayList<ExcelDataInfo>();
        InputStream inputStream = null;
        //创建新excel文档
        Workbook book = null;
        try {
            inputStream = new FileInputStream(file);
            if (file.getName().contains(".xlsx")) {
                book = new XSSFWorkbook(inputStream);
            } else if (file.getName().contains(".xls")) {
                book = new HSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new ExcelException("请使用 .xls或.xlsx 为后缀的excel 版本 或直接下载模板");
        }
        for (int pageNum = 0; pageNum < book.getNumberOfSheets(); pageNum++) {
            //获取指定索引的页
            Sheet sheet = book.getSheetAt(pageNum);
            if (sheet == null) {
                continue;
            }
            ExcelDataInfo excelDataInfo = new ExcelDataInfo();
            String sheetName = sheet.getSheetName();
            List<Map<String, String>> list = new ArrayList<>();
            for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> map = new HashMap<>();
                //根据索引获取具体的行,第一行为表头所以索引从1开始
                Row row = sheet.getRow(i);
                //如果这行对象不为空就进行读取
                if (row != null) {
                    for (int j = 0; j < colNum; j++) {
                        MergedResult mergedRegion = isMergedRegion(sheet, i, j);
                        if (mergedRegion.isMerged()) {
                            int firstRow = mergedRegion.getFirstRow();
                            int firstColumn = mergedRegion.getFirstColumn();
                            if (firstRow == i && firstColumn == j) {
                                Cell cell = row.getCell(j);
                                String cellStr = getExcelCellValue(cell);
                                map.put("cell" + j, cellStr);
                            } else if (firstRow == i && firstColumn != j) {
                                String cellStr = map.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            } else {
                                Map<String, String> mapCell = list.get(firstRow - rowNum);
                                String cellStr = mapCell.get("cell" + firstColumn);
                                map.put("cell" + j, cellStr);
                            }
                        } else {
                            Cell cell = row.getCell(j);
                            String cellStr = getExcelCellValue(cell);
                            map.put("cell" + j, cellStr);
                        }
                    }
                    map.put("cell_index", i + "");
                }
                list.add(map);
            }
            excelDataInfo.setSheetName(sheetName);
            excelDataInfo.setDataList(list);
            pageList.add(excelDataInfo);
        }
        return pageList;
    }


    //获取excel单元格中的值
    public static String getExcelCellValue(Cell cell) {
        String ret = "";
        try {
            if (cell == null) {
                ret = "";
            } else if (cell.getCellType() == CellType.STRING) {
                ret = cell.getStringCellValue().trim();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                short format = cell.getCellStyle().getDataFormat();
                if (14 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "yyyy-MM-dd");
                } else if (31 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "yyyy年MM月dd日");
                } else if (57 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "yyyy年MM月");
                } else if (58 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "MM月dd日");
                } else if (20 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "HH:mm");
                } else if (20 == format) {
                    Date date = cell.getDateCellValue();
                    ret = DateFormatUtils.format(date, "HH时mm分");
                } else {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        ret = DateFormatUtils.format(date, "yyyy-MM-dd");
                    } else {
                        ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                        String temp = ret.substring(ret.indexOf(".") + 1, ret.length());
                        try {
                            if (Integer.parseInt(temp) == 0) {
                                ret = ret.substring(0, ret.indexOf("."));
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

            } else if (cell.getCellType() == CellType.FORMULA) {//有公式的Excel单元格
                //这样对于字符串cell.getStringCellValue()方法即可取得其值，如果公式生成的是数值，使用cell.getStringCellValue()方法会抛出IllegalStateException异常，在异常处理中使用cell.getNumericCellValue();即可。
                try {
                    ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                    String temp = ret.substring(ret.indexOf(".") + 1, ret.length());
                    try {
                        if (Integer.parseInt(temp) == 0) {
                            ret = ret.substring(0, ret.indexOf("."));
                        }
                    } catch (Exception ex) {
                    }
                } catch (IllegalStateException e) {
                    ret = String.valueOf(cell.getStringCellValue());
                    String temp = ret.substring(ret.indexOf(".") + 1, ret.length());
                    try {
                        if (Integer.parseInt(temp) == 0) {
                            ret = ret.substring(0, ret.indexOf("."));
                        }
                    } catch (Exception ex) {
                    }
                }
            } else if (cell.getCellType() == CellType.ERROR) {
                ret = "" + cell.getErrorCellValue();
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                ret = "" + cell.getBooleanCellValue();
            } else if (cell.getCellType() == CellType.BLANK) {
                ret = "";
            } else {
                System.out.println(cell.getCellType());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = "";
        }

        return ret.trim();
    }

    // 判断是否是合并单元格
    public static MergedResult isMergedRegion(Sheet sheet, int row, int column) {
        MergedResult mergedResult = new MergedResult();
        boolean isMerged = false;//判断是否合并单元格

        mergedResult.setRowIndex(row);//判断的行
        mergedResult.setColumnIndex(column);//判断的列
        //获取sheet中有多少个合并单元格
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            // 获取合并后的单元格
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow //判断行
                    && column >= firstColumn && column <= lastColumn) {//判断列
                isMerged = true;
                mergedResult.setFirstRow(firstRow);
                mergedResult.setLastRow(lastRow);
                mergedResult.setFirstColumn(firstColumn);
                mergedResult.setLastColumn(lastColumn);
                mergedResult.setRowMergeNum(lastRow - firstRow + 1);
                mergedResult.setColumnMergeNum(lastColumn - firstColumn + 1);
                break;
            }
        }
        mergedResult.setMerged(isMerged);
        return mergedResult;
    }

}
