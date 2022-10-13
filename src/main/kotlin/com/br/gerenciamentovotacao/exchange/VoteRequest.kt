package com.br.gerenciamentovotacao.exchange

import com.br.gerenciamentovotacao.model.enums.VoteType
import java.util.UUID

data class VoteRequest(
    val voteType: VoteType,
    val agendaId: UUID,
    val associatedId: UUID,
    val associatedCpf: String
)