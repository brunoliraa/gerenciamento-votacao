package com.br.gerenciamentovotacao.exchange

import com.br.gerenciamentovotacao.model.enums.VoteType
import java.time.LocalDateTime
import java.util.UUID

data class VoteResponse(
    val id: UUID,
    val createdAt: LocalDateTime,
    val voteType: VoteType,
    val agendaId: UUID,
    val associatedId: UUID
)
