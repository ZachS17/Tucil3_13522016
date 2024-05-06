import javax.swing.*;

import java.util.*;
import java.util.List;

import Solver.*;

import Utilities.Dictionary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;

public class GUI {
    public static void main(String[] args) {
        Dictionary.readDictionary();
        JFrame frame = new JFrame("Word Ladder");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel for title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Word Ladder");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel startLabel = new JLabel("Start Word:");
        inputPanel.add(startLabel);
        JTextField startField = new JTextField(10);
        inputPanel.add(startField);
        JLabel targetLabel = new JLabel("Target Word:");
        inputPanel.add(targetLabel);
        JTextField targetField = new JTextField(10);
        inputPanel.add(targetField);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Result panel with scrollable JTextArea
        JPanel resultPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ucsButton = new JButton("UCS");
        buttonPanel.add(ucsButton);
        JButton gbfsButton = new JButton("GBFS");
        buttonPanel.add(gbfsButton);
        JButton aStarButton = new JButton("A*");
        buttonPanel.add(aStarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the search buttons
        ActionListener searchActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm.resetStaticAttributes();
                String startWord = startField.getText();
                String targetWord = targetField.getText();

                JButton clickedButton = (JButton) e.getSource();
                String algorithm = clickedButton.getText(); // Get the text of the button

                List<String> solution = new ArrayList<>();
                Duration duration = Duration.ofSeconds(0);
                Runtime runtime = Runtime.getRuntime();
                long initialMemory = runtime.totalMemory() - runtime.freeMemory();
                Algorithm tempAlgorithm = new UCS("bebas", "asal");

                // Call the appropriate algorithm based on the selected button
                if (algorithm.equals("UCS")) {
                    // Call UCS algorithm
                    tempAlgorithm = new UCS(startWord.toLowerCase(),
                            targetWord.toLowerCase());
                    Instant startTime = Instant.now();
                    solution = tempAlgorithm.solve();
                    Instant endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                } else if (algorithm.equals("GBFS")) {
                    // Call GBFS algorithm
                    tempAlgorithm = new GBFS(startWord.toLowerCase(),
                            targetWord.toLowerCase());
                    Instant startTime = Instant.now();
                    solution = tempAlgorithm.solve();
                    Instant endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                } else if (algorithm.equals("A*")) {
                    // Call A* algorithm
                    UCS tempUCS = new UCS(startWord.toLowerCase(), targetWord.toLowerCase());
                    GBFS tempGBFS = new GBFS(startWord.toLowerCase(), targetWord.toLowerCase());
                    tempAlgorithm = new AStar(tempUCS, tempGBFS);
                    Instant startTime = Instant.now();
                    solution = tempAlgorithm.solve();
                    Instant endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                }

                long finalMemory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println(solution);

                resultPanel.removeAll();
                // Sample result
                for (String word : solution) {
                    JPanel wordPanel = new JPanel();
                    wordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    for (char c : word.toCharArray()) {
                        JLabel letterLabel = new JLabel(String.valueOf(c));
                        letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        letterLabel.setPreferredSize(new Dimension(20, 20));
                        wordPanel.add(letterLabel);
                    }
                    resultPanel.add(wordPanel);
                }

                // Add an additional panel with manual string
                JPanel additionalPanel = new JPanel();
                JLabel additionalLabel = new JLabel("Durasi: " + duration.toMillis() + " ms " + "Memori: "
                        + (finalMemory - initialMemory) + " bytes " + "Jumlah Simpul Solusi: " + solution.size()
                        + " Jumlah Simpul Kunjungan: " + tempAlgorithm.getNumVisited());
                additionalPanel.add(additionalLabel);
                resultPanel.add(additionalPanel);

                // Update UI
                resultPanel.revalidate();
                resultPanel.repaint();
            }
        };

        ucsButton.addActionListener(searchActionListener);
        gbfsButton.addActionListener(searchActionListener);
        aStarButton.addActionListener(searchActionListener);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
