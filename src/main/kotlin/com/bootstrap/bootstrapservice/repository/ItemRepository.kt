package com.bootstrap.bootstrapservice.repository

import com.bootstrap.bootstrapservice.model.Item
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String> {

    fun findByName(name: String): Mono<Item>

}