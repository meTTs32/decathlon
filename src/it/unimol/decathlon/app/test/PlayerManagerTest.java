package it.unimol.decathlon.app.test;

import it.unimol.decathlon.app.Player;
import it.unimol.decathlon.app.PlayerManager;
import org.junit.Test;

public class PlayerManagerTest {

    private PlayerManager playerManager;

    @Test
    public void testInstance() {
        this.playerManager = PlayerManager.getInstance();
        assert this.playerManager != null;
    }

    @Test
    public void testBoardSize() {
        this.playerManager = PlayerManager.getInstance();
        int oldSize = this.playerManager.getBoard().size();
        try{
            Player testPlayer = new Player("Test");
            this.playerManager.addPlayer(testPlayer);
        }catch (Exception ignored){}
        assert this.playerManager.getBoard().size() == oldSize + 1;
    }

    @Test
    public void testPlayer() {
        this.playerManager = PlayerManager.getInstance();
        try{
            Player testPlayer = new Player("Test");
            this.playerManager.addPlayer(testPlayer);
        }catch (Exception ignored){}
        assert this.playerManager.getPlayer(0) != null;
    }
}
