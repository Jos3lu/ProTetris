package com.example.protetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends View implements View.OnClickListener {

    private MainBoard mainBoard;
    private ProTetris proTetris;
    private ImageView rotateButton;
    private ImageView leftButton;
    private ImageView rightButton;
    private TextView actualPoints;
    private Score score;
    private Timer timer;
    private List<Piece> pieces;
    private UpcomingPiece upcomingPiece;
    private int timerPeriod;
    private boolean stop;

    public MainGame(Context context, UpcomingPiece upcomingPiece, MainBoard mainBoard) {
        super(context);
        this.timer = new Timer();
        this.proTetris = (ProTetris) context;
        this.upcomingPiece = upcomingPiece;
        this.timerPeriod = 1000; //La pieza baja cada segundo
        score = new Score();
        this.mainBoard = mainBoard;
        this.stop = proTetris.getStop();
        this.pieces = mainBoard.getPieces();
        this.actualPoints = proTetris.getPoints();
        this.actualPoints.append("0");

        this.rotateButton = proTetris.getRotateButton();
        this.leftButton = proTetris.getLeftButton();
        this.rightButton = proTetris.getRightButton();

        this.rotateButton.setOnClickListener(this);
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
        startGame();
    }

    public void startGame() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                proTetris.runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if (!gameOver() && !proTetris.getStop()) {
                            if (!mainBoard.moveOneDown(mainBoard.getActualPiece())) {
                                int rowsRemoved = mainBoard.removeCompleteLines();

                                Piece actualPiece = mainBoard.getActualPiece();
                                pieces.remove(actualPiece);

                                pieces.add(new Piece((int) (Math.random() * 7) + 1));

                                upcomingPiece.invalidate();

                                if (rowsRemoved > 0) {
                                    score.setActualScore(score.getActualScore() + rowsRemoved*30);
                                    int points = score.getActualScore();

                                    actualPoints.setText(points);
                                }
                            }
                            invalidate();
                        }
                    }
                });
            }
        }, 0, timerPeriod);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        for (int row = 0; row < mainBoard.getBOARD_NUM_ROWS(); row++) {
            for (int col = 0; col < mainBoard.getBOARD_NUM_COLS(); col++) {
                int blocks = mainBoard.drawBlocks(row, col);
                paint.setColor(blocks);
                canvas.drawRect(col*48, row*55, col*48 + 48, row*55 + 55, paint);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!proTetris.getStop()) {
            switch (view.getId()) {
                case R.id.rotateButton:
                    mainBoard.rotate(mainBoard.getActualPiece());
                    invalidate();
                    break;
                case R.id.leftButton:
                    mainBoard.moveToLeft(mainBoard.getActualPiece());
                    invalidate();
                    break;
                case R.id.rightButton:
                    mainBoard.moveToRight(mainBoard.getActualPiece());
                    invalidate();
                    break;
            }
        }
    }

    public boolean gameOver() {
        if (this.mainBoard.checkGameOver(this.mainBoard.getActualPiece())) {
            this.timer.cancel();
            this.mainBoard.resetBoard(this.mainBoard.getBoard());
            proTetris.setStop(true);

            //Mostrar game over
            Intent intent = new Intent(this.getContext(), GameOver.class);
            getContext().startActivity(intent);

            return true;
        }
        return false;
    }
}