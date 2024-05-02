SELECT * FROM Compte;
SELECT * FROM histotrans;
UPDATE Compte SET solde = 0;
SELECT c.* FROM client c;
SELECT cl.* FROM Client cl ,Compte c  WHERE cl.id = 1 AND c.id = 1 AND c.num = 12345678 AND c.codepin = 12345;
UPDATE Client SET  = 12345678555;
CREATE OR REPLACE PROCEDURE Transfert(v_montant DECIMAL(15,2) , v_num_send INT, v_num_receive INT) AS $$
DECLARE
	v_montant_tmp DECIMAL(15,2);
	v_montant_tmp_r DECIMAL(15,2);
	v_new_montant DECIMAL(15,2);
	v_new_montant_r DECIMAL(15,2);
	v_compte RECORD;
	v_compte_r RECORD;
BEGIN
	SELECT * INTO v_compte FROM Compte WHERE num = v_num_send;
	SELECT * INTO v_compte_r FROM Compte WHERE num = v_num_receive;
	IF v_compte.etat = TRUE AND v_compte_r.etat = TRUE THEN
		v_montant_tmp := v_compte.solde;
		v_new_montant := v_montant_tmp - v_montant;
		v_montant_tmp_r := v_compte_r.solde;
		v_new_montant_r := v_montant_tmp_r + v_montant;
		UPDATE Compte SET solde = v_new_montant WHERE num = v_num_send;
		UPDATE Compte SET solde = v_new_montant_r WHERE num = v_num_receive;
	END IF;
	
END
$$ LANGUAGE plpgsql;

CALL Transfert(50000,12345678,15987532);
CALL Retrait(,124);
UPDATE Compte SET etat = false;
UPDATE Compte SET solde;