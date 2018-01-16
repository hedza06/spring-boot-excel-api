package com.springboot.excelapi.integration;

import com.springboot.excelapi.Application;
import com.springboot.excelapi.dto.DemoDTO;
import com.springboot.excelapi.dto.ExampleDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "no-liquibase" })
public class ExcelResourceIntegrationTest {

    private static final String TARGET_API_KNOWN_CELLS    = "http://localhost:8080/api/known-cells";
    private static final String TARGET_API_SPECIFIC_CELLS = "http://localhost:8080/api/specific-cells";

    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void mapRowsFromExcelFileTestWithKnownCells()
    {
        HttpEntity requestEntity = new HttpEntity<>(Void.class);
        ParameterizedTypeReference<List<ExampleDTO>> typeRef = new ParameterizedTypeReference<List<ExampleDTO>>() {};

        ResponseEntity<List<ExampleDTO>> responseEntity = testRestTemplate.exchange(
                TARGET_API_KNOWN_CELLS,
                HttpMethod.GET,
                requestEntity,
                typeRef
        );

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        List<ExampleDTO> responseBody = responseEntity.getBody();
        assertThat(responseBody.size()).isGreaterThan(0);
        assertThat(responseBody.size()).isEqualTo(3);

        assertThat(responseBody.get(0).getFullName()).isEqualTo("Heril Muratovic");
    }

    @Test
    public void mapSpecificRows()
    {
        HttpEntity requestEntity = new HttpEntity<>(Void.class);
        ParameterizedTypeReference<List<DemoDTO>> typeReference = new ParameterizedTypeReference<List<DemoDTO>>() {};

        ResponseEntity<List<DemoDTO>> responseEntity = testRestTemplate.exchange(
                TARGET_API_SPECIFIC_CELLS,
                HttpMethod.GET,
                requestEntity,
                typeReference
                );

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        List<DemoDTO> responseBody = responseEntity.getBody();
        assertThat(responseBody.size()).isGreaterThan(0);
        assertThat(responseBody.size()).isEqualTo(2);
        assertThat(responseBody.get(0).getName()).isEqualTo("John Doe");
    }
}
