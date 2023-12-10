package org.jberger.pergen.tests.mock;

import org.jberger.pergen.main.SystemKiller;

public class MockSystemKiller extends SystemKiller {

    private boolean exited = false;

    public MockSystemKiller() {
        super();
    }

    @Override
    public void exit() {
        exited = true;
    }

    public boolean getExited() {
        return exited;
    }
}
