package com.springboot.excelapi.services;

import com.springboot.excelapi.dto.DemoDTO;
import com.springboot.excelapi.dto.ExampleDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Description(value = "Service layer responsible for processing data.")
@Service
public class ExcelService {

    /**
     * Method for reading from specific excel file when we know types and number of cells.
     *
     * @return list of mapped objects
     * @throws IOException - throws IO exception.
     */
    public List<ExampleDTO> readFromExcelWithKnownObject() throws IOException
    {
        // get file that needs to be mapped into object.
        Resource resource = new ClassPathResource("documents/sample.xlsx");
        FileInputStream inputStream = new FileInputStream(resource.getFile());

        // get workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ExampleDTO> exampleDTOList = new ArrayList<>();

        // iterate through rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();

            // skip heading row.
            if (currentRow.getRowNum() == 0) {
                continue;
            }

            // mapped to example object.
            ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setFullName(currentRow.getCell(0).getStringCellValue());
            exampleDTO.setAddress(currentRow.getCell(1).getStringCellValue());
            exampleDTO.setOrderDate(currentRow.getCell(2).getDateCellValue().toString());
            exampleDTO.setProduct(currentRow.getCell(3).getStringCellValue());
            exampleDTO.setQuantity(currentRow.getCell(4).getNumericCellValue());

            exampleDTOList.add(exampleDTO);
        }
        return exampleDTOList;
    }

    /**
     * Method for reading excel file from specific position.
     *
     * @return List of mapped demo objects.
     * @throws IOException - input / output exception.
     */
    public List<DemoDTO> readSpecificCellsFromExcel() throws IOException
    {
        // get file that needs to be mapped into object.
        Resource resource = new ClassPathResource("documents/table.xlsx");
        FileInputStream inputStream = new FileInputStream(resource.getFile());

        // get workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<DemoDTO> demoList = new ArrayList<>();

        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();
            if (currentRow.getRowNum() == 7) {
                continue;
            }

            // generate demo object.
            DemoDTO demoDTO = new DemoDTO();
            demoDTO.setName(currentRow.getCell(4).getStringCellValue());
            demoDTO.setAddress(currentRow.getCell(5).getStringCellValue());
            demoDTO.setAge(currentRow.getCell(6).getNumericCellValue());

            demoList.add(demoDTO);
        }
        return demoList;
    }
}
