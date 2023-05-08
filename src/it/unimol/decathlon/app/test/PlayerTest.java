package it.unimol.decathlon.app.test;

import it.unimol.decathlon.app.Player;
import org.junit.*;

public class PlayerTest {

    private Player player;

    @Test
    public void testName() {
        try {
            this.player = new Player("Test");
        } catch (Exception ignored){}

        Assert.assertEquals("Test", this.player.getName());
    }

    @Test
    public void testScore(){
        try {
            this.player = new Player("Test");
        } catch (Exception ignored){}

        this.player.addScore(10);
        Assert.assertEquals(10, this.player.getTempScore());
    }

    @Test
    public void testTempScore(){
        try {
            this.player = new Player("Test");
        } catch (Exception ignored){}

        this.player.setTempScore(10);
        Assert.assertEquals(10, this.player.getTempScore());
    }
}
