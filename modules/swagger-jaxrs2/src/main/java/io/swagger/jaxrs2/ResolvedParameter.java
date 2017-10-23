package io.swagger.jaxrs2;

import io.swagger.oas.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class ResolvedParameter {
    public List<Parameter> parameters = new ArrayList<>();
    public Parameter requestBody;
}
