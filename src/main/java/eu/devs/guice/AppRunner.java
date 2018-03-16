package eu.devs.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;

public class AppRunner {

    public static void main(String[] args) {
        final HikariDataSource dataSource = createDataSource();
        final Injector injector = Guice.createInjector(new AppModule(dataSource));

        injector.getInstance(WorkerExecutor.class).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        }));
    }

    private static HikariDataSource createDataSource(){
        final HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:test");
        ds.setUsername("sa");
        return ds;
    }
}
