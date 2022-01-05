package pl.wk.zadanie24;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDAO {

    private final Connection connection;

    public TransactionDAO() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/budget", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void save(Transaction transaction) {
        final String sql = "INSERT INTO budget (type, description, amount, date) VALUES (?,?,?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transaction.getTransactionType().toString());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setString(3, transaction.getAmount().toString());
            preparedStatement.setString(4, transaction.getDate().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Boolean delete(Integer id) {
        if (findTransactionById(id).isEmpty()) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM budget WHERE id = ?;")) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(Transaction transaction) {
        final String sql = "UPDATE budget SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transaction.getTransactionType().toString());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setString(3, transaction.getAmount().toString());
            preparedStatement.setString(4, transaction.getDate().toString());
            preparedStatement.setString(5, transaction.getId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Optional<List<Transaction>> findInboundTransactions() {
        return findTransactionsByType(TransactionType.IN);
    }

    public Optional<List<Transaction>> findOutboundTransactions() {
        return findTransactionsByType(TransactionType.OUT);
    }

    public Optional<Transaction> findTransactionById(Integer id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM budget WHERE id=?;")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            } else {
                resultSet.next();
                Transaction transaction = getTransaction(resultSet);
                return Optional.of(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Optional<List<Transaction>> findTransactionsByType(TransactionType transactionType) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM budget WHERE type=?;")) {
            preparedStatement.setString(1, transactionType.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            } else {
                while(resultSet.next()) {
                    Transaction transaction = getTransaction(resultSet);
                    transactions.add(transaction);
                }
                return Optional.of(transactions);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Transaction getTransaction(ResultSet resultSet) throws SQLException {
        Integer id = Integer.valueOf(resultSet.getString("id"));
        TransactionType resultTransactionType = TransactionType.valueOf(resultSet.getString("type"));
        String description = resultSet.getString("description");
        Double amount = resultSet.getDouble("amount");
        String date = resultSet.getString("date");
        return new Transaction(id, resultTransactionType, description, amount, date);
    }
}
