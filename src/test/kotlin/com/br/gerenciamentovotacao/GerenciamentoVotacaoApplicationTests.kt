package com.br.gerenciamentovotacao

import com.br.gerenciamentovotacao.client.cpfvalidator.CpfValidatorClient
import com.br.gerenciamentovotacao.client.cpfvalidator.exchange.CpfValidatorResponse
import com.br.gerenciamentovotacao.client.cpfvalidator.exchange.VoteStatus
import com.br.gerenciamentovotacao.controller.AgendaController
import com.br.gerenciamentovotacao.controller.VoteController
import com.br.gerenciamentovotacao.exchange.AgendaRequest
import com.br.gerenciamentovotacao.exchange.VoteRequest
import com.br.gerenciamentovotacao.exchange.VotingRequest
import com.br.gerenciamentovotacao.model.AgendaDocument
import com.br.gerenciamentovotacao.model.VoteDocument
import com.br.gerenciamentovotacao.model.enums.VoteType
import com.br.gerenciamentovotacao.repository.AgendaRepository
import com.br.gerenciamentovotacao.repository.VoteRepository
import com.br.gerenciamentovotacao.service.AgendaService
import com.br.gerenciamentovotacao.service.VoteService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient
import org.mockito.BDDMockito.given
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import org.springframework.context.annotation.Import
import reactor.core.publisher.Flux
import java.util.UUID

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [AgendaController::class, VoteController::class])
@Import(value = [AgendaService::class, VoteService::class])
class GerenciamentoVotacaoApplicationTests {

    @Autowired
	private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var agendaRepository: AgendaRepository

    @MockBean
    private lateinit var voteRepository: VoteRepository

    @MockBean
    private lateinit var webClient: WebClient

    @MockBean
    private lateinit var cpfValidatorClient: CpfValidatorClient

    @Test
    fun shouldCreateAgendaWithSuccess() {

        val agendaId = UUID.randomUUID()

        val agenda = AgendaDocument(agendaId, "teste", LocalDateTime.now(), false, null, null)

		val agendaRequest = AgendaRequest("teste")

        given(agendaRepository.insert(any(AgendaDocument::class.java))).willReturn(Mono.just(agenda))

        webTestClient.post()
            .uri("/v1/agenda")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(agendaRequest)
			.exchange()
			.expectStatus().isCreated
    }

    @Test
    fun shouldInitVotingWithSuccess(){

        val agendaId = UUID.randomUUID()

        val agenda = AgendaDocument(agendaId, "teste", LocalDateTime.now(), false, null, null)

        val agendaUpdated = AgendaDocument(agendaId, "teste", LocalDateTime.now(), true, LocalDateTime.now(), 120L)

        val votingRequest = VotingRequest(120L, agendaId)

        given(agendaRepository.save(any(AgendaDocument::class.java))).willReturn(Mono.just(agendaUpdated))
        given(agendaRepository.findById(any(UUID::class.java))).willReturn(Mono.just(agenda))

        webTestClient.patch()
            .uri("/v1/agenda/init-voting")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(votingRequest)
            .exchange()
            .expectStatus().isOk

    }

    @Test
    fun shouldVoteWithSuccess(){

        val agendaId = UUID.randomUUID()
        val associatedId = UUID.randomUUID()
        val associatedCpf = "62289608068"

        val voteRequest = VoteRequest(VoteType.YES, agendaId, associatedId, associatedCpf)

        val cpfValidatorResponse = CpfValidatorResponse(VoteStatus.ABLE_TO_VOTE)

        val voteDocument = VoteDocument(UUID.randomUUID(), LocalDateTime.now(),VoteType.YES,agendaId,associatedId)

        val agenda = AgendaDocument(agendaId, "teste", LocalDateTime.now(), true, LocalDateTime.now(), 120L)

        given(voteRepository.insert(any(VoteDocument::class.java))).willReturn(Mono.just(voteDocument))
        given(agendaRepository.findById(any(UUID::class.java))).willReturn(Mono.just(agenda))
        given(cpfValidatorClient.validate(associatedCpf)).willReturn(Mono.just(cpfValidatorResponse))

        webTestClient.post()
            .uri("/v1/vote")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(voteRequest)
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    fun shouldReturnVotesbyAgendaWithSuccess(){

        val agendaId = UUID.randomUUID()

        given(voteRepository.findAllByAgendaId(agendaId)).willReturn(Flux.just())

        webTestClient.get()
            .uri("/v1/vote/agenda/{agendaId}", agendaId)
            .header("Content-type", "application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
    }

}
