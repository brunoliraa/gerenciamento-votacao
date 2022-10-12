package com.br.gerenciamentovotacao.controller

import com.br.gerenciamentovotacao.exchange.VoteRequest
import com.br.gerenciamentovotacao.exchange.VoteResponse
import com.br.gerenciamentovotacao.mapper.toDocument
import com.br.gerenciamentovotacao.mapper.toResponse
import com.br.gerenciamentovotacao.service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/v1/vote")
class VoteController(private val voteService: VoteService) {

    @PostMapping
    fun createVote(@RequestBody voteRequest: VoteRequest): Mono<ResponseEntity<VoteResponse>> {
        return voteService.doVote(voteRequest.toDocument(), voteRequest.associatedCpf)
            .map{ ResponseEntity.created(URI.create("")).body(it.toResponse())}
    }

    @GetMapping("/agenda/{agendaId}")
    fun getVotesByAgenda(@PathVariable agendaId: UUID): ResponseEntity<Any> {
        return ResponseEntity.ok(voteService.getVotesByAgenda(agendaId))
    }
}