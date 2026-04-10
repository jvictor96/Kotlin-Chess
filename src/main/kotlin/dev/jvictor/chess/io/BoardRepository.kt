package dev.jvictor.chess.io

import dev.jvictor.chess.core.Board
import dev.jvictor.chess.ports.PersistenceAdapter
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Profile("jpa")
public interface BoardRepository : PersistenceAdapter, JpaRepository<Board, UUID> {
    override fun getBoardById(id: UUID): Board?
    override fun save(board: Board): Board
    override fun findAll(): MutableList<Board>
}