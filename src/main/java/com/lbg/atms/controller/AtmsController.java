package com.lbg.atms.controller;

import com.lbg.atms.model.AtmResponse;
import com.lbg.atms.service.AtmsService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("atms")
@OpenAPIDefinition(info = @Info(title = "Lloyds ATM open APIs"))
public class AtmsController {

    private final AtmsService atmsService;

    public AtmsController(AtmsService atmsService) {
        this.atmsService = atmsService;
    }

    @GetMapping
    @Operation(operationId = "getAtms", summary = "Get Lloyds bank ATMs",
               responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Invalid URI"),
                    @ApiResponse(responseCode = "500", description = "Error while fetching the ATMs information")
    })
    public ResponseEntity<AtmResponse> getAtms() {
        return atmsService.getAtms();
    }

    @GetMapping("/{identification}")
    @Operation(operationId = "getAtm", summary = "Get Lloyds bank ATM by provided identification code",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "identification", description = "ATM identification code") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Invalid ATM identification code or URI"),
                    @ApiResponse(responseCode = "500", description = "Error while fetching the ATM information")
    })
    public ResponseEntity<AtmResponse> getAtm(@PathVariable String identification) {
        return atmsService.getAtm(identification);
    }
}
