import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

public class ChineseLocalizationTest {

    private ResourceBundle bundle;

    @BeforeEach
    public void setUp() {
        bundle = ResourceBundle.getBundle("bundle_CH");
    }

    @Test
    public void testIndexPage() {
        // Assertions for the index page
        assertEquals("欢迎来到QuadLingo", bundle.getString("welcomeMessage"));
        assertEquals("欢迎", bundle.getString("welcomeLabel"));
        assertEquals("您的学习之旅从这里开始。", bundle.getString("descriptionMessage"));
        assertEquals("报名", bundle.getString("signUp"));
    }

    @Test
    public void testLoginPage() {
        // Assertions for the login page
        assertEquals("用户名", bundle.getString("usernameLabel"));
        assertEquals("密码", bundle.getString("passwordLabel"));
        assertEquals("登录", bundle.getString("login"));
        assertEquals("回去", bundle.getString("goBackButton"));
        assertEquals("没有帐户？改为注册：", bundle.getString("noAccountMessage"));
        assertEquals("用户不存在。", bundle.getString("userDoesNotExist"));
        assertEquals("密码不正确", bundle.getString("incorrectPassword"));
        assertEquals("登录错误", bundle.getString("loginErrorTitle"));
    }

    @Test
    public void testSignUpPage() {
        // Assertions for the register page
        assertEquals("电子邮件", bundle.getString("emailLabel"));
        assertEquals("已经有帐户？改为登录：", bundle.getString("hasAccountLabel"));
        assertEquals("所有字段均为必填字段。", bundle.getString("allFieldsRequired"));
        assertEquals("电子邮件格式无效", bundle.getString("invalidEmail"));
        assertEquals("使用此电子邮件的帐户已存在。", bundle.getString("accountExists"));
        assertEquals("密码必须包含至少 1 个大写字母。", bundle.getString("oneUppercaseLetter"));
        assertEquals("密码必须包含至少 1 个数字。", bundle.getString("oneNumber"));
        assertEquals("密码必须至少为 8 个字符。", bundle.getString("atLeastEight"));
        assertEquals("注册错误", bundle.getString("signUpErrorTitle"));
        assertEquals("注册失败。请再试一次。", bundle.getString("errorContext"));
    }

    @Test
    public void testAchievementsPage() {
        // Assertions for the achievements page
        assertEquals("完成 1 项测验", bundle.getString("quizRequirement1"));
        assertEquals("完成 5 个测验", bundle.getString("quizRequirement5"));
        assertEquals("完成 10 个测验", bundle.getString("quizRequirement10"));
        assertEquals("掌握 5 张抽认卡", bundle.getString("flashcardRequirement5"));
        assertEquals("掌握 10 张抽认卡", bundle.getString("flashcardRequirement10"));
        assertEquals("成就", bundle.getString("achievementsTitle"));
        assertEquals("轮廓", bundle.getString("profileButton"));
        assertEquals("主页", bundle.getString("homeButton"));
        assertEquals("解锁徽章", bundle.getString("unlockedBadges"));
        assertEquals("锁定徽章", bundle.getString("lockedBadges"));
    }

    @Test
    public void testProgressPage() {
        // Assertions for the progress page
        assertEquals("进步", bundle.getString("progressTitle"));
        assertEquals("你的分数", bundle.getString("userScore"));
        assertEquals("您的测验进度", bundle.getString("quizProgress"));
        assertEquals("你的抽认卡进度", bundle.getString("flashcardProgress"));
        assertEquals("你已经完成了 {0} 个测验中的 {1} 个。", bundle.getString("quizzesCompleted"));
        assertEquals("你已经掌握了 {0} 张抽认卡中的 {1} 张。", bundle.getString("flashcardsMastered"));
    }

