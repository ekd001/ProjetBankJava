/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetSystem;

import acteurSystem.Client;
import java.time.LocalDate;

/**
 *
 * @author EKLOU Dodji
 */
public class HistoriqueTransaction {
    private int numeroT;
    private LocalDate date;
    private Compte c;
    private Client cl;
    private Systeme sys;
    private String typeTrans;
    private double montant;
    
    public void HistoriqueTransaction (int numeroT, Compte c,Client cl,Systeme sys,String typeTrans,double montant){
        this.numeroT = numeroT;
        this.date = LocalDate.now();
        this.c = c;
        this.cl = cl;
        this.sys = sys;
        this.typeTrans = typeTrans;
        this.montant = montant;
    }
    
    public void HistoriqueTransaction (){
    }
    
    public int getNumeroT(){
        return numeroT;
    }
    
    public void setNumeroT(int numeroT){
        this.numeroT = numeroT;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public void setDate(LocalDate date){
        this.date = date;
    }
    
    public Compte getCompte(){
        return this.c;
    }
    
    public void setCompte(Compte c){
        this.c = c;
    }
    
    public Client getClient(){
        return this.cl;
    }
    
    public void setClient(Client cl){
        this.cl = cl;
    }
     
}
