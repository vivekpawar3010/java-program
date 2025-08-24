package studywork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGame extends JFrame {
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY_SQUARE = '-';
    private JButton[][] squares;
    private char[][] board;
    private JTextField moveInput;

    public ChessGame() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        initializeBoard();
        createBoardGUI(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        moveInput = new JTextField(10); // Input field for moves
        JButton submitButton = new JButton("Submit Move");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String move = moveInput.getText();
                if (!move.isEmpty()) {
                    // Process move here
                    processMove(move);
                    // Clear input field after processing move
                    moveInput.setText("");
                }
            }
        });
        inputPanel.add(moveInput);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void initializeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        // Place pieces on the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row == 0 || row == 7) {
                    switch (col) {
                        case 0:
                        case 7:
                            board[row][col] = 'R'; // Rook
                            break;
                        case 1:
                        case 6:
                            board[row][col] = 'N'; // Knight
                            break;
                        case 2:
                        case 5:
                            board[row][col] = 'B'; // Bishop
                            break;
                        case 3:
                            board[row][col] = 'Q'; // Queen
                            break;
                        case 4:
                            board[row][col] = 'K'; // King
                            break;
                    }
                } else if (row == 1 || row == 6) {
                    board[row][col] = 'P'; // Pawn
                } else {
                    board[row][col] = EMPTY_SQUARE; // Empty square
                }
            }
        }
    }

    private void createBoardGUI(JPanel boardPanel) {
        squares = new JButton[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new JButton(String.valueOf(board[row][col]));
                squares[row][col].setPreferredSize(new Dimension(80, 80));
                squares[row][col].setEnabled(false); // Disable buttons, since they're just for display
                boardPanel.add(squares[row][col]);
            }
        }
    }

    private void processMove(String move) {
        // Extract source and destination squares from the move string
        String[] moveParts = move.split(" ");
        if (moveParts.length != 2) {
            JOptionPane.showMessageDialog(this, "Invalid move format! Please enter two coordinates separated by space.");
            return;
        }
        String fromSquare = moveParts[0];
        String toSquare = moveParts[1];

        // Update the GUI to reflect the move
        updateBoardGUI(fromSquare, toSquare);
    }

    private void updateBoardGUI(String fromSquare, String toSquare) {
        // Convert algebraic notation to board indices
        int fromRow = BOARD_SIZE - Character.getNumericValue(fromSquare.charAt(1));
        int fromCol = fromSquare.charAt(0) - 'a';
        int toRow = BOARD_SIZE - Character.getNumericValue(toSquare.charAt(1));
        int toCol = toSquare.charAt(0) - 'a';

        // Update button text
        squares[fromRow][fromCol].setText(String.valueOf(EMPTY_SQUARE));
        squares[toRow][toCol].setText(String.valueOf(board[fromRow][fromCol]));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChessGame();
            }
        });
    }
}
