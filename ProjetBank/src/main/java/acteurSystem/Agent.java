/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acteurSystem;

import java.awt.AWTException;
import objetSystem.Compte;
import objetSystem.Systeme;

/**
 *
 * @author EKLOU Dodji
 */
public class Agent {
    private final Systeme accesSys;
    
    public Agent (int id,Systeme sys){
        this.accesSys = sys;
    }
    
   
    
    public void gelerCompte(int x,int num){
        this.accesSys.etatCompte(x,num);
    }
    
    public void activerCompte(int x, int num){
       this.accesSys.etatCompte(x, num);
    }
    
    public void ouvrirCompte(int id1, String nom1, String prenoms1,int annee1,int mois1,int jour1,String adresse1,int contact1, String email1,int num, int codePIN) throws AWTException{
        Client c = new Client(id1,nom1,prenoms1,annee1,mois1,jour1,adresse1,contact1,email1,this.accesSys);
        this.accesSys.ajouterClient(c);
        this.accesSys.ajouterCompte(new Compte(num,codePIN,c));
    }
    
    public void supprCompte(int id1, int num1, int codePIN) throws AWTException{
        this.accesSys.supprCompte(id1,num1,codePIN);
    }
    
    public void supprClient(int id1) throws AWTException{
        this.accesSys.supprClient(id1);
    }
    
    
    public void demandeReleve(int id, int num, int annee, int mois, int jour){
        this.accesSys.releveDeCompte(id, num, jour, mois, annee);
    }
    
    public void afficherCompte(){
        this.accesSys.afficherCompte();
    }
    
    public void afficherCompte(int id){
        this.accesSys.afficherCompte(id);
    }
    
    public void afficherClient(){
        this.accesSys.afficherClient();
    }
    
    public void afficherClient(int id){
        this.accesSys.afficherClient(id);
    }
   
}
