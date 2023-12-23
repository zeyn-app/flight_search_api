package com.zeynapp.amadeusTravelToFuture.controllers;

import com.zeynapp.amadeusTravelToFuture.dto.BaseResponse;
import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportRequest;
import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportResponse;
import com.zeynapp.amadeusTravelToFuture.services.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<BaseResponse<List<AirportResponse>>> getAll(){
        List<AirportResponse> responseList = airportService.getAll();

        BaseResponse<List<AirportResponse>> response = BaseResponse.<List<AirportResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(responseList)
                .isSuccess(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<AirportResponse>> create(@RequestBody AirportRequest airportRequest){
        AirportResponse airportResponse = airportService.create(airportRequest);

        BaseResponse<AirportResponse> baseResponse = BaseResponse.<AirportResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(airportResponse)
                .isSuccess(true)
                .build();

        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Long id){
        airportService.delete(id);
        System.out.println("Airport removed successfully");
    }
}