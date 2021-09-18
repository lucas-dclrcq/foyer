package org.ldclrcq.foyer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

public class Launcher implements QuarkusApplication {
    public static void main(String[] args) {
        Quarkus.run(Launcher.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
