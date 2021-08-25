# Singhal-Heuristic-Algorithm-in-Java
This repo contains code in java that implements singhal heuristic algorithm for achieving mutual exclusion using daj toolkit.
Now, what's daj tookit ? It's a toolkit for the Simulation of Distributed Algorithms in Java, for more info on how to set it up and write your own algorithms
check out : https://www3.risc.jku.at/software/daj/


The mail files are NewSinghal.java / SinghalME.java and MESSAGE.java and Main.java

In SinghalME.java , the nodes are requesting randomly for critical section, the algorithm will go on until all requests are serviced whereas in NewSinghalME the algorithm
asks you that for a particular site 'i' whether you want to request to get into CS (Critical Section);

Main.java is reposible for creating the topology as well as calling the utility functions to simulate the algorithm on the constructed network

MESSAGE.java describes the format of the messages that are exchanges between the nodes;

So, to sucessfully run and simulate this in your local machine, you'd want to visit  https://www3.risc.jku.at/software/daj/ and understand the toolkit so that you'd know
which java files are used in what manner and sequence;
