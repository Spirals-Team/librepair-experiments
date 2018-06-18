# docker

## server

Para subir o servidor localmente (porta 3306):

```
./server.sh
```

Esse script só funciona na primeira vez que for rodado, pois irá tentar criar um container com o servidor de banco de dados, e a partir da segunda vez ele já estará criado e irá falhar. Veja a parte dos comandos de Docker abaixo sobre o comando `start`

## client web

Use o client para rodar queries no server (a senha, usuário e database são "academia"):

```
./client-web.sh
```

Diferente do script do servidor de banco de dados, esse script da ferramenta cliente destrói qualquer instância previamente existente e recria. Não fazemos isso no caso do server porque não queremos apagar o container e perder seus dados.


## client de linha de comando

Use o client para rodar queries no server (a senha é "academia"):

```
./client-command-line.sh
```

Diferente do script do servidor de banco de dados, esse script da ferramenta cliente destrói qualquer instância previamente existente e recria. Não fazemos isso no caso do server porque não queremos apagar o container e perder seus dados.

## comandos úteis de Docker

Os scripts acima são simplificações para rodar uma vez os containers, mas é importante conhecer alguns comandos úteis de Docker para lidar com containers já existentes etc:

```
# lista containers em execução (atenção ao campo id, será usado abaixo)
docker ps

# lista todos os containers existentes, mesmo os parados
docker ps -a

# se o container já estiver criado mas estiver parado (você desligou a máquina, por exemplo)
# não rode os scripts dessa pasta para, pois eles tentarão criar o container novamente. Basta você dar `start` em um container já existente
docker start <id>
```


# MySQL

Comandos úteis no client de MySQL:

```
show databases;
use academia; -- seleciona a database que vamos usar na sessão do client
show tables;
select * from account;
```
