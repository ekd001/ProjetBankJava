CREATE TABLE Client (
    id INT NOT NULL UNIQUE ,
    noms VARCHAR(50) NOT NULL,
    prenoms VARCHAR(50) NOT NULL,
	dt_naissance DATE NOT NULL,
	adresse VARCHAR(50) NOT NULL ,
	contact INT NOT NULL UNIQUE,
	email VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(id)
)

CREATE TABLE Compte(
	num INT PRIMARY KEY NOT NULL UNIQUE,
	solde DECIMAL(12,2),
	codePin NUMERIC(5) NOT NULL UNIQUE,
	id INT NOT NULL UNIQUE,
	FOREIGN KEY (id) REFERENCES Client ON UPDATE CASCADE ON DELETE CASCADE
)

CREATE TABLE HistoTrans(
	numero INT PRIMARY KEY NOT NULL UNIQUE,
	dateT DATE NOT NULL,
	id INT UNIQUE NOT NULL,
	num INT UNIQUE NOT NULL,
	label VARCHAR(8) NOT NULL,
	FOREIGN KEY (id) REFERENCES Client ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (num) REFERENCES Compte ON UPDATE CASCADE ON DELETE CASCADE
)

	


CREATE OR REPLACE PROCEDURE Depot(v_montant DECIMAL(15,2) , v_numero INT) AS $$
DECLARE
	v_montant_tmp DECIMAL(15,2);
	v_new_montant DECIMAL(15,2);
	v_compte RECORD;
BEGIN
	SELECT * INTO v_compte FROM Compte WHERE num = v_numero;
	
	IF v_compte.etat = TRUE THEN
		v_montant_tmp := v_compte.solde;
		v_new_montant := v_montant_tmp + v_montant;
		UPDATE Compte SET solde = v_new_montant WHERE num = v_numero;
	END IF;

	
END
$$ LANGUAGE plpgsql; 

CREATE OR REPLACE PROCEDURE Retrait(v_montant DECIMAL(15,2) , v_numero INT) AS $$
DECLARE
	
	v_new_montant DECIMAL(15,2);
	v_compte RECORD;
BEGIN
	SELECT * INTO v_compte FROM Compte WHERE num = v_numero;
	
	IF v_compte.etat = TRUE THEN
		v_new_montant := v_compte.solde - v_montant;
		UPDATE Compte SET solde = v_new_montant WHERE num = v_numero;
	END IF;

	
END
$$ LANGUAGE plpgsql;
	