package it.itispaleocapa.tascal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    
    Registro r = new Registro();

    @Test
    public void newVoto(){
        String test0 = r.addStudente();
        assertThrows(MateriaNotExistException.class, () -> {
            r.addVoto(test0, "Informatica", 7);
        });
        assertThrows(VotoOutOfBoundsException.class, () -> {
            r.addVoto(test0, "Matematica", -1);
        });
    }

    @Test
    public void visualizzaVotiAlunno(){
        String test = r.addStudente();
        r.addVoto(test, "Scienze", 9);
        r.addVoto(test, "Matematica", 5);
        r.addVoto(test, "Italiano", 2);
        r.addVoto(test, "Scienze", 7);
        r.printVotiAlunno(test);
    }

    @Test
    public void mediaComplessiva(){
        r = new Registro();
        String test = r.addStudente();
        r.addVoto(test, "Matematica", 2);
        r.addVoto(test, "Italiano", 4);
        r.addVoto(test, "Scienze", 9);
        r.addVoto(test, "Scienze", 1);
        assertEquals(test, 3.6, r.mediaComplessivaAlunno(test), 0.1);
    }

    @Test
    public void mediaMateria(){
        String test = r.addStudente();
        r.addVoto(test, "Matematica", 1);
        r.addVoto(test, "Matematica", 5);
        r.addVoto(test, "Matematica", 9);
        assertEquals(test, 5.0, r.mediaMateriaAlunno(test, "Matematica"), 0);
    }

    @Test
    public void alunniInsufficienti(){
        r = new Registro();
        String a1 = r.addStudente();
        String a2 = r.addStudente();
        String a3 = r.addStudente();
        r.addVoto(a1, "Matematica", 2);
        r.addVoto(a2, "Italiano", 10);
        r.addVoto(a3, "Scienze", 5);
        String[] studIns = r.studentiMediaInsufficienteGenerale();
        assertTrue(studIns[0].compareTo(a1) == 0 && studIns[1].compareTo(a3) == 0 && studIns.length == 2);
    }

    @Test
    public void alunniMediaInsufficienteMate(){
        r = new Registro();
        String a1 = r.addStudente();
        String a2 = r.addStudente();
        r.addVoto(a1, "Matematica", 2);
        r.addVoto(a2, "Scienze", 5);
        String[] studIns = r.studenteMediaInsufficienteMateria("Matematica");
        assertTrue(studIns[0].compareTo(a1) == 0 && studIns.length == 1);
    }

    @Test
    public void alunniNoInsufficienze(){
        String a3 = r.addStudente();
        String a4 = r.addStudente();
        r.addVoto(a3, "Matematica", 4);
        r.addVoto(a3, "Italiano", 10);
        r.addVoto(a4, "Scienze", 9);
        String[] studSuff = r.studentiNessunVotoInsufficiente();
        assertTrue(studSuff[0] == a4 && studSuff.length == 1);
    }
}
