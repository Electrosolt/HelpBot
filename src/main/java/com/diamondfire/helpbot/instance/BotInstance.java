package com.diamondfire.helpbot.instance;

import com.diamondfire.helpbot.command.CommandHandler;
import com.diamondfire.helpbot.command.impl.codeblock.*;
import com.diamondfire.helpbot.command.impl.filespitter.*;
import com.diamondfire.helpbot.command.impl.other.*;
import com.diamondfire.helpbot.command.impl.stats.*;
import com.diamondfire.helpbot.command.impl.stats.support.*;
import com.diamondfire.helpbot.components.config.Config;
import com.diamondfire.helpbot.events.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class BotInstance {

    private static final CommandHandler handler = new CommandHandler();
    private static JDA jda;
    private static Config config;

    public static void start() throws InterruptedException, LoginException {

        config = new Config();
        handler.register(
                // query commands
                new CodeCommand(),
                new RankCommand(),
                new BlockCommand(),
                new SearchCommand(),
                new TagsCommand(),
                //file listers
                new SoundListCommand(),
                new ParticleListCommand(),
                new PotionListCommand(),
                // others
                new MimicCommand(),
                new FetchDataCommand(),
                new InfoCommand(),
                new EvalCommand(),
                new GarfieldCommand(),
                new HelpCommand(),
                new RestartCommand(),
                new FetchDumpCommand(),
                new RawCommand(),
                new SamQuotesCommand(),
                // statsbot
                new StatsCommand(),
                new InBadCommand(),
                new JoinBadCommand(),
                new PlotCommand(),
                new ProfileCommand(),
                new ActivePlotsCommand(),
                new TrendingPlotsCommand(),
                new PlotsCommand(),
                new CpTopCommand(),
                new RetiredListCommand(),
                new StaffListCommand(),
                new SessionTopCommand(),
                new LastJoinedCommand(),
                new SessionStatsCommand(),
                new NewPlayersCommand(),
                new StatsGraphCommand(),
                new NewJoinGraphCommand(),
                new PlayersCommand(),
                new BoostersCommand(),
                new DiscordBoostersCommand(),
                new TimeTopCommand(),
                new QueueCommand(),
                new WhoHelpedCommand(),
                new HelpedByCommand(),
                new NamesCommand(),
                new PlayerJoinGraphCommand(),
                new CpCommand(),
                new CpRequirementsCommand(),
                new VoteGivenLeaderboard()
        );

        JDABuilder builder = JDABuilder.createDefault(config.getToken());
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);
        builder.setActivity(Activity.watching("for ?help"));
        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS);
        builder.addEventListeners(new MessageEvent(), new ReactionEvent());

        jda = builder.build();
        jda.awaitReady();
    }

    public static JDA getJda() {
        return jda;
    }

    public static CommandHandler getHandler() {
        return handler;
    }

    public static Config getConfig() {
        return config;
    }
}
