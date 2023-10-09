import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

class Play extends JPanel {

    // Declaring the Game class for changing the scene
    final Game game;

    RandomNumber randomNumber = new RandomNumber();

    ScoringSystem scoringSystem = new ScoringSystem();

    ScoreFiles scoreFiles = new ScoreFiles();

    public Play(Game game) {
        this.game = game;

        // Layout to be used in this panel
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        createGUI();
    }

    void createGUI() {

        // Declare the variables needed
        JLabel playScore, gameText, mysteryNumber, statusImage;
        JButton continueButton, enterButton;
        JTextField inputText;
        JPanel gridPanel;
        int random = randomNumber.generateNumber();

        // Setting up and Display the Score in the Current Game
        playScore = new JLabel("Score: " + scoreFiles.intScore("current_score.txt") + "   Games: "
                + scoreFiles.intScore("num_game.txt"));
        playScore.setFont(new Font("MV Boli", Font.BOLD, 24));
        playScore.setForeground(new Color(0, 0, 0));
        playScore.setBorder(new EmptyBorder(20, 0, 0, 0));
        playScore.setAlignmentX(CENTER_ALIGNMENT);
        add(playScore);

        gameText = new JLabel("Guess the Number");
        gameText.setForeground(new Color(0, 0, 0));
        gameText.setFont(new Font("MV Boli", Font.BOLD, 30));
        gameText.setBorder(new EmptyBorder(30, 0, 0, 0));
        gameText.setAlignmentX(CENTER_ALIGNMENT);
        add(gameText);

        JLabel gameText2 = new JLabel("between 1 and 5");
        gameText2.setForeground(new Color(0, 0, 0));
        gameText2.setFont(new Font("MV Boli", Font.BOLD, 30));
        gameText2.setBorder(new EmptyBorder(5, 0, 0, 0));
        gameText2.setAlignmentX(CENTER_ALIGNMENT);
        add(gameText2);

        mysteryNumber = new JLabel("?");
        mysteryNumber.setIcon(new ImageIcon("mystery_square.png"));
        mysteryNumber.setHorizontalTextPosition(JLabel.CENTER);
        mysteryNumber.setFont(new Font("MV Boli", Font.BOLD, 100));
        mysteryNumber.setForeground(new Color(0, 0, 0));
        mysteryNumber.setAlignmentX(CENTER_ALIGNMENT);
        add(mysteryNumber);

        statusImage = new JLabel("Input a number");
        statusImage.setForeground(new Color(0, 0, 0));
        statusImage.setFont(new Font("MV Boli", Font.BOLD, 30));
        statusImage.setAlignmentX(CENTER_ALIGNMENT);
        statusImage.setBorder(BorderFactory.createEmptyBorder(-10, 0, 15, 0));
        add(statusImage);

        gridPanel = new JPanel();
        gridPanel.setMaximumSize(new Dimension(260, 50));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gridPanel.setLayout(new GridLayout(0, 2));
        gridPanel.setOpaque(false);

        inputText = new JTextField();
        inputText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputText.setBackground(new Color(192, 192, 192));
        inputText.setForeground(new Color(0X62355F));
        inputText.setHorizontalAlignment(JTextField.CENTER);
        inputText.setFont(new Font("MV Boli", Font.BOLD, 28));
        gridPanel.add(inputText);

        enterButton = new JButton("Enter");
        enterButton.setForeground(new Color(0, 0, 0));
        enterButton.setFont(new Font("MV Boli", Font.BOLD, 25));
        enterButton.setBackground(new Color(192, 192, 192));
        enterButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        enterButton.setHorizontalAlignment(JTextField.CENTER);
        enterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gridPanel.add(enterButton);
        add(gridPanel);

        continueButton = new JButton("Continue Playing?");
        continueButton.setForeground(new Color(0, 0, 0));
        continueButton.setBackground(new Color(192, 192, 192));
        continueButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        continueButton.setPreferredSize(new Dimension(250, 50));
        continueButton.setBorder(BorderFactory.createLineBorder(new Color(125, 95, 123), 3));
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.setAlignmentX(CENTER_ALIGNMENT);
        continueGame(continueButton);
        add(continueButton);

        JButton backButton1 = new JButton("Back to Menu");
        backButton1.setFont(new Font("MV Boli", Font.BOLD, 20));
        backButton1.setForeground(new Color(0, 0, 0));
        backButton1.setBackground(new Color(192, 192, 192));
        backButton1.setPreferredSize(new Dimension(200, 50));
        backButton1.setBorder(BorderFactory.createLineBorder(new Color(125, 95, 123), 3));
        backButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton1.setAlignmentX(CENTER_ALIGNMENT);
        linkMenu(backButton1);
        add(backButton1);

        // When user click the enter button
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton1);
            }
        });

        // When user hits the button key while inputting in text field
        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus(inputText, mysteryNumber, random, statusImage, gridPanel, continueButton, backButton1);
            }
        });
    }

    public void linkMenu(JButton jLabel) {
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Want to Stop the Game?", "Are you Sure",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == 0) {
                    // Insert the current score and number of games played to High Score when
                    // quiting the game
                    scoreFiles.compareScore("high_score.txt", "current_score.txt", "num_game.txt");
                    game.showView(new Menu(game));
                }
            }
        });
    }

    // Method to continue a game
    public void continueGame(JButton jLabel) {
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.showView(new Play(game));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("background.png").getImage(), 0, 0, null);
    }

    void changeStatus(JTextField input, JLabel mysterynum, int randnum, JLabel status, JPanel gridPanel,
            JButton contButton, JButton backButton) {
        if (String.valueOf(randnum).equals(input.getText())) {
            status.setText("Correct!");
            status.setFont(new Font("MV Boli", Font.BOLD, 30));
            status.setForeground(new Color(0, 0, 0));
            status.setPreferredSize(new Dimension(200, 50));

            mysterynum.setText(input.getText()); // Change the value of ? to the number

            gridPanel.setVisible(false); // Hide the Panel with the enter button and text field

            contButton.setVisible(true); // Set and Show the continue button (for playing again)
            scoringSystem.scoreAttempt();
            scoreFiles.write("current_score.txt", scoreFiles.intScore("current_score.txt") + scoringSystem.getScore()); // Set
                                                                                                                        // how
                                                                                                                        // many
                                                                                                                        // games
                                                                                                                        // played
                                                                                                                        // continuously
            scoreFiles.write("num_game.txt", scoreFiles.intScore("num_game.txt") + 1);

        } else {
            try {
                // Convert the user input number (string) to int
                int textToInt = Integer.parseInt(input.getText());
                // Comparing the user input to the random number
                if (textToInt > 5 || textToInt < 1) {
                    // If the user input higher than 50 and lower than 1, executed this block of
                    // code

                    status.setText("Out of Range");
                    status.setFont(new Font("MV Boli", Font.BOLD, 30));
                    status.setForeground(new Color(0, 0, 0));
                    status.setPreferredSize(new Dimension(200, 50));

                } else if (textToInt > randnum) {
                    // If the user input higher than random number, executed this block of code
                    status.setText("Too High!! Try Again");
                    status.setFont(new Font("MV Boli", Font.BOLD, 30));
                    status.setForeground(new Color(0, 0, 0));
                    status.setPreferredSize(new Dimension(200, 50));
                } else if (textToInt < randnum) {
                    // If the user input lower than random number, executed this block of code
                    status.setText("Too Low!! Try Again");
                    status.setFont(new Font("MV Boli", Font.BOLD, 30));
                    status.setForeground(new Color(0, 0, 0));
                    status.setPreferredSize(new Dimension(200, 50));
                }
            } catch (NumberFormatException ex) {
                status.setText("Enter a Number");
                status.setFont(new Font("MV Boli", Font.BOLD, 30));
                status.setForeground(new Color(0, 0, 0));
                status.setPreferredSize(new Dimension(200, 50));
            }
        }
        input.setText("");
        scoringSystem.incrementAttempt();
    }

}

