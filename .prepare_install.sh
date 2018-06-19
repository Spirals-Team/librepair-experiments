#!/bin/sh
touch ~/.profile

if [ -z "$ROUGE_HOME" ];then
	echo "\$ROUGE_HOME isn't set. Do you want to set it automatically to your .profile file ?" 
	echo "export ROUGE_HOME=\"$(pwd)/ROUGE-1.5.5/RELEASE-1.5.5\"" >> ~/.profile
fi

if [ -z "$JAVA_HOME" ];then
	echo "\$JAVA_HOME isn't set. Do you want to set it automatically to your .profile file ?"
	echo "export JAVA_HOME=\"$(dirname $(dirname $(readlink -f $(which javac))))\"" >> ~/.profile
fi

echo "MOTS needs python3, two python package, gensim and jep in order to run word embeddings model. MOTS also need glpk-utils package as ILP solver. Do you want to automatically install these (It might need sudo) ?" 
apt-get update
apt-get install -y python3-pip
apt-get install -y glpk-utils
pip3 install --upgrade pip
pip3 install jep --user
pip3 install --upgrade gensim --user

if [ -z "$JEP_HOME" ]; then
	echo "export JEP_HOME=\"$(python3 -m site --user-site)/jep\"" >> ~/.profile
fi

echo "Do you want to (re)install ROUGE for automatic summary evaluation (y|n)" answer
(echo y;echo o conf prerequisites_policy follow;echo o conf commit) | cpan install XML::DOM > /dev/null
