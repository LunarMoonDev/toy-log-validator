package com.project.toy_log_validator.service.impl;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.enums.Error;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.service.DataValidatorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataValidatorServiceImpl implements DataValidatorService {

    @Override
    public Validation dataValidation(double []reportSize, CSVReader reportSheet, String uuid) throws GenericException {
        log.info("X-Tracker: {} | validating data", uuid);
        log.debug("X-Tracker: {} | request id: {}", reportSheet);

        // int reportSize[] = WorkbookUtil.getDimensions(reportSheet);
        // int rowMaxIdx = reportSize[0];
        // int collMaxIdx = reportSize[1];

        // Row headerRow = reportSheet.getSheetAt(0).getRow(1);


        // for (int rollIdx = 1; rollIdx < rowMaxIdx; rollIdx++) {
        //     Row row = reportSheet.getSheetAt(0).getRow(1);

        //     for (int collIdx = 0; collIdx < collMaxIdx; collIdx++) {
        //         Cell cell = row.getCell(collIdx);
        //         Cell headerCell = headerRow.getCell(collIdx);

        //         if(cell == null || cell.getCellType().equals(BLANK)) {
        //             throw new GenericException(Error.DATA_EMP_ERROR);
        //         }

        //         if (headerCell.getStringCellValue().equals("DIRECT_HIT") ||
        //                 headerCell.getStringCellValue().equals("CRIT_HIT")) {
        //             if (!(cell.getCellType().equals(NUMERIC) && cell.getNumericCellValue() / 100 <= 1)) {
        //                 throw new GenericException(Error.DATA_PRC_ERROR);
        //             }
        //         } else if (headerCell.getStringCellValue().equals("DAMAGE")) {
        //             if(!(cell.getCellType().equals(NUMERIC) && cell.getNumericCellValue() % 10 > 1)) {
        //                 throw new GenericException(Error.DATA_DMG_ERROR);
        //             }
        //         } else if (cell.getCellType() != STRING) {
        //             throw new GenericException(Error.DATA_VAL_ERROR);
        //         }
        //     }
        // }

        

        return Validation.PASS;
    }

}
