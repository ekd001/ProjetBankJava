/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acteurSystem;

import java.awt.AWTException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import objetSystem.Compte;
import objetSystem.HistoriqueTransaction;
import objetSystem.Systeme;

/**
 *
 * @author EKLOU Dodji
 */
public class Client {

    
    private int id;
    private String noms;
    private String prenoms;
    private LocalDate dateDeNaissance;
    private String adresse;
    private int contact;
    private String email;
    private Systeme accesSys;
    private List<Compte> listeCompte = new ArrayList<>();
    private List<HistoriqueTransaction> ht = new ArrayList<>();
    
    public Client (int id,String noms,String prenoms,int annee,int mois,int jour,String adresse,int contact,String email,Systeme sys){
        this.id = id;
        this.noms = noms;
        this.prenoms = prenoms;
        this.dateDeNaissance = LocalDate.of(annee, mois, jour);
        this.adresse = adresse;
        this.contact = contact;
        this.email = email;
        this.accesSys = sys;
    }
    
    public  Client (){
    }
   // Ascesseurs de Id
    public int getId(){
        return id;
    }
    //Ascesseurs de 
    public void setId(int id){
        this.id = id;
    }
    //Ascesseurs de noms
    public String getNoms(){
        return noms;
    }
    
    public void setNoms(String noms){
        this.noms = noms;
    }
     //Ascesseurs de prenom
    public String getPrenoms(){
        return prenoms;
    }
    
    public void setPrenoms(String Prenoms){
        this.prenoms = prenoms;
    }
     //Ascesseurs de dateDeNaissance
    public LocalDate getDateDeNaissance(){
        return dateDeNaissance;
    }
    
    public void setDateDeNaissance(LocalDate dateDeNaissance){
        this.dateDeNaissance = dateDeNaissance;
    }
    //Ascesseurs de adresse
    public String getAdresse(){
        return adresse;
    }
    
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
     //Ascesseurs de contact
    public int getContact(){
        return contact;
    }
    
    public void setContact(int contact){
        this.contact = contact;
    }
    //Ascesseurs de email
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    
    public void faireDepot(double montant, int num){
        this.accesSys.depotDuClient(montant, num);
    }
    
    public void faireRetrait(double montant, int num){
        this.accesSys.retraitDuClient(montant, num);
    }
    
    public void demandeReleve(int id, int num) throws AWTException{
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = date.format(formatter);
       
        String[] s1 = s.split("-");
        List<String> s2 = new ArrayList<>(Arrays.asList(s1));
        int annee = Integer.parseInt(s2.get(0));
        int mois = Integer.parseInt(s2.get(1));
        int jour = Integer.parseInt(s2.get(2));
        this.accesSys.releveDeCompte(id, num, jour, mois, annee);
        System.out.println("");
        this.accesSys.saisieInt("Taper un nombre pour fermer : ");
    }
    
    public void faireTransfert(double montant,int num) throws AWTException{
        this.accesSys.transfertDuClient(montant, num);
    }
    
    public void ajouterCompte(Compte c){
        this.listeCompte.add(c);
    }
    
    public Compte getCompte(){
        return this.listeCompte.get(0);
    }
    
    public void afficherLeCompte(int id) throws AWTException{
        this.accesSys.afficherCompte(id);
        System.out.println("");
        this.accesSys.saisieInt("Taper un nombre pour fermer : ");
    }
    
    public void addHistoTransactionCl(HistoriqueTransaction ht){
         
    }
}
