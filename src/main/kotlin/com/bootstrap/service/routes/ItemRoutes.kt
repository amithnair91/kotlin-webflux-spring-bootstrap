package com.bootstrap.service.routes

import com.bootstrap.service.handler.ItemHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class ItemRoutes (val handler: ItemHandler) {

    @Bean
    fun apiRouter() =
            router {
                (accept(MediaType.APPLICATION_JSON) and "/api")
                .nest {
                    "/item".nest {
                        GET("/", handler::findAll)
                        GET("/{name}", handler::findOne)
                        POST("/", handler::create)
                    }
                }
                (accept(MediaType.APPLICATION_JSON) and "/encrypted")
                        .nest {
                    GET("/", handler::getEncryptedMessage)
                }
            }
}