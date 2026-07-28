//package com.flightopsservice.service.interservice;
//
//import com.payload.response.AirlineResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class AirlineIntegrationService {
//
//    private final RestTemplate restTemplate;
//
//
//    public AirlineResponse getAirlineById(Long id){
//        String url="http://localhost:5002/api/airlines/"+id;
//        return restTemplate.getForObject(url,AirlineResponse.class);
//    }
//
//
//}


// NON MI SERVE PIU QEUSTO PERCHE HO MESSO FEIGN CLIENT