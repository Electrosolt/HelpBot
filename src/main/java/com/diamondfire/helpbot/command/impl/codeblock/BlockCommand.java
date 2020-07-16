package com.diamondfire.helpbot.command.impl.codeblock;

import com.diamondfire.helpbot.command.argument.ArgumentSet;
import com.diamondfire.helpbot.command.argument.impl.types.DefinedStringArgument;
import com.diamondfire.helpbot.command.help.CommandCategory;
import com.diamondfire.helpbot.command.help.HelpContext;
import com.diamondfire.helpbot.command.help.HelpContextArgument;
import com.diamondfire.helpbot.command.permissions.Permission;
import com.diamondfire.helpbot.components.codedatabase.db.CodeDatabase;
import com.diamondfire.helpbot.components.codedatabase.db.datatypes.CodeBlockActionData;
import com.diamondfire.helpbot.components.codedatabase.db.datatypes.CodeBlockData;
import com.diamondfire.helpbot.components.codedatabase.db.datatypes.SimpleData;
import com.diamondfire.helpbot.events.CommandEvent;

import java.util.List;
import java.util.stream.Collectors;


public class BlockCommand extends AbstractMultiQueryCommand {

    @Override
    public String getName() {
        return "block";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"codeblock"};
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Searches for actions based on their code block.")
                .category(CommandCategory.CODE_BLOCK)
                .addArgument(
                        new HelpContextArgument()
                                .name("codeblock|action|game value")
                );
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().
                addArgument("codeblock", new DefinedStringArgument(CodeDatabase.getCodeBlocks().stream()
                        .filter((codeBlockData -> codeBlockData.getAssociatedAction() == null))
                        .map(CodeBlockData::getName)
                        .toArray(String[]::new)));
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    protected List<String> filterData(List<SimpleData> data, CommandEvent event) {
        return data.stream()
                .filter(simpleData -> simpleData instanceof CodeBlockActionData)
                .filter((simpleData -> ((CodeBlockActionData) simpleData).getCodeblockName().equalsIgnoreCase(event.getArgument("codeblock"))))
                .map(SimpleData::getMainName)
                .collect(Collectors.toList());
    }

    @Override
    protected String getSearchQuery(CommandEvent event) {
        return event.getArgument("codeblock");
    }
}