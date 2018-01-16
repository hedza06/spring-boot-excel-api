package com.springboot.excelapi.resources;

import com.springboot.excelapi.dto.DemoDTO;
import com.springboot.excelapi.dto.ExampleDTO;
import com.springboot.excelapi.services.ExcelService;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Description(value = "Resource layer for handling REST requests.")
@RestController
@RequestMapping("api")
public class ExcelResource {

    private ExcelService excelService;

    /**
     * Constructor / dependency injector
     * @param excelService - service layer dependency.
     */
    public ExcelResource(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping(value = "/known-cells", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExampleDTO>> mapExcelRowsToObject() throws IOException
    {
        List<ExampleDTO> exampleDTOList = this.excelService.readFromExcelWithKnownObject();
        return new ResponseEntity<>(exampleDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/specific-cells", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DemoDTO>> mapSpecificCellsToObject() throws IOException
    {
        List<DemoDTO> demoDTOList = this.excelService.readSpecificCellsFromExcel();
        return new ResponseEntity<>(demoDTOList, HttpStatus.OK);
    }
}
