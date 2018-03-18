package co.spraybot;

import io.vertx.core.Verticle;

/**
 * Responsible for interacting with the ChatAdapter to ensure ChatMessages that correspond to some ChatCommand gets passed
 * to the ChatCommandRunner appropriately. Ultimately User interaction is parsed by the ChatBot and in more traditional
 * systems could be seen as the Router.
 *
 * A ChatBot has to process a large number of ChatMessages, essentially very single ChatMessage that comes across the
 * ChatAdapter connection, to determine what actions should take place. This should be the ChatBots only responsibility
 * and once your ChatBot implementation is doing anything more than determining if a ChatCommand should be run for a given
 * ChatMessage and passing that to the ChatCommandRunner you should re-evaluate the design of your application.
 *
 * ### Verticle Messages
 *
 * - address: spraybot.chatbot
 * - op: chatMessageReceived
 * - body: ChatMessage The ChatMessage received from the chat connection; may or may not have a command associated to it
 * - response: no response provided by the ChatBot
 *
 * - address: spraybot.commandrunner
 * - op: performCommand
 * - commandName (header): String The ChatCommand that should be executed
 * - body: ChatMessage
 * - response: Void Will return success or failure depending on if the ChatCommand executed or not
 *
 * @author Charles Sprayberry
 */
public interface ChatBot extends Verticle {

    /**
     * @return The name that your ChatBot refers to themselves as when communicating with Users
     */
    String getName();

    /**
     * Program a command to be executed anytime a ChatMessage matches the messagePattern, regardless of whether the
     * message is directly at the ChatBot, a general message to the room, or mentioning other Users.
     *
     * @param messagePattern A regex that the ChatMessage contents must match to perform this command
     * @param commandName The name of the ChatCommand that we want to run if the ChatMessage matches the pattern
     */
    void programCommand(String messagePattern, String commandName);

    /**
     * Program a command to be executed ONLY if the ChatBot is directly mentioned; meaning that if the ChatMessage body
     * does not start with @chatBotName or chatBotName the ChatCommand will not run.
     *
     * DO NOT include the ChatBot's name in the regex pattern, only include that part of the ChatMessage that should be
     * matched after the name and space in the message body.
     *
     * @param messagePattern A regex that the ChatMessage contents must match to perform this command
     * @param commandName The name of the ChatCommand that we want to run if the ChatMessage matches the pattern
     */
    void programCommandMentionOnly(String messagePattern, String commandName);

    /**
     * @param chatMessage The ChatMessage should be processed to determine if there's a ChatCommand programmed for it
     */
    void chatMessageReceived(ChatMessage chatMessage);

}