class ScoringSystem {
    int score, attempts;

    public ScoringSystem() {
        this.attempts = 1;
    }

    public int getScore() {
        return score;
    }

    public int getAttempts() {
        return attempts;
    }

    public void scoreAttempt() {
        if (this.attempts == 1)
            this.score += 5;
        else if (this.attempts == 2)
            this.score += 2;
        else
            this.score++;
    }

    public void incrementAttempt() {
        this.attempts++;
    }
}

class ScoreFiles {

    public String showScore(String filename) {
    String score = "0";
    try (Scanner scan = new Scanner(new File(filename))) {
        score = scan.nextLine();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return score;
}

    public String showGames(String filename) {
    String attempt = "0";
    try (Scanner scan = new Scanner(new File(filename))) {
        scan.nextLine();
        attempt = scan.nextLine();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return attempt;
}

    // Convert the showScore method to int
    public int intScore(String filename) {
        return Integer.parseInt(showScore(filename));
    }

    // Convert the showsGames method to int
    public int intGames(String filename) {
        return Integer.parseInt(showGames(filename));
    }

    // Used in current_score.txt and num_game.txt
    public void write(String filename, int score) {
        try {
            FileWriter score_writer = new FileWriter(filename);
            score_writer.write(String.valueOf(score));
            score_writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Overwrites the text file
    // Used in high_score.txt
    public void writeScoreAttempts(String filename, int score, int attemtps) {
        try {
            FileWriter score_writer = new FileWriter(filename);
            score_writer.write(String.valueOf(score));
            score_writer.write("\n");
            score_writer.write(String.valueOf(attemtps));
            score_writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Compare the score if it is a high score
    public void compareScore(String high_score, String current_score, String current_played_games) {
        if (intScore(high_score) < intScore(current_score)) {
            writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
        } else if (intScore(high_score) == intScore(current_score)) {
            if (intGames(high_score) > intScore(current_played_games)) {
                writeScoreAttempts(high_score, intScore(current_score), intScore(current_played_games));
            }
        }
    }
}

class RandomNumber {
    final int min = 1, max = 5;

    // Generate a Random Number between 1 and 5
    public int generateNumber() {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}

class Menu extends JPanel {
    final Game game;
    ScoreFiles scoreFiles = new ScoreFiles();

    public Menu(Game game) {
        this.game = game;

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);

        createGUI();
    }

    public void createGUI() {

        JLabel logoImage, scoreLabel, scoreText, playButton;

        logoImage = new JLabel("Guess the Number", JLabel.CENTER);
        logoImage.setFont(new Font("MV Boli", Font.BOLD, 33));
        logoImage.setForeground(new Color(0, 0, 0));
        logoImage.setBorder(new EmptyBorder(100, 0, 0, 0));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);
        add(logoImage);

        scoreLabel = new JLabel("High Score", JLabel.CENTER);
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 45));
        scoreLabel.setForeground(new Color(0, 0, 0));
        scoreLabel.setBorder(new EmptyBorder(85, 0, 0, 0));
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreLabel);

        scoreText = new JLabel(scoreFiles.showScore("high_score.txt") + " points for "
                + scoreFiles.showGames("high_score.txt") + " games");
        scoreText.setFont(new Font("MV Boli", Font.BOLD, 18));
        scoreText.setForeground(new Color(0, 0, 0));
        scoreText.setBorder(new EmptyBorder(10, 0, 0, 0));
        scoreText.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreText);

        playButton = new JLabel(new ImageIcon("start.png"));
        playButton.setBorder(new EmptyBorder(60, 0, 0, 0));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        linkPlay(playButton);
        add(playButton);

    }

    public void linkPlay(JLabel jLabel) {
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Set the data inside to zero
                scoreFiles.write("current_score.txt", 0);
                scoreFiles.write("num_game.txt", 0);
                game.showView(new Play(game));
            }
        });
    }

    // For changing the background of JPanel
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("background.png").getImage(), 0, 0, null);
    }
}

class Game extends JFrame {

    JPanel viewPanel;

    public Game() {

        // Initialize the viewPanel the moment Game Class is called
        viewPanel = new JPanel(new BorderLayout());

        // Setting up the Game
        this.setTitle("Guess the Number"); // Title
        this.setPreferredSize(new Dimension(350, 660)); // Dimension
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the Application when clicking x
        this.add(viewPanel, BorderLayout.CENTER); // Add the JPanel in this frame
        showView(new Menu(this));
        this.setVisible(true); // To view the window
        this.pack(); // To size the components(button, img) optimally
        this.setResizable(false); // To avoid resizing the window
        this.setLocationRelativeTo(null); // To set the window in the center
    }

    public void showView(JPanel jPanel) {
        viewPanel.removeAll();
        viewPanel.add(jPanel, BorderLayout.CENTER);
        viewPanel.revalidate();
        viewPanel.repaint();
    }
}

class Main {
    public static void main(String[] args) {
        new Game();
    }
}