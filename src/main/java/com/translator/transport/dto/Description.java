package com.translator.transport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Description {
    @JsonProperty("description")
    public String description;
    @JsonProperty("language")
    public Language language;
}
