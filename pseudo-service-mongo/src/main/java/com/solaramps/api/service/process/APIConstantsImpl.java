package com.solaramps.api.service.process;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@Builder
public class APIConstantsImpl implements APIConstants {
    private HttpMethod method;
    private String url;
    private String urlSuffix;
}
