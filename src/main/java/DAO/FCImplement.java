package DAO;

import Model.Flashcard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FCImplement implements FlashcardDao {
    private Connection connection;

    public FCImplement(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Flashcard> getFlashcardsByTopic(String topic, int userId, String languageCode) {
        List<Flashcard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD WHERE Topic = ? AND language_code2 = ? AND FlashCardID NOT IN (SELECT FlashCardID FROM ISMASTERED WHERE UserID = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, topic);
            stmt.setString(2, languageCode);
            stmt.setInt(3, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String term = rs.getString("Term");
                    String translation = rs.getString("Translation");
                    flashCards.add(new Flashcard(term, translation, topic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashCards;
    }

    @Override
    public List<Flashcard> getTopics(String languageCode) {
        List<Flashcard> topics = new ArrayList<>();
        String query = "SELECT DISTINCT Topic FROM FLASHCARD WHERE language_code2 = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, languageCode);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String topic = rs.getString("Topic");
                    topics.add(new Flashcard(null, null, topic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topics;
    }

    @Override
    public List<Flashcard> getAllFlashcards(String languageCode) {
        List<Flashcard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD WHERE language_code2 = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String term = rs.getString("Term");
                String translation = rs.getString("Translation");
                String topic = rs.getString("Topic");
                flashCards.add(new Flashcard(term, translation, topic));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flashCards;
    }

    @Override
    public void masterFlashcard(int flashCardId, int userId) {
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
    public void unmasterFlashcard(int flashCardId, int userId) {
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
    public List<Flashcard> getMasteredFlashcardsByUser(int userId, String language) {
        List<Flashcard> flashCards = new ArrayList<>();
        String query = "SELECT Term, Translation, Topic FROM FLASHCARD " +
                "WHERE FlashCardID IN (SELECT FlashCardID FROM ISMASTERED WHERE UserID = ?) " +
                "AND language_code2 = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, language);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String term = rs.getString("Term");
                    String translation = rs.getString("Translation");
                    String topic = rs.getString("Topic");
                    flashCards.add(new Flashcard(term, translation, topic));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashCards;
    }


    @Override
    public int getCurrentFlashcardId(String term) {
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
    public boolean isFlashcardMastered(int flashCardId, int userId) {
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
    public void unmasterAllFlashcards(int userId, String language) {
        String query = "DELETE FROM ISMASTERED " +
                "WHERE UserID = ? AND FlashCardID IN (SELECT FlashCardID FROM FLASHCARD WHERE language_code2 = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, language);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}