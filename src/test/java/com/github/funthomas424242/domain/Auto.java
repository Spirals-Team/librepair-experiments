package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.RadesAddBuilder;

@RadesAddBuilder(simpleBuilderClassName = "CarBuilder")
public class Auto {

    protected String typ;

    Object motor;

    protected String hersteller;

}
