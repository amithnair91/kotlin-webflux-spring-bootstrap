package com.bootstrap.service.handler

import com.bootstrap.service.model.Item
import com.bootstrap.service.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


@Component
class ItemHandler(@Autowired val repository: ItemRepository) {

    var logger = LoggerFactory.getLogger(ItemHandler::class.java)

    private fun json() = ok().contentType(APPLICATION_JSON_UTF8)

    fun findAll(request: ServerRequest): Mono<ServerResponse> {
        logger.info("finding all")
       return  json().body(repository.findAll())
    }

    fun findOne(request: ServerRequest) = json().body(repository.findByName(request.pathVariable("name")))

    fun create(request: ServerRequest) = json().body(repository.saveAll(request.bodyToMono(Item::class.java)))

}

