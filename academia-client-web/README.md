# academia-client-web

Cliente web que consome dados da API.


## rodando com docker

É a forma recomendada de rodar, já que só exige Docker instalado na máquina.

Rode o script (a partir da raíz do projeto `academia-client-web`):

```
# se o script for incompatível com seu SO, copie os comandos do script e os execute diretamente
./scripts/docker-run-dev.sh
```

A aplicação irá subir no endereço [http://localhost:3000]()

Rodando com Docker, é necessário que esteja configurado no package.json o valor `"proxy": "http://academia-api:8080"`. Ao rodar com Docker, um script (`set-docker-proxy.js`) irá setar esse valor automaticamente. O problema é, como usamos volumes do Docker mapeando a pasta do projeto local, o seu arquivo local `package.json` também será alterado. Sugerimos manter sempre o valor original nesse campo, que é `"proxy": "http://localhost:8080"`


## rodando localmente

Se você não quiser rodar com Docker, pode rodar diretamente no seu SO. Nesse caso, é necessário garantir que  os pré-requisitos estão instalados.

Além disso, é necessário que o `package.json` contenha o valor `"proxy": "http://localhost:8080"` (veja explicação no tópico sobre rodar com Docker).

### pré-requisitos

É necessário ter [nodejs](https://nodejs.org/en/) instalado, na versão especificada no arquivo `.nvmrc`.

### instalando dependências

```
npm install # instala dependências descritas no arquivo package.json
```

### rodando a app

```
npm start
```

A aplicação irá subir no endereço [http://localhost:3000]()
