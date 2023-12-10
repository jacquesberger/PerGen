package org.jberger.pergen.main;

import org.jberger.pergen.output.MessageWriter;

public class ArgsValidator {

    private MessageWriter writer;
    private SystemKiller killer;
    public ArgsValidator(MessageWriter writer, SystemKiller killer) {
        this.writer = writer;
        this.killer = killer;
    }

    public void validate(String[] args) {
        if (args.length != 1) {
            writer.displayUsage();
            killer.exit();
        }
    }
}
