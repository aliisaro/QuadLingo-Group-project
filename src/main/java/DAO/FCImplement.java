package DAO;

import Model.FlashCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FCImplement implements FlashCardDao {

    private Connection connection;


    public FCImplement(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FlashCard> getFlashCardsByTopic(String topic, int userId) {
        List<FlashCard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD WHERE Topic = ? AND FlashCardID NOT IN (SELECT FlashCardID FROM ISMASTERED WHERE UserID = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, topic);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String term = rs.getString("Term");
                    String translation = rs.getString("Translation");
                    flashCards.add(new FlashCard(term, translation, topic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashCards;
    }

    @Override
    public List<FlashCard> getTopics() {
        List<FlashCard> topics = new ArrayList<>();
        String query = "SELECT DISTINCT Topic FROM FLASHCARD";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String topic = rs.getString("Topic");
                topics.add(new FlashCard(null, null, topic));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topics;
    }

    @Override
    public List<FlashCard> getAllFlashCards() {
        List<FlashCard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String term = rs.getString("Term");
                String translation = rs.getString("Translation");
                String topic = rs.getString("Topic");
                flashCards.add(new FlashCard(term, translation, topic));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flashCards;
    }

    @Override
    public void masterFlashCard(int flashCardId, int userId) {
        String query = "INSERT INTO ISMASTERED (FlashCardID, UserID) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, flashCardId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unmasterFlashCard(int flashCardId, int userId) {
        String query = "DELETE FROM ISMASTERED WHERE FlashCardID = ? AND UserID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, flashCardId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FlashCard> getMasteredFlashCardsByUser(int userId) {
        List<FlashCard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD WHERE FlashCardID IN (SELECT FlashCardID FROM ISMASTERED WHERE UserID = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String term = rs.getString("Term");
                    String translation = rs.getString("Translation");
                    String topic = rs.getString("Topic");
                    flashCards.add(new FlashCard(term, translation, topic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashCards;
    }

    @Override
    public void checkMasteredStatus(int flashCardId, int userId) {
        String query = "SELECT * FROM ISMASTERED WHERE FlashCardID = ? AND UserID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, flashCardId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Flashcard " + flashCardId + " is mastered by user " + userId);
                } else {
                    System.out.println("Flashcard " + flashCardId + " is not mastered by user " + userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentFlashCardId(String term) {
        int flashCardId = 0;
        String query = "SELECT FlashCardID FROM FLASHCARD WHERE Term = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, term);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    flashCardId = rs.getInt("FlashCardID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashCardId;
    }

    @Override
    public boolean isFlashCardMastered(int flashCardId, int userId) {
        String query = "SELECT * FROM ISMASTERED WHERE FlashCardID = ? AND UserID = ?";
        boolean isMastered = false;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, flashCardId);
            stmt.setInt(2, userId);
            if (stmt.executeQuery().next()) {
                isMastered = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isMastered;
    }

    @Override
    public void unmasterAllFlashCards(int userId) {
        String query = "DELETE FROM ISMASTERED WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}