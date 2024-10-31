package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

// [Excel 파일 처리 유틸]
public interface ExcelFileUtil {
    // (액셀 파일을 읽어서 데이터 반환)
    // 파일 내 모든 시트, 모든 행열 데이터 반환
    // 반환값 : [시트번호][행번호][컬럼번호] == 셀값
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Map<@Valid @NotNull String, @Valid @NotNull List<@Valid @NotNull List<@Valid @NotNull String>>> readExcel(InputStream excelFile) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException;

    // 시트, 행열 제한
    // 반환값 : [행번호][컬럼번호] == 셀값, 없는 시트번호라면 null 반환
    List<@Valid @NotNull List<@Valid @NotNull String>> readExcel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InputStream excelFile,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer sheetIdx,          // 가져올 시트 인덱스 (0부터 시작)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer rowRangeStartIdx,   // 가져올 행 범위 시작 인덱스 (0부터 시작)
            Integer rowRangeEndIdx, // 가져올 행 범위 끝 인덱스 null 이라면 전부 (0부터 시작)
            List<@Valid @NotNull Integer> columnRangeIdxList, // 가져올 열 범위 인덱스 리스트 null 이라면 전부 (0부터 시작)
            Integer minColumnLength // 결과 컬럼의 최소 길이 (길이를 넘으면 그대로, 미만이라면 "" 로 채움)
    ) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException;

    // (액셀 파일생성)
    // inputExcelSheetDataMap : [시트이름][행번호][컬럼번호] == 셀값
    void writeExcel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            FileOutputStream fileOutputStream,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<@Valid @NotNull String, @Valid @NotNull List<@Valid @NotNull List<@Valid @NotNull String>>> inputExcelSheetDataMap
    ) throws IOException;
}
