package com.br.gerenciamentovotacao.mapper

import com.br.gerenciamentovotacao.exchange.AgendaRequest
import com.br.gerenciamentovotacao.exchange.AgendaResponse
import com.br.gerenciamentovotacao.exchange.VotingResponse
import com.br.gerenciamentovotacao.model.AgendaDocument
import java.time.LocalDateTime
import java.util.UUID

fun AgendaRequest.toDocument(agendaId: UUID? = UUID.randomUUID()): AgendaDocument {
    return AgendaDocument(agendaId!!, this.name, LocalDateTime.now(), false,null,null)
}

fun AgendaDocument.toResponse(): AgendaResponse {
    return AgendaResponse(id, name, createdAt)
}

fun AgendaDocument.toVotingResponse(): VotingResponse {
    return VotingResponse(agendaId = this.id, active = this.active, startVotingAt = this.startVotingAt!!, votingTimeInSeconds = this.votingTimeInSeconds!!)
}