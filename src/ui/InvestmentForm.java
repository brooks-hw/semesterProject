package ui;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class InvestmentForm extends JPanel {
    private CardLayout cardLayout;
    private JPanel questionPanel;
    private List<String> questions;
    private List<String[]> optionsList;
    private int totalQuestions;
    private int currentQuestionIndex = 0;
    private JLabel smallHeader;
    private int totalScore = -1;
    private List<Integer> answers;
    private iScreenManager screenManager;
    private Image backgroundImage;

    public InvestmentForm(iScreenManager screenManager) {
        // Hardcode questions and options here
        this.questions = Arrays.asList(
                "How would you react if your investments dropped by 20%?",
                "How long are you willing to invest for?",
                "How do you feel about high-risk investments?",
                "What is your main investment goal?",
                "How much experience do you have with investing?",
                "What is your tolerance for losing money?",
                "What type of investments do you prefer?",
                "How do you feel about new & unfamiliar industries?",
                "How involved do you want to be in managing investments?",
                "What type of investor do you consider yourself to be?"
        );

        this.optionsList = Arrays.asList(
                new String[]{"Buy more investments", "Hold onto my investments", "Sell some investments", "Sell all my investments"},
                new String[]{"More than 10 years", "5-10 years", "1-5 years", "Less than a year"},
                new String[]{"Seek high returns", "Comfortable with high risks", "Take small risks", "Avoid them completely"},
                new String[]{"Short-term profit", "Wealth accumulation", "Steady income", "Retirement savings"},
                new String[]{"Extensive experience", "Moderate experience", "A little, but I'm still learning", "None, I am a beginner"},
                new String[]{"I'm comfortable losing money", "I can handle moderate losses", "I can tolerate minor losses", "I can't afford to lose any money"},
                new String[]{"High-growth assets like crypto", "Mostly stocks, with some risk", "Balanced, some stocks & bonds", "Safe investments like bonds"},
                new String[]{"Enjoy taking risks", "Open to experimenting", "Willing to consider", "Prefer known industries"},
                new String[]{"I want to make all decisions", "I actively manage it", "I monitor my investments", "I want no involvement"},
                new String[]{"A speculative investor", "An aggressive investor", "A balanced investor", "A conservative investor"}
        );

        this.totalQuestions = questions.size();
        this.answers = Arrays.asList(new Integer[totalQuestions]);
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon(getClass().getResource("/images/image2.jpg")).getImage();

        setLayout(new BorderLayout());
        setOpaque(false);

        // Small Header (Top Left Corner)
        smallHeader = new JLabel(getHeaderText(), SwingConstants.LEFT);
        smallHeader.setFont(new Font("Arial", Font.PLAIN, 18));
        smallHeader.setForeground(Color.GRAY);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        headerPanel.add(smallHeader);
        add(headerPanel, BorderLayout.NORTH);

        // Question Panel (CardLayout for switching questions)
        cardLayout = new CardLayout();
        questionPanel = new JPanel(cardLayout);
        questionPanel.setOpaque(false);

        // Create question cards
        for (int i = 0; i < totalQuestions; i++) {
            questionPanel.add(createQuestionCard(i), "Q" + i);
        }

        // Add final card (congratulations page)
        questionPanel.add(new FormCongratulations(screenManager), "Q" + totalQuestions);

        // Show first question by default
        cardLayout.show(questionPanel, "Q0");
        smallHeader.setText(getHeaderText());

        add(questionPanel, BorderLayout.CENTER);
    }

    private String getHeaderText() {
        return "Risk Profile Analysis: Question " + (currentQuestionIndex + 1);
    }

    private JPanel createQuestionCard(int questionIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Question Label
        JLabel questionLabel = new JLabel(questions.get(questionIndex), SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        questionLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(questionLabel);

        panel.add(Box.createVerticalStrut(50));
        panel.add(labelPanel);
        panel.add(Box.createVerticalStrut(50));

        // Options Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        for (int i = 0; i < optionsList.get(questionIndex).length; i++) {
            String option = optionsList.get(questionIndex)[i];
            int optionIndex = i;

            JButton button = new JButton(option);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setBackground(new Color(184, 134, 11));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(500, 70));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    recordAnswer(optionIndex);
                    nextQuestion();
                }
            });

            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonWrapper.setOpaque(false);
            buttonWrapper.add(button);

            buttonPanel.add(buttonWrapper);
            buttonPanel.add(Box.createVerticalStrut(20));
        }

        panel.add(buttonPanel);
        return panel;
    }

    private void nextQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            currentQuestionIndex++;
            if (currentQuestionIndex < totalQuestions) {
                smallHeader.setText(getHeaderText());
                cardLayout.show(questionPanel, "Q" + currentQuestionIndex);
            } else {
                User user = User.getInstance();
                user.setTotalScore(totalScore);
                user.setRiskProfile(calculateRiskProfile(totalScore));
                try {
                    writeData("accData", totalScore, calculateRiskProfile(totalScore));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                smallHeader.setText("Risk Profile Analysis: Complete!");
                screenManager.switchTo("Congratulations");
            }
            revalidate();
            repaint();
        }
    }

    private void recordAnswer(int optionIndex) {
        answers.set(currentQuestionIndex, optionIndex);
        totalScore += (3 - optionIndex); // 3 points for most aggressive, 0 for most conservative
    }

    public void resetForm() {
        currentQuestionIndex = 0;
        totalScore = -1;
        answers = Arrays.asList(new Integer[totalQuestions]);
        smallHeader.setText(getHeaderText());
        cardLayout.show(questionPanel, "Q0");
        revalidate();
        repaint();
        totalScore = 0;
    }

    private String calculateRiskProfile(int score) {
        if (score <= 8) {
            return "Conservative Investor";
        } else if (score <= 16) {
            return "Balanced Investor";
        } else if (score <= 23) {
            return "Aggressive Investor";
        } else {
            return "Speculative Investor";
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            resetForm();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public static void writeData(String filename, int score, String risk) throws IOException {
        Path filePath = Paths.get("data", filename + ".csv");

        List<String> lines = Files.readAllLines(filePath);
        StringBuilder output = new StringBuilder();

        for (String line : lines) {
            String[] parts = line.split(",");
            User user = User.getInstance();
            if (parts.length >= 3) {
                if (Objects.equals(user.getUsername(), parts[0])) {
                    line = parts[0] + "," + parts[1] + "," + parts[2] + "," + score + "," + risk;
                }
            }
            output.append(line).append("\n");
        }

        Files.write(filePath, output.toString().getBytes());
    }
}
