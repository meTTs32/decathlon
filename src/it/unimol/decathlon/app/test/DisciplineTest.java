package it.unimol.decathlon.app.test;

import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.Player;
import it.unimol.decathlon.app.PlayerManager;
import org.junit.Test;

public class DisciplineTest {

    private Discipline discipline;

    @Test
    public void testName() {
        this.discipline = new Discipline("Test");
        assert this.discipline.getName().equals("Test");
    }

    @Test
    public void testFinished() {
        try {
            PlayerManager.getInstance().addPlayer(new Player("Test"));
        }catch (Exception ignored){}
        this.discipline = new Discipline("Test");
        assert !this.discipline.isFinished();
    }

}
