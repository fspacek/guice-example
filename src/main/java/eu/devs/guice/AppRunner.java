package eu.devs.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;

public class AppRunner {

    public static void main(String[] args) {
        final var dataSource = createDataSource();
        final var injector = Guice.createInjector(new AppModule(dataSource));

        injector.getInstance(WorkerExecutor.class).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(!dataSource.isClosed()) {
                dataSource.close();
            }
        }));
    }

    private static HikariDataSource createDataSource(){
        final var ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:test");
        ds.setUsername("sa");
        return ds;
    }
}
