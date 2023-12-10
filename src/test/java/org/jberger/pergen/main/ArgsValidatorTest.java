package org.jberger.pergen.main;

import org.jberger.pergen.output.MessageWriter;
import org.jberger.pergen.tests.mock.MockPrintStream;
import org.jberger.pergen.tests.mock.MockSystemKiller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsValidatorTest {

    @Test
    void testNormalCase() {
        MessageWriter writer = new MessageWriter(new MockPrintStream());
        MockSystemKiller killer = new MockSystemKiller();
        ArgsValidator validator = new ArgsValidator(writer, killer);
        String[] args = new String[1];
        args[0] = "test";
        validator.validate(args);
        Assertions.assertFalse(killer.getExited());
    }

    @Test
    void testErrorCase() {
        MessageWriter writer = new MessageWriter(new MockPrintStream());
        MockSystemKiller killer = new MockSystemKiller();
        ArgsValidator validator = new ArgsValidator(writer, killer);
        String[] args = new String[0];
        validator.validate(args);
        Assertions.assertTrue(killer.getExited());
    }
}