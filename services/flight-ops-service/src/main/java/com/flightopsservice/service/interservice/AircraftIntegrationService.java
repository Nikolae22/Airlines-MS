//package com.flightopsservice.service.interservice;
//
//import com.payload.response.AircraftResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class AircraftIntegrationService {
//
//    private final RestTemplate restTemplate;
//
//    public AircraftResponse getAircraftById(Long id){
//        String url="http://localhost:5003/api/aircraft/"+id;
//        return restTemplate.getForObject(url,AircraftResponse.class);
//    }
//}


// NON MI SERVE PIU QEUSTO PERCHE HO MESSO FEIGN CLIENT