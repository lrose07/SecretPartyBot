import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

class SecretPartyBot {

    private static String apiKey = System.getenv("secretPartyBotApiKey");

    SecretPartyBot() {
        JDABuilder bot = new JDABuilder(AccountType.BOT);
        System.out.println("Key: " + apiKey);


    }
}
