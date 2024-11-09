import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

public class FrenchLocalizationTest {

    private ResourceBundle bundle;

    @BeforeEach
    public void setUp() {
        bundle = ResourceBundle.getBundle("bundle_FR");
    }

    @Test
    public void testIndexPage() {
        // Assertions for the index page
        assertEquals("Bienvenue à QuadLingo", bundle.getString("welcomeMessage"));
        assertEquals("Bienvenue", bundle.getString("welcomeLabel"));
        assertEquals("Votre parcours d'apprentissage commence ici.", bundle.getString("descriptionMessage"));
        assertEquals("Inscrivez-vous", bundle.getString("signUp"));
    }

    @Test
    public void testLoginPage() {
        // Assertions for the login page
        assertEquals("Nom d'utilisateur", bundle.getString("usernameLabel"));
        assertEquals("Mot de passe", bundle.getString("passwordLabel"));
        assertEquals("Se connecter", bundle.getString("login"));
        assertEquals("Retourner", bundle.getString("goBackButton"));
        assertEquals("Vous n'avez pas de compte ? Inscrivez-vous plutôt :", bundle.getString("noAccountMessage"));
        assertEquals("L'utilisateur n'existe pas.", bundle.getString("userDoesNotExist"));
        assertEquals("Mot de passe incorrect.", bundle.getString("incorrectPassword"));
        assertEquals("Error de connexion", bundle.getString("loginErrorTitle"));
    }

    @Test
    public void testSignUpPage() {
        // Assertions for the signup page
        assertEquals("E-mail", bundle.getString("emailLabel"));
        assertEquals("Vous avez un compte ? Connectez-vous plutôt :", bundle.getString("hasAccountLabel"));
        assertEquals("Toutes les zones sont obligatoires.", bundle.getString("allFieldsRequired"));
        assertEquals("Format d'e-mail invalide.", bundle.getString("invalidEmail"));
        assertEquals("Un compte avec cet e-mail existe déjà.", bundle.getString("accountExists"));
        assertEquals("Mot de passe doit contenir au moins une lettre majuscule.", bundle.getString("oneUppercaseLetter"));
        assertEquals("Mot de passe doit contenir au moins un nombre.", bundle.getString("oneNumber"));
        assertEquals("Mot de passe doit comporter au moins 8 caractères.", bundle.getString("atLeastEight"));
        assertEquals("Error d'inscription", bundle.getString("signUpErrorTitle"));
        assertEquals("Inscription échouée. Veuillez réessayer.", bundle.getString("errorContext"));
    }

    @Test
    public void testAchievementsPage() {
        // Assertions for the achievements page
        assertEquals("Compléter un quiz", bundle.getString("quizRequirement1"));
        assertEquals("Compléter 5 quizzes", bundle.getString("quizRequirement5"));
        assertEquals("Compléter 10 quizzes", bundle.getString("quizRequirement10"));
        assertEquals("Maîtriser 5 Cartes mémo", bundle.getString("flashcardRequirement5"));
        assertEquals("Maîtriser 10 Cartes mémo", bundle.getString("flashcardRequirement10"));
        assertEquals("Réalisations", bundle.getString("achievementsTitle"));
        assertEquals("Profil", bundle.getString("profileButton"));
        assertEquals("Page d'accueil", bundle.getString("homeButton"));
        assertEquals("Badges déverrouillés", bundle.getString("unlockedBadges"));
        assertEquals("Badges verrouillés", bundle.getString("lockedBadges"));
    }

    @Test
    public void testProgressPage() {
        // Assertions for the progress page
        assertEquals("Progrès", bundle.getString("progressTitle"));
        assertEquals("Votre score", bundle.getString("userScore"));
        assertEquals("Votre progression de quiz", bundle.getString("quizProgress"));
        assertEquals("Votre progression des cartes mémo", bundle.getString("flashcardProgress"));
        assertEquals("Vous avez complété {1} quiz(zes) sur {0}.", bundle.getString("quizzesCompleted"));
        assertEquals("Vous avez complété {1} carte(s) mémo sur {0}.", bundle.getString("flashcardsMastered"));
    }

