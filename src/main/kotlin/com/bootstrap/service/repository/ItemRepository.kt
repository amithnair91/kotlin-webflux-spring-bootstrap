package com.bootstrap.service.repository

import com.bootstrap.service.model.Item
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String> {

    fun findByName(name: String): Mono<Item>

}