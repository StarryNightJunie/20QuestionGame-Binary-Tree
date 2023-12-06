// Name: Huy Truong Ngo
// CS 145
// Lab 6: 20 question
import java.util.*;
import java.io.*;
public class QuestionTree {
    private Scanner input;
    private QuestionNode root;
    private UserInterface ui;
    private int gameCount;
    private int gameWins;

    public QuestionTree(UserInterface ui) {
        if (ui == null) {
            throw new IllegalArgumentException();
        }
        root = new QuestionNode("A:Computer");
        input = new Scanner(System.in);
        this.ui = ui;
        gameCount = 0;
        gameWins = 0;
    }

    // load a new question tree by the user
    public void load(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        this.root = this.loadHelp(input);
    }

    private QuestionNode loadHelp(Scanner input) {
        String data = input.nextLine();
        QuestionNode current = new QuestionNode(data);
        // if node is a question
        if (data.startsWith("Q:")) {
            current.yesNode = loadHelp(input);
            current.noNode = loadHelp(input);
        }
        return current;
    }

    // Save tree to output file
    public void save(PrintStream output) {
       if (output == null) {
           throw new IllegalArgumentException();
       }
       saveHelp(output, root);
    }

    private void saveHelp(PrintStream output, QuestionNode root) {
        // if node is a leaf
        if (root.yesNode == null && root.noNode == null) {
            output.println(root.data);
        } else {
            output.println(root.data);
            this.saveHelp(output, root.yesNode);
            this.saveHelp(output, root.noNode);
        }
    }

    // Number of games
    public int totalGames() {
        return gameCount;
    }
    // Number of wins
    public int gamesWon() {
        return gameWins;
    }
    public void play() {
        gameCount++;
        this.root = playGame(this.root);
    }

    private QuestionNode playGame(QuestionNode current) {
        if (current.yesNode == null && current.noNode == null) {
            ui.println("Would your object happen to be " + current.data.substring(2) + "? ");
            if (ui.nextBoolean()) {
                ui.println("I win!");
                gameWins++;
            } else {
                ui.print("I lose. What is your object? ");
                QuestionNode answer = new QuestionNode("A:" + ui.nextLine());
                ui.print("Type a yes/no question to distinguish your item from ");
                ui.println(current.data.substring(2) + ": ");
                String question = ui.nextLine();
                ui.println("And what is the answer for your object? ");
                if (ui.nextBoolean()) {
                    current = new QuestionNode("Q:" + question, answer, current);
                } else {
                    current = new QuestionNode("Q:" + question, current, answer);
                }
            }
        // ask the question and go down the tree
        } else {
            ui.println(current.data.substring(2));
            if (ui.nextBoolean()) {
                current.yesNode = playGame(current.yesNode);
            } else {
                current.noNode = playGame(current.noNode);
            }
        }
        return current;
    }
}