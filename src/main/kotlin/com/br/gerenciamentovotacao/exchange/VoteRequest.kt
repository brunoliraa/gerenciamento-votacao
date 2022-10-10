package com.br.gerenciamentovotacao.exchange

import com.br.gerenciamentovotacao.model.enums.VOTE_TYPE
import java.util.UUID

data class VoteRequest(
    val voteType: VOTE_TYPE,
    val agendaId: UUID,
    val associatedId: UUID,
    val associatedCpf: String
)