package com.br.gerenciamentovotacao.exchange

import com.br.gerenciamentovotacao.model.enums.VOTE_TYPE
import java.time.LocalDateTime
import java.util.UUID

data class VoteResponse(
    val id: UUID,
    val createdAt: LocalDateTime,
    val voteType: VOTE_TYPE,
    val agendaId: UUID,
    val associatedId: UUID
)
