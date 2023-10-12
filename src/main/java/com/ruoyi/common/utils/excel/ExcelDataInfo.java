package com.ruoyi.common.utils.excel;

import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class ExcelDataInfo {
    private  String sheetName;
    private List<Map<String, String>> dataList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }
}
