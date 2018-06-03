package br.com.skip.the.dishes.query.resource.order

import br.com.skip.the.dishes.query.repository.order.OrderEntity
import br.com.skip.the.dishes.query.resource.DEFAULT_PAGE
import br.com.skip.the.dishes.query.resource.DEFAULT_SIZE
import br.com.skip.the.dishes.query.resource.ORDER_PATH
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(path = [ORDER_PATH])
interface OrderApi {

    @GetMapping
    fun findAll(@RequestParam(name = "page", required = false, defaultValue = "$DEFAULT_PAGE") page: Int,
                @RequestParam(name = "size", required = false, defaultValue = "$DEFAULT_SIZE") size: Int,
                @RequestParam(name = "fields", required = false) fields: String): ResponseEntity<List<OrderEntity>>

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable("id") id: String): ResponseEntity<OrderEntity>

}
