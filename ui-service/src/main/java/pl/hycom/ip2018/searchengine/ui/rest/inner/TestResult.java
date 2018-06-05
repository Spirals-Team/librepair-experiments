package pl.hycom.ip2018.searchengine.ui.rest.inner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResult {

    private String term;

    private Integer searches;

    private Float percent;
}
