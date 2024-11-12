import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

public class ArabicLocalizationTest {

    private ResourceBundle bundle;

    @BeforeEach
    public void setUp() {
        bundle = ResourceBundle.getBundle("bundle_AR");
    }

    @Test
    public void testIndexPage() {
        // Assertions for the index page
        assertEquals("مرحباً بكم في QuadLingo", bundle.getString("welcomeMessage"));
        assertEquals("مرحباً،", bundle.getString("welcomeLabel"));
        assertEquals("رحلة التعلم الخاصة بك تبدأ هنا.", bundle.getString("descriptionMessage"));
        assertEquals("اشتراك", bundle.getString("signUp"));
    }

    @Test
    public void testLoginPage() {
        // Assertions for the login page
        assertEquals("اسم المستخدم", bundle.getString("usernameLabel"));
        assertEquals("كلمة المرور", bundle.getString("passwordLabel"));
        assertEquals("تسجيل الدخول", bundle.getString("login"));
        assertEquals("عُد", bundle.getString("goBackButton"));
        assertEquals("ليس لديك حساب؟ قم بالتسجيل بدلاً من ذلك:", bundle.getString("noAccountMessage"));
        assertEquals("المستخدم غير موجود.", bundle.getString("userDoesNotExist"));
        assertEquals("كلمة المرور غير صحيحة.", bundle.getString("incorrectPassword"));
        assertEquals("خطأ تسجيل الدخول", bundle.getString("loginErrorTitle"));
    }

    @Test
    public void testSignUpPage() {
        // Assertions for the register page
        assertEquals("بريد إلكتروني", bundle.getString("emailLabel"));
        assertEquals("هل لديك حساب بالفعل؟ قم بتسجيل الدخول بدلاً من ذلك:", bundle.getString("hasAccountLabel"));
        assertEquals("جميع الحقول مطلوبة.", bundle.getString("allFieldsRequired"));
        assertEquals("تنسيق البريد الإلكتروني غير صالح.", bundle.getString("invalidEmail"));
        assertEquals("يوجد حساب بهذا البريد الإلكتروني بالفعل.", bundle.getString("accountExists"));
        assertEquals("يجب أن تحتوي كلمة المرور على حرف كبير واحد على الأقل.", bundle.getString("oneUppercaseLetter"));
        assertEquals("يجب أن تحتوي كلمة المرور على رقم واحد على الأقل.", bundle.getString("oneNumber"));
        assertEquals("يجب أن تتكون كلمة المرور من 8 أحرف على الأقل.", bundle.getString("atLeastEight"));
        assertEquals("خطأ في التسجيل", bundle.getString("signUpErrorTitle"));
        assertEquals("فشل التسجيل. يرجى المحاولة مرة أخرى.", bundle.getString("errorContext"));
    }

    @Test
    public void testAchievementsPage() {
        // Assertions for the achievements page
        assertEquals("أكمل اختبار واحد", bundle.getString("quizRequirement1"));
        assertEquals("أكمل 5 اختبارات", bundle.getString("quizRequirement5"));
        assertEquals("أكمل 10 اختبارات", bundle.getString("quizRequirement10"));
        assertEquals("بطاقات تعليمية للمستوى الخامس", bundle.getString("flashcardRequirement5"));
        assertEquals("بطاقات تعليمية لإتقان 10", bundle.getString("flashcardRequirement10"));
        assertEquals("الإنجازات", bundle.getString("achievementsTitle"));
        assertEquals("حساب تعريفي", bundle.getString("profileButton"));
        assertEquals("الصفحة الرئيسية", bundle.getString("homeButton"));
        assertEquals("شارات مفتوحة", bundle.getString("unlockedBadges"));
        assertEquals("شارات مقفلة", bundle.getString("lockedBadges"));
    }

    @Test
    public void testProgressPage() {
        // Assertions for the progress page
        assertEquals("تقدم", bundle.getString("progressTitle"));
        assertEquals("نتيجتك", bundle.getString("userScore"));
        assertEquals("تقدمك في الاختبار", bundle.getString("quizProgress"));
        assertEquals("تقدم بطاقتك التعليمية", bundle.getString("flashcardProgress"));
        assertEquals("لقد أكملت {1} من {0} اختبارًا.", bundle.getString("quizzesCompleted"));
        assertEquals("لقد أتقنت {1} من {0} بطاقة تعليمية.", bundle.getString("flashcardsMastered"));
    }

