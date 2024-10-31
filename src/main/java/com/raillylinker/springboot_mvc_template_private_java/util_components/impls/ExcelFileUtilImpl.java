package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.util_components.ExcelFileUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelFileUtilImpl implements ExcelFileUtil {
    // (액셀 파일을 읽어서 데이터 반환)
    // 파일 내 모든 시트, 모든 행열 데이터 반환
    // 반환값 : [시트번호][행번호][컬럼번호] == 셀값
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Map<@Valid @NotNull String, @Valid @NotNull List<@Valid @NotNull List<@Valid @NotNull String>>> readExcel(InputStream excelFile) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Map<String, List<List<String>>> resultObject = new HashMap<>();

        try (@Valid @NotNull @org.jetbrains.annotations.NotNull OPCPackage opc = OPCPackage.open(excelFile)) {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            XSSFReader xssfReader = new XSSFReader(opc);
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            var styles = xssfReader.getStylesTable();
            var strings = new ReadOnlySharedStringsTable(opc);

            while (sheets.hasNext()) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                ExcelSheetHandler sheetHandler = new ExcelSheetHandler(0, null, null, null);

                try (@Valid @NotNull @org.jetbrains.annotations.NotNull InputStream sheet = sheets.next()) {
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    InputSource inputSource = new InputSource(sheet);
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                    saxParserFactory.setNamespaceAware(true);
                    var parser = saxParserFactory.newSAXParser();
                    var xmlReader = parser.getXMLReader();
                    xmlReader.setContentHandler(handle);
                    xmlReader.parse(inputSource);
                }
                resultObject.put(sheets.getSheetName(), sheetHandler.sheet());
            }
        }
        return resultObject;
    }

    @Override
    public List<@Valid @NotNull List<@Valid @NotNull String>> readExcel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InputStream excelFile,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer sheetIdx,          // 가져올 시트 인덱스 (0부터 시작)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer rowRangeStartIdx,   // 가져올 행 범위 시작 인덱스 (0부터 시작)
            Integer rowRangeEndIdx, // 가져올 행 범위 끝 인덱스 null 이라면 전부 (0부터 시작)
            List<@Valid @NotNull Integer> columnRangeIdxList, // 가져올 열 범위 인덱스 리스트 null 이라면 전부 (0부터 시작)
            Integer minColumnLength // 결과 컬럼의 최소 길이 (길이를 넘으면 그대로, 미만이라면 "" 로 채움)
    ) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        List<List<String>> resultObject = null;

        try (@Valid @NotNull @org.jetbrains.annotations.NotNull OPCPackage opc = OPCPackage.open(excelFile)) {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            XSSFReader xssfReader = new XSSFReader(opc);
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            var styles = xssfReader.getStylesTable();
            var strings = new ReadOnlySharedStringsTable(opc);

            int currentSheetIdx = 0;
            while (sheets.hasNext()) {
                try (@Valid @NotNull @org.jetbrains.annotations.NotNull InputStream sheet = sheets.next()) {
                    if (sheetIdx == currentSheetIdx++) {
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        ExcelSheetHandler sheetHandler = new ExcelSheetHandler(
                                rowRangeStartIdx, rowRangeEndIdx, columnRangeIdxList, minColumnLength);
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        InputSource inputSource = new InputSource(sheet);
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                        saxParserFactory.setNamespaceAware(true);
                        var parser = saxParserFactory.newSAXParser();
                        var xmlReader = parser.getXMLReader();
                        xmlReader.setContentHandler(handle);
                        xmlReader.parse(inputSource);
                        resultObject = sheetHandler.sheet();
                    }
                }
            }
        }
        return resultObject;
    }

    // (액셀 파일생성)
    // inputExcelSheetDataMap : [시트이름][행번호][컬럼번호] == 셀값
    @Override
    public void writeExcel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            FileOutputStream fileOutputStream,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<@Valid @NotNull String, @Valid @NotNull List<@Valid @NotNull List<@Valid @NotNull String>>> inputExcelSheetDataMap
    ) throws IOException {
        try (@Valid @NotNull @org.jetbrains.annotations.NotNull XSSFWorkbook workbook = new XSSFWorkbook()) {
            inputExcelSheetDataMap.forEach((sheetName, rowList) -> {
                var xssfSheet = workbook.createSheet(sheetName);
                for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
                    var row = xssfSheet.createRow(rowIndex);
                    List<String> columnList = rowList.get(rowIndex);
                    for (int columnIndex = 0; columnIndex < columnList.size(); columnIndex++) {
                        row.createCell(columnIndex).setCellValue(columnList.get(columnIndex));
                    }
                }
            });
            workbook.write(fileOutputStream);
        }
    }

    // 중첩 클래스: ExcelSheetHandler
    public static class ExcelSheetHandler implements XSSFBSheetHandler.SheetContentsHandler {
        public ExcelSheetHandler(int rowRangeStartIdx, Integer rowRangeEndIdx, List<Integer> columnRangeIdxList, Integer minColumnLength) {
            if (rowRangeStartIdx < 0 || (rowRangeEndIdx != null && rowRangeEndIdx < 0) ||
                    (rowRangeEndIdx != null && rowRangeStartIdx > rowRangeEndIdx)) {
                throw new RuntimeException("Invalid row range indices.");
            }
            this.rowRangeStartIdx = rowRangeStartIdx;
            this.rowRangeEndIdx = rowRangeEndIdx;
            this.columnRangeIdxList = columnRangeIdxList;
            this.minColumnLength = minColumnLength;
        }

        private final int rowRangeStartIdx;
        private final Integer rowRangeEndIdx;
        private final List<Integer> columnRangeIdxList;
        private final Integer minColumnLength;
        private final List<List<String>> sheet = new ArrayList<>();
        private final List<String> row = new ArrayList<>();
        private int currentRowIdx = -1;
        private int currentColumnIdx = -1;
        private boolean nowOutRowRange = true;

        @Override
        public void startRow(int rowNum) {
            currentRowIdx = rowNum;
            currentColumnIdx = -1;
            nowOutRowRange = rowRangeStartIdx > currentRowIdx ||
                    (rowRangeEndIdx != null && rowRangeEndIdx < currentRowIdx);
        }

        @Override
        public void cell(String cellName, String cellValue, XSSFComment comment) {
            if (nowOutRowRange) return;

            int columnIdx = new CellReference(cellName).getCol();
            int nowAddRowIdx = currentColumnIdx + 1;
            currentColumnIdx = columnIdx;

            for (int idx = nowAddRowIdx; idx < columnIdx; idx++) {
                if (columnRangeIdxList == null || columnRangeIdxList.contains(idx)) {
                    row.add("");
                }
            }
            if (columnRangeIdxList == null || columnRangeIdxList.contains(columnIdx)) {
                row.add(cellValue);
            }
        }

        @Override
        public void endRow(int rowNum) {
            if (!nowOutRowRange) {
                if (minColumnLength != null && row.size() < minColumnLength) {
                    for (int idx = row.size(); idx < minColumnLength; idx++) {
                        row.add("");
                    }
                }
                sheet.add(new ArrayList<>(row));
                row.clear();
            }
        }

        public List<List<String>> sheet() {
            return sheet;
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

        @Override
        public void hyperlinkCell(String arg0, String arg1, String arg2, String arg3, XSSFComment arg4) {
        }
    }
}
