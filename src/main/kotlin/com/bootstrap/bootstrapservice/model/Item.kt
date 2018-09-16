package com.bootstrap.bootstrapservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Item(val name: String, val value: String, @Id val id: String? = null)