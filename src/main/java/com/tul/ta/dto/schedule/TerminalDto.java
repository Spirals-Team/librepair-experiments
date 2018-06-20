package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TerminalDto {

    @JsonProperty(value = "Name")
    public Integer name;
}
