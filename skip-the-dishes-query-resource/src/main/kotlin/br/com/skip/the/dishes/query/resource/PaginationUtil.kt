package br.com.skip.the.dishes.query.resource

import org.springframework.data.domain.Page
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.http.HttpHeaders

fun <T> generatePaginationHttpHeaders(page: Page<T>, baseUrl: String): HttpHeaders =
        HttpHeaders()
                .apply { add("X-Total-Count", java.lang.Long.toString(page.totalElements)) }
                .apply { add(HttpHeaders.LINK, generateLink(page = page, baseUrl = baseUrl)) }

private fun <T> generateLink(page: Page<T>, baseUrl: String) = buildString {
    if (page.number + 1 < page.totalPages) {
        append("""<${generateUri(baseUrl, page.number + 1, page.size)}>; rel="next",""")
    }

    if (page.number > 0) {
        append("""<${generateUri(baseUrl, page.number - 1, page.size)}>; rel="prev",""")
    }

    append("""<${generateUri(baseUrl, generateLastPage(page), page.size)}>; rel="last",""")
    append("""<${generateUri(baseUrl, 0, page.size)}>; rel="first"""")
}

private fun <T> generateLastPage(page: Page<T>) =
        if (page.totalPages > 0) page.totalPages - 1 else 0

private fun generateUri(baseUrl: String, page: Int, size: Int): String =
        UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString()
