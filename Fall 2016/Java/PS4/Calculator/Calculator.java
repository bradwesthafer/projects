package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brad on 10/28/2016.
 */
public class Calculator extends JFrame implements ActionListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int BUTTON_WIDTH = 202;
    public static final int BUTTON_HEIGHT = 140;
    public static final int TEXT_HEIGHT = 40;
    private boolean answerDisplayed = false;

    private double operand1;
    private double operand2;
    private double result;
    private int currentOperand;
    private char operation;
    private char lastOp;
    private JTextField text;

    public static void main(String[] args) {
        Calculator window = new Calculator("Calculator");
        window.setVisible(true);
    }

    public Calculator(String title) {
        super(title);

        operand1 = 0.0;
        operand2 = 0.0;
        operation = '=';
        lastOp = '=';
        currentOperand = 1;

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        text = new JTextField();
        text.setVisible(true);
        text.setEditable(false);
        text.setBackground(Color.white);
        text.setPreferredSize(new Dimension(WIDTH, TEXT_HEIGHT));
        text.setText("0");
        add(text, BorderLayout.NORTH);

        JButton backspaceButton = new JButton("Backspace");
        backspaceButton.addActionListener(this);
        backspaceButton.setPreferredSize(new Dimension((int)(BUTTON_WIDTH * 1.5), BUTTON_HEIGHT));
        add(backspaceButton, BorderLayout.WEST);

        JButton cButton = new JButton("C");
        cButton.addActionListener(this);
        JButton ceButton = new JButton("CE");
        ceButton.addActionListener(this);
        JPanel clearButtons = new JPanel();
        clearButtons.setLayout(new GridLayout(1, 2));
        clearButtons.add(ceButton);
        clearButtons.add(cButton);
        clearButtons.setVisible(true);
        clearButtons.setPreferredSize(new Dimension(BUTTON_WIDTH * 2, BUTTON_HEIGHT));
        add(clearButtons, BorderLayout.EAST);

        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(this);
        JButton oneButton = new JButton("1");
        oneButton.addActionListener(this);
        JButton twoButton = new JButton("2");
        twoButton.addActionListener(this);
        JButton threeButton = new JButton("3");
        threeButton.addActionListener(this);
        JButton fourButton = new JButton("4");
        fourButton.addActionListener(this);
        JButton fiveButton = new JButton("5");
        fiveButton.addActionListener(this);
        JButton sixButton = new JButton("6");
        sixButton.addActionListener(this);
        JButton sevenButton = new JButton("7");
        sevenButton.addActionListener(this);
        JButton eightButton = new JButton("8");
        eightButton.addActionListener(this);
        JButton nineButton = new JButton("9");
        nineButton.addActionListener(this);
        JButton plusminusButton = new JButton("+/-");
        plusminusButton.addActionListener(this);
        JButton decimalButton = new JButton(".");
        decimalButton.addActionListener(this);
        JButton plusButton = new JButton("+");
        plusButton.addActionListener(this);
        JButton minusButton = new JButton("-");
        minusButton.addActionListener(this);
        JButton multiplyButton = new JButton("*");
        multiplyButton.addActionListener(this);
        JButton divideButton = new JButton("/");
        divideButton.addActionListener(this);
        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        JButton percentButton = new JButton("%");
        percentButton.addActionListener(this);
        JButton reciprocalButton = new JButton("1/x");
        reciprocalButton.addActionListener(this);
        JButton sqrtButton = new JButton("sqrt");
        sqrtButton.addActionListener(this);
        JPanel primaryButtons = new JPanel();
        primaryButtons.setLayout(new GridLayout(4, 5));
        primaryButtons.add(sevenButton);
        primaryButtons.add(eightButton);
        primaryButtons.add(nineButton);
        primaryButtons.add(divideButton);
        primaryButtons.add(sqrtButton);
        primaryButtons.add(fourButton);
        primaryButtons.add(fiveButton);
        primaryButtons.add(sixButton);
        primaryButtons.add(multiplyButton);
        primaryButtons.add(reciprocalButton);
        primaryButtons.add(oneButton);
        primaryButtons.add(twoButton);
        primaryButtons.add(threeButton);
        primaryButtons.add(minusButton);
        primaryButtons.add(percentButton);
        primaryButtons.add(zeroButton);
        primaryButtons.add(plusminusButton);
        primaryButtons.add(decimalButton);
        primaryButtons.add(plusButton);
        primaryButtons.add(equalsButton);
        primaryButtons.setVisible(true);
        primaryButtons.setPreferredSize(new Dimension(WIDTH, BUTTON_HEIGHT * 4));
        add(primaryButtons, BorderLayout.SOUTH);

        JMenu fileMenu = new JMenu("File");
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(this);
        fileMenu.add(close);
        JMenu helpMenu = new JMenu("Help");
        JMenuBar bar = new JMenuBar();
        bar.add(fileMenu);
        bar.add(helpMenu);
        setJMenuBar(bar);
    }

    // All operations mimic behavior of Windows Calculator equivalents

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand) {
            case "Close":
                System.exit(0);
                break;
            case "Backspace":
                backspace();
                break;
            case "0":
                addText("0");
                break;
            case "1":
                addText("1");
                break;
            case "2":
                addText("2");
                break;
            case "3":
                addText("3");
                break;
            case "4":
                addText("4");
                break;
            case "5":
                addText("5");
                break;
            case "6":
                addText("6");
                break;
            case "7":
                addText("7");
                break;
            case "8":
                addText("8");
                break;
            case "9":
                addText("9");
                break;
            case ".":
                addText(".");
                break;
            case "CE":
                ce();
                break;
            case "C":
                c();
                break;
            case "+":
                performOperation('+');
                break;
            case "-":
                performOperation('-');
                break;
            case "*":
                performOperation('*');
                break;
            case "/":
                performOperation('/');
                break;
            case "=":
                performOperation('=');
                break;
            case "%":
                percent();
                break;
            case "sqrt":
                text.setText(String.valueOf(Math.sqrt(Double.valueOf(text.getText()))));
                break;
            case "+/-":
                text.setText(String.valueOf(Double.valueOf(text.getText()) * -1));
                break;
            case "1/x":
                text.setText(String.valueOf(1/Double.valueOf(text.getText())));
        }
    }

    public void backspace() {
        String currentText = text.getText();
        if (currentText == "0") {
            return;
        }
        if (currentText.length() == 1) {
            text.setText("0");
        }
        else {
            text.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void addText(String add) {
        String currentText = text.getText();
        if(add.equals(".") && answerDisplayed) {
            text.setText("0" + add);
            answerDisplayed = false;
        }
        if(currentText.equals("0") || answerDisplayed || currentText.equals(String.valueOf(operation))) {
            text.setText(add);
            answerDisplayed = false;
            return;
        }
        if(add.equals(".") && currentText.contains(".")) {
            return;
        }
        else {
            text.setText(currentText + add);
        }
    }

    public void ce() {
        if(answerDisplayed) {
            operand1 = 0;
        }
        text.setText("0");
    }

    public void c() {
        text.setText("0");
        answerDisplayed = false;
        operand1 = 0;
        operand2 = 0;
        operation = '=';
        currentOperand = 1;
    }

    private boolean isOperator(String input) {
        return (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("="));
    }

    private void performOperation(char op) {
        if(op == '=') {
            if(currentOperand == 1 && lastOp == '=') {
                operand1 = Double.valueOf(text.getText());
                result = doOp();
                text.setText(Double.toString(result));
                answerDisplayed = true;
            }
            else if(currentOperand == 1) {
                operand1 = result;
                operand2 = Double.valueOf(text.getText());
                result = doOp();
                operation = op;
                text.setText(String.valueOf(result));
            }
            else if(isOperator(text.getText())) {
                operand2 = operand1;
                result = doOp();
                text.setText(Double.toString(result));
                answerDisplayed = true;
            }
            else {
                operand2 = Double.valueOf(text.getText());
                result = doOp();
                text.setText(Double.toString(result));
                answerDisplayed = true;
                currentOperand--;
            }
        }
        else if(currentOperand == 1 && lastOp == '=') {
            operand1 = Double.valueOf(text.getText());
            operation = op;
            text.setText(String.valueOf(operation));
            currentOperand++;
        }
        else if(currentOperand == 1) {
            operand1 = result;
            operand2 = Double.valueOf(text.getText());
            result = doOp();
            operation = op;
            text.setText(String.valueOf(result));
        }
        else if(isOperator(text.getText())) {
            operation = op;
            text.setText(String.valueOf(operation));
        }
        else {
            operand2 = Double.valueOf(text.getText());
            result = doOp();
            text.setText(Double.toString(result));
            operation = op;
            answerDisplayed = true;
            currentOperand--;
        }
        lastOp = op;
    }

    private double doOp() {
        switch(operation) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                return operand1;
        }
    }

    private void percent() {
        if(isOperator(text.getText())) {
            operand2 = operand1 * (operand1/100.0);
        }
        else {
            operand2 = operand1 * Double.valueOf(text.getText())/100;
        }
        result = doOp();
        text.setText(String.valueOf(result));
        currentOperand = 1;
        answerDisplayed = true;
    }
}
