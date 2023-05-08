package it.unimol.decathlon.app.test;

import it.unimol.decathlon.app.Discipline;
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
        this.discipline = new Discipline("Test");
        assert !this.discipline.isFinished();
    }

}
