# previsao-tempo

[![Maintainability](https://api.codeclimate.com/v1/badges/0bd518bda8b73d6c66ae/maintainability)](https://codeclimate.com/github/alexNeto/previsao-tempo/maintainability)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1dfc419284d8412dbafaee4db34844a4)](https://www.codacy.com/app/alexNeto/previsao-tempo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alexNeto/previsao-tempo&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/alexNeto/previsao-tempo.svg?branch=master)](https://travis-ci.org/alexNeto/previsao-tempo)
[![Coverage Status](https://coveralls.io/repos/github/alexNeto/previsao-tempo/badge.svg?branch=master)](https://coveralls.io/github/alexNeto/previsao-tempo?branch=master)


api para informações sobre o clima nos próximos 7 dias


## Requisitos do Sistema


- [x] O banco de dados deverá ser implementado no SQLite;
- [ ] O sistema deverá manter os dados das cidades já consultadas na tbcidade para evitar ter de buscar no serviço do
CPTEC o código da cidade a cada nova consulta da mesma cidade;
- [ ] O sistema deverá manter os dados de previsão já consultados na tbprevisao e a data de atualização no campo. 
atualizacao. Se no momento da consulta a data de atualização da previsão for diferente de hoje, todos os
dados de previsão da cidade deverão ser removidos da tbprevisao e novos dados deverão ser buscados no serviço
do CPTEC;
- [ ] O sistema deverá ter uma interface gráfica ou por linha de comando para o usuário fornecer o nome de uma cidade
ou parte do nome. Na sequência o sistema deverá exibir como resultado a previsão do tempo para os próximos 7
dias - formada por:
	* data (dd/mm/aaaa);
	* tempo;
	* IUV;
	* temperatura mínima;
	* temperatura máxima
- [ ] No caso do nome fornecido pelo usuário resultar em vários nomes, por exemplo, São José, o sistema deverá exibir somente o 1o resultado da
consulta.
- [ ] A 1a vez que o programa for executado o BD e suas tabelas deverão ser criadas.


## Como usar

Rode o projeto localmente, 

acesse `http://localhost:4567/uf/<sigla do estado>/cidade/<nome da cidade>`
* **estado:** sigla do estado, com caixa alta ou baixa, por exemplo: SP, rj, mg...
* **cidade:** nome da cidade com hifen para espaços e sem acentos ou caracteres especiais como til e cedilhas. Por exemplo para procurar pela cidade de **São José dos Campos** use **sao-jose-dos-campos**.
