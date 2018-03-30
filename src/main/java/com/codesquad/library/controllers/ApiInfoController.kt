package com.codesquad.library.controllers

import com.codesquad.library.domain.Article
import com.codesquad.library.domain.BookCategories
import com.codesquad.library.domain.BookLocations
import com.codesquad.library.domain.Featured
import com.codesquad.library.domain.repositories.ArticleRepository
import com.codesquad.library.domain.repositories.FeaturedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/info")
@RestController
@CrossOrigin
class ApiInfoController constructor(private val featuredRepository: FeaturedRepository, private val articleRepository: ArticleRepository) {

    @GetMapping("/categories")
    fun getCategories(): List<BookCategories> {
        return BookCategories.values().asList()
    }

    @GetMapping("/locations")
    fun getLocations(): List<BookLocations> {
        return BookLocations.values().asList()
    }

    @GetMapping("/featured")
    fun getFeatured(): List<Featured> {
        return featuredRepository.findAll().filterNotNull()
    }

    @GetMapping("/article")
    fun getArticle(): List<Article> {
        return articleRepository.findAll().filterNotNull()
    }
}