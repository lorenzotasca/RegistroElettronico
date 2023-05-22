package it.itispaleocapa.tascal;

import java.util.*;
import java.util.function.*;

public class Registro {
    private HashMap<String, Studente> studenti = new HashMap<String, Studente>();
    private static int idCount = 0;

    public String addStudente() {
        String id = new Integer(idCount).toString();
        studenti.put(id, new Studente());
        idCount++;
        return id;
    }

    public void addVoto(String utente, String materia, int voto){
        if (voto > 10 || voto < 1) 
            throw new VotoOutOfBoundsException();
        studenti.get(utente).addVoto(materia, voto);
    }

    private Function<LinkedList<Integer>, Double> mediaVotiMateria = voti -> {
        BinaryOperator<Double> comb = (Double a, Double b) -> {
            return a;
        };
        return voti.stream().reduce(0.0, new BiFunction<Double,Integer,Double>() {
            public Double apply(Double media, Integer voto) {
                return media + (double)voto / voti.size();
            }
        }, comb);
    };

    private Function<HashMap<String, LinkedList<Integer>>, Double> mediaVotiPagella = pagella -> {
        BinaryOperator<Double> comb = (Double a, Double b) -> {
            return a;
        };
        pagella.entrySet().removeIf(e -> e.getValue().isEmpty());
        return pagella.values().stream().reduce(0.0, new BiFunction<Double, LinkedList<Integer>, Double>() {
            public Double apply(Double media, LinkedList<Integer> materia) {
                return media + mediaVotiMateria.apply(materia) / pagella.values().size();
            }
        }, comb);
    };

    public void printVotiAlunno(String alunno){
        studenti.get(alunno).getVoti().forEach(new BiConsumer<String,LinkedList<Integer>>() {
            public void accept(String materia, LinkedList<Integer> voti) {
                voti.forEach(voto -> System.out.println(materia + " : " + voto));  
            }
        });
    }

    public double mediaMateriaAlunno(String alunno, String materia){
        return mediaVotiMateria.apply(studenti.get(alunno).getVotiMateria(materia));
    }

    public double mediaComplessivaAlunno(String alunno){
        return mediaVotiPagella.apply(studenti.get(alunno).getVoti());
    }

    public String[] studentiMediaInsufficienteGenerale() {
        return studenti.keySet().stream().filter(new Predicate<String>() {
            public boolean test(String s) {
                if (mediaVotiPagella.apply(studenti.get(s).getVoti()) < 6)
                    return true;
                return false;
            }
        }).toArray(String[]::new);
    }
    public String[] studenteMediaInsufficienteMateria(String materia) {
        return studenti.keySet().stream().filter(new Predicate<String>() {
            public boolean test(String s) {
                if (studenti.get(s).getVotiMateria(materia).size() > 0 && mediaVotiMateria.apply(studenti.get(s).getVotiMateria(materia)) < 6)
                    return true;
                return false;
            }
        }).toArray(String[]::new);
    }
    public String[] studentiNessunVotoInsufficiente() {
        return studenti.keySet().stream().filter(new Predicate<String>() {
            public boolean test(String s) {
                for (LinkedList<Integer> materia : studenti.get(s).getVoti().values()) {
                    for (int voto : materia) {
                        if (voto < 6) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }).toArray(String[]::new);
    }
}
