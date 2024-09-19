package com.co.code.assistant.providers.openia.client;

import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.dto.SuggestionDto;
import com.co.code.assistant.providers.openia.dto.OpenIARequestDto;
import com.co.code.assistant.providers.openia.dto.OpenIAResponseDto;
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

public class OpenIASuggestionClient implements ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>> > {

    @Inject
    public ObjectMapperHelper objectMapperHelper;

    protected static final Logger logger = LoggerFactory.getLogger(OpenIASuggestionClient.class);

    @Override
    public Observable<ISuggestionDto> getInformation(Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
                    ObjectMapper mapper = objectMapperHelper.getObjectMapper();
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(Timeout.ofMilliseconds(30000))
                            .setResponseTimeout(Timeout.ofMilliseconds(30000))
                            .build();

                    String json = "{\"model\":\"gpt-4o\", \"messages\":[{\"role\": \"system\", \"content\": \"You are a helpful assistan 2t.\"}, {\"role\": \"user\", \"content\": \"" + ((List) params.get("code")).get(0) +"\"}]}";
                    String apikey = System.getenv("openaiKey");
                    OpenIARequestDto.OpenIAMessage message1 = OpenIARequestDto.OpenIAMessage.builder().role("system").content((String) ((List) params.get("prompt")).get(0)).build();
                    OpenIARequestDto.OpenIAMessage message2 = OpenIARequestDto.OpenIAMessage.builder().role("user").content((String) ((List) params.get("code")).get(0)).build();
                    OpenIARequestDto openIARequestDto = OpenIARequestDto.builder()
                            .model("gpt-4o")
                            .messages(List.of(message1, message2))
                            .build();
                    json = mapper.writeValueAsString(openIARequestDto);
                    HttpPost request = new HttpPost("https://api.openai.com/v1/chat/completions");
                    request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                    request.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", apikey));
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
                                    OpenIAResponseDto response1 = mapper.readValue(rta, OpenIAResponseDto.class);
                                    return SuggestionDto.builder().content(response1.choices.get(0).message.content).build();
                                }
                        );
                    } catch (IOException e) {
                        logger.error("error", e);
                        return SuggestionDto.builder().content("ERROR " + e.getMessage()).build();
                    }
                }
        );
    }

    @Override
    public void setInformation(Map<String, List<String>> params) {

    }
}
