package dev.jvictor.chess.http

import dev.jvictor.chess.MatchService
import dev.jvictor.chess.core.Board
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/player")
class MeuController(private val service: MatchService) {

    @GetMapping("/{player}")
    fun getAllForPlayer(@PathVariable player: String): List<Board> {
        return service.getPlayerMatches(player)
    }

    @GetMapping("/{player}/match/{id}")
    fun getMatch(@PathVariable player: String, @PathVariable id: UUID): Board? {
        return service.getMatch(id, player)
    }

    @PostMapping("/{player}/challenge/{opponent}")
    fun challenge(@PathVariable player: String, @PathVariable opponent: String, @RequestBody movement: MoveRequest): Board {
        return service.challengePlayer(player, opponent, movement.movement)
    }

    @PostMapping("/{player}/match/{id}/resign")
    fun resign(@PathVariable player: String, @PathVariable id: UUID): Board? {
        return service.resign(id, player)
    }

    @PostMapping("/{player}/match/{id}/move")
    fun move(@PathVariable id: UUID, @PathVariable player: String, @RequestBody movement: MoveRequest): Board ?{
        return service.movePiece(id, player, movement.movement)
    }
}