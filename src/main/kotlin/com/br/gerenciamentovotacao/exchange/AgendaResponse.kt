package com.br.gerenciamentovotacao.exchange

import java.time.LocalDateTime
import java.util.UUID

data class AgendaResponse(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime
)
