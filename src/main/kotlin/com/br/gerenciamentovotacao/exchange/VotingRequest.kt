package com.br.gerenciamentovotacao.exchange

import java.util.UUID

data class VotingRequest(
    val votingTimeInSeconds: Long?,
    val agendaId: UUID
)