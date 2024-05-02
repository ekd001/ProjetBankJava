/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetSystem;

import acteurSystem.Agent;
import acteurSystem.Client;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author EKLOU Dodji
 */
public class Systeme {
    private boolean lancement;
    public Systeme(){
    
    }
    
//Module du systeme    
    
    public void setLancement(boolean b){
        this.lancement = b;
    }
    
    
    public void etatCompte(int x, int num){
        switch (x) {
            case 0:this.gelerCompte(num);
                break;
            case 1:this.activerCompte(num);
                break;
            default:
                System.out.println("Taper 0 pour desactiver et 1 pour activer le compte");
                break;
        }
    }
    
    public void ajouterCompte(Compte c){
       
        try{
           System.out.println("En cours d'enregistrement du compte.... ");
            Class.forName("org.postgresql.Driver");
           Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            st.executeUpdate("INSERT INTO Compte VALUES("+c.getNum()+","+c.getSolde()+","+c.getCodePin()+","+c.getClient()+")");
            st.close();
            connect.close();
            System.out.println("Enrégistrement terminé");
           
            
        }catch(ClassNotFoundException | SQLException e){
              System.out.println("Erreur de saisie des données ou les données du compte existent dejà");
              this.attente();
        }
    }
    
    public void ajouterClient(Client cl) throws AWTException{
         try{
           if(this.verifierExistenceClient(cl.getId()) == false){
                 System.out.println("En cours d'enregistrement du client....");
             
                Class.forName("org.postgresql.Driver");
                Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");

                Statement st = connect.createStatement();
                st.executeUpdate("INSERT INTO Client VALUES("+cl.getId()+",'"+cl.getNoms()+"','"+cl.getPrenoms()+"','"+cl.getDateDeNaissance()+"','"+cl.getAdresse()+"',"+cl.getContact()+",'"+cl.getEmail()+"')");
                System.out.println("Enregistrement terminé");
                st.close();
                connect.close();
                this.attente();
           }else{
                System.out.println("Erreur de saisie des données ou les données du client existent dejà");
                this.attente();
           }
          
         }catch(ClassNotFoundException | SQLException e){
            System.out.println("Erreur de saisie des données ou les données du client existent dejà");
            this.attente();
        }
    }
    
    public void supprCompte(int id, int num, int codePIN) throws AWTException{
        try{
            if(this.verifierExistenceCompte(num)){
                System.out.println("Suppression du compte en cours....");
                Class.forName("org.postgresql.Driver");

                Class.forName("org.postgresql.Driver");
                Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");

                Statement st = connect.createStatement();
                st.executeUpdate("DELETE FROM Compte WHERE num = "+num+" AND codepin = "+codePIN+" AND Compte.id = "+id+"");

                st.close();
                connect.close();
                System.out.println("Compte supprimé");
                this.attente(); 
            }else{
                System.out.println("Error: compte non supprimé, données mal saisies ou compte inexistant");
                this.attente();
            }
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: compte non supprimé, données mal saisies ou compte inexistant");
            this.attente();
        }
        
    }
    
    public void supprClient(int id) throws AWTException{
        try{
            if(this.verifierExistenceClient(id)){
                System.out.println("Suppression du client en cours....");
                Class.forName("org.postgresql.Driver");
            
                Class.forName("org.postgresql.Driver");
                Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");

                Statement st = connect.createStatement();
                st.executeUpdate("DELETE FROM Client WHERE id = "+id+"");
                st.executeUpdate("DELETE FROM Compte WHERE Compte.id = "+id+"");
                st.close();
                connect.close();
                System.out.println("Client supprimé");
                this.attente();
            }else{
                 System.out.println("Error: client non supprimé ou client n'existe pas");
                 this.attente();
            }
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: client non supprimé ou client n'existe pas");
            this.attente();
        }
    }
    
    public void gelerCompte(int num){
        try{
            System.out.println("Desactivation du compte en cours....");
            
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            st.executeUpdate("UPDATE Compte SET etat = FALSE WHERE num = "+num+"");
            
            st.close();
            connect.close();
            System.out.println("Compte desactivé");
            this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: le numero de compte n'existe pas");
            this.attente();
        }
    }
    
