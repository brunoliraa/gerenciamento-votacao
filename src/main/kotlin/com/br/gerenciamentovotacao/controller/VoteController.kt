package com.br.gerenciamentovotacao.controller

import com.br.gerenciamentovotacao.exchange.VoteRequest
import com.br.gerenciamentovotacao.exchange.VoteResponse
import com.br.gerenciamentovotacao.mapper.toDocument
import com.br.gerenciamentovotacao.mapper.toResponse
import com.br.gerenciamentovotacao.service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/v1/vote")
class VoteController(private val voteService: VoteService) {

    @PostMapping
    fun createVote(@RequestBody voteRequest: VoteRequest): Mono<ResponseEntity<VoteResponse>> {
        return voteService.doVote(voteRequest.toDocument())
            .map{ ResponseEntity.created(URI.create("")).body(it.toResponse())}
    }

}