import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.GuildImpl;
import net.dv8tion.jda.internal.entities.TextChannelImpl;

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
            decideAndCallAction(guild, event, commandParts);
        }
    }

    private void decideAndCallAction(Guild guild, MessageReceivedEvent event, String[] commands) {
        switch (commands[0]) {
            case "hello":
                respondToGreeting(event);
                break;
            case "secret-party":
                startAParty(guild, event, commands);
                break;
            default:
                event.getChannel().sendMessage("I'm not sure what you want, but let's party anyway!").queue();
        }
    }

    private void respondToGreeting(MessageReceivedEvent event) {
        event.getChannel().sendMessage("my party people!").queue();
    }

    private void startAParty(Guild guild, MessageReceivedEvent event, String[] partyDetails) {
        if (partyDetails.length >= 2) {
            event.getChannel().sendMessage("Let's have a party!").queue();
            guild.createTextChannel(partyDetails[1]).complete();
        } else {
            event.getChannel().sendMessage("You didn't tell me where the party's at!").queue();
        }
    }
}