    public void activerCompte(int num){
        try{
            System.out.println("Activation du compte en cours....");
            
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            st.executeUpdate("UPDATE Compte SET etat = TRUE WHERE num = "+num+"");
            
            st.close();
            connect.close();
            System.out.println("Compte activé");
            this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: le numero de compte n'existe pas");
            this.attente();
        }
    }
    
    public void depotDuClient(double montant, int num){
       
        try{
            System.out.println("Transaction de dépôt en cours....");
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT etat FROM Compte WHERE num = "+num+"");
            while(rs.next()){
                boolean verifie = rs.getBoolean("etat");
                if(verifie){
                
                    if(montant > 0){
                        Statement st0 = connect.createStatement();
                        st0.executeUpdate("CALL Depot("+montant+","+num+")");
                        System.out.println("Transaction de dépôt de "+montant+" sur le compte de numero "+num+"");
                        st0.close();
                        this.etablirHistoTrans("depot", montant, num);
                        this.attente();
                    }else{
                        System.out.println("Montant doit être plus grand que 0");
                        this.attente();
                    }
   
            }else{
                System.out.println("Le compte de numero "+num+" est bloqué");
                this.attente();
            }
           }
            st.close();
            connect.close();
            }catch(ClassNotFoundException | SQLException e){
            System.out.println("Transaction de dépôt echoué");
            this.attente();
        }
        
      
    }
    
    public void transfertDuClient(double montant, int num) throws AWTException{
        
        try{
            int numr = this.saisieInt("Entrer le numéro de compte du recepteur : ");
            System.out.println("Transaction de dépôt en cours....");
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT etat FROM Compte WHERE num = "+num+"");
            while(rs.next()){
                boolean verifie = rs.getBoolean("etat");
                if(verifie){
                
                    if(montant > 0){
                        Statement st0 = connect.createStatement();
                        st0.executeUpdate("CALL Transfert("+montant+","+num+","+numr+")");
                        System.out.println("Transaction de dépôt de "+montant+" sur le compte de numero "+numr+"");
                        st0.close();
                        this.etablirHistoTrans("depot sur compte", montant, num);
                        this.attente();
                    }else{
                        System.out.println("Montant doit être plus grand que 0");
                        this.attente();
                    }
   
            }else{
                System.out.println("Le compte de numero "+num+"  ou "+numr+" est bloqué");
                this.attente();
            }
           }
            st.close();
            connect.close();
            }catch(ClassNotFoundException | SQLException e){
            System.out.println("Transaction de dépôt echoué");
            System.out.println("");
            this.attente();
        }
        
         
    }
    
    public void retraitDuClient(double montant, int num){
        try{
            System.out.println("Transaction de retrait en cours....");
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT etat,solde FROM Compte WHERE num = "+num+"");
            while(rs.next()){
                boolean verifie = rs.getBoolean("etat");
                double solde = rs.getDouble("solde");
                
                if(verifie){
                
                    if((montant > 0)&&(solde >= montant)){
                        Statement st0 = connect.createStatement();
                        st0.executeUpdate("CALL Retrait("+montant+","+num+")");
                        System.out.println("Transaction de retrait de "+montant+" sur le compte de numero "+num+"");
                        st0.close();
                        this.etablirHistoTrans("retrait", montant, num);
                        this.attente();
                    }else{
                        System.out.println("Montant doit être plus grand que 0");
                        this.attente();
                    }
   
            }else{
                System.out.println("Le compte de numero "+num+" est bloqué");
                this.attente();
            }
            }
            st.close();
            connect.close();
            }catch(ClassNotFoundException | SQLException e){
            System.out.println("Transaction échoué");
            this.attente();
        }
        
    }
    
