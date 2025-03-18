//mostly ai generated

package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainFrame {
    private static JFrame frame;
    private static CardLayout mainCardLayout;
    private static JPanel mainCardPanel;

    public static void main(String[] args) {
        createWindow();
    }

    public static void createWindow() {
        frame = new JFrame("Investment Buddy");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Background panel with image
        BackgroundPanel backgroundImagePanel = new BackgroundPanel("images/image2.jpg");
        backgroundImagePanel.setLayout(new BorderLayout());

        // Main Card Layout Panel
        mainCardLayout = new CardLayout();
        mainCardPanel = new JPanel(mainCardLayout);
        mainCardPanel.setOpaque(false);

        // Add Start Menu
        JPanel startMenu = startMenu();
        startMenu.setOpaque(false);
        mainCardPanel.add(startMenu, "StartMenu");

        // Add InvestmentForm with multiple questions
        List<String> questions = Arrays.asList(
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

        List<String[]> optionsList = Arrays.asList(
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

        InvestmentForm investmentForm = new InvestmentForm(questions, optionsList);
        mainCardPanel.add(investmentForm, "InvestmentForm");

        LoginPage loginPage = new LoginPage();
        mainCardPanel.add(loginPage, "LoginPage");

        HomePage homePage = new HomePage();
        mainCardPanel.add(homePage, "HomePage");

        backgroundImagePanel.add(mainCardPanel, BorderLayout.CENTER);
        frame.setContentPane(backgroundImagePanel);
        frame.setVisible(true);
    }


    public static JPanel startMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

        JPanel leftAlignPanel = createLeftAlignPanel();
        leftAlignPanel.add(createTitle("Investment"));
        leftAlignPanel.add(createTitle("Buddy"));
        leftAlignPanel.add(Box.createVerticalStrut(20));
        leftAlignPanel.add(createSeparator());
        leftAlignPanel.add(Box.createVerticalStrut(20));
        leftAlignPanel.add(createStartButton());

        leftAlignPanel.setOpaque(false);
        panel.add(leftAlignPanel);
        panel.setOpaque(false);
        return panel;
    }

    private static JPanel createLeftAlignPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private static JLabel createTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setForeground(Color.WHITE);
        return title;
    }

    private static JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(325, 8));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setBackground(new Color(192, 192, 192));
        return separator;
    }

    private static JButton createStartButton() {
        JButton startButton = new JButton("  start  ");
        startButton.setFont(new Font("Arial", Font.PLAIN, 60));
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startButton.setBackground(new Color(184, 134, 11));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            mainCardLayout.show(mainCardPanel, "LoginPage");
        });

        return startButton;
    }
}
