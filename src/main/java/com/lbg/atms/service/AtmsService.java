package com.lbg.atms.service;

import com.lbg.atms.client.RestClient;
import com.lbg.atms.model.AtmResponse;
import com.lbg.atms.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AtmsService {

    Logger logger = LoggerFactory.getLogger(AtmsService.class);

    @Value("{lbg.atms.request.uri}")
    String uri;

    private final RestClient restClient;

    public AtmsService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<AtmResponse> getAtms() {
        logger.info("Entered getAtms method");
        ResponseEntity<AtmResponse> response;
        AtmResponse atmResponse = new AtmResponse();
        try {
            Map<String, Object> apiResponse = (Map<String, Object>) restClient.getObject(uri, Map.class);
            atmResponse.setResponse(apiResponse);
            response = new ResponseEntity<AtmResponse>(atmResponse, HttpStatus.OK);
        } catch (Exception exception) {
            atmResponse.setError(getErrorObject("An Error occurred while getting the atms"));
            response = new ResponseEntity<AtmResponse>(atmResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Service returned with response code: {}",response.getStatusCodeValue());
        return response;
    }

    public ResponseEntity<AtmResponse> getAtm(String identification) {
        logger.info("Entered getAtm method with identification value: {}",identification);
        ResponseEntity<AtmResponse> response;
        AtmResponse atmResponse = new AtmResponse();
        Map<String,Object> apiResponse = (Map<String, Object>) restClient.getObject(uri,Map.class);
        Map<String, Object> atm = getAtmObject(identification,apiResponse);
        if(null == atm) {
            atmResponse.setError(getErrorObject("ATM not found with given identification number"));
            response = new ResponseEntity<AtmResponse>(atmResponse, HttpStatus.NOT_FOUND);
        } else {
            atmResponse.setResponse(atm);
            response = new ResponseEntity<AtmResponse>(atmResponse, HttpStatus.OK);
        }
        logger.info("Service returned with response code: {}",response.getStatusCodeValue());
        return response;
    }

    private Error getErrorObject(String message) {
        Error error = new Error();
        error.setMessage(message);
        return error;
    }

    private Map<String, Object> getAtmObject(String identification, Map<String, Object> apiResponse) {
        List<Map<String,Object>> data = (List<Map<String, Object>>) apiResponse.get("data")   ;
        List<Object> brands = data.stream().map(brand-> brand.get("Brand")).collect(Collectors.toList());
        List<Object> brand = (List<Object>) brands.get(0);
        Map<String, Object> brandDetails = (Map<String, Object>) brand.get(0);
        List<Map<String, Object>> atms = (List<Map<String, Object>>) brandDetails.get("ATM");
        Map<String, Object> atm = atms.stream()
                .filter(obj -> obj.get("Identification").toString().equalsIgnoreCase(identification))
                .findFirst()
                .orElse(null);
        return atm;
    }
}
