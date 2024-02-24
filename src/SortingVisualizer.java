import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class SortingVisualizer extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BAR_WIDTH = 20;

    private int[] array;
    private JPanel controlPanel;
    private JPanel drawPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JComboBox<String> algorithmComboBox;
    private boolean sortingPaused;
    private boolean sortingCompleted;

    private SortingAlgorithm sortingAlgorithm;

    public SortingVisualizer() {
        setTitle("Sorting Algorithm Visualizer");
        setSize(WIDTH, HEIGHT);
//        setResizable(false); // Disable resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        controlPanel = new JPanel();
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBars(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(WIDTH, HEIGHT - 100);
            }
        };

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        algorithmComboBox = new JComboBox<>(new String[]{"Bubble Sort", "Insertion Sort", "Selection Sort","Quick Sort", "Merge Sort"}); // Add other sorting algorithms here
        sortingPaused = false;
        sortingCompleted = false;

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(algorithmComboBox);

        add(controlPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            if (!sortingCompleted) {
                new Thread(this::startSorting).start();
            }
        });

        pauseButton.addActionListener(e -> {
            sortingPaused = !sortingPaused;
            if (sortingPaused) {
                pauseButton.setText("Resume");
            } else {
                pauseButton.setText("Pause");
            }
        });

        resetButton.addActionListener(e -> {
            resetSorting();
        });

        algorithmComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            switch (selectedAlgorithm) {
                case "Bubble Sort":
                    sortingAlgorithm = new BubbleSort();
                    break;
                case "Insertion Sort":
                    sortingAlgorithm = new InsertionSort();
                    break;
                case "Selection Sort":
                    sortingAlgorithm = new SelectionSort();
                    break;
                // Add other sorting algorithms here
            }
        });

        generateRandomArray();
        sortingAlgorithm = new BubbleSort(); // Initialize with default algorithm
    }

    private void drawBars(Graphics g) {
        Color barColor = Color.BLUE;
        Color textColor = Color.BLACK;

        Font font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();

        for (int i = 0; i < array.length; i++) {
            int x = i * (BAR_WIDTH + 2);
            int y = drawPanel.getHeight() - array[i];

            // Set color for the bars
            g.setColor(barColor);
            g.fillRect(x, y, BAR_WIDTH, array[i]);

            // Draw text label with correct color
            String text = Integer.toString(array[i]);
            int textWidth = fontMetrics.stringWidth(text);
            int textX = x + (BAR_WIDTH - textWidth) / 2;
            int textY = y - 5; // Adjust position above the bar
            g.setColor(textColor);
            g.drawString(text, textX, textY);
        }
    }


    private void generateRandomArray() {
        int arraySize = WIDTH / (BAR_WIDTH + 2);
        array = new int[arraySize];
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(HEIGHT); // Use HEIGHT instead of drawPanel.getHeight() to prevent  "bound must be positive" exception
        }
    }
    private void startSorting() {
        sortingCompleted = false;
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resetButton.setEnabled(false);

        sortingAlgorithm.sort(array, this::updateArray); // Call sort method of selected algorithm

        sortingCompleted = true;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);
    }

    private void resetSorting() {
        sortingCompleted = false;
        sortingPaused = false;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(false);

        generateRandomArray();
        drawPanel.repaint();
    }

    private void updateArray(int[] arr) {
        array = Arrays.copyOf(arr, arr.length);
        drawPanel.repaint();
        try {
            Thread.sleep(50); // Add delay for visualization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (sortingPaused) {
            while (sortingPaused) {
                try {
                    Thread.sleep(100); // Pause handling
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingVisualizer visualizer = new SortingVisualizer();
            visualizer.setVisible(true);
        });
    }
}
