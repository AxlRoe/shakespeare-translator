package com.translator.transport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HighestStat {
    @JsonProperty("name")
    public String name;
    @JsonProperty("url")
    public String url;
}
