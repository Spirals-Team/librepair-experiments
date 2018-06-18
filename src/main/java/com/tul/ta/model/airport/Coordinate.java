package com.tul.ta.model.airport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinate {

    public Double latitude;
    public Double longitude;
}
