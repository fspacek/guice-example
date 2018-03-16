package eu.devs.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Singleton
public class PeriodicWorker implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(PeriodicWorker.class);
    private final DataSource dataSource;

    @Inject
    public PeriodicWorker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select now()");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                LOG.info("Current time is {}", rs.getTimestamp(1));
            }
            TimeUnit.SECONDS.sleep(5);
        } catch (SQLException | InterruptedException e) {
            LOG.error("Unable to test database connection");
        }
    }
}
