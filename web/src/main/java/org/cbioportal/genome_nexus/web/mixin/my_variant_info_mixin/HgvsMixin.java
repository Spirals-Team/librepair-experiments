package org.cbioportal.genome_nexus.web.mixin.my_variant_info_mixin;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class HgvsMixin
{
    @ApiModelProperty(value = "coding", required = false)
    private String coding;

    @ApiModelProperty(value = "genomic", required = false)
    private String genomic;
}