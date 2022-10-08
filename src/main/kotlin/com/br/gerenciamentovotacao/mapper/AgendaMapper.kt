package com.br.gerenciamentovotacao.mapper

import com.br.gerenciamentovotacao.exchange.AgendaRequest
import com.br.gerenciamentovotacao.exchange.AgendaResponse
import com.br.gerenciamentovotacao.model.AgendaDocument
import java.time.LocalDateTime
import java.util.UUID

fun AgendaRequest.toDocument(agendaId: UUID? = UUID.randomUUID()): AgendaDocument {
    return AgendaDocument(agendaId!!, this.name, LocalDateTime.now())
}

fun AgendaDocument.toResponse(): AgendaResponse {
    return AgendaResponse(id, name, createdAt)
}