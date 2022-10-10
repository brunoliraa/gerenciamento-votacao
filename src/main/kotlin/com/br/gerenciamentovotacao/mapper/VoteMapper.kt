package com.br.gerenciamentovotacao.mapper

import com.br.gerenciamentovotacao.exchange.VoteRequest
import com.br.gerenciamentovotacao.exchange.VoteResponse
import com.br.gerenciamentovotacao.model.VoteDocument
import java.time.LocalDateTime
import java.util.UUID

fun VoteRequest.toDocument(id: UUID? = UUID.randomUUID()): VoteDocument {
    return VoteDocument(
        id = id!!,
        createdAt = LocalDateTime.now(),
        voteType = this.voteType,
        agendaId = this.agendaId,
        associatedId = this.associatedId
    )
}

fun VoteDocument.toResponse(): VoteResponse {
    return VoteResponse(
        id = this.id,
        createdAt = this.createdAt,
        voteType = this.voteType,
        agendaId = this.agendaId,
        associatedId = this.agendaId,
    )
}