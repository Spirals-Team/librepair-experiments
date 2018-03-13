#!/usr/bin/env bash
# first batch of Defects4j bugs
cd nopol
env NOPOL_EVAL_DEFECTS4J=1 mvn -q test -Dtest="Defects4jEvaluationTest"



