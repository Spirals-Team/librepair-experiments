package ru.aserdyuchenko.fileManager;

/**
 * Необходим для чтения app.properties.
 */
interface Settings {
/**
 * Метод для чтения app.properties.
 * @param key - key.
 * @return string.
 */
	String getValue(String key);
}
