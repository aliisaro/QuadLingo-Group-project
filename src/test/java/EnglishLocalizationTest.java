import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

public class EnglishLocalizationTest {

    private ResourceBundle bundle;

    @BeforeEach
    public void setUp() {
        bundle = ResourceBundle.getBundle("bundle_EN");
    }

    @Test
    public void testIndexPage() {
        // Assertions for the index page
        assertEquals("Welcome to QuadLingo", bundle.getString("welcomeMessage"));
        assertEquals("Welcome, ", bundle.getString("welcomeLabel"));
        assertEquals("Your learning journey starts here.", bundle.getString("descriptionMessage"));
        assertEquals("Sign up", bundle.getString("signUp"));
    }

    @Test
    public void testLoginPage() {
        // Assertions for the login page
        assertEquals("Username", bundle.getString("usernameLabel"));
        assertEquals("Password", bundle.getString("passwordLabel"));
        assertEquals("Login", bundle.getString("login"));
        assertEquals("Go back", bundle.getString("goBackButton"));
        assertEquals("Don't have an account? Sign up instead:", bundle.getString("noAccountMessage"));
        assertEquals("User does not exist.", bundle.getString("userDoesNotExist"));
        assertEquals("Incorrect password.", bundle.getString("incorrectPassword"));
        assertEquals("Login error", bundle.getString("loginErrorTitle"));
    }

    @Test
    public void testSignUpPage() {
        // Assertions for the register page
        assertEquals("Email", bundle.getString("emailLabel"));
        assertEquals("Already have an account? Login instead:", bundle.getString("hasAccountLabel"));
        assertEquals("All fields are required.", bundle.getString("allFieldsRequired"));
        assertEquals("Invalid email format.", bundle.getString("invalidEmail"));
        assertEquals("An account with this email already exists.", bundle.getString("accountExists"));
        assertEquals("Password must contain at least 1 uppercase letter.", bundle.getString("oneUppercaseLetter"));
        assertEquals("Password must contain at least 1 number.", bundle.getString("oneNumber"));
        assertEquals("Password must be at least 8 characters long.", bundle.getString("atLeastEight"));
        assertEquals("Sign up error", bundle.getString("signUpErrorTitle"));
        assertEquals("Sign up failed. Please try again.", bundle.getString("errorContext"));
    }

    @Test
    public void testAchievementsPage() {
        // Assertions for the achievements page
        assertEquals("Complete 1 quiz", bundle.getString("quizRequirement1"));
        assertEquals("Complete 5 quizzes", bundle.getString("quizRequirement5"));
        assertEquals("Complete 10 quizzes", bundle.getString("quizRequirement10"));
        assertEquals("Master 5 Flashcards", bundle.getString("flashcardRequirement5"));
        assertEquals("Master 10 Flashcards", bundle.getString("flashcardRequirement10"));
        assertEquals("Achievements", bundle.getString("achievementsTitle"));
        assertEquals("Profile", bundle.getString("profileButton"));
        assertEquals("Home page", bundle.getString("homeButton"));
        assertEquals("Unlocked badges", bundle.getString("unlockedBadges"));
        assertEquals("Locked badges", bundle.getString("lockedBadges"));
    }

    @Test
    public void testProgressPage() {
        // Assertions for the progress page
        assertEquals("Progress", bundle.getString("progressTitle"));
        assertEquals("Your score", bundle.getString("userScore"));
        assertEquals("Your quiz progress", bundle.getString("quizProgress"));
        assertEquals("Your flashcard progress", bundle.getString("flashcardProgress"));
        assertEquals("You have completed {0} of {1} quizzes.", bundle.getString("quizzesCompleted"));
        assertEquals("You have mastered {0} of {1} flashcards.", bundle.getString("flashcardsMastered"));
    }

    @Test
    public void testHomepagePage() {
        // Assertions for the homepage
        assertEquals("Quiz Library", bundle.getString("quizLibraryButton"));
        assertEquals("Flashcard Library", bundle.getString("flashcardLibraryButton"));
        assertEquals("Achievements", bundle.getString("achievementsButton"));
        assertEquals("Logout", bundle.getString("logoutButton"));
    }
    @Test
    public void testProfilePage() {
        // Assertions for the profile page
        assertEquals("Profile", bundle.getString("profilePageTitle"));
        assertEquals("Username: ", bundle.getString("currentUsernameLabel"));
        assertEquals("Email: ", bundle.getString("currentEmailLabel"));
        assertEquals("Password: **********", bundle.getString("currentPasswordLabel"));
        assertEquals("Change username:", bundle.getString("changeUsernameLabel"));
        assertEquals("Change email:", bundle.getString("changeEmailLabel"));
        assertEquals("Change password:", bundle.getString("changePasswordLabel"));
        assertEquals("Save changes", bundle.getString("saveChangesButton"));
        assertEquals("At least one field must be filled out to update the profile.", bundle.getString("emptyFieldsAlert"));
        assertEquals("Profile updated successfully.", bundle.getString("profileUpdateSuccessAlert"));
        assertEquals("Failed to update profile.", bundle.getString("profileUpdateFailedAlert"));
        assertEquals("Back to homepage", bundle.getString("backToHomeButton"));
        assertEquals("Progress", bundle.getString("progressPageButton"));
        assertEquals("Error", bundle.getString("errorAlertTitle"));
        assertEquals("Success", bundle.getString("successAlertTitle"));
    }

    @Test
    public void testFlashcardPage() {
        // Assertions for the flashcard page
        assertEquals("You don't have mastered flashcards yet", bundle.getString("noMastered"));
        assertEquals("Go back to library", bundle.getString("backToFlashLibraryButton"));
        assertEquals("You have mastered all flashcards in this topic. Great job!", bundle.getString("masterAllTopic"));
        assertEquals("Show answer", bundle.getString("showAnswer"));
        assertEquals("Master this", bundle.getString("masterThis"));
        assertEquals("Unmaster this", bundle.getString("unmasterThis"));
        assertEquals("Next flashcard", bundle.getString("nextFlashcard"));
        assertEquals("Show term", bundle.getString("showTerm"));
        assertEquals("Show translation", bundle.getString("showTranslation"));
    }

    @Test
    public void testFlashcardLibraryPage() {
        // Assertions for the flashcard library page
        assertEquals("Flashcards", bundle.getString("flashcardTitle"));
        assertEquals("Unmaster all flashcards", bundle.getString("unmasterAllButton"));
        assertEquals("Mastered Flashcards", bundle.getString("masteredFlashcardsButton"));
    }

    @Test
    public void testQuizPage() {
        // Assertions for the quiz page
        assertEquals("Submit Answer", bundle.getString("submitAnswer"));
        assertEquals("Cancel Quiz", bundle.getString("cancelQuiz"));
        assertEquals("Correct!", bundle.getString("correct"));
        assertEquals("Incorrect! The correct answer is: ", bundle.getString("incorrect"));
        assertEquals("Please select an answer before submitting.", bundle.getString("selectAnswerError"));
        assertEquals("Quiz Finished!", bundle.getString("quizFinished"));
        assertEquals("Your score: ", bundle.getString("yourScore"));
        assertEquals(" out of ", bundle.getString("outOf"));
    }
}
