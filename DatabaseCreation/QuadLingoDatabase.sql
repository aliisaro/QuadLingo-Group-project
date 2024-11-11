/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for QuadLingo
DROP DATABASE IF EXISTS `QuadLingo`;
CREATE DATABASE IF NOT EXISTS `QuadLingo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `QuadLingo`;

-- Dumping structure for taulu QuadLingo.ANSWER
DROP TABLE IF EXISTS `ANSWER`;
CREATE TABLE IF NOT EXISTS `ANSWER` (
  `AnswerID` int(11) NOT NULL AUTO_INCREMENT,
  `Answer` varchar(50) NOT NULL,
  PRIMARY KEY (`AnswerID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.ANSWER: ~50 rows (suunnilleen)
INSERT INTO `ANSWER` (`AnswerID`, `Answer`) VALUES
	(1, 'Hei'),
	(2, 'Kiitos'),
	(3, 'Anteeksi'),
	(4, 'Kyllä'),
	(5, 'Ei'),
	(6, 'Yksi'),
	(7, 'Kaksi'),
	(8, 'Kolme'),
	(9, 'Neljä'),
	(10, 'Viisi'),
	(11, 'Punainen'),
	(12, 'Sininen'),
	(13, 'Vihreä'),
	(14, 'Keltainen'),
	(15, 'Musta'),
	(16, 'Hyvää huomenta'),
	(17, 'Hyvää yötä'),
	(18, 'Nähdään myöhemmin'),
	(19, 'Näkemiin'),
	(20, 'Hyvää päivänjatkoa'),
	(21, 'Leipä'),
	(22, 'Maito'),
	(23, 'Omena'),
	(24, 'Juusto'),
	(25, 'Vesi'),
	(26, 'Lentokenttä'),
	(27, 'Passi'),
	(28, 'Lippu'),
	(29, 'Juna-asema'),
	(30, 'Turvatarkastus'),
	(31, 'Rakastan sinua'),
	(32, 'Anteeksi'),
	(33, 'Olen pahoillani'),
	(34, 'Onnittelut'),
	(35, 'Hyvää syntymäpäivää'),
	(36, 'Minä'),
	(37, 'Sinä'),
	(38, 'Hän'),
	(39, 'Me'),
	(40, 'He'),
	(41, 'Maanantai'),
	(42, 'Kesäkuu'),
	(43, 'Joulukuu'),
	(44, 'Keskiviikko'),
	(45, 'Syyskuu'),
	(46, 'Syödä'),
	(47, 'Juoda'),
	(48, 'Mennä'),
	(49, 'Tulla'),
	(50, 'Nähdä');

