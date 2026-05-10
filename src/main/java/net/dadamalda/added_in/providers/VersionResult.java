package net.dadamalda.added_in.providers;

import net.neoforged.neoforge.common.util.TriState;

public record VersionResult(TriState state, String version) {
    public static VersionResult none() {
        return new VersionResult(TriState.FALSE, "");
    }
    public static VersionResult pass() {
        return new VersionResult(TriState.DEFAULT, "");
    }
    public static VersionResult success(String version) {
        return new VersionResult(TriState.TRUE, version);
    }
}