    public void etablirHistoTrans(String typeTrans,double montant,int num){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
         
            Statement st0 = connect.createStatement();
            ResultSet rs = st0.executeQuery("SELECT id FROM Compte WHERE num = "+num+"");
            
            while(rs.next()){
                int id1 = rs.getInt("id");
                
                LocalDate dt = LocalDate.now();
                Statement st = connect.createStatement();
                st.executeUpdate("INSERT INTO HistoTrans VALUES(DEFAULT,'"+dt+"',"+id1+","+num+",'"+typeTrans+"',"+montant+")");
                st.close();
            }
            st0.close();
            connect.close();
            System.out.println("Vous avez éffectuer un "+typeTrans+" de montant "+montant+" de votre compte de numero "+num+" le "+LocalDate.now()+"");
            this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            this.attente();
        }
    }
    
    public void afficherCompte(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT c.num,c.solde,c.codepin,c.etat,cl.noms,cl.prenoms FROM Compte c,Client cl WHERE c.id = cl.id");
            System.out.println("");
            System.out.println("*************LISTE DES COMPTES***************");
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.format("|%-10s|%-25s|%-10s|%-15s|%-26s|\n","numero","Solde","CodePIN","etat","Proprietaire");

            while(rs.next()){
                DecimalFormat format = new DecimalFormat("#.##");
                String solde = format.format(rs.getDouble("solde"));
                String etat;
                if(rs.getBoolean("etat")){etat = "Active";}else{etat = "Desactiver";}
                System.out.println("--------------------------------------------------------------------------------------------");
                System.out.format("|%-10s|%-25s|%-10s|%-15s|%-26s|\n",Integer.toString(rs.getInt("num")),solde,Integer.toString(rs.getInt("codepin")),etat,rs.getString("noms")+" "+rs.getString("prenoms"));

            }
        System.out.println("--------------------------------------------------------------------------------------------");
        this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Pas de compte enrégistré");
            this.attente();
        }
        
        
       
    }
    
    
    public void afficherCompte(int id){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT c.num,c.solde,c.codepin,c.etat,cl.noms,cl.prenoms FROM Compte c,Client cl WHERE c.id = "+id+" AND cl.id = "+id+"");
            System.out.println("");
            System.out.println("*************LISTE DE VOS COMPTES***************");
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.format("|%-10s|%-25s|%-10s|%-15s|%-26s|\n","numero","Solde","CodePIN","etat","Proprietaire");
            while(rs.next()){
                DecimalFormat format = new DecimalFormat("#.##");
                String solde = format.format(rs.getDouble("solde"));
                String etat;
                if(rs.getBoolean("etat")){etat = "Active";}else{etat = "Desactiver";}
                System.out.println("--------------------------------------------------------------------------------------------");
                System.out.format("|%-10s|%-25s|%-10s|%-15s|%-26s|\n",Integer.toString(rs.getInt("num")),solde,Integer.toString(rs.getInt("codepin")),etat,rs.getString("noms")+" "+rs.getString("prenoms"));

            }
        System.out.println("--------------------------------------------------------------------------------------------");
        this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Pas de compte enrégistré");
            this.attente();
        }
        
        
        
    }
    
    public void afficherClient(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Client");
            
            System.out.println("");
            System.out.println("*************LISTE DES CLIENTS***************");
            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("|%-10s|%-20s|%-24s|%-10s|%-25s|%-15s|%-26s|\n","Id","nom","prenom","Date de naissance","Adresse","Contact","Email");
            while(rs.next()){
                 
                 System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                 System.out.format("|%-10s|%-20s|%-24s|%-17s|%-25s|%-15s|%-26s|\n",Integer.toString(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),Integer.toString(rs.getInt(6)),rs.getString(7));
                 
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Pas d'information sur les clients");
            this.attente();
        }
    }
    
    public void afficherClient(int id){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Client WHERE id = "+id+"");
            
            System.out.println("");
            System.out.println("*************INFORMATIONS DU CLIENT***************");
            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("|%-10s|%-20s|%-24s|%-10s|%-25s|%-15s|%-26s|\n","Id","nom","prenom","Date de naissance","Adresse","Contact","Email");
            
            while(rs.next()){
                 System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
                 System.out.format("|%-10s|%-20s|%-24s|%-17s|%-25s|%-15s|%-26s|\n",Integer.toString(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),Integer.toString(rs.getInt(6)),rs.getString(7));
                 
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
            this.attente();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Le client n'est pas enrégistré sur la base de donnée ");
            this.attente();
        }
        
    }
    
    public void releveDeCompte(int id, int num, int jour,int mois,int annee){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT numero,datet,num,label,montant FROM HistoTrans WHERE id = "+id+" AND num = "+num+" AND datet BETWEEN DATE'"+annee+"-"+mois+"-1' AND DATE'"+annee+"-"+mois+"-"+jour+"'");
            
            System.out.println("");
            System.out.println("***********RELEVER DE COMPTE***********");
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------");
            System.out.format("|%-10s|%-10s|%-10s|%-10s|%-22s|%n","numero","date","numero de compte","type","montant");
            while(rs.next()){
                DecimalFormat format = new DecimalFormat("#.##");
                String montant = format.format(rs.getDouble("montant"));
                System.out.println("--------------------------------------------------------------------------");
                
                System.out.format("|%-10d|%-10s|%-16d|%-10s|%-22s|%n",rs.getInt(1),rs.getString("datet"),rs.getInt("num"),rs.getString("label"),montant);
            }
            System.out.println("--------------------------------------------------------------------------");
            this.attente();
            
        
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Pas de transaction éffecutée ou compte inexistant ou client inexistant");
            this.attente();
        }
    }
    
    public void bienvenue(){
        System.out.println("*****************************************************");
        System.out.println("\t\tBIENVENUE CHEZ POObank\t\t");
        System.out.println("*****************************************************");
    }
    
    public boolean getlancer(){
        return this.lancement;
    }
    
    public  String saisieString(String elt) throws AWTException{
        try{
            Scanner lireString = new Scanner(System.in);
            System.out.print(""+elt+"");
            return lireString.nextLine();
        }catch(Exception e){
            System.out.println("Problème survenu au niveau de saisieString");
            this.attente();
            this.effacerConsole();
            this.connexionAuSysteme();
        }
        return null;
    }
    
    public int saisieInt(String elt) throws AWTException{
        try{
             Scanner lireInt = new Scanner(System.in);
            System.out.print(""+elt+"");
            return lireInt.nextInt();   
        }catch(Exception e){
            System.out.println("    Saisir un nombre");
            this.attente();
            this.effacerConsole();
            this.connexionAuSysteme();
        }
        return 0;
       
    }
    
    public double saisieDouble(String elt) throws AWTException{
        try{
            Scanner lireDouble = new Scanner(System.in);
            System.out.print(""+elt+"");
            return lireDouble.nextDouble();
        }catch(Exception e){
            System.out.println("    Saisir un nombre");
            this.attente();
            this.effacerConsole();
            this.connexionAuSysteme();
        }
        return 0;
    }
    
    public void effacerConsole() throws AWTException {
        Robot rob = new Robot();
        try {
            rob.keyPress(KeyEvent.VK_CONTROL); // press "CTRL"
            rob.keyPress(KeyEvent.VK_L); // press "L"
            rob.keyRelease(KeyEvent.VK_L); // unpress "L"
            rob.keyRelease(KeyEvent.VK_CONTROL); // unpress "CTRL"
            Thread.sleep(100); // add delay in milisecond, if not there will automatically stop after clear
            this.bienvenue();
        } catch (InterruptedException e){
                System.out.println("Problème survenu au niveau de la méthode clear");
        }
        
    }
    
    
    public boolean verifierNombre(int x, int taille){
        try{
            String n = Integer.toString(x);
            int t = n.length();
            return t == taille;
        }catch(Exception e){
            System.out.println("Problème survenu au niveau de la méthode clear");
        }
        return false;
    }
    
    public void attente(){
        try{
            Thread.sleep(1800);
        }catch(InterruptedException e){
            System.out.println("Problème survenu au niveau de la méthode attente ");
        }
    }
    
    public boolean verifierExistenceClient(int x) throws AWTException{
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM Client WHERE id = "+x+" ");
            while(rs.next()){
                if(rs.getInt("id") == x){
                    return true;
                }
                
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Le client n'existe pas dans la base");
            this.attente();
            this.effacerConsole();
            this.choisirActionAgent();
        }
        return false;
    }
    
     public boolean verifierExistenceCompte(int x) throws AWTException{
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
            
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT num FROM Compte WHERE num = "+x+" ");
            while(rs.next()){
                if(rs.getInt("num") == x){
                    return true;
                }
                
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Le compte n'existe pas dans la base");
            this.attente();
            this.effacerConsole();
            this.choisirActionAgent();
        }
        return false;
    }
    
    
    
