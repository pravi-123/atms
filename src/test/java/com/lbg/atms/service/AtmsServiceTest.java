package com.lbg.atms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.atms.client.RestClient;
import com.lbg.atms.model.AtmResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

public class AtmsServiceTest {

    @Mock
    private RestClient restClient;

    private AtmsService atmsService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String CONTENT = "{\"meta\":{\"LastUpdated\":\"2022-01-07T11:11:28.045Z\",\"TotalResults\":1918,\"Agreement\":\"Use of the APIs and any related data will be subject to the terms of the Open Licence and subject to terms and conditions\",\"License\":\"https://www.openbanking.org.uk/open-licence\",\"TermsOfUse\":\"https://www.openbanking.org.uk/terms\"},\"data\":[{\"Brand\":[{\"BrandName\":\"Lloyds Bank\",\"ATM\":[{\"Identification\":\"LFFFBC11\",\"SupportedCurrencies\":[\"GBP\"],\"Location\":{\"PostalAddress\":{\"AddressLine\":[\"1 VICTORIA ROAD;\"],\"StreetName\":\"1 VICTORIA ROAD\",\"TownName\":\"CONSETT\",\"CountrySubDivision\":[\"COUNTY DURHAM\"],\"Country\":\"GB\",\"PostCode\":\"DH8 5AE\"}}},{\"Identification\":\"LFFFAC11\",\"SupportedCurrencies\":[\"GBP\"],\"Location\":{\"PostalAddress\":{\"AddressLine\":[\"1 VICTORIA ROAD;\"],\"StreetName\":\"1 VICTORIA ROAD\",\"TownName\":\"CONSETT\",\"CountrySubDivision\":[\"COUNTY DURHAM\"],\"Country\":\"GB\",\"PostCode\":\"DH8 5AE\"}}}]}]}]}";

    private static final String ATM = "{\"Identification\":\"LFFFBC11\",\"SupportedCurrencies\":[\"GBP\"],\"Location\":{\"PostalAddress\":{\"AddressLine\":[\"1 VICTORIA ROAD;\"],\"StreetName\":\"1 VICTORIA ROAD\",\"TownName\":\"CONSETT\",\"CountrySubDivision\":[\"COUNTY DURHAM\"],\"Country\":\"GB\",\"PostCode\":\"DH8 5AE\"}}}";

    @BeforeEach
    void init() throws JsonProcessingException {
        restClient = mock(RestClient.class);
        when(restClient.getObject(anyString(),any())).thenReturn(getAtmsObject());
        atmsService = new AtmsService(restClient);
        ReflectionTestUtils.setField(atmsService, "uri", "https://api.lloydsbank.com/open-banking/v2.2/atms");

    }

    @Test
    public void testGetAtms() throws JsonProcessingException {
        ResponseEntity<AtmResponse> response = atmsService.getAtms();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(CONTENT, objectMapper.writeValueAsString(response.getBody().getResponse()));
    }

    @Test
    public void testGetAtm200() throws JsonProcessingException {
        ResponseEntity<AtmResponse> response = atmsService.getAtm("LFFFBC11");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ATM, objectMapper.writeValueAsString(response.getBody().getResponse()));
    }

    @Test
    public void testGetAtm400() throws JsonProcessingException {
        ResponseEntity<AtmResponse> response = atmsService.getAtm("LFFFBA11");
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("ATM not found with given identification number",
                response.getBody().getError().getMessage());
    }

    private Map getAtmsObject() throws JsonProcessingException {
        Map atms = objectMapper.readValue(CONTENT, Map.class);
        return atms;
    }


}
