package mindexpander.storage;

import mindexpander.common.Messages;
import mindexpander.data.QuestionBank;
import mindexpander.data.question.FillInTheBlanks;
import mindexpander.data.question.MultipleChoice;
import mindexpander.data.question.Question;
import mindexpander.data.question.QuestionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Handles saving and loading of {@code QuestionBank} to ensure data persistence.
 * <p>
 * The file format follows:
 * <pre>
 * FITB|QuestionText|Answer
 * MCQ|QuestionText|Option1|Option2|Option3|Option4
 * </pre>
 * <ul>
 *     <li>For FITB, the third field is the correct answer.</li>
 *     <li>For MCQ, the first option is always treated as the correct answer,
 *         and the remaining three are incorrect options.</li>
 * </ul>
 * <p>
 * This class ensures that saved questions are automatically restored
 * when the application restarts.
 * Currently supports:
 * <ul>
 *     <li>Fill-in-the-Blanks (FITB)</li>
 *     <li>Multiple Choice Questions (MCQ)</li>
 * </ul>
 *
 * @author Jensen Kuok
 * @version 2.0
 * @since 2025-03-14
 */
public class StorageFile {
    private final File file;

    public StorageFile() {
        this.file = new File("./data/MindExpander.txt");
    }

    /**
     * Saves the given {@code QuestionBank} to the storage file.
     * <p>
     * Ensures the storage directory exists before writing data.
     *
     * @param questionBank The {@code QuestionBank} instance to be saved.
     */
    public void save(QuestionBank questionBank) {
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            boolean directoryCreated = parentDir.mkdirs();
            if (!directoryCreated) {
                System.out.println("Warning: Failed to create directory for storage file.");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < questionBank.getQuestionCount(); i++) {
                Question q = questionBank.getQuestion(i);
                writer.write(formatQuestionForSaving(q));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private String formatQuestionForSaving(Question q) {
        String delimiter = Messages.STORAGE_DELIMITER;
        if (q.getType() == QuestionType.FITB) {
            return "FITB" + delimiter + q.getQuestion() + delimiter + q.getAnswer();
        } else if (q.getType() == QuestionType.MCQ) {
            MultipleChoice mcq = (MultipleChoice) q;
            List<String> options = mcq.getOptions();
            return "MCQ" + delimiter + mcq.getQuestion() + delimiter + String.join(delimiter, options);
        }
        return "";
    }

    /**
     * Loads questions from the storage file into a new {@code QuestionBank} instance.
     * <p>
     * If the storage file does not exist, an empty {@code QuestionBank} is returned.
     * Otherwise, it reads the file line by line, parses each question, and adds it
     * to the {@code QuestionBank}.
     * </p>
     *
     * @return A {@code QuestionBank} containing all questions loaded from the storage file.
     *         If the file is missing or empty, an empty {@code QuestionBank} is returned.
     */
    public QuestionBank load() {
        List<Question> questions = new ArrayList<>();

        if (!file.exists()) {
            return new QuestionBank(questions);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Question parsedQuestion = parseQuestionFromFile(line);
                if (parsedQuestion != null) {
                    questions.add(parsedQuestion);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        return new QuestionBank(questions);
    }

    /**
     * Parses a line from the storage file and returns a {@code Question}.
     */
    private Question parseQuestionFromFile(String line) {
        String[] parts = line.split(Pattern.quote(Messages.STORAGE_DELIMITER));
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        String questionText = parts[1];
        String answer = parts[2];

        if ("FITB".equals(type)) {
            return new FillInTheBlanks(questionText, answer);
        } else if ("MCQ".equals(type) && parts.length == 6) {
            String mcqQuestionText = parts[1];
            List<String> options = Arrays.asList(parts[2], parts[3], parts[4], parts[5]);
            String correctAnswer = parts[2];
            return new MultipleChoice(mcqQuestionText, correctAnswer, options);
        }
        return null; // Unsupported or malformed line
    }

}
