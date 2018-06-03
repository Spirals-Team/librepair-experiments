package br.com.skip.the.dishes.query.resource.order

import br.com.skip.the.dishes.query.repository.order.OrderEntity
import br.com.skip.the.dishes.query.repository.order.OrderRepository
import br.com.skip.the.dishes.query.resource.DEFAULT_PAGE
import br.com.skip.the.dishes.query.resource.DEFAULT_SIZE
import br.com.skip.the.dishes.query.resource.ORDER_PATH
import br.com.skip.the.dishes.query.resource.generatePaginationHttpHeaders
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderResource(private val orderRepository: OrderRepository) : OrderApi {

    override fun findAll(@RequestParam(name = "page", required = false, defaultValue = "$DEFAULT_PAGE") page: Int,
                         @RequestParam(name = "size", required = false, defaultValue = "$DEFAULT_SIZE") size: Int,
                         @RequestParam(name = "fields", required = false) fields: String): ResponseEntity<List<OrderEntity>> {

        val orderPage: Page<OrderEntity> = if (fields != null && !fields.trim { it <= ' ' }.isEmpty()) {
            orderRepository.findAll(PageRequest.of(page, size, Sort.by(*fields.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())))
        } else {
            orderRepository.findAll(PageRequest.of(page, size))
        }

        return generatePaginationHttpHeaders(orderPage, ORDER_PATH)
                .let { ResponseEntity(orderPage.content, it, HttpStatus.OK) }
    }

    override fun findById(@PathVariable("id") id: String): ResponseEntity<OrderEntity> {
        return orderRepository
                .findById(id)
                .map<ResponseEntity<OrderEntity>> { p -> ResponseEntity(p, HttpStatus.OK) }
                .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

}
