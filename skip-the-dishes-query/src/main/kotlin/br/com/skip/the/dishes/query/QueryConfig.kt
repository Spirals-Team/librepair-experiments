package br.com.skip.the.dishes.query


import br.com.skip.the.dishes.query.event.handler.order.OrderSubscription
import br.com.skip.the.dishes.query.event.handler.product.ProductSubscription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

import javax.annotation.PostConstruct

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
@ComponentScan(basePackages = ["br.com.skip.the.dishes.query"])
@EnableResourceServer
@Configuration
class QueryConfig(val productSubscription: ProductSubscription, val orderSubscription: OrderSubscription) {

    @PostConstruct
    fun initSubscriptions() {
        productSubscription.start()
        orderSubscription.start()
    }

}
