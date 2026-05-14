package net.dadamalda.added_in.providers;

@FunctionalInterface
public interface VersionProvider<T> {
    VersionResult getVersion(T object);
}
