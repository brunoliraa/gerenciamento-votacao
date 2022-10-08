package com.br.gerenciamentovotacao.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("agenda")
data class AgendaDocument(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime
)