package com.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {

    private Long id;
    private String name;
    private String cityCode;
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String timeZoneOffset;
}
