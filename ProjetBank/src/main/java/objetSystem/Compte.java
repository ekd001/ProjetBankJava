/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetSystem;

import acteurSystem.Client;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EKLOU Dodji
 */
public class Compte {
    private int num;
    private double solde;
    private int codePin;
    private boolean etat;
    private Client cl;
    private List<HistoriqueTransaction> ht = new ArrayList<>();
    
    public Compte (int num, int codePin,Client cl){
        this.num = num;
        this.solde = 0;
        this.codePin = codePin;
        this.etat = true;
        this.cl = cl;
    }
    
    public Compte(int num, double solde, int codepin, boolean etat, Client cl){
        this.num = num;
        this.solde = solde;
        this.codePin = codepin;
        this.etat = etat;
        this.cl = cl;
    }
    
    public Compte(){
        this.etat = true;
    }
    
    // Ascesseurs de num
    public int getNum(){
        return num;
    }
    
    public void setNum(int num){
        this.num = num;
    }
    // Ascesseurs de solde
    public double getSolde(){
        return solde;
    }
    
    public void setSolde(double solde){
        this.solde = solde;
    }
    // Ascesseurs de codePin 
    public int getCodePin(){
        return codePin;
    }
    
    public void setCodePin(int codePin){
        this.codePin = codePin;
    }
    
    public boolean getEtat(){
        return this.etat;
    }
    
    public void setEtat(int etat){
        switch (etat) {
            case 0:
                this.etat = FALSE;
                break;
            case 1:
                this.etat = TRUE;
                break;
            default:
                System.out.println("Taper le nombre 0 ou 1");
                break;
        }
        
    }
    
    public int getClient(){
        return this.cl.getId();
    }
    
    public void setClient(Client cl){
        this.cl = cl;
    }
    
    public void afficher(){
    
    }
    
    public void addHistoTransaction(HistoriqueTransaction ht){
        this.ht.add(ht);
    }
}
