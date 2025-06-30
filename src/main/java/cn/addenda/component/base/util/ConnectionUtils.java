package cn.addenda.component.base.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author addenda
 * @since 2022/4/28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionUtils {

  /**
   * 获取连接
   */
  public static Connection openConnection(DataSource dataSource) throws SQLException {
    assertDatasource(dataSource);
    return dataSource.getConnection();
  }

  /**
   * @return 原始的autoCommit
   */
  public static boolean setAutoCommitFalse(Connection connection) throws SQLException {
    assertConnection(connection);
    boolean originalAutoCommit = connection.getAutoCommit();
    if (originalAutoCommit) {
      connection.setAutoCommit(false);
    }
    return originalAutoCommit;
  }

  /**
   * @return 原始的autoCommit
   */
  public static boolean setAutoCommitTrue(Connection connection) throws SQLException {
    assertConnection(connection);
    boolean originalAutoCommit = connection.getAutoCommit();
    if (!originalAutoCommit) {
      connection.setAutoCommit(true);
    }
    return originalAutoCommit;
  }

  /**
   * @return 原始的autoCommit
   */
  public static boolean setAutoCommit(Connection connection, boolean expect) throws SQLException {
    assertConnection(connection);
    boolean originalAutoCommit = connection.getAutoCommit();
    if (expect != originalAutoCommit) {
      connection.setAutoCommit(expect);
    }
    return originalAutoCommit;
  }

  /**
   * @return 原始的transactionIsolation
   */
  public static int setTransactionIsolation(Connection connection, int expect) throws SQLException {
    assertConnection(connection);
    int transactionIsolation = connection.getTransactionIsolation();
    if (transactionIsolation != expect) {
      connection.setTransactionIsolation(expect);
    }
    return transactionIsolation;
  }

  public static void close(Connection connection) throws SQLException {
    assertConnection(connection);
    connection.close();
  }

  public static void rollback(Connection connection) throws SQLException {
    assertConnection(connection);
    connection.rollback();
  }

  public static void commit(Connection connection) throws SQLException {
    assertConnection(connection);
    connection.commit();
  }

  private static void assertConnection(Connection connection) throws SQLException {
    if (connection == null) {
      throw new IllegalStateException("connection is null");
    }
    if (connection.isClosed()) {
      throw new IllegalStateException("connection has been closed");
    }
  }

  private static void assertDatasource(DataSource dataSource) {
    if (dataSource == null) {
      throw new IllegalStateException("dataSource is null");
    }
  }

}