    @Test
    public void testHomepagePage() {
        // Assertions for the homepage
        assertEquals("测验库", bundle.getString("quizLibraryButton"));
        assertEquals("抽认卡库", bundle.getString("flashcardLibraryButton"));
        assertEquals("成就", bundle.getString("achievementsButton"));
        assertEquals("注销", bundle.getString("logoutButton"));
    }
    @Test
    public void testProfilePage() {
        // Assertions for the profile page
        assertEquals("轮廓", bundle.getString("profilePageTitle"));
        assertEquals("用户名:", bundle.getString("currentUsernameLabel"));
        assertEquals("电子邮件:", bundle.getString("currentEmailLabel"));
        assertEquals("密码:", bundle.getString("currentPasswordLabel"));
        assertEquals("更改用户名:", bundle.getString("changeUsernameLabel"));
        assertEquals("更改电子邮件:", bundle.getString("changeEmailLabel"));
        assertEquals("更改密码:", bundle.getString("changePasswordLabel"));
        assertEquals("保存更改", bundle.getString("saveChangesButton"));
        assertEquals("必须至少填写一个字段才能更新个人资料。", bundle.getString("emptyFieldsAlert"));
        assertEquals("个人资料更新成功。", bundle.getString("profileUpdateSuccessAlert"));
        assertEquals("更新个人资料失败。", bundle.getString("profileUpdateFailedAlert"));
        assertEquals("主页", bundle.getString("backToHomeButton"));
        assertEquals("进步", bundle.getString("progressPageButton"));
        assertEquals("错误", bundle.getString("errorAlertTitle"));
        assertEquals("成功", bundle.getString("successAlertTitle"));
        assertEquals("更改语言:", bundle.getString("changeLanguageLabel"));

    }

    @Test
    public void testFlashcardPage() {
        // Assertions for the flashcard page
        assertEquals("您还没有掌握抽认卡。", bundle.getString("noMastered"));
        assertEquals("抽认卡库", bundle.getString("backToFlashLibraryButton"));
        assertEquals("您已经掌握了本主题中的所有抽认卡。干得好！", bundle.getString("masterAllTopic"));
        assertEquals("显示答案", bundle.getString("showAnswer"));
        assertEquals("掌握这个", bundle.getString("masterThis"));
        assertEquals("解开这个", bundle.getString("unmasterThis"));
        assertEquals("下一张抽认卡", bundle.getString("nextFlashcard"));
        assertEquals("显示术语", bundle.getString("showTerm"));
        assertEquals("显示翻译", bundle.getString("showTranslation"));
    }

    @Test
    public void testFlashcardLibraryPage() {
        // Assertions for the flashcard library page
        assertEquals("抽认卡", bundle.getString("flashcardTitle"));
        assertEquals("解除所有控制", bundle.getString("unmasterAllButton"));
        assertEquals("掌握抽认卡", bundle.getString("masteredFlashcardsButton"));
    }

    @Test
    public void testQuizPage() {
        // Assertions for the quiz page
        assertEquals("提交答案", bundle.getString("submitAnswer"));
        assertEquals("取消测验", bundle.getString("cancelQuiz"));
        assertEquals("正确答案", bundle.getString("correct"));
        assertEquals("不正确！正确答案是：", bundle.getString("incorrect"));
        assertEquals("请在提交前选择一个答案。", bundle.getString("selectAnswerError"));
        assertEquals("测验结束！", bundle.getString("quizFinished"));
        assertEquals("您的分数：", bundle.getString("yourScore"));
        assertEquals("出", bundle.getString("outOf"));
    }

    @Test
    public void testLoggedOutPage() {
        // Assertions for the logged out page
        assertEquals("您已退出。", bundle.getString("loggedOutMessage"));
        assertEquals("转到起始页", bundle.getString("indexPageButton"));
    }

    @Test
    public void testHelpMessage() {
        // Assertions for help messages
        assertEquals("这是您的主页。您可以从这里访问测验库、抽认卡库、成就和您的个人资料。", bundle.getString("helpHomepage"));
        assertEquals("这是测验库。您可以从这里参加测验。测验分为不同的主题。", bundle.getString("helpQuizLibrary"));
        assertEquals("这是抽认卡库。你可以练习词汇。抽认卡分为不同的主题。", bundle.getString("helpFlashcardLibrary"));
        assertEquals("要访问 QuadLingo 的功能，请登录或注册。", bundle.getString("helpIndex"));
        assertEquals("帮助", bundle.getString("help"));
    }
}
