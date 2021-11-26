
public class GameControls {
    private boolean running;

    public GameControls() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void resume() {
        running = true;
        run();
    }

    public void pause() {
        running = false;
    }
}
