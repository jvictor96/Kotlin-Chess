package dev.jvictor.chess.io

import dev.jvictor.chess.core.Board
import dev.jvictor.chess.ports.PersistenceAdapter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.ArrayList
import java.util.UUID

@Repository
@Profile("dev", "local")
class InMemoryPersistence : PersistenceAdapter {
    var boards: MutableList<Board> = java.util.ArrayList<Board>()
    var nextId: Int = 0

    override fun getBoard(id: UUID): Board? {
        return boards.firstOrNull() { it.id == id }
    }

    override fun saveBoard(id: UUID, board: Board) {
        board.id = id
        boards.add(board)
    }


    override fun listGames(): MutableList<Board> {
        return boards
    }

    override fun clearAll() {
        boards = ArrayList<Board>() as MutableList<Board>
        nextId = 0
    }
}
