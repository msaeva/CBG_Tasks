package connectfourgame;

import java.util.concurrent.ThreadLocalRandom;

public class GamePlayer implements Player {
    private final Board board;
    private final GameController gameController;
    private final Integer playerNumber;
    private volatile boolean running = true;
    private volatile static int lastMove = 0;

    public GamePlayer(Board board, GameController gameController, Integer playerNumber) {
        this.board = board;
        this.gameController = gameController;
        this.playerNumber = playerNumber;
    }

    @Override
    public int nextMove(int otherPlayerLastMove) {
        System.out.println("Other player last move is:" + otherPlayerLastMove);
        return ThreadLocalRandom.current().nextInt(1, 8);
    }

    @Override
    public synchronized void gameOver() {
        running = false;
        notify();
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            int move = nextMove(lastMove);
            String symbol = (playerNumber == 1) ? "X" : "Y";

            if (board.makeMove(move, symbol)) {
                lastMove = move;
                System.out.println("Player " + symbol + " made a move in column " + move + ".");
            } else {
                System.out.println("Invalid move by " + symbol);
            }

            gameController.switchTurn();
        }
    }
}
