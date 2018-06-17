# Import the modules
import sys
import random
import datetime

# ----------------------------- CONSTANTS -----------------------------

CLIENT_PATTERN = "INSERT INTO BROUILLES.CLIENT(ID,ADRESSE,CATEGORIE,DENOMINATION,MAIL_1,MAIL_2,NOM,NUMERO,PRENOM,TELEPHONE_1,TELEPHONE_2) VALUES ([INDEX_CLIENT],'avenue des Brouilles','PARTICULIER','un client','toto@gmail.com','toto2@gmail.com','Vienne[INDEX_CLIENT]','[NUMERO_CLIENT]','Julien[INDEX_CLIENT]','0609632255','0609632255');"
COMMON_EVENEMENT_PATTERN = "INSERT INTO BROUILLES.COMMON_EVENEMENT(ID, NB_PERSONNES, NOM, NUMERO, DEBUT, FIN, TYPE_EVENEMENT, ETAT, CLIENT_ID ) VALUES ([INDEX_EVT], 10, 'mariage [INDEX_EVT]', '[NUMERO_EVT]', TO_TIMESTAMP('[DEBUT_EVT]', 'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP('[FIN_EVT]', 'YYYY-MM-DD HH24:MI'), 'MARIAGE', 'PRE_RESERVE', [CLIENT_ID]);";
MARIAGE_PATTERN = "INSERT INTO BROUILLES.EVT_MARIAGE (LOCATION_SALLE, AVEC_REPAS, AVEC_COCKTAIL, AVEC_BRUNCH, NB_PERSONNES_REPAS, NB_PERSONNES_COCKTAIL, COMMON_EVENEMENT) VALUES (0, 0, 0, 0, 10, 10, [INDEX_EVT]);";

POSTIT_PATTERN = "INSERT INTO BROUILLES.POST_IT(ID, DATE_PI, NOM) VALUES ([INDEX_POST_IT], TO_TIMESTAMP('[DATE_PI]', 'YYYY-MM-DD HH24:MI'), '[NOM_PI][INDEX_POST_IT]');";

file = open("load.sql","w")

# ----------------------------- METHODS -----------------------------
def creerClientsEtEvenements(nbClients, nbEvtsParClient):

    print '>>> Debut creation des clients et evenements'
    
    today = datetime.datetime.now();
    debutBase = today.replace(hour=18, minute=0, second = 0);
    finBase = today.replace(hour=23, minute=0, second = 0);
    
    for ic in range(1, nbClients + 1):
        
        # --- CLIENTS -----
        numeroClient = 'C-2018-' + '%05d' % ic
        client = CLIENT_PATTERN.replace("[NUMERO_CLIENT]", numeroClient);
        client = client.replace("[INDEX_CLIENT]", str(ic));
        file.write('\n') 
        file.write(client) 
        file.write('\n') 

        # --- EVENEMENTS -----
        for j in range(1, nbEvtsParClient +1):

            iEvt = (ic - 1) * nbEvtsParClient + j;
            debut = debutBase + datetime.timedelta(days=iEvt - 1)
            fin = finBase + datetime.timedelta(days=iEvt - 1)
            debut = debut.strftime('%Y-%m-%d %H:%M')
            fin = fin.strftime('%Y-%m-%d %H:%M')
            numeroEvenement = 'E-2018-' + '%05d' % iEvt
            
            common = COMMON_EVENEMENT_PATTERN.replace("[INDEX_EVT]", str(iEvt));
            common = common.replace("[DEBUT_EVT]", debut);
            common = common.replace("[FIN_EVT]", fin);
            common = common.replace("[NUMERO_EVT]", numeroEvenement);
            common = common.replace("[CLIENT_ID]", str(ic));
            
            mariage = MARIAGE_PATTERN.replace("[INDEX_EVT]", str(iEvt));
            
            file.write(common) 
            file.write('\n') 
            file.write(mariage)
            file.write('\n')
        
    file.write('\n')
    
    print '<<< Fin creation des clients et evenements'
    
def creerGroupesDePostIts(nbGroupes):

    print '>>> Debut creation des post-its'
    
    today = datetime.datetime.now();
    date10h = today.replace(hour=10, minute=0, second = 0);
    date14h30 = today.replace(hour=14, minute=30, second = 0);
    date18h45 = today.replace(hour=18, minute=45, second = 0);
    
    for i in range(1, nbGroupes + 1):
        
        ip = (i - 1) * 3;
        
        # --- POST_IT -----
       
        postit10h = POSTIT_PATTERN.replace("[INDEX_POST_IT]", str(ip + 1));
        datePi10h = date10h + datetime.timedelta(days=i - 1)
        datePi10h = datePi10h.strftime('%Y-%m-%d %H:%M')
        postit10h = postit10h.replace("[DATE_PI]", datePi10h); 
        postit10h = postit10h.replace("[NOM_PI]", 'Aller chercher le pain '); 
        
        file.write(postit10h) 
        file.write('\n') 
        
        postit14h30 = POSTIT_PATTERN.replace("[INDEX_POST_IT]", str(ip + 2));
        datePi14h30 = date14h30 + datetime.timedelta(days=i - 1)
        datePi14h30 = datePi14h30.strftime('%Y-%m-%d %H:%M')
        postit14h30 = postit14h30.replace("[DATE_PI]", datePi14h30); 
        postit14h30 = postit14h30.replace("[NOM_PI]", 'Reception des gateaux'); 
        
        file.write(postit14h30) 
        file.write('\n') 
        
        postit18h45 = POSTIT_PATTERN.replace("[INDEX_POST_IT]", str(ip + 3));
        datePi18h45 = date18h45 + datetime.timedelta(days=i - 1)
        datePi18h45 = datePi18h45.strftime('%Y-%m-%d %H:%M')
        postit18h45 = postit18h45.replace("[DATE_PI]", datePi18h45); 
        postit18h45 = postit18h45.replace("[NOM_PI]", 'Voir le traiteur'); 
        
        file.write(postit18h45) 
        file.write('\n') 
        
    file.write('\n')
    
    print '>>> Fin creation des post-its'

# ----------------------------- MAIN -----------------------------
if __name__ == '__main__':
    creerClientsEtEvenements(10,3)
    creerGroupesDePostIts(10)
    file.close();
