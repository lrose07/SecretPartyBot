import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
            managePrivateCategory(guild);
            String channelName = partyDetails[1];
            try {
                ArrayList<Member> privateChannelMembersList = getPrivateChannelUsers(guild, event.getAuthor(), partyDetails);
                event.getChannel().sendMessage("Let's have a party!").queue();
                ChannelAction ca = guild.createTextChannel(channelName);
                addMembersToChannel(guild, ca, privateChannelMembersList);

//                List<TextChannel> tc = guild.getTextChannels();
//                System.out.println(tc.toString());
//                TextChannel tc = guild.getTextChannelsByName(channelName, false).get(0);
//                tc.sendMessage("Welcome!").queue();
            } catch (MemberNotFoundException e) {
                event.getChannel().sendMessage("Hmm, " + e.getNotFoundName() + " can't be found!").queue();
            }
        } else {
            event.getChannel().sendMessage("You didn't tell me where the party's at!").queue();
        }
    }

    private ArrayList<Member> parseUserNames(Guild guild, String[] names) throws MemberNotFoundException {
        ArrayList<Member> users = new ArrayList<>();
        for (String userName : names) {
            List<Member> currentMembers = guild.getMembersByName(userName, true);
            if (!currentMembers.isEmpty()) {
                Member tempUser = currentMembers.get(0);
                users.add(tempUser);
            } else {
                throw new MemberNotFoundException(userName);
            }
        }
        return users;
    }

    private ArrayList<Member> getPrivateChannelUsers(Guild guild, User requester, String[] allCommands) throws MemberNotFoundException {
        String[] nameCommands = new String[allCommands.length - 1];
        allCommands[1] = requester.getName();
        System.arraycopy(allCommands, 1, nameCommands, 0, allCommands.length-1);
        return parseUserNames(guild, nameCommands);
    }

    private void addMembersToChannel(Guild g, ChannelAction tca, ArrayList<Member> members) {
        tca.addPermissionOverride(g.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL));
        for (Member m : members) {
            tca.addPermissionOverride(m, EnumSet.of(Permission.VIEW_CHANNEL), null);
        }
        tca.queue();
    }

    private void managePrivateCategory(Guild guild) {
        String privateCategoryName = "private";
        List<Category> currentCategories = guild.getCategoriesByName(privateCategoryName, true);
        if (currentCategories.isEmpty()) {
            guild.createCategory(privateCategoryName).complete();
        }
    }
}
