//mostly ai generated

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InvestmentForm extends JPanel {
    private CardLayout cardLayout;
    private JPanel questionPanel;
    private List<String> questions;
    private List<String[]> optionsList;
    private int totalQuestions;
    private int currentQuestionIndex = 0;
    private JLabel smallHeader;

    public InvestmentForm(List<String> questions, List<String[]> optionsList) {
        this.questions = questions;
        this.optionsList = optionsList;
        this.totalQuestions = questions.size();

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

        for (int i = 0; i < totalQuestions; i++) {
            questionPanel.add(createQuestionCard(i), "Q" + i);
        }

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
        if (currentQuestionIndex < totalQuestions - 1) {
            currentQuestionIndex++;
            smallHeader.setText(getHeaderText());
            cardLayout.show(questionPanel, "Q" + currentQuestionIndex);

            revalidate();
            repaint();
        }
    }
}