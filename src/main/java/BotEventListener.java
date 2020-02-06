import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BotEventListener extends ListenerAdapter {

    private Logger debugLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().equals("?Hello")) {
            event.getChannel().sendMessage("World!").queue();
        }
    }
}
