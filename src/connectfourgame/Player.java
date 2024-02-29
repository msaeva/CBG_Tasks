package connectfourgame;

public interface Player extends Runnable {
    int nextMove(int otherPlayerLastMove);

    void gameOver();
}
