package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Arrays;


public class InvestmentForm extends JPanel {
    private CardLayout cardLayout;
    private JPanel questionPanel;
    private List<String> questions;
    private List<String[]> optionsList;
    private int totalQuestions;
    private int currentQuestionIndex = 0;
    private JLabel smallHeader;
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
                new String[]{"Sell all my investments", "Sell some investments", "Hold onto my investments", "Buy more investments"},
                new String[]{"Less than a year", "1-5 years", "5-10 years", "More than 10 years"},
                new String[]{"Avoid them completely", "Take small risks", "Comfortable with high risks", "Seek high returns"},
                new String[]{"Wealth accumulation", "Retirement savings", "Short-term profit", "Steady income"},
                new String[]{"None, I am a beginner", "A little, but I'm still learning", "Moderate experience", "Extensive experience"},
                new String[]{"I can't afford to lose any money", "I can tolerate minor losses", "I can handle moderate losses", "I'm comfortable losing money"},
                new String[]{"Safe investments like bonds", "Balanced, some stocks & bonds", "Mostly stocks, with some risk", "High-growth assets like crypto"},
                new String[]{"Prefer known industries", "Willing to consider", "Open to experimenting", "Enjoy taking risks"},
                new String[]{"I want no involvement", "I monitor my investments", "I actively manage it", "I want to make all decisions"},
                new String[]{"A conservative investor", "A balanced investor", "An aggressive investor", "A speculative investor"}
        );

        this.totalQuestions = questions.size();
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();

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

        for (String option : optionsList.get(questionIndex)) {
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
                smallHeader.setText("Risk Profile Analysis: Complete!");
                screenManager.switchTo("Congratulations");
            }
            revalidate();
            repaint();
        }
    }

    public void resetForm() {
        currentQuestionIndex = 0;
        smallHeader.setText(getHeaderText());
        cardLayout.show(questionPanel, "Q0");
        revalidate();
        repaint();
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
}
