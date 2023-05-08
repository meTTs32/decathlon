package it.unimol.decathlon.app.test;

import it.unimol.decathlon.app.Dice;
import org.junit.Test;

public class DiceTest {

    @Test
    public void singleRollTest() {
        int test = Dice.roll();
        assert test >= 1 && test <= 6;
    }

    @Test
    public void multipleRollTest() {
        int[] test = Dice.roll(5);
        for (int j : test) {
            assert j >= 1 && j <= 6;
        }
    }
}
