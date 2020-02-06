import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;
import java.util.logging.Logger;

class SecretPartyBot {

    private static String apiToken = System.getenv("secretPartyBotApiKey");

    private Logger debugLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    SecretPartyBot() {
        try {
            startBot();
        } catch (LoginException le) {
            debugLogger.log(Level.SEVERE, "Login failed, check your API token.");
        }
    }

    private void startBot() throws LoginException {
        JDABuilder bot = new JDABuilder(AccountType.BOT);
        bot.setToken(apiToken);
        bot.addEventListener(new BotEventListener());
        bot.buildAsync(); // LoginException

        debugLogger.log(Level.INFO, "SecretPartyBot online");
    }
}
