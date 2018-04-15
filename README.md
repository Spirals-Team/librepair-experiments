# DevOpsProject

## Utilisation de maven
Pour cette commande on peut specifier un parametre -Tx (x etant le nombre de threads a executer avec)

mvn [cycle de vie]


Les cycle de vie : 
------------------
L'execution d'une de ces etapes va executer toute etape d'avant

validate :  valide que le projet est correct et que toutes les infos necessaires sont disponibles

compile : compile

test : lance les test

package :  package les sources compil ́ees dans un format distribuable (par ex JAR)

integration-test : test d'integration

verify :  Lance les tests pour verifier la qualite du package

install :  Installe le package dans le depot local

deploy :  Copie le package final dans un depot distant pour le partager

(Repris depuis le cours de DevOps de Thomas Ropars sur l'integration continue)


Maven site :
------------
Pour la couverture de code, utiliser la commande "mvn site" qui va faire un site dans "target/site/index.html"


How to push to a repository :
-----------------------------

1. Make sure you are working on a branch 
	if you don't work on a branch : 
		git branch : list current branches
		git branch "nameBranchLocal" : create a branch with "nameBranchLocal"
		git checkout "nameBranchLocal" : move to the "nameBranchLocal"

2. Commit changes of your local repository
		
3. Fetch and merge the changes to the current branch
		git remote -v : list the remote repositories
		git fetch "nameRemoteRepo" : fetches the files made to the remote repository "name"
		git merge "nameRemoteRepo"/"nameBranchRemote" "nameBranchLocal" : Merges the files on the "nameRemoteRepo"/"nameBranchRemote" with the "nameBranchLocal" branchs
		
4. Execute maven and clean generated files from pushing
		mvn install : lets you execute the test life cycle which will test your files for errors
		bash scriptCleanUp.sh or ./scriptCleanUp.sh : clears the local repository cache from the files that were generated
		Note : This will not delete them from your local repository, it will just enforce the .gitignore rules
				Explanation : The files that are written into the .gitignore will still be commited if they are present in the cache of the git repository
				
5. Push the changes to the remote branch
	Recommit if needed
	Push them to the remote branch : 
		git push "nameRemoteRepo" "nameBranchLocal" : This creates a remote repository branch with the same name as the local branch (if it doesn't exist), if it does exist, will push to the remote branch otherwise
		