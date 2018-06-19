#!/bin/sh

if [ -z $ROUGE_HOME ];then
	read -p "\$ROUGE_HOME isn't set. Do you want to set it automatically to your .profile file ? (y|n)" answer
	case ${answer:0:1} in
   	    * )
		    echo "export ROUGE_HOME=\"$(pwd)/ROUGE-1.5.5/RELEASE-1.5.5\"" >> ~/.profile
		source ~/.profile
    	    ;;
       	    N|n )
		echo "Please set the \$ROUGE_HOME variable before relaunch installation script."
       		exit 1
    	    ;;
	esac
fi

if [ -z $JAVA_HOME ];then
	read -p "\$JAVA_HOME isn't set. Do you want to set it automatically to your .profile file ? (y|n)" answer
	case ${answer:0:1} in
   	    * )
		echo "export JAVA_HOME=\"$(dirname $(dirname $(readlink -f $(which javac))))\"" >> ~/.profile
		source ~/.profile
    	    ;;
       	    N|n )
		echo "Please set the \$JAVA_HOME variable before relaunch installation script."
       		exit 1
    	    ;;
	esac
fi

if [ -z $CORPUS_DATA ]; then
	echo "You might define \$CORPUS_DATA to your folder containing TAC/DUC corpus. Continue (y|n)"
	case ${answer:0:1} in
   	    * )
        	exit 1
    	    ;;
    	    N|n )
       		break
    	    ;;
	esac
fi

if [ ! -x $(command -v jep) ]; then
	read -p "MOTS needs python3, two python package, gensim and jep in order to run word embeddings model. MOTS also need glpk-utils package as ILP solver. Do you want to automatically install these (It might need sudo) ? (y|n)" answer
	case ${answer:0:1} in
   	    Y|y )
		apt-get update
    	    	apt-get install -y python3-pip
		apt-get install -y glpk-utils
		pip3 install --upgrade pip
		pip3 install jep --user
		pip3 install --upgrade gensim --user
	    ;;
       	    * )
		echo "Please install these package before relaunching installation script."
       		exit 1
    	    ;;
	esac
fi

if [ -z $JEP_HOME ]; then
	echo "export JEP_HOME=\"$(python3 -m site --user-site)/jep\"" >> ~/.profile
	source ~/.profile
fi


read -p "Do you want to (re)install ROUGE for automatic summary evaluation (y|n)" answer
case ${answer:0:1} in
    Y|y )
	(echo y;echo o conf prerequisites_policy follow;echo o conf commit) | cpan install XML::DOM
	./rouge_install.sh
    ;;
    * )
	break
    ;;
esac

source ./jep_env_before
read -p "Do you want to run MOTS test after install (could take several minute) (y|n)" answer
case ${answer:0:1} in
    * )
	mvn install
    ;;
    N|n )
	mvn -DskipTests=true install
    ;;
esac
source ./jep_env_after

if [ -n $CORPUS_DATA ]; then
	echo Update TAC/DUC multicorpus configuration file to your \$CORPUS_DATA folder.
	sed -i "s/\$CORPUS_DATA/$CORPUS_DATA/g" conf/*.xml
fi

echo "Installation of MOTS succeed in the target directory !"
exit 0 
