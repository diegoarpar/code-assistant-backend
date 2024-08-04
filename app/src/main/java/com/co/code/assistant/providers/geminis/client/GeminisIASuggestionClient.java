package com.co.code.assistant.providers.geminis.client;

import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.copilot.client.CopilotASuggestionClient;
import com.co.code.assistant.providers.geminis.dto.GeminisAIRequestDto;
import com.co.code.assistant.providers.geminis.dto.GeminisAIResponseDto;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.dto.SuggestionDto;
import com.co.code.assistant.utils.ObjectMapperHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GeminisIASuggestionClient implements ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>> > {

    @Inject
    public ObjectMapperHelper objectMapperHelper;

    protected static final Logger logger = LoggerFactory.getLogger(GeminisIASuggestionClient.class);


    @Override
    public Observable<ISuggestionDto> getInformation(Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
                    ObjectMapper mapper = objectMapperHelper.getObjectMapper();
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(Timeout.ofMilliseconds(6000))
                            .setResponseTimeout(Timeout.ofMilliseconds(6000))
                            .build();

                    String json = "{\"model\":\"gpt-4o\", \"messages\":[{\"role\": \"system\", \"content\": \"You are a helpful assistan 2t.\"}, {\"role\": \"user\", \"content\": \"" + ((List) params.get("code")).get(0) +"\"}]}";
                    GeminisAIRequestDto.GeminisAIRequestPartsDto part1 = GeminisAIRequestDto.GeminisAIRequestPartsDto.builder().text((String) ((List) params.get("prompt")).get(0)).build();
                    GeminisAIRequestDto.GeminisAIRequestContentDto content1 = GeminisAIRequestDto.GeminisAIRequestContentDto.builder().role("model").parts(List.of(part1)).build();

                    GeminisAIRequestDto.GeminisAIRequestPartsDto part2 = GeminisAIRequestDto.GeminisAIRequestPartsDto.builder().text((String) ((List) params.get("code")).get(0)).build();
                    GeminisAIRequestDto.GeminisAIRequestContentDto content2 = GeminisAIRequestDto.GeminisAIRequestContentDto.builder().role("user").parts(List.of(part2)).build();
                    GeminisAIRequestDto geminisAIRequestDto = GeminisAIRequestDto.builder().contents(List.of(content1, content2)).build();
                    json = mapper.writeValueAsString(geminisAIRequestDto);
                    String apikey = System.getenv("geminisKey");
                    HttpPost request = new HttpPost(String.format("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=%s", apikey));
                    request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                    request.setConfig(requestConfig);
                    try {
                        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
                    } catch (Exception e) {
                        logger.error("error", e);
                    }
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                    try {
                        return httpClient.execute(request,
                                response -> {
                                    String rta = EntityUtils.toString(response.getEntity());
                                    GeminisAIResponseDto response1 = mapper.readValue(rta, GeminisAIResponseDto.class);
                                    return SuggestionDto.builder().id(response1.candidates.get(0).content.parts.get(0).text).build();
                                }
                        );
                    } catch (IOException e) {
                        logger.error("error", e);
                        return SuggestionDto.builder().id("NA").build();
                    }
                }
        );
    }

    @Override
    public void setInformation(Map<String, List<String>> params) {

    }
}
