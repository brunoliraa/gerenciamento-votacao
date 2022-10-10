package com.br.gerenciamentovotacao.exchange

import java.time.LocalDateTime
import java.util.UUID

data class VotingResponse(
    val agendaId: UUID,
    val active: Boolean,
    val startVotingAt: LocalDateTime,
    val votingTimeInSeconds: Long
)