    @Test
    public void testHomepagePage() {
        // Assertions for the homepage
        assertEquals("مكتبة الاختبارات", bundle.getString("quizLibraryButton"));
        assertEquals("مكتبة البطاقات التعليمية", bundle.getString("flashcardLibraryButton"));
        assertEquals("الإنجازات", bundle.getString("achievementsButton"));
        assertEquals("تسجيل الخروج", bundle.getString("logoutButton"));
    }

    @Test
    public void testProfilePage() {
        // Assertions for the profile page
        assertEquals("حساب تعريفي", bundle.getString("profilePageTitle"));
        assertEquals("اسم المستخدم:", bundle.getString("currentUsernameLabel"));
        assertEquals("بريد إلكتروني:", bundle.getString("currentEmailLabel"));
        assertEquals("كلمة المرور: **********", bundle.getString("currentPasswordLabel"));
        assertEquals("تغيير اسم المستخدم:", bundle.getString("changeUsernameLabel"));
        assertEquals("تغيير البريد الإلكتروني:", bundle.getString("changeEmailLabel"));
        assertEquals("تغيير كلمة المرور:", bundle.getString("changePasswordLabel"));
        assertEquals("حفظ التغييرات", bundle.getString("saveChangesButton"));
        assertEquals("يجب ملء حقل واحد على الأقل لتحديث الملف الشخصي.", bundle.getString("emptyFieldsAlert"));
        assertEquals("تم تحديث الملف الشخصي بنجاح.", bundle.getString("profileUpdateSuccessAlert"));
        assertEquals("فشل في تحديث الملف الشخصي.", bundle.getString("profileUpdateFailedAlert"));
        assertEquals("العودة إلى الصفحة الرئيسية", bundle.getString("backToHomeButton"));
        assertEquals("تقدم", bundle.getString("progressPageButton"));
        assertEquals("خطأ", bundle.getString("errorAlertTitle"));
        assertEquals("نجاح", bundle.getString("successAlertTitle"));
    }

    @Test
    public void testFlashcardPage() {
        // Assertions for the flashcard page
        assertEquals("لم تتقن البطاقات التعليمية بعد", bundle.getString("noMastered"));
        assertEquals("العودة إلى المكتبة", bundle.getString("backToFlashLibraryButton"));
        assertEquals("لقد أتقنت جميع البطاقات التعليمية في هذا الموضوع. عمل رائع!", bundle.getString("masterAllTopic"));
        assertEquals("إظهار الإجابة", bundle.getString("showAnswer"));
        assertEquals("أتقن هذا", bundle.getString("masterThis"));
        assertEquals("إلغاء إتقان هذا", bundle.getString("unmasterThis"));
        assertEquals("البطاقة التعليمية التالية", bundle.getString("nextFlashcard"));
        assertEquals("عرض المصطلح", bundle.getString("showTerm"));
        assertEquals("إظهار الترجمة", bundle.getString("showTranslation"));
    }

    @Test
    public void testFlashcardLibraryPage() {
        // Assertions for the flashcard library page
        assertEquals("البطاقات التعليمية", bundle.getString("flashcardTitle"));
        assertEquals("إلغاء إتقان جميع البطاقات التعليمية", bundle.getString("unmasterAllButton"));
        assertEquals("البطاقات التعليمية المُدارة", bundle.getString("masteredFlashcardsButton"));
    }

    @Test
    public void testQuizPage() {
        // Assertions for the quiz page
        assertEquals("أرسل الإجابة", bundle.getString("submitAnswer"));
        assertEquals("إلغاء الاختبار", bundle.getString("cancelQuiz"));
        assertEquals("صحيح!", bundle.getString("correct"));
        assertEquals("خطأ! الإجابة الصحيحة هي:", bundle.getString("incorrect"));
        assertEquals("الرجاء اختيار الإجابة قبل الإرسال.", bundle.getString("selectAnswerError"));
        assertEquals("إنتهى الإختبار!", bundle.getString("quizFinished"));
        assertEquals("نقاطك:", bundle.getString("yourScore"));
        assertEquals("خارج من", bundle.getString("outOf"));
    }
}
