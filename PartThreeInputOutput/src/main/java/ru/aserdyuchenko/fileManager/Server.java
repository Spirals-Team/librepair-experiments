package ru.aserdyuchenko.fileManager;

/**
 * Сам сервер, который выполняет все действия.
 */
interface Server {
/**
 * Переменная нужна для перехода в нужный каталог.
 * @param NOWPATH.
 */
String NOWPATH = "./";
/**
 * Запуск сервера и получение команд от клиента.
 * @param homePath - homePath.
 */
	void startServer(String homePath);
/**
 * Возвращает список корневого каталога.
 */
	void seeHomePath();
/**
 * Осуществляет переход в выбранные каталог.
 * Или возвращает содержимое данного каталога.
 * Это происходит с помощью переменной NOWPATH.
 * Метод всегда будет возвращать содержимое именно каталога (NOWPATH).
 * А переход в выбранный каталог будет происходить с помощью
 * сложения уже имеющейся строки NOWPATH и строки, которую передаст клиент.
 * @param thisPath - thisPath.
 */
	void seeThisPath(String thisPath);
/**
 * Скачать файл.
 * @param nameFile - nameFile.
 */
	void downloadFile(String nameFile);
/**
 * Загрузить файл.
 * Я ещё не знаю как будет происходить загрузка файла, поэтому не знаю
 * нужно ли передавать какой-нибудь параметр.
 */
	void pushFile();
}
