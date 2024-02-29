package connectfourgame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static connectfourgame.Constants.SLEEP_DURATION_5000;

public class GameController implements Runnable {
    private final Board board = new Board();
    private final Player playerOne;
    private final Player playerTwo;
    private Player currentPlayer;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public GameController() {
        playerOne = new GamePlayer(board, this, 1);
        playerTwo = new GamePlayer(board, this, 2);
        currentPlayer = playerOne;
    }

    /**
     * Runs the game loop, coordinating player turns, checking game state
     */
    @Override
    public void run() {
        executor.execute(playerOne);
        executor.execute(playerTwo);

        while (!board.checkWin() && !Thread.currentThread().isInterrupted()) {
            synchronized (currentPlayer) {
                currentPlayer.notify();
            }

            try {
                Thread.sleep(SLEEP_DURATION_5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkGameState();
        }

        executor.shutdownNow();

        System.out.println("Game Over.");
        playerOne.gameOver();
        playerTwo.gameOver();
    }

    /**
     * Switches the turn to the other player.
     */
    public void switchTurn() {
        currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
        System.out.println("Current player: Player " + (currentPlayer == playerOne ? "X" : "Y"));
    }

    public synchronized void checkGameState() {
        if (board.checkWin()) {
            System.out.println("Player " + (currentPlayer == playerOne ? "Y" : "X") + " wins the game!");
            stopGame();
        } else if (board.isFull()) {
            System.out.println("The board is full of tokens, it's a draw!");
            stopGame();
        }
        board.printBoard();
    }

    private void stopGame() {
        Thread.currentThread().interrupt();
        playerOne.gameOver();
        playerTwo.gameOver();
    }
}
