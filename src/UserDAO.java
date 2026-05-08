import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(full_name,email,password,account_number,balance) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, "ACC" + System.currentTimeMillis());
            ps.setDouble(5, 0.0);

            int i = ps.executeUpdate();
            if (i > 0) status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public User loginUser(String email, String password) {
        User user = null;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAccountNumber(rs.getString("account_number"));
                user.setBalance(rs.getDouble("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAccountNumber(rs.getString("account_number"));
                user.setBalance(rs.getDouble("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean depositMoney(String email, double amount) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE users SET balance = balance + ? WHERE email=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setString(2, email);

            int i = ps.executeUpdate();
            if (i > 0) status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean withdrawMoney(String email, double amount) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String checkQuery = "SELECT balance FROM users WHERE email=?";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setString(1, email);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance >= amount) {
                    String updateQuery = "UPDATE users SET balance = balance - ? WHERE email=?";
                    PreparedStatement ps2 = con.prepareStatement(updateQuery);

                    ps2.setDouble(1, amount);
                    ps2.setString(2, email);

                    int i = ps2.executeUpdate();
                    if (i > 0) status = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean transferMoney(String senderEmail, String receiverAccount, double amount) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String senderQuery = "SELECT balance, account_number FROM users WHERE email=?";
            PreparedStatement ps1 = con.prepareStatement(senderQuery);
            ps1.setString(1, senderEmail);

            ResultSet senderRs = ps1.executeQuery();

            if (senderRs.next()) {
                double senderBalance = senderRs.getDouble("balance");
                String senderAccount = senderRs.getString("account_number");

                if (senderAccount.equals(receiverAccount)) {
                    return false;
                }

                if (senderBalance >= amount) {
                    String receiverQuery = "SELECT * FROM users WHERE account_number=?";
                    PreparedStatement ps2 = con.prepareStatement(receiverQuery);
                    ps2.setString(1, receiverAccount);

                    ResultSet receiverRs = ps2.executeQuery();

                    if (receiverRs.next()) {
                        String debitQuery = "UPDATE users SET balance = balance - ? WHERE email=?";
                        PreparedStatement debitPs = con.prepareStatement(debitQuery);
                        debitPs.setDouble(1, amount);
                        debitPs.setString(2, senderEmail);
                        int debit = debitPs.executeUpdate();

                        String creditQuery = "UPDATE users SET balance = balance + ? WHERE account_number=?";
                        PreparedStatement creditPs = con.prepareStatement(creditQuery);
                        creditPs.setDouble(1, amount);
                        creditPs.setString(2, receiverAccount);
                        int credit = creditPs.executeUpdate();

                        if (debit > 0 && credit > 0) status = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public void saveTransaction(String email, String type, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO transactions(email, type, amount) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int saveTransactionAndGetId(String email, String type, double amount, String receiverAccount) {
    int transactionId = 0;

    try {
        Connection con = DBConnection.getConnection();

        String query = "INSERT INTO transactions(email, type, amount, receiver_account) VALUES(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, email);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.setString(4, receiverAccount);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            transactionId = rs.getInt(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return transactionId;
}
// APPLY CARD
public boolean applyForCard(String email, String cardType, double charge) {
    try {
        Connection con = DBConnection.getConnection();

        String q = "INSERT INTO card_applications(email, card_type, annual_charge, status) VALUES(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(q);

        ps.setString(1, email);
        ps.setString(2, cardType);
        ps.setDouble(3, charge);
        ps.setString(4, "PENDING");

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// UPDATE STATUS (ADMIN)
public void updateCardStatus(int id, String status) {
    try {
        Connection con = DBConnection.getConnection();

        String q = "UPDATE card_applications SET status=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(q);

        ps.setString(1, status);
        ps.setInt(2, id);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}