package ru.otus.spring.quiz.config;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class CommandLineInterfaceConfig implements PromptProvider {

    public static final String SHELL_COMMAND_GROUP_QUIZ_COMMANDS = "Quiz Commands";

    private static final String PROMPT = "quiz:> ";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(PROMPT);
    }

}
