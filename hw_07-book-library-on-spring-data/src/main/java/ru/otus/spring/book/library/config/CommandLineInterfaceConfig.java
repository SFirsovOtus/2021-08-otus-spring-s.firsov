package ru.otus.spring.book.library.config;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInterfaceConfig implements PromptProvider {

    public static final String SHELL_COMMAND_GROUP_BOOK_LIBRARY_COMMANDS = "Book Library Commands";

    private static final String PROMPT = "book-library:> ";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(PROMPT);
    }

}
