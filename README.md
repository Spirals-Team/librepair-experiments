# textAnalyzer

[![Build Status](https://travis-ci.org/vpodgurskiy/textAnalyzer-2.svg?branch=master)](https://travis-ci.org/vpodgurskiy/textAnalyzer-2) 
[![Maintainability](https://api.codeclimate.com/v1/badges/e4f198dd2d39cf6fc3c9/maintainability)](https://codeclimate.com/github/vpodgurskiy/textAnalyzer-2/maintainability)

## describe

_Анализатор текста принимает на вход текст из консоли, проводит его анализ,
 согласно модели и выводит результат в консоль, в виде списка._

## Setup

$ mvn clean

$ mvn package


## run

$ java -jar (Path to project)/target/textAnalyzer.jar


## Пример
Дан текст: "Тестовый текст. Текст должен быть в одну строку. Пили-пере-пили."

Результат работы приложения:
- Всего символов в тексте: 64

- Всего символов без пробелов: 56

- Количество встречающихся букв:
{б=1, в=2, д=2, е=6, ж=1, и=4, й=1, к=3, л=3, н=2, о=4, п=3, р=2, с=4, т=8, у=2, ы=2, ь=1}

- Количество встречающихся слов:
{быть=1, в=1, должен=1, одну=1, пере=1, пили=2, строку=1, текст=2, тестовый=1}


## help

usage:

    --h, print help
    
    --quit, end programm

    --char <arg>  print only char statistics

    --word <arg>  print only word statistics

    <arg> print full statistics