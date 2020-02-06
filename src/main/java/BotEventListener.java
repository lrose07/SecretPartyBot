import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BotEventListener extends ListenerAdapter {

    private Logger debugLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().charAt(0) == '%') {
            String commandEntered = event.getMessage().getContentRaw().substring(1).toLowerCase();
            String[] commandParts = commandEntered.split(" ");
            switch (commandParts[0]) {
                case "hello":
                    event.getChannel().sendMessage("my party people!").queue();
                    break;
                case "secret-party":
                    if (commandParts.length >= 2) {
                        event.getChannel().sendMessage("Let's have a party!").queue();
                        guild.createTextChannel(commandParts[1]).complete();
                        event.getMessage().getAuthor().openPrivateChannel().queue(channel ->
                                channel.sendMessage("test").queue());
                    } else {
                        event.getChannel().sendMessage("You didn't tell me where the party's at!").queue();
                    }
                    break;
                default:
                    event.getChannel().sendMessage("I'm not sure what you want, but let's party anyway!").queue();
            }
        }
    }
}