//************************************************************************************************************    
//******************************************Fonctionnemt du système
    
    public void execution() throws AWTException{
        try{
            this.effacerConsole();
            this.setLancement(true);
            while(this.getlancer()){
                this.connexionAuSysteme();
            }
        }catch(AWTException e){
            System.out.println("Demarrage du systeme echouè");
        }
       
    }
    
    
    public void connexionAuSysteme(){
        try{
            int choix;
        System.out.println("Options de connexion au Systeme");
        System.out.println("    1- Client");
        System.out.println("    2- Agent");
        System.out.println("    3- Fermer le systeme");
      
        choix = this.saisieInt("Choisissez votre options : ");
        switch(choix){
            case 1: this.effacerConsole(); this.ChoisirActionClient(this.authentificationClient()); break;
            
            case 2:this.effacerConsole(); this.authentificationAgent();this.effacerConsole();this.choisirActionAgent(); break;
            
            case 3: this.effacerConsole();this.setLancement(false);break;
            
            default:this.effacerConsole();connexionAuSysteme(); break;
            }
        }catch(AWTException e){
            System.out.println("Problème survenu au niveau de la méthode connexionAuSysteme");
            this.attente();
        }
        
    }
    
     public void authentificationAgent() throws AWTException{
        int y1 = saisieInt("    Veuillez entrez votre Identifiant : ");
        int y2 = saisieInt("    Veuillez entrez votre mot de passe : ");
        
        if(y1 != 1){
            
            System.out.println("    Identifiant incorrect");
            this.attente();
            this.effacerConsole();
            this.connexionAuSysteme();
        }
        
        if(y2 != 159753){
            
            System.out.println("    Mot de passe incorrect");
            this.attente();
            this.effacerConsole();
            this.connexionAuSysteme();
        }
    }
    
    
    public void ouvertureCompteSaisie() throws AWTException{
        System.out.println("Information du client");
        try{
            int x1 = this.saisieInt("    Identifiant du Client : ");
            String x2 = this.saisieString("    Nom du Client : ");
            String x9 = this.saisieString("    Prenoms du client : ");
            int x3 = this.saisieInt("    Annee de Naissance : ");
            int x4 = this.saisieInt("    Mois de Naissance : ");
            int x5 = this.saisieInt("    Jour de Naissance : ");
            String x6 = this.saisieString("    Adresse du Client : ");
            int x7 = this.saisieInt("    Contact du Client : ");
            String x8 = this.saisieString("    Email du Client : ");

            System.out.println("Information du Compte");
            int y1 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Numero de compte : ");
            int y2 = this.saisieInt("Note : Le Code PIN doit etre de 5 chiffres \n    Le code PIN : ");

            if(this.verifierNombre(y1, 8) == false){
                this.effacerConsole();
                this.ouvertureCompteSaisie();
            }
            
                
            if(this.verifierNombre(y2, 5) == false){
                this.effacerConsole();
                this.ouvertureCompteSaisie();
            }
            
            Agent a = new Agent(1,this);
            a.ouvrirCompte(x1, x2.toUpperCase(), x9, x3, x4, x5, x6, x7, x8, y1, y2);
     
        }catch(AWTException e){
            System.out.println("Information mal saisies");
            this.attente();
            this.effacerConsole();
            this.choisirActionAgent();
        }
        
    }
     
    public void supprimerCompteSaisie() throws AWTException{
       System.out.println("Suppression du Compte");
       int x1 = this.saisieInt("    Identifiant du Client : ");
       int y1 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Numero du compte : ");
       int y2 = this.saisieInt("Note : Le Code PIN doit etre de 5 chiffres \n    Le code PIN : "); 
       
       Agent a = new Agent(1,this);
       a.supprCompte(x1, y1, y2);
      
    }
    
    public void SupprimerClientSaisie() throws AWTException{
        System.out.println("Suppression du CLient");
        int x1 = this.saisieInt("    Identifiant du Client : ");
        
        Agent a = new Agent(1,this);
        a.supprClient(x1);
        
    }
    
   
    public void gererEtatCompte() throws AWTException{
        int etat;
        Agent a = new Agent(1,this);
        System.out.println("Activation ou Desactivation d'un compte");
        System.out.println("    0- Desactiver Compte ");
        System.out.println("    1- Activer Compte ");
        
        etat = this.saisieInt("Choisissez une option : ");
        
        switch (etat){
            case 0: 
                int y0 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Numero du compte : ");
                a.gelerCompte(etat, y0);
                break;
            case 1: 
                int y1 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Numero du compte : ");
                a.activerCompte(etat, y1);
                break;
            
        }
       
    }
    
    
     public void releveDeCompteSaisie() throws AWTException{
        System.out.println("Etablir le releve de compte");
        int x1 = this.saisieInt("    Identifiant du Client : ");
        int x2 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Numero du compte : ");
        System.out.println("La date de la fin du mois selectionné pour établir le releve");
        int y3 = this.saisieInt("   Entrer le jour : ");
        int y2 = this.saisieInt("   Entrer le mois : ");
        int y1 = this.saisieInt("   Entrer l'annee : ");
        
        Agent a = new Agent(1,this);
        a.demandeReleve(x1, x2, y1, y2, y3);
        System.out.println("");
        this.saisieInt("Taper un nombre pour fermer : ");
        
    }
    
    public void listeDeCompteSaisie() throws AWTException{
        System.out.println("Choisir une options");
        System.out.println("    1- Afficher un compte");
        System.out.println("    2- Afficher les comptes");
        int choix = this.saisieInt("Entrer votre choix : ");
        Agent a = new Agent(1,this);
        switch(choix){
            case 1 : a.afficherCompte(this.saisieInt("Entrer l'identifiant du client : "));break;
            case 2 : a.afficherCompte();
        }
        System.out.println("");
        this.saisieInt("Taper un nombre pour fermer : ");
    }
    
    public void listeDeClientSaisie() throws AWTException{
        System.out.println("Choisir une options");
        System.out.println("    1- Afficher un client");
        System.out.println("    2- Afficher les clients");
        int choix = this.saisieInt("Entrer votre choix : ");
        Agent a = new Agent(1,this);
        switch(choix){
            case 1 : a.afficherClient(this.saisieInt("Entrer l'identifiant du client : "));break;
            case 2 : a.afficherClient();
        }
        System.out.println("");
        this.saisieInt("Taper un nombre pour fermer : ");
    }    

    
    public void choisirActionAgent() throws AWTException{
        int choix;
        System.out.println("Selection d'action");
        System.out.println("    1- Ouvrir un compte ");
        System.out.println("    2- Supprimer un compte  ");
        System.out.println("    3- Supprimer un client ");
        System.out.println("    4- Gerer l'activation du compte ");
        System.out.println("    5- Effectuer un releve de compte ");
        System.out.println("    6- Afficher la liste des comptes ");
        System.out.println("    7- Afficher la liste des clients");
        System.out.println("    8- Revenir à l'options de connexion ");
        
        choix = this.saisieInt("Choisissez une option : ");
        
        switch (choix){
            case 1: this.effacerConsole(); this.ouvertureCompteSaisie(); this.effacerConsole(); this.choisirActionAgent(); break;
            
            case 2: this.effacerConsole(); this.supprimerCompteSaisie(); this.effacerConsole(); this.choisirActionAgent(); break;
            
            case 3: this.effacerConsole(); this.SupprimerClientSaisie(); this.effacerConsole(); this.choisirActionAgent(); break;
            
            case 4: this.effacerConsole(); this.gererEtatCompte(); this.effacerConsole(); this.choisirActionAgent(); break;
            
            case 5: this.effacerConsole(); this.releveDeCompteSaisie(); this.effacerConsole();  this.choisirActionAgent(); break;
            
            case 6: this.effacerConsole(); this.listeDeCompteSaisie(); this.effacerConsole(); this.choisirActionAgent();break;
            
            case 7: this.effacerConsole(); this.listeDeClientSaisie(); this.effacerConsole(); this.choisirActionAgent();break;
            
            case 8: this.effacerConsole();this.connexionAuSysteme();break;
            
            default : this.effacerConsole(); this.choisirActionAgent(); break;
        }
    }
    
    
    public Client authentificationClient() throws AWTException{
        int x1 = this.saisieInt("    Veuillez entrez votre Identifiant : ");
        int x2 = this.saisieInt("Note : Le Numero de compte doit etre de 8 chiffres \n    Veuillez entrez votre Numero du compte : ");
        int x3 = this.saisieInt("Note : Le Code PIN doit etre de 5 chiffres \n    Veuillez entrez votre Le code PIN : "); 
        
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BDbank", "postgres", "123456789");
         
            Statement st0 = connect.createStatement();
            ResultSet rs = st0.executeQuery("SELECT cl.* FROM Client cl ,Compte c  WHERE cl.id = "+x1+" AND c.id = "+x1+" AND c.num = "+x2+" AND c.codepin = "+x3+"");
            
            Statement st = connect.createStatement();
            ResultSet rs0 = st.executeQuery("SELECT * FROM Compte WHERE num = "+x2+" AND id = "+x1+"");
            
            while((rs.next()) && (rs0.next()) ){
                String s  = rs.getString("dt_naissance");
                String[] s1 = s.split("-");
                List<String> s2 = new ArrayList<>(Arrays.asList(s1));
                int annee = Integer.parseInt(s2.get(0));
                int mois = Integer.parseInt(s2.get(1));
                int jour = Integer.parseInt(s2.get(2));
                
                Client cl = new Client(rs.getInt("id"), rs.getString("noms"),rs.getString("prenoms"),annee,mois,jour,rs.getString("adresse"),rs.getInt("contact"),rs.getString("email"),this);
                cl.ajouterCompte(new Compte(rs0.getInt("num"),rs0.getDouble("solde"),rs0.getInt("codepin"),rs0.getBoolean("etat"),cl));
               
                return cl;
            }
            st.close();
            connect.close();
            
            st0.close();
            connect.close();
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Le client n'existe pas");
            this.attente();
        }
        return null;
        
    }
    
    public double faireUnDepotSaisie() throws AWTException{
       double montant = this.saisieDouble("Veuillez entrer le montant a deposer : ");
       return montant;
    }
    
    public double faireUnRetraitSaisie() throws AWTException{
        double montant = this.saisieDouble("Veuillez entrer montant a retiré : ");
        return montant;
    }
    
    public void ChoisirActionClient(Client cl) throws AWTException{
        
        
        this.effacerConsole();
        if(cl != null){
            int choix;
            
            System.out.println("Selection d'action");
            System.out.println("    1- Faire un Depot ");
            System.out.println("    2- Faire un Retrait ");
            System.out.println("    3- Demande de Releve ");
            System.out.println("    4- Transfert d'argent");
            System.out.println("    5- Afficher les comptes");
            System.out.println("    6- Retour à l'options de connexion ");
            
            choix = this.saisieInt("Choisissez une option : ");

            switch (choix){
                case 1: this.effacerConsole(); cl.faireDepot(this.faireUnDepotSaisie(), cl.getCompte().getNum()); this.effacerConsole(); this.connexionAuSysteme();
                    break;
                    
                case 2: this.effacerConsole(); cl.faireRetrait(this.faireUnRetraitSaisie(), cl.getCompte().getNum()); this.effacerConsole(); this.connexionAuSysteme();
                    break;
                    
                case 3: this.effacerConsole(); cl.demandeReleve(cl.getId(), cl.getCompte().getNum()); this.effacerConsole(); this.connexionAuSysteme();
                    break;
                    
                case 4: this.effacerConsole(); cl.faireTransfert(this.faireUnDepotSaisie(), cl.getCompte().getNum()); this.effacerConsole(); this.connexionAuSysteme();
                    break;
                
                case 5: this.effacerConsole(); cl.afficherLeCompte(cl.getId()); this.effacerConsole(); this.connexionAuSysteme();
                    break;
                
                case 6: this.effacerConsole(); this.connexionAuSysteme();
                    break;
                default: this.effacerConsole(); this.ChoisirActionClient(cl);
            }
        }else{
            System.out.println("Saisie de données incorrecte");
            this.attente();
            this.effacerConsole(); 
            this.connexionAuSysteme();
        }
        
        
    }   
}
