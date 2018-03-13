#!/usr/bin/env bash
# second batch of Defects4j bugs
cd nopol
env _JAVA_OPTIONS= MAVEN_OPTS="-Xmx5G" NOPOL_EVAL_DEFECTS4J=1 mvn -q test -Dtest="Defects4jEvaluation2_Math_Test"



