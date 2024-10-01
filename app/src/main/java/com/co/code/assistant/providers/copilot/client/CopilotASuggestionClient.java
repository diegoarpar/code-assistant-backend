package com.co.code.assistant.providers.copilot.client;

import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.copilot.dto.CopilotAIRequestDto;
import com.co.code.assistant.providers.copilot.dto.CopilotAIResponseDto;
import com.co.code.assistant.providers.geminis.dto.GeminisAIRequestDto;
import com.co.code.assistant.providers.geminis.dto.GeminisAIResponseDto;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.dto.SuggestionDto;
import com.co.code.assistant.utils.ObjectMapperHelper;
import com.co.code.assistant.utils.SystemEnvHelper;
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

public class CopilotASuggestionClient implements ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>> > {

    @Inject
    public ObjectMapperHelper objectMapperHelper;

    protected static final Logger logger = LoggerFactory.getLogger(CopilotASuggestionClient.class);

    @Override
    public Observable<ISuggestionDto> getInformation(Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
                    ObjectMapper mapper = objectMapperHelper.getObjectMapper();
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(Timeout.ofMilliseconds(30000))
                            .setResponseTimeout(Timeout.ofMilliseconds(30000))
                            .build();

                    String json = "{\"model\":\"gpt-4o\", \"messages\":[{\"role\": \"system\", \"content\": \"You are a helpful assistan 2t.\"}, {\"role\": \"user\", \"content\": \"" + ((List) params.get("code")).get(0) +"\"}]}";
                    CopilotAIRequestDto.CopilotAIRequestMessageDto message1 = CopilotAIRequestDto.CopilotAIRequestMessageDto.builder().content((String) ((List) params.get("prompt")).get(0)).role("system").build();
                    CopilotAIRequestDto.CopilotAIRequestMessageDto message2 = CopilotAIRequestDto.CopilotAIRequestMessageDto.builder().content((String) ((List) params.get("code")).get(0)).role("user").build();

                    CopilotAIRequestDto messages = CopilotAIRequestDto.builder().messages(List.of(message1, message2)).build();
                    json = mapper.writeValueAsString(messages);
                    String apikey = System.getenv("copilotKey");
                    String resource = System.getenv("copilotResource");
                    HttpPost request = new HttpPost(String.format("https://%s.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2023-03-15-preview", resource));
                                                    //https://opeaniainstance.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2023-03-15-preview
                    request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    request.addHeader("api-key", apikey);
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
                                    if (response.getCode() >= 300) {
                                        logger.error(EntityUtils.toString(response.getEntity()));
                                        return SuggestionDto.builder().content("ERROR " +response.getCode() + " " + EntityUtils.toString(response.getEntity())).build();
                                    }
                                    String rta = EntityUtils.toString(response.getEntity());
                                    CopilotAIResponseDto response1 = mapper.readValue(rta, CopilotAIResponseDto.class);
                                    return SuggestionDto.builder().content(response1 != null && response1.choices != null && !response1.choices.isEmpty() && response1.choices.get(0).message != null? response1.choices.get(0).message.content: "Error").build();
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
