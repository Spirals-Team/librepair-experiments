#!/bin/bash

#cd $ROUGE_HOME
folder=$ROUGE_HOME/data/WordNet-2.0-Exceptions
rm $folder/WordNet-2.0.exc.db # only if exist
perl $folder/buildExeptionDB.pl . exc $folder/WordNet-2.0.exc.db

#cd ..
rm $ROUGE_HOME/data/WordNet-2.0.exc.db # only if exist
ln -s $folder/WordNet-2.0.exc.db $ROUGE_HOME/data/WordNet-2.0.exc.db