-- Dumping structure for taulu QuadLingo.BADGE
DROP TABLE IF EXISTS `BADGE`;
CREATE TABLE IF NOT EXISTS `BADGE` (
  `BadgeID` int(11) NOT NULL AUTO_INCREMENT,
  `BadgeTitle` varchar(200) NOT NULL,
  `QuizzesRequired` int(11) DEFAULT NULL,
  PRIMARY KEY (`BadgeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.BADGE: ~0 rows (suunnilleen)

-- Dumping structure for taulu QuadLingo.CORRECTANSWER
DROP TABLE IF EXISTS `CORRECTANSWER`;
CREATE TABLE IF NOT EXISTS `CORRECTANSWER` (
  `QuestionID` int(11) NOT NULL,
  `AnswerID` int(11) NOT NULL,
  KEY `QuestionID` (`QuestionID`),
  KEY `AnswerID` (`AnswerID`),
  CONSTRAINT `CORRECTANSWER_ibfk_1` FOREIGN KEY (`QuestionID`) REFERENCES `QUESTION` (`QuestionID`),
  CONSTRAINT `CORRECTANSWER_ibfk_2` FOREIGN KEY (`AnswerID`) REFERENCES `ANSWER` (`AnswerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.CORRECTANSWER: ~55 rows (suunnilleen)
INSERT INTO `CORRECTANSWER` (`QuestionID`, `AnswerID`) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 4),
	(5, 5),
	(6, 6),
	(7, 7),
	(8, 8),
	(9, 9),
	(10, 10),
	(11, 11),
	(12, 12),
	(13, 13),
	(14, 14),
	(15, 15),
	(16, 16),
	(17, 17),
	(18, 18),
	(19, 19),
	(20, 20),
	(21, 21),
	(22, 22),
	(23, 23),
	(24, 24),
	(25, 25),
	(26, 26),
	(27, 27),
	(28, 28),
	(29, 29),
	(30, 30),
	(31, 31),
	(32, 32),
	(33, 33),
	(34, 34),
	(35, 35),
	(36, 36),
	(37, 37),
	(38, 38),
	(39, 39),
	(40, 40),
	(41, 41),
	(42, 42),
	(43, 43),
	(44, 44),
	(45, 45),
	(46, 46),
	(47, 47),
	(48, 48),
	(49, 49),
	(50, 50),
	(51, 1),
	(52, 2),
	(53, 3),
	(54, 4),
	(55, 5);

-- Dumping structure for taulu QuadLingo.EARNED
DROP TABLE IF EXISTS `EARNED`;
CREATE TABLE IF NOT EXISTS `EARNED` (
  `BadgeID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  KEY `BadgeID` (`BadgeID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `EARNED_ibfk_1` FOREIGN KEY (`BadgeID`) REFERENCES `BADGE` (`BadgeID`),
  CONSTRAINT `EARNED_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `LINGOUSER` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.EARNED: ~0 rows (suunnilleen)

-- Dumping structure for taulu QuadLingo.FLASHCARD
DROP TABLE IF EXISTS `FLASHCARD`;
CREATE TABLE IF NOT EXISTS `FLASHCARD` (
  `FlashcardID` int(11) NOT NULL AUTO_INCREMENT,
  `Term` varchar(200) NOT NULL,
  `Translation` varchar(200) NOT NULL,
  `Topic` varchar(200) NOT NULL,
  `language_code2` varchar(50) NOT NULL,
  PRIMARY KEY (`FlashcardID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table QuadLingo.FLASHCARD: ~37 rows (suunnilleen)
INSERT INTO `FLASHCARD` (`FlashcardID`, `Term`, `Translation`, `Topic`, `language_code2`) VALUES
	(1, 'Dog', 'Koira', 'Animals', 'en'),
	(2, 'Cat', 'Kissa', 'Animals', 'en'),
	(3, 'Car', 'Auto', 'Life', 'en'),
	(4, 'Home', 'Koti', 'Life', 'en'),
	(5, 'Children', 'Lapsia', 'Life', 'en'),
	(6, 'Bathroom', 'Kylpyhuone', 'Life', 'en'),
	(7, 'Pilot', 'Lentäjä', 'Travel', 'en'),
	(8, 'Hotel', 'Hotelli', 'Travel', 'en'),
	(9, 'Travel', 'Matkustaa', 'Travel', 'en'),
	(10, 'Passport', 'Passi', 'Travel', 'en'),
	(11, 'Apple', 'Omena', 'Food', 'en'),
	(12, 'Bread', 'Leipä', 'Food', 'en'),
	(13, 'Milk', 'Maito', 'Food', 'en'),
	(14, 'Vegetable', 'Vihannes', 'Food', 'en'),
	(15, 'Meat', 'Liha', 'Food', 'en'),
	(16, 'Tree', 'Puu', 'Nature', 'en'),
	(17, 'Lake', 'Järvi', 'Nature', 'en'),
	(18, 'Mountain', 'Vuori', 'Nature', 'en'),
	(19, 'Sea', 'Meri', 'Nature', 'en'),
	(20, 'Iloinen', 'Hilpeä', 'Synonyms for adjectives', 'en'),
	(21, 'Surullinen', 'Alakuloinen', 'Synonyms for adjectives', 'en'),
	(22, 'Nopea', 'Ripeä', 'Synonyms for adjectives', 'en'),
	(23, 'Vihainen', 'Kiukkuinen', 'Synonyms for adjectives', 'en'),
	(24, 'Iso', 'Suuri', 'Synonyms for adjectives', 'en'),
	(25, 'Näkemiin', 'Goodbye', 'Communication', 'en'),
	(26, 'Kiva tavata', 'Nice to meet you', 'Communication', 'en'),
	(27, 'Mitä kuuluu?', 'How are you?', 'Communication', 'en'),
	(28, 'Nähdään myöhemmin', 'See you later', 'Communication', 'en'),
	(29, 'Break a leg', 'Onnea', 'Idioms', 'en'),
	(30, 'Burn the midnight oil', 'Opiskella myöhään', 'Idioms', 'en'),
	(31, 'Piece of cake', 'Erittäin helppoa', 'Idioms', 'en'),
	(32, 'Hit the nail on the head', 'Tarkka', 'Idioms', 'en'),
	(33, 'Let the cat out of the bag', 'Paljastaa salaisuus', 'Idioms', 'en'),
	(34, '车', 'Auto', '生活', 'ch'),
	(35, '家', 'Koti', '生活', 'ch'),
	(36, '孩子们', 'Lapset', '生活', 'ch'),
	(37, '浴室', 'Kylpyhuone', '生活', 'ch');

-- Dumping structure for taulu QuadLingo.ISCOMPLETED
DROP TABLE IF EXISTS `ISCOMPLETED`;
CREATE TABLE IF NOT EXISTS `ISCOMPLETED` (
  `UserID` int(11) NOT NULL,
  `QuizID` int(11) NOT NULL,
  `Score` int(11) DEFAULT NULL,
  KEY `UserID` (`UserID`),
  KEY `QuizID` (`QuizID`),
  CONSTRAINT `ISCOMPLETED_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `LINGOUSER` (`UserID`),
  CONSTRAINT `ISCOMPLETED_ibfk_2` FOREIGN KEY (`QuizID`) REFERENCES `QUIZ` (`QuizID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.ISCOMPLETED: ~25 rows (suunnilleen)
INSERT INTO `ISCOMPLETED` (`UserID`, `QuizID`, `Score`) VALUES
	(1, 1, 5),
	(1, 2, 5),
	(1, 3, 5),
	(1, 4, 5),
	(1, 5, 5),
	(1, 6, 5),
	(1, 7, 5),
	(1, 8, 5),
	(1, 9, 5),
	(1, 10, 5),
	(15, 1, 5),
	(2, 7, 4),
	(2, 5, 2),
	(940, 3, 5),
	(940, 1, 5),
	(940, 8, 5),
	(940, 6, 5),
	(15, 2, 5),
	(15, 3, 5),
	(989, 7, 3),
	(989, 1, 1),
	(1073, 1, 4),
	(1073, 2, 4),
	(1073, 3, 3),
	(1073, 5, 5);

-- Dumping structure for taulu QuadLingo.ISMASTERED
DROP TABLE IF EXISTS `ISMASTERED`;
CREATE TABLE IF NOT EXISTS `ISMASTERED` (
  `UserID` int(11) NOT NULL,
  `FlashcardID` int(11) NOT NULL,
  KEY `UserID` (`UserID`),
  KEY `FlashcardID` (`FlashcardID`),
  CONSTRAINT `ISMASTERED_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `LINGOUSER` (`UserID`),
  CONSTRAINT `ISMASTERED_ibfk_2` FOREIGN KEY (`FlashcardID`) REFERENCES `FLASHCARD` (`FlashcardID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table QuadLingo.ISMASTERED: ~9 rows (suunnilleen)
INSERT INTO `ISMASTERED` (`UserID`, `FlashcardID`) VALUES
	(15, 1),
	(15, 1),
	(990, 3),
	(990, 3),
	(990, 3),
	(2, 1),
	(2, 2),
	(2, 1),
	(2, 1);

-- Dumping structure for taulu QuadLingo.LINGOUSER
DROP TABLE IF EXISTS `LINGOUSER`;
CREATE TABLE IF NOT EXISTS `LINGOUSER` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `UserPassword` varchar(60) NOT NULL,
  `QuizzesCompleted` int(11) DEFAULT 0,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `unique_email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=1074 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for taulu QuadLingo.QUESTION
DROP TABLE IF EXISTS `QUESTION`;
CREATE TABLE IF NOT EXISTS `QUESTION` (
  `QuestionID` int(11) NOT NULL AUTO_INCREMENT,
  `Question` varchar(200) NOT NULL,
  `QuizID` int(11) NOT NULL,
  PRIMARY KEY (`QuestionID`),
  KEY `QuizID` (`QuizID`),
  CONSTRAINT `QUESTION_ibfk_1` FOREIGN KEY (`QuizID`) REFERENCES `QUIZ` (`QuizID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table QuadLingo.QUESTION: ~55 rows (suunnilleen)
INSERT INTO `QUESTION` (`QuestionID`, `Question`, `QuizID`) VALUES
	(1, 'What is the Finnish word for "hello"?', 1),
	(2, 'What is the Finnish word for "thank you"?', 1),
	(3, 'What is the Finnish word for "sorry"?', 1),
	(4, 'What is the Finnish word for "yes"?', 1),
	(5, 'What is the Finnish word for "no"?', 1),
	(6, 'What is the Finnish word for the number "one"?', 2),
	(7, 'What is the Finnish word for the number "two"?', 2),
	(8, 'What is the Finnish word for the number "three"?', 2),
	(9, 'What is the Finnish word for the number "four"?', 2),
	(10, 'What is the Finnish word for the number "five"?', 2),
	(11, 'What is the Finnish word for the color "red"?', 3),
	(12, 'What is the Finnish word for the color "blue"?', 3),
	(13, 'What is the Finnish word for the color "green"?', 3),
	(14, 'What is the Finnish word for the color "yellow"?', 3),
	(15, 'What is the Finnish word for the color "black"?', 3),
	(16, 'How do you say "Good morning" in Finnish?', 4),
	(17, 'How do you say "Good night" in Finnish?', 4),
	(18, 'How do you say "See you later" in Finnish?', 4),
	(19, 'How do you say "Goodbye" in Finnish?', 4),
	(20, 'How do you say "Have a nice day" in Finnish?', 4),
	(21, 'What is the Finnish word for "bread"?', 5),
	(22, 'What is the Finnish word for "milk"?', 5),
	(23, 'What is the Finnish word for "apple"?', 5),
	(24, 'What is the Finnish word for "cheese"?', 5),
	(25, 'What is the Finnish word for "water"?', 5),
	(26, 'What is the Finnish word for "airport"?', 6),
	(27, 'What is the Finnish word for "passport"?', 6),
	(28, 'What is the Finnish word for "ticket"?', 6),
	(29, 'What is the Finnish word for "train station"?', 6),
	(30, 'What is the Finnish word for "security check"?', 6),
	(31, 'How do you say "I love you" in Finnish?', 7),
	(32, 'How do you say "Excuse me" in Finnish?', 7),
	(33, 'How do you say "I\'m sorry" in Finnish?', 7),
	(34, 'How do you say "Congratulations" in Finnish?', 7),
	(35, 'How do you say "Happy Birthday" in Finnish?', 7),
	(36, 'What is the Finnish word for "I"?', 8),
	(37, 'What is the Finnish word for "you" (singular informal)?', 8),
	(38, 'What is the Finnish word for "he/she"?', 8),
	(39, 'What is the Finnish word for "we"?', 8),
	(40, 'What is the Finnish word for "they"?', 8),
	(41, 'What is the Finnish word for "Monday"?', 9),
	(42, 'What is the Finnish word for "June"?', 9),
	(43, 'What is the Finnish word for "December"?', 9),
	(44, 'What is the Finnish word for "Wednesday"?', 9),
	(45, 'What is the Finnish word for "September"?', 9),
	(46, 'What is the Finnish word for "to eat"?', 10),
	(47, 'What is the Finnish word for "to drink"?', 10),
	(48, 'What is the Finnish word for "to go"?', 10),
	(49, 'What is the Finnish word for "to come"?', 10),
	(50, 'What is the Finnish word for "to see"?', 10),
	(51, '芬兰语中的“你好”是哪个单词？', 11),
	(52, '芬兰语中的“谢谢”怎么说？', 11),
	(53, '芬兰语中的“请”是哪个单词？', 11),
	(54, '芬兰语中的“是”是哪个单词？', 11),
	(55, '芬兰语中的“不”是哪个单词？', 11);

-- Dumping structure for taulu QuadLingo.QUIZ
DROP TABLE IF EXISTS `QUIZ`;
CREATE TABLE IF NOT EXISTS `QUIZ` (
  `QuizID` int(11) NOT NULL AUTO_INCREMENT,
  `QuizTitle` varchar(200) NOT NULL,
  `QuizScore` int(11) NOT NULL,
  `language_code` varchar(2) NOT NULL DEFAULT 'en',
  PRIMARY KEY (`QuizID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table QuadLingo.QUIZ: ~11 rows (suunnilleen)
INSERT INTO `QUIZ` (`QuizID`, `QuizTitle`, `QuizScore`, `language_code`) VALUES
	(1, 'Basic Finnish Vocabulary', 5, 'en'),
	(2, 'Finnish Numbers', 5, 'en'),
	(3, 'Finnish Colors', 5, 'en'),
	(4, 'Finnish Greetings and Farewells', 5, 'en'),
	(5, 'Finnish Food Vocabulary', 5, 'en'),
	(6, 'Finnish Travel Vocabulary', 5, 'en'),
	(7, 'Finnish Phrases', 5, 'en'),
	(8, 'Finnish Grammar Basics', 5, 'en'),
	(9, 'Finnish Days and Months', 5, 'en'),
	(10, 'Finnish Common Verbs', 5, 'en'),
	(11, '基础芬兰语词汇', 5, 'ch');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
