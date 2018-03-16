package eu.devs.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.sql.DataSource;
import java.util.Objects;

public class AppModule extends AbstractModule {

    private final DataSource dataSource;

    public AppModule(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource cannot be null");
    }

    @Override
    protected void configure() {
        bind(PeriodicWorker.class);
    }

    @Provides
    @Singleton
    public DataSource dataSource() {
        return dataSource;
    }
}
