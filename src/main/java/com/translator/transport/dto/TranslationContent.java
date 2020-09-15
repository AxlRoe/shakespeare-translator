package com.translator.transport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TranslationContent {
    String text;
    String translation;
    String translated;
}
