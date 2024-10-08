package com.project.toy_log_validator.utils;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class WorkbookUtil {
    public static int[] getDimensions(CSVReader reportSheet) throws CsvValidationException, IOException {
        int rowIdx = 1;
        int colIdx = reportSheet.readNext().length;
        
        String[] nextLine = reportSheet.readNext();
        while(nextLine != null) {
            rowIdx +=1;

            nextLine = reportSheet.readNext();
        }

        int[] reportSize = new int[2];
        reportSize[0] = rowIdx;
        reportSize[1] = colIdx;

        return reportSize;
    }
}
