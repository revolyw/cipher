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

/**
 * Excel处理工具
 * Created by Willow on 16/11/16.
 */
public class ExcelUtil {
    /**
     * 从文件路径中读取Excel，输出List
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<List<String>> readExcelFile(String filePath, int columnNum) throws IOException {
        return readExcelFile(new FileInputStream(filePath),columnNum);
    }

    /**
     * 从输入流中读取Excel，输出List
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static List<List<String>> readExcelFile(InputStream inputStream, int columNum) throws IOException {
        POIFSFileSystem pfs = new POIFSFileSystem(inputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(pfs);
        int currRow = 0;
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<List<String>> sheetList = new ArrayList<List<String>>();
        List<String> lineList;

        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            lineList = readLine(sheet, i,columNum);
            if (lineList != null) {
                sheetList.add(lineList);
            }
        }
        return sheetList;
    }

    /**
     * 读取Excel中的每一行，输出List
     *
     * @param sheet
     * @param row
     * @return
     */
    private static List<String> readLine(HSSFSheet sheet, int row, int columnNum) {
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
