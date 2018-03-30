package com.codesquad.library.dtos.model.book

import com.codesquad.library.domain.BookCategories
import com.codesquad.library.domain.BookLocations
import com.fasterxml.jackson.annotation.JsonProperty

data class NewBookDocument(

        @field:JsonProperty("booktitle")val title : String = "",
        val author : String = "",
        val description : String = "",
        val isbn : String = "",
        val category : String? = null,
        val location : BookLocations? = null
)