#!/bin/bash

###############################################################################
## Compilando
javac LoginInterface.java
javac LoginClient.java
javac LoginServer.java

###############################################################################
## Geração (estática) dos stubs e skeletons (não é mais necessária, atualmente é dinâmica)

# rmic CalculadoraServer

## OBS.: generation and use of skeletons and static stubs for JRMP is deprecated.
# Skeletons are unnecessary, and static stubs have been superseded by dynamically generated stubs.
# Users are encouraged to migrate away from using rmic to generate skeletons and static stubs.
# See the documentation for java.rmi.server.UnicastRemoteObject.

###############################################################################
## Executando:

## garanta que o serviço RMI Registry esteja executando e na MESMA PASTA das classes:
killall -9 rmiregistry
rmiregistry 1099 &

## execute o servidor da aplicação, que irá consultar o (servidor de) Registro RMI local
xterm -hold -e 'java LoginServer' &
## OU
## execute o servidor da aplicação, que irá consultar o (servidor de) Registro RMI no IP informado
# xterm -hold -e 'java -Djava.rmi.server.hostname=172.22.70.30 CalculadoraServer' &

sleep 2

# terminal execute o cliente
xterm -hold -e 'java LoginClient' &

###############################################################################
## OBS.: se quiser rodar o cliente numa máquina e o servidor EM OUTRA máquina:

## 1) Na classe CalculadoraCliente, ao invés de localhost utilize o IP do rmiregistry (resolvedor RMI)
#     de sua rede linha 41: Naming.lookup("//172.22.70.30:1099/Calculadora");

## 2) No servidor, execute-o informando o IP do servidor como sendo seu java.rmi.server.hostname:
#xterm -hold -e 'java -Djava.rmi.server.hostname=172.22.70.30 CalculadoraServer' &
#     isto evita que ele utilize o localhost por padrão


