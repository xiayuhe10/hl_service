package com.ruoyi.common.utils.excel;

public class MergedResult {
    boolean isMerged;//是否合并单元格
    int rowIndex;//行下标
    int columnIndex;//列下标
    int firstRow;//合并的行 开始下标
    int lastRow;//合并的行 结束下标
    int firstColumn;//合并的列 开始下标
    int lastColumn;//合并的列 结束下标
    int rowMergeNum;//合并的行数
    int columnMergeNum;//合并的列数

    public boolean isMerged() {
        return isMerged;
    }

    public void setMerged(boolean merged) {
        isMerged = merged;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(int firstColumn) {
        this.firstColumn = firstColumn;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(int lastColumn) {
        this.lastColumn = lastColumn;
    }

    public int getRowMergeNum() {
        return rowMergeNum;
    }

    public void setRowMergeNum(int rowMergeNum) {
        this.rowMergeNum = rowMergeNum;
    }

    public int getColumnMergeNum() {
        return columnMergeNum;
    }

    public void setColumnMergeNum(int columnMergeNum) {
        this.columnMergeNum = columnMergeNum;
    }
}
