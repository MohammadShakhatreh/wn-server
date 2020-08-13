package com.worldnavigator.game.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public final class Invoker {

    private Map<String, Command> commands;

    private final ApplicationContext applicationContext;

    @Autowired
    public Invoker(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void collect() {
        Map<String, Command> beans = applicationContext.getBeansOfType(Command.class);

        this.commands = beans.values()
                .stream()
                .collect(toMap(Command::name, identity()));
    }

    public String execute(PlayerContext context, String line) {

        String[] parts = line.trim().toLowerCase().split("\\s+", 2);

        Command command = commands.get(parts[0]);

        if(command != null) {

            if(command.available(context)) {

                String[] args = parts.length > 1 ? parts[1].split("\\s+") : new String[]{};

                if(command.validate(context, args))
                    return command.execute(context, args);
                else
                    return "The arguments you supplied are not valid!";

            } else {
                return "The command (%s) is not available!";
            }
        }

        return String.format("The command (%s) doesn't exists!", parts[0]);
    }

    public List<Command> getAvailableCommands(PlayerContext context) {
        return commands.values()
                .stream()
                .filter(c -> c.available(context))
                .collect(toList());
    }
}
