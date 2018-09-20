package com.bootstrap.service.handler

import com.bootstrap.service.model.Item
import com.bootstrap.service.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


@Component
class ItemHandler(@Autowired val repository: ItemRepository) {

    var logger = LoggerFactory.getLogger(ItemHandler::class.java)

    private fun json() = ok().contentType(APPLICATION_JSON_UTF8)
    private fun notfound() = notFound().build()
    private fun nocontent() = noContent().build()

    fun findAll(request: ServerRequest): Mono<ServerResponse> {
        logger.trace("A TRACE Message")
        logger.debug("A DEBUG Message")
        logger.info("An INFO Message")
        logger.warn("A WARN Message")
        logger.error("An ERROR Message")

        return repository
                .findAll()
                .collectList()
                .flatMap { itemList ->
                    if (itemList.isEmpty()) {
                        nocontent()
                    } else {
                        json()
                                .body(fromObject(itemList))
                    }
                }
    }

    fun create(request: ServerRequest) = json().body(repository.saveAll(request.bodyToMono(Item::class.java)))

    fun findOne(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable("name")
        return repository
                .findByName(name)
                .flatMap { item ->
                    json().body(fromObject(item))
                }
                .switchIfEmpty(notfound())
    }

}

