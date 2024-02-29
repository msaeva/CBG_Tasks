package connectfourgame;

public class ConnectFourGame {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Thread newThread = new Thread(gameController);
        newThread.start();
    }
}
