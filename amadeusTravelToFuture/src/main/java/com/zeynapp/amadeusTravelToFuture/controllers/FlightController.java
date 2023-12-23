package com.zeynapp.amadeusTravelToFuture.controllers;

import com.zeynapp.amadeusTravelToFuture.dto.BaseResponse;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightRequest;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightResponse;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightUpdateRequest;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.SearchFlightResponse;
import com.zeynapp.amadeusTravelToFuture.services.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1/flights")
@AllArgsConstructor
public class FlightController {

    private final FlightService flightService;
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getAll() {
        List<FlightResponse> responseList = flightService.getAll();

        BaseResponse<List<FlightResponse>> baseResponse = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(responseList)
                .isSuccess(true)
                .build();

        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<SearchFlightResponse>>> search(@RequestParam(value = "from") String from,
                                                            @RequestParam(value = "to") String to,
                                                            @RequestParam(value = "departureDate") LocalDateTime departureDateTime,
                                                            @RequestParam(value = "returnDate", required = false) LocalDateTime returnDateTime) {
        List<SearchFlightResponse> responseList = flightService.search(from, to, departureDateTime, returnDateTime);

        BaseResponse<List<SearchFlightResponse>> baseResponse = BaseResponse.<List<SearchFlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(responseList)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<FlightResponse>> create(@Valid @RequestBody FlightRequest flightRequest) {
        FlightResponse response = flightService.create(flightRequest);

        BaseResponse<FlightResponse> baseResponse = BaseResponse.<FlightResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(response)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        flightService.delete(id);
        System.out.println("Airport removed successfully");
    }

    // update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<FlightResponse>> update( @PathVariable Long id, @RequestBody FlightUpdateRequest flightUpdateRequest){
        FlightResponse response = flightService.update(id, flightUpdateRequest);

        BaseResponse<FlightResponse> baseResponse = BaseResponse.<FlightResponse>builder()
                .status(HttpStatus.OK.value())
                .data(response)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
