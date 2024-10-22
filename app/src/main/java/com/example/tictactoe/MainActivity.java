package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerXTurn = true;
    private boolean isPlayingWithBot = false;
    private Button[][] buttons = new Button[3][3];
    private TextView statusText;
    private Handler handler = new Handler();
    private boolean gameEnded = false; // Флаг для отслеживания состояния игры

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gameEnded || !((Button) v).getText().toString().equals("")) return; // Проверка на окончание игры

                        if (isPlayerXTurn) {
                            ((Button) v).setText("X");
                            statusText.setText("Ход Игрока O");
                        } else {
                            ((Button) v).setText("O");
                            statusText.setText("Ход Игрока X");
                        }
                        isPlayerXTurn = !isPlayerXTurn;

                        checkWinner();
                        if (isPlayingWithBot && !isPlayerXTurn) {
                            // Добавляем задержку перед ходом бота
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    botMove();
                                }
                            }, 2000); // Задержка 2 секунды
                        }
                    }
                });
            }
        }

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard(); // Сброс игры
            }
        });

        Button switchModeButton = findViewById(R.id.switchModeButton);
        switchModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayingWithBot = !isPlayingWithBot;
                resetBoard();
                if (isPlayingWithBot) {
                    statusText.setText("Игрок X против Бота");
                    switchModeButton.setText("Играть с другом");
                } else {
                    statusText.setText("Ход Игрока X");
                    switchModeButton.setText("Играть с Ботом");
                }
            }
        });
    }

    private void botMove() {
        if (tryToWin()) return;
        if (tryToBlockPlayer()) return;
        if (buttons[1][1].getText().toString().equals("")) {
            buttons[1][1].setText("O");
            isPlayerXTurn = true;
            statusText.setText("Ход Игрока X");
            checkWinner();
            return;
        }

        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!buttons[row][col].getText().toString().equals(""));
        buttons[row][col].setText("O");
        isPlayerXTurn = true;
        statusText.setText("Ход Игрока X");
        checkWinner();
    }

    private boolean tryToWin() {
        return tryToCompleteLine("O");
    }

    private boolean tryToBlockPlayer() {
        return tryToCompleteLine("X");
    }

    private boolean tryToCompleteLine(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(symbol) &&
                    buttons[i][1].getText().toString().equals(symbol) &&
                    buttons[i][2].getText().toString().equals("")) {
                buttons[i][2].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
            if (buttons[i][0].getText().toString().equals(symbol) &&
                    buttons[i][2].getText().toString().equals(symbol) &&
                    buttons[i][1].getText().toString().equals("")) {
                buttons[i][1].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
            if (buttons[i][1].getText().toString().equals(symbol) &&
                    buttons[i][2].getText().toString().equals(symbol) &&
                    buttons[i][0].getText().toString().equals("")) {
                buttons[i][0].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().toString().equals(symbol) &&
                    buttons[1][i].getText().toString().equals(symbol) &&
                    buttons[2][i].getText().toString().equals("")) {
                buttons[2][i].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
            if (buttons[0][i].getText().toString().equals(symbol) &&
                    buttons[2][i].getText().toString().equals(symbol) &&
                    buttons[1][i].getText().toString().equals("")) {
                buttons[1][i].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
            if (buttons[1][i].getText().toString().equals(symbol) &&
                    buttons[2][i].getText().toString().equals(symbol) &&
                    buttons[0][i].getText().toString().equals("")) {
                buttons[0][i].setText("O");
                isPlayerXTurn = true;
                statusText.setText("Ход Игрока X");
                checkWinner();
                return true;
            }
        }

        if (buttons[0][0].getText().toString().equals(symbol) &&
                buttons[1][1].getText().toString().equals(symbol) &&
                buttons[2][2].getText().toString().equals("")) {
            buttons[2][2].setText("O");
            isPlayerXTurn = true;
            statusText.setText("Ход Игрока X");
            checkWinner();
            return true;
        }
        if (buttons[0][0].getText().toString().equals(symbol) &&
                buttons[2][2].getText().toString().equals(symbol) &&
                buttons[1][1].getText().toString().equals("")) {
            buttons[1][1].setText("O");
            isPlayerXTurn = true;
            statusText.setText("Ход Игрока X");
            checkWinner();
            return true;
        }
        if (buttons[1][1].getText().toString().equals(symbol) &&
                buttons[2][2].getText().toString().equals(symbol) &&
                buttons[0][0].getText().toString().equals("")) {
            buttons[0][0].setText("O");
            isPlayerXTurn = true;
            statusText.setText("Ход Игрока X");
            checkWinner();
            return true;
        }
        return false;
    }

    private void checkWinner() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Проверка победы по строкам, столбцам и диагоналям
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2]) && !field[i][0].equals("")) {
                announceWinner(field[i][0], i, -1); // -1 для столбцов
                return;
            }
            if (field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i]) && !field[0][i].equals("")) {
                announceWinner(field[0][i], -1, i); // -1 для строк
                return;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2]) && !field[0][0].equals("")) {
            announceWinner(field[0][0], -1, -1); // Главная диагональ
            return;
        }

        if (field[0][2].equals(field[1][1]) && field[1][1].equals(field[2][0]) && !field[0][2].equals("")) {
            announceWinner(field[0][2], -1, -1); // Побочная диагональ
            return;
        }

        // Проверка ничьей
        boolean allFilled = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals("")) {
                    allFilled = false;
                    break;
                }
            }
        }

        if (allFilled) {
            announceDraw();
        }
    }

    private void announceWinner(String winner, int winningRow, int winningCol) {
        statusText.setText("Победитель: " + winner);

        // Если победа по строке
        if (winningRow != -1) {
            for (int i = 0; i < 3; i++) {
                buttons[winningRow][i].setBackgroundColor(Color.GREEN);
            }
        }
        // Если победа по столбцу
        else if (winningCol != -1) {
            for (int i = 0; i < 3; i++) {
                buttons[i][winningCol].setBackgroundColor(Color.GREEN);
            }
        }
        // Если победа по главной диагонали
        else if (buttons[0][0].getText().toString().equals(winner) &&
                buttons[1][1].getText().toString().equals(winner) &&
                buttons[2][2].getText().toString().equals(winner)) {
            buttons[0][0].setBackgroundColor(Color.GREEN);
            buttons[1][1].setBackgroundColor(Color.GREEN);
            buttons[2][2].setBackgroundColor(Color.GREEN);
        }
        // Если победа по побочной диагонали
        else if (buttons[0][2].getText().toString().equals(winner) &&
                buttons[1][1].getText().toString().equals(winner) &&
                buttons[2][0].getText().toString().equals(winner)) {
            buttons[0][2].setBackgroundColor(Color.GREEN);
            buttons[1][1].setBackgroundColor(Color.GREEN);
            buttons[2][0].setBackgroundColor(Color.GREEN);
        }

        disableButtons();
        gameEnded = true; // Устанавливаем флаг окончания игры
    }


    private void announceDraw() {
        statusText.setText("Ничья!");
        disableButtons(); // Закрываем кнопки
        gameEnded = true; // Устанавливаем флаг окончания игры
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(""); // Сброс текста
                buttons[i][j].setEnabled(true); // Активировать кнопки
                buttons[i][j].setBackgroundColor(Color.WHITE); // Сброс цвета
            }
        }
        isPlayerXTurn = true; // Устанавливаем, что ходит игрок X
        statusText.setText("Ход Игрока X"); // Устанавливаем начальное состояние
        gameEnded = false; // Сбрасываем флаг окончания игры
    }
}
