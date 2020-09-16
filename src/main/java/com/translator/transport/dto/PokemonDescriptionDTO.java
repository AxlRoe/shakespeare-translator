package com.translator.transport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PokemonDescriptionDTO {
    @JsonProperty("descriptions")
    public List<Description> descriptions = null;
    @JsonProperty("geneModulo")
    public Integer geneModulo;
    @JsonProperty("highestStat")
    public HighestStat highestStat;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("possibleValues")
    public List<Integer> possibleValues = null;
}
