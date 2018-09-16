package com.bootstrap.service.routes

import com.bootstrap.service.model.Item
import com.bootstrap.service.repository.ItemRepository
import io.kotlintest.Tags.Companion.Empty
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemRoutesTests(@Autowired val client: WebTestClient, @Autowired val itemRepository: ItemRepository) {

    @BeforeEach
    fun beforeEach(){
        itemRepository.deleteAll().block()
    }

	@Test
	fun `get all Items`() {
        itemRepository.save(Item("book", "clean code")).block()
        itemRepository.save(Item("plant", "oak")).block()

        val result = client.get().uri("/api/item/").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList<Item>()
                .hasSize(2)
                .returnResult().responseBody!!

        result[0].name shouldBe "book"
        result[0].value shouldBe "clean code"
        result[1].name shouldBe "plant"
        result[1].value shouldBe "oak"
    }

    @Test
    fun `get an item`() {
        itemRepository.save(Item("codeBook", "refactoring legacy code")).block()

        val result = client.get().uri("/api/item/codeBook").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(Item::class.java)
                .returnResult().responseBody!!

        result.value shouldBe "refactoring legacy code"
    }

    @Test
    fun `create Item`(){
        val result = client.post().uri("/api/item/")
                .accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                .syncBody(Item("book", "harry potter"))
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList<Item>()
                .hasSize(1)
                .returnResult().responseBody!!

        result[0].id shouldNotBe Empty
        result[0].name shouldBe "book"
        result[0].value shouldBe "harry potter"
        
    }

}
