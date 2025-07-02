package com.example.ghibliapi.client;

import com.example.ghibliapi.dto.TextToImageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@FeignClient(
        name = "stabilityAIClient",
        url = "${stability.api.base-url}",
        configuration = com.example.ghibliapi.config.FeignConfig.class
)
public interface StabilityAIClient {

    @PostMapping(
            value = "/v1/generation/{engine_id}/text-to-image",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Accept=image/png"}

    )

    byte[] generateImageFromText(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("engine_id") String engineId,
            @RequestBody TextToImageRequest requestBody
    );

    @PostMapping(
            value = "/v1/generation/{engine_id}/image-to-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            headers = {"Accept=image/png"}
    )
    byte[] generateImageFromImage(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("engine_id") String engineId,
            @RequestPart("init_image") MultipartFile initImage,
            @RequestPart("text_prompts[0][text]") String prompt,
            @RequestPart("style_preset") String stylePreset,
            @RequestPart("image_strength") String imageStrength
    );

}