    @Test
    public void testHomepagePage() {
        // Assertions for the homepage
        assertEquals("Bibliothèque de Quiz", bundle.getString("quizLibraryButton"));
        assertEquals("Bibliothèque de Cartes mémo", bundle.getString("flashcardLibraryButton"));
        assertEquals("Réalisations", bundle.getString("achievementsButton"));
        assertEquals("Déconnexion", bundle.getString("logoutButton"));
    }

    @Test
    public void testProfilePage() {
        // Assertions for the profile page
        assertEquals("Profil", bundle.getString("profilePageTitle"));
        assertEquals("Nom d'utilisateur :", bundle.getString("currentUsernameLabel"));
        assertEquals("E-mail :", bundle.getString("currentEmailLabel"));
        assertEquals("Mot de passe: **********", bundle.getString("currentPasswordLabel"));
        assertEquals("Changer le nom d'utilisateur :", bundle.getString("changeUsernameLabel"));
        assertEquals("Changer le e-mail:", bundle.getString("changeEmailLabel"));
        assertEquals("Changer le mot de passe :", bundle.getString("changePasswordLabel"));
        assertEquals("Enregistrer les modifications", bundle.getString("saveChangesButton"));
        assertEquals("Au moins une zone doit être remplie pour mettre à jour le profil.", bundle.getString("emptyFieldsAlert"));
        assertEquals("Profil mis à jour avec succès.", bundle.getString("profileUpdateSuccessAlert"));
        assertEquals("Échec de la mise à jour du profil.", bundle.getString("profileUpdateFailedAlert"));
        assertEquals("Retour à la page d'accueil", bundle.getString("backToHomeButton"));
        assertEquals("Progrès", bundle.getString("progressPageButton"));
        assertEquals("Erreur", bundle.getString("errorAlertTitle"));
        assertEquals("Succès", bundle.getString("successAlertTitle"));
    }

    @Test
    public void testFlashcardPage() {
        // Assertions for the flashcard page
        assertEquals("Vous ne maîtrisez pas encore les cartes mémo.", bundle.getString("noMastered"));
        assertEquals("Retourner à la bibliothèque", bundle.getString("backToFlashLibraryButton"));
        assertEquals("Vous avez maîtrisé toutes les flashcards dans ce sujet. Bravo !", bundle.getString("masterAllTopic"));
        assertEquals("Afficher la réponse", bundle.getString("showAnswer"));
        assertEquals("Maîtriser ceci", bundle.getString("masterThis"));
        assertEquals("Démaîtriser ceci", bundle.getString("unmasterThis"));
        assertEquals("Prochaine carte mémo", bundle.getString("nextFlashcard"));
        assertEquals("Afficher le terme", bundle.getString("showTerm"));
        assertEquals("Afficher la traduction", bundle.getString("showTranslation"));
    }

    @Test
    public void testFlashcardLibraryPage() {
        // Assertions for the flashcard library page
        assertEquals("Cartes mémo", bundle.getString("flashcardTitle"));
        assertEquals("Démaîtriser tout", bundle.getString("unmasterAllButton"));
        assertEquals("Cartes mémo maîtrisées", bundle.getString("masteredFlashcardsButton"));
    }

    @Test
    public void testQuizPage() {
        // Assertions for the quiz page
        assertEquals("Soumettre une réponse", bundle.getString("submitAnswer"));
        assertEquals("Annuler le Quiz", bundle.getString("cancelQuiz"));
        assertEquals("Correct !", bundle.getString("correct"));
        assertEquals("Incorrect ! La bonne réponse est :", bundle.getString("incorrect"));
        assertEquals("Veuillez sélectionner une réponse avant de soumettre.", bundle.getString("selectAnswerError"));
        assertEquals("Quiz terminé !", bundle.getString("quizFinished"));
        assertEquals("Votre score :", bundle.getString("yourScore"));
        assertEquals("hors de ", bundle.getString("outOf"));
    }
}
