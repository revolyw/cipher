package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel处理工具
 * Created by Willow on 16/11/16.
 */
public class ExcelUtil {
    /**
     * 从文件路径中读取Excel，输出List
     * 根据文件后缀选取读取方法
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<List<String>> readExcelFile(String filePath, int columnNum) throws IOException {
        if (filePath.endsWith(".xls"))
            return readXlsFile(new FileInputStream(filePath), columnNum);
        if (filePath.endsWith(".xlsx"))
            return readXlsxFile(new FileInputStream(filePath), columnNum);
        return null;
    }

    /**
     * 从输入流中读取xlsx格式Excel，输出List
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static List<List<String>> readXlsxFile(InputStream inputStream, int columnNum) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        int currRow = 0;
        List<List<String>> sheetList = new ArrayList<List<String>>();
        List<String> lineList;

        int rowNum = xssfSheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            lineList = readLineForXlsx(xssfSheet, i, columnNum);
            if (lineList != null) {
                sheetList.add(lineList);
            }
        }
        return sheetList;
    }

    /**
     * 读取Excel中的每一行，输出List
     * 针对xlsx文件
     *
     * @param sheet
     * @param row
     * @return
     */
    private static List<String> readLineForXlsx(XSSFSheet sheet, int row, int columnNum) {
        columnNum = columnNum > 0 ? columnNum : 20;//默认读取20列
        XSSFRow rowLine = sheet.getRow(row);
        if (rowLine == null)
            return null;
        List<String> lineList = new ArrayList<String>();
        for (int i = 0; i < columnNum; i++) {
            XSSFCell cell = rowLine.getCell((short) i);
            if (cell == null) {
                lineList.add("");
            } else {
                int cellType = cell.getCellType();
                if (cellType == XSSFCell.CELL_TYPE_STRING) {//字符串类型
                    lineList.add(cell.getStringCellValue().trim());
                } else if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {//数字类型
                    lineList.add(String.valueOf(cell.getNumericCellValue()));
                } else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {//布尔类型
                    lineList.add(String.valueOf(cell.getBooleanCellValue()));
                } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {//公式类型
                    lineList.add(String.valueOf(cell.getNumericCellValue()));
                } else {//CELL_TYPE_BLANK, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                    lineList.add("");
                }
            }
        }

        return lineList;
    }

    /**
     * 从输入流中读取xls格式Excel，输出List
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static List<List<String>> readXlsFile(InputStream inputStream, int columnNum) throws IOException {
        POIFSFileSystem pfs = new POIFSFileSystem(inputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(pfs);
        int currRow = 0;
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<List<String>> sheetList = new ArrayList<List<String>>();
        List<String> lineList;

        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            lineList = readLineForXls(sheet, i, columnNum);
            if (lineList != null) {
                sheetList.add(lineList);
            }
        }
        return sheetList;
    }

    /**
     * 读取Excel中的每一行，输出List
     * 针对xls文件
     *
     * @param sheet
     * @param row
     * @return
     */
    private static List<String> readLineForXls(HSSFSheet sheet, int row, int columnNum) {
        columnNum = columnNum > 0 ? columnNum : 20;//默认读取20列
        HSSFRow rowLine = sheet.getRow(row);
        if (rowLine == null)
            return null;
        List<String> lineList = new ArrayList<String>();
        for (int i = 0; i < columnNum; i++) {
            HSSFCell cell = rowLine.getCell((short) i);
            if (cell == null) {
                lineList.add("");
            } else {
                int cellType = cell.getCellType();
                if (cellType == HSSFCell.CELL_TYPE_STRING) {
                    lineList.add(cell.getStringCellValue().trim());
                } else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                    lineList.add(String.valueOf(cell.getNumericCellValue()));
                } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                    lineList.add(String.valueOf(cell.getBooleanCellValue()));
                } else {
                    lineList.add("");
                }
            }
        }

        return lineList;
    }
}
