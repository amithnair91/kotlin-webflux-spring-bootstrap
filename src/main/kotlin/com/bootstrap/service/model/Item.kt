package com.bootstrap.service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "item")
data class Item(val name: String, val value: String, @Id val id: String? = null)