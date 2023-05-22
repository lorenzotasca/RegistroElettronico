package it.itispaleocapa.tascal;

import java.util.*;

public class Studente {
    private HashMap<String, LinkedList<Integer>> voti;
    public Studente(){
        voti = new HashMap<String, LinkedList<Integer>>();
        voti.put("Matematica", new LinkedList<Integer>());
        voti.put("Italiano", new LinkedList<Integer>());
        voti.put("Scienze", new LinkedList<Integer>());
    }
    public HashMap<String, LinkedList<Integer>> getVoti(){
        return voti;
    }
    public LinkedList<Integer> getVotiMateria(String materia) {
        if (voti.get(materia) == null)
            throw new MateriaNotExistException();
        return voti.get(materia);
    }
    public void addVoto(String materia, int voto) {
        if (voti.get(materia) == null)
            throw new MateriaNotExistException();
        voti.get(materia).addLast(voto);
    }
}
