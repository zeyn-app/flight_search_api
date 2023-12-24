package com.zeynapp.amadeusTravelToFuture.services;

import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportRequest;
import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportResponse;
import com.zeynapp.amadeusTravelToFuture.exceptions.AirportException;
import com.zeynapp.amadeusTravelToFuture.models.Airport;
import com.zeynapp.amadeusTravelToFuture.repositories.AirportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    // neyin testi
    private AirportService airportService;

    // airportservice oluşturmam için neyin nesnelerine ihtiyacım var
    private AirportRepository airportRepository;

    // her test senaryosundan önce çalışır 1 kere
    @BeforeEach
    void setUp() {
        // bunu mocklamak lazım çünkü airportservice unitinin dışında kalıyor
        airportRepository = Mockito.mock(AirportRepository.class); //pom.xml->springboot test dependency den geliyor
        airportService = new AirportService(airportRepository);
    }

    // adım1: test adı yazılması
    @Test
    void shouldReturnListOfAirportResponses_whenGetAllCalled() {
        // adım2: test verilerinin hazırlanması
        // given
        // ihtiyacım olan parametler ve nesneler
        // parametre-> yok
        // dönüş tipi --> list airportResponse
        // airport listesi--> kullanılacak
        List<Airport> airports = Arrays.asList(
                Airport.builder().id(1L).city("Test City").build(),
                Airport.builder().id(2L).city("Test City2").build(),
                Airport.builder().id(3L).city("Test City3").build());

        List<AirportResponse> expectedResult = Arrays.asList(
                AirportResponse.builder().id(1L).city("Test City").build(),
                AirportResponse.builder().id(2L).city("Test City2").build(),
                AirportResponse.builder().id(3L).city("Test City3").build()
        );

        // adım3: bağımlı srvicelerin davranışlarının belirlenmesi
        Mockito.when(airportRepository.findAll()).thenReturn(airports);
        // mocklanması gereken kısımlar bağımlılıklar

        // adım4: test edilecek methodun çalıştırılması
        List<AirportResponse> actualResult = airportService.getAll();

        // adım5: test sonuçlarının karşılaştırılması
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);

        // adım6: bağımlı servislerin çalıştırılmasının kontrol edilmesi
        Mockito.verify(airportRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldReturnAirportResponse_whenCreateCalled(){
        // adım2: verilerin hazırlanması
        AirportRequest airportRequest = AirportRequest.builder()
                .city("Test City")
                .build();

        Airport airport = Airport.builder()
                .id(1L)
                .city("Test City")
                .build();

        AirportResponse expectAirportResponse = AirportResponse.builder()
                .id(1L)
                .city("Test City")
                .build();

        // adım3: bağımlı srvicelerin davranışlarının belirlenmesi
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(airport);

        // adım4: test edilecek methodun çalıştırılması
        AirportResponse actualAirportResponse = airportService.create(airportRequest);

        // adım5: test sonuçlarının karşılaştırılması
        assertNotNull(actualAirportResponse);
        assertEquals(expectAirportResponse, actualAirportResponse);
        assertEquals(expectAirportResponse.getId(), actualAirportResponse.getId());
        assertEquals(expectAirportResponse.getCity(), actualAirportResponse.getCity());

        // adım6: bağımlı servislerin çalıştırılmasının kontrol edilmesi
        Mockito.verify(airportRepository, Mockito.times(1)).save(Mockito.any(Airport.class));
    }

    @Test
    void shouldDeleteAirportWithGivenAirportId_whenDeleteCalled(){
        // Adım2: veriler belirlendi
        Long id = 99L;
        Airport airport = Airport.builder()
                .id(id)
                .city("Test City")
                .isActive(true)
                .build();

        // Adım3: bağımlı srvicelerin davranışlarının belirlenmesi
        Mockito.when(airportRepository.findById(id)).thenReturn(Optional.of(airport));
        // dönüş değeri ile ilgilenmiyorsak daha esnek yaklaşım için thenAnswer kullanılabilir
        // getArgument(0) airport nesnesi döndürür
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Adım4: test edilecek methodun çalıştırılması
        airportService.delete(id);

        // adım5: test sonuçlarının karşılaştırılması
        assertEquals(airport.getId(), id);
        assertEquals(airport.getIsActive(), false);

        // adım6: bağımlı servislerin çalıştırılmasının kontrol edilmesi
        Mockito.verify(airportRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(airportRepository, Mockito.times(1)).save(Mockito.any(Airport.class));
    }

    @Test
    void shouldReturnAirportWithGivenAirportId_whenFindByIdCalled(){
        Long id = 99L;

        Mockito.when(airportRepository.findById(id)).thenReturn(Optional.of(Airport.builder().id(id).build()));
        Airport airport = airportService.findById(id);

        assertNotNull(airport);
        assertEquals(airport.getId(), id);

        Mockito.verify(airportRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    void shouldThrowAirportException_whenAirportNotFound(){
        Long id = 99L;

        Mockito.when(airportRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        assertThrows(AirportException.class, ()-> airportService.findById(id));
    }

    @Test
    void shouldThrowAirportException_whenAirportExist(){
        String city = "Test City";
        AirportRequest airportRequest = AirportRequest.builder().city("Test City").build();

        Mockito.when(airportRepository.existsAirportByCity(city)).thenReturn(Boolean.TRUE);

        assertThrows(AirportException.class, () -> airportService.create(airportRequest));
    }

    // test senaryosundan sonra bu çalışır ve sonraki test senaryosuna geçilir
    @AfterEach
    public void tearDown() {
    }
}