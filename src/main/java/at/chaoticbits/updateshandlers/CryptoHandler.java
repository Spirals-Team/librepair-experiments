package at.chaoticbits.updateshandlers;

import at.chaoticbits.coinmarket.CoinMarketScheduler;
import at.chaoticbits.config.Bot;
import at.chaoticbits.coinmarket.CoinMarketCapService;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.InputStream;
import java.util.Timer;


/**
 * Crypto Polling Bot, that processes currency requests
 */
public class CryptoHandler extends TelegramLongPollingBot {

    private static final String LOGTAG  = "CryptoHandler";


    /**
     * Instantiate CryptoHandler and start coin market scheduler
     */
    public CryptoHandler() {

        CoinMarketScheduler cmScheduler = new CoinMarketScheduler () ;

        int initialDelay = 100 ;
        int fixedRate = 60 * 60 * 1000; // every hour
        new Timer().schedule(cmScheduler, initialDelay, fixedRate) ;
    }


    @Override
    public void onUpdateReceived(Update update) {

        //check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();

            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if(message.hasText()) {

                //create a object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.enableMarkdown(true);
                sendMessageRequest.setChatId(message.getChatId());

                String command = message.getText();

                try {

                    if (command.startsWith("/")) {

                        // determine end of currency slug in command string
                        int currencyEnd = command.indexOf('@') == - 1 ? command.length() : command.indexOf('@');

                        try {

                            if (command.startsWith("//")) {
                                sendMessageRequest.setText(EmojiParser.parseToUnicode(CoinMarketCapService.getFormattedCurrencyDetails(command.substring(2, currencyEnd))));
                                sendMessage(sendMessageRequest);
                            } else {

                                InputStream imageInputStream = CoinMarketCapService.getCurrencyDetailsImage(command.substring(1, currencyEnd));

                                SendPhoto photo = new SendPhoto();
                                photo.setChatId(message.getChatId());
                                photo.setNewPhoto(command, imageInputStream);
                                sendPhoto(photo);
                            }

                        } catch (Exception e) {
                            BotLogger.error(LOGTAG, e.getMessage());

                            // replace '_' characters because of telegram markdown
                            sendMessageRequest.setText(e.getMessage().replaceAll("_", "\\\\_"));
                            sendMessage(sendMessageRequest);
                        }
                    }

                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e.getMessage());
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Bot.config.botName;
    }

    @Override
    public String getBotToken() {
        return System.getenv("CMBOT_TELEGRAM_TOKEN");
    }
}
