package it.unimol.decathlon.ui;

import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.PlayerManager;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class MainScreen extends Screen{

    private List<Screen> disciplineList;
    private String currentDiscipline;


    public MainScreen() {

        File file = new File(FILENAME);


        if(file.exists()) {
            try (
                FileInputStream fis = new FileInputStream(FILENAME);
                ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                Object[] obj = (Object[]) ois.readObject();

                this.disciplineList = (List) obj[0];
                this.playerManager = PlayerManager.getInstance((PlayerManager) obj[1]);
                this.currentDiscipline = (String) obj[2];

            } catch (Exception e) {}
        } else {
            this.disciplineList = new ArrayList<>();
            this.currentDiscipline = "";
            this.playerManager = PlayerManager.getInstance();
        }

        this.discipline = null;
    }

    public void start() {

        if (!new File(FILENAME).exists()) {
            StartScreen init = new StartScreen();
            init.start();
            this.creaDiscipline();
            this.save();
        }

        for (Screen s : this.disciplineList) {
            this.delayScreen(1000);
            this.clearScreen();
            this.setCurrent(s);
            if (!(s.isFinished())) {
                this.stampaRecap(this.disciplineList);
                s.start();
            }
            this.save();
        }

        EndingScreen end = new EndingScreen();
        end.start();

    }

    private void addDiscipline(Screen s) {
        this.disciplineList.add(s);
    }

    private void setCurrent(Screen s) {
        this.currentDiscipline = s.getDiscipline().getName();

    }

    private void stampaRecap(List<Screen> lista) {
        String output = "Discipline:\n";
        for(Screen s : lista) {
            if(s.isFinished()) {
                output+= "#    " + s.getDiscipline().getName() + "\n";
            } else if(this.currentDiscipline.equals(s.getDiscipline().getName())) {
                output += "*    " + this.currentDiscipline  + "\n";
            } else {
                output += "     " + s.getDiscipline().getName() + "\n";
            }
        }
        System.out.println(output + "\n" + this.playerManager.toString() );
        System.out.print("Premi invio per iniziare a giocare");
        this.waitUserInput();
    }

    private void creaDiscipline() {

        Screen100Metri centoMetri = new Screen100Metri(new Discipline("100 Metri"));
        Screen100Metri centoMetri2 = new Screen100Metri(new Discipline("100 Metria"));

        this.addDiscipline(centoMetri);
        this.addDiscipline(centoMetri2);
    }

    private void save() {

        Object[] obj = new Object[3];

        obj[0] = new ArrayList<>(this.disciplineList);
        obj[1] = this.playerManager;
        obj[2] = this.currentDiscipline;

        try (
                FileOutputStream fos = new FileOutputStream(FILENAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(obj);
        } catch (Exception e) {}
    }
}
