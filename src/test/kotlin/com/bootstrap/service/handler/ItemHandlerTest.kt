package com.bootstrap.service.handler

import com.bootstrap.service.model.Item
import com.bootstrap.service.repository.ItemRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class ItemHandlerTest {

    var monoItem: Mono<Item>? = null
    var fluxItem: Flux<Item>? = null

    @BeforeEach
    fun setUp() {
        val item = Item("book", "clean code")
        monoItem = Mono.just(item)
        fluxItem = Flux.just(item)
    }

    @Test
    fun `findAll success`() {
        val repository: ItemRepository = mock()
        val handler = ItemHandler(repository)
        val request: ServerRequest = mock()

        whenever(repository.findAll()).thenReturn(fluxItem)

        handler.findAll(request)
                .map {
                    assertEquals(APPLICATION_JSON_UTF8, it.headers().contentType)
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .block()

    }

    @Test
    fun `findOne success`() {
        val repository: ItemRepository = mock()
        val handler = ItemHandler(repository)
        val request: ServerRequest = mock()

        whenever(request.pathVariable("name")).thenReturn("someRandomItem")
        whenever(repository.findByName("someRandomItem")).thenReturn(monoItem)

        //need to check response body
        handler.findOne(request)
                .map {
                    assertEquals(APPLICATION_JSON_UTF8, it.headers().contentType)
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .block()

    }

    @Test
    fun `create success`() {
        val repository: ItemRepository = mock()
        val handler = ItemHandler(repository)
        val request: ServerRequest = mock()

        whenever(request.bodyToMono(Item::class.java)).thenReturn(monoItem!!)
        whenever(repository.saveAll(monoItem!!)).thenReturn(fluxItem)

        handler.create(request)
                .map {
                    assertEquals(APPLICATION_JSON_UTF8, it.headers().contentType)
                    assertEquals(HttpStatus.OK, it.statusCode())
                }.block()

    }

}