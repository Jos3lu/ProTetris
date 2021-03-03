package com.example.protetris;


import android.graphics.Color;

import java.util.LinkedList;
import java.util.List;

public class MainBoard {
    /*
    Static makes the variables belong to the class not the object,
     all the objects are gonna have the same value

    Test x = new Test();
    Test y = new Test();
    x.instanceVariable = 10;
    y.instanceVariable = 20;
    System.out.println(x.instanceVariable);

    This print 20.
     */
    // Constantes y variables
    private static final int EMPTY = 0;
    private static final int S_PIECE = 1;
    private static final int I_PIECE = 2;
    private static final int J_PIECE = 3;
    private static final int O_PIECE = 4;
    private static final int L_PIECE = 5;
    private static final int Z_PIECE = 6;
    private static final int T_PIECE = 7;
    private static final int SHADOW = 8;

    private final int BOARD_NUM_ROWS = 20;
    private final int BOARD_NUM_COLS = 10;

    private final int NUM_TYPE = 7; //Numero de diferentes piezas
    private final int FIRST = 0; //Para coger la primera pieza del LinkedList de pieces

    private Piece shadowActualPiece;
    private Piece shadowRandomPiece;
    private List<Piece> pieces = new LinkedList<>();

    private int actualRows = BOARD_NUM_ROWS;

    private int board[][]; //Tablero del juego

    public MainBoard() {
        //Inicialización tablero
        board = new int[BOARD_NUM_ROWS][BOARD_NUM_COLS];
        this.resetBoard(this.board);
        //Inicialización piezas
        shadowActualPiece = new Piece(SHADOW);
        shadowRandomPiece = new Piece(SHADOW);
        int pieceRandom1 = (int) (Math.random() * NUM_TYPE) + 1;
        int pieceRandom2 = (int) (Math.random() * NUM_TYPE) + 1;
        pieces.add(new Piece(pieceRandom1));
        pieces.add(new Piece(pieceRandom2));
    }

    public int [][] getBoard() {
        return this.board;
    }

    public int getBOARD_NUM_COLS() {
        return this.BOARD_NUM_COLS;
    }

    public int getActualRows() {
        return this.actualRows;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Piece getShadowActualPiece() {
        return shadowActualPiece;
    }

    public Piece getShadowRandomPiece() {
        return shadowRandomPiece;
    }

    public Piece getActualPiece() {
        return pieces.get(FIRST);
    }

    public Piece getNextPiece() {
        return pieces.get(FIRST + 1);
    }

    public void resetBoard(int [][] board) {
        for (int row = 0; row < actualRows; row++) {
            for (int col = 0; col < BOARD_NUM_COLS; col++) {
                board[row][col] = 0; //Inicializa el tablero
            }
        }
    }

    public boolean checkGameOver(Piece actualPiece) throws InterruptedException {
        Coordinates newXY = actualPiece.copyCoord(actualPiece.coord);
        newXY.updateCoord(1, 0);
        if (actualPiece.checkCollision(this.board,newXY) && actualPiece.getMinX(actualPiece.coord) < 2) {
            Thread.sleep(500);
            if (actualPiece.checkCollision(this.board,newXY)) {
                return true;
            }
        }
        return false;
    }

    //Para dibujar los bloques en el tablero
    public int drawBlocks(int row, int col,int num) {
        switch (num){
            case 0: {
                switch (board[row][col]) {
                    case EMPTY:
                        return Color.parseColor("#0030272A");
                    case S_PIECE:
                        return Color.parseColor("#0087FC");
                    case I_PIECE:
                        return Color.parseColor("#FD2929");
                    case J_PIECE:
                        return Color.parseColor("#9C00E2");
                    case O_PIECE:
                        return Color.parseColor("#00D1FE");
                    case L_PIECE:
                        return Color.parseColor("#FDD401");
                    case Z_PIECE:
                        return Color.parseColor("#FD6801");
                    case T_PIECE:
                        return Color.parseColor("#03DF04");
                    case SHADOW:
                        return Color.parseColor("#26F2F2F2");
                }
                break;
            }
            case 1: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#0087FC");
            }
            case 2: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#FD2929");
            }
            case 3: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#00D1FE");
            }
            case 4: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#9C00E2");
            }
            case 5: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#FDD401");
            }
            case 6: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#FD6801");
            }
            case 7: {
                if (board[row][col] == EMPTY) {
                    return Color.parseColor("#0030272A");
                } else if (board[row][col] == SHADOW) {
                    return Color.parseColor("#26F2F2F2");
                }
                return Color.parseColor("#03DF04");
            }
        }
        return Color.parseColor("#0030272A");
    }

    public int removeCompleteLines(Piece randomPiece) {
        int linesToRemove = 0;

        for (int row = 0; row < actualRows; row++) {
            int rowComplete = 0;
            for (int col = 0; col < BOARD_NUM_COLS; col++) {
                if (this.board[row][col] > EMPTY && this.board[row][col] != SHADOW) {
                    rowComplete++;
                }
            }
            if (rowComplete == BOARD_NUM_COLS) {
                for (int row1 = row; row1 > 0; row1--) {
                    for (int col1 = 0; col1 < BOARD_NUM_COLS; col1++) {
                        this.board[row1][col1] = this.board[row1 - 1][col1];
                    }
                }
                //Tenemos que actualizar las coordenadas de la pieza aleatoria en caso de que esté en el tablero
                if (randomPiece != null) {
                    randomPiece.moveCoord(1, 0);
                }

                //En caso de que se haga fila con la pieza random, actualizar la posicion de la pieza actual
                this.getActualPiece().moveCoord(1,0);

                linesToRemove++;
            }
        }
        return linesToRemove;
    }

    public void removePiece(Piece actualPiece, int [][] board) {
        board[actualPiece.coord.getCoord1().x][actualPiece.coord.getCoord1().y] = actualPiece.getNoBlock();
        board[actualPiece.coord.getCoord2().x][actualPiece.coord.getCoord2().y] = actualPiece.getNoBlock();
        board[actualPiece.coord.getCoord3().x][actualPiece.coord.getCoord3().y] = actualPiece.getNoBlock();
        board[actualPiece.coord.getCoord4().x][actualPiece.coord.getCoord4().y] = actualPiece.getNoBlock();
    }

    public void addPiece(Piece actualPiece, int [][] board) {
        board[actualPiece.coord.getCoord1().x][actualPiece.coord.getCoord1().y] = actualPiece.getTetromino();
        board[actualPiece.coord.getCoord2().x][actualPiece.coord.getCoord2().y] = actualPiece.getTetromino();
        board[actualPiece.coord.getCoord3().x][actualPiece.coord.getCoord3().y] = actualPiece.getTetromino();
        board[actualPiece.coord.getCoord4().x][actualPiece.coord.getCoord4().y] = actualPiece.getTetromino();
    }


    public void rotate(Piece actualPiece, boolean isActualPiece) {
        Piece checkPiece = new Piece(actualPiece);
        if ((actualPiece.rotatePiece(this.board, checkPiece.coord))) {
            removePiece(actualPiece, this.board);
            shadowPiece(checkPiece, isActualPiece);
            addPiece(checkPiece, this.board);
            actualPiece.updateAfterRotate(this.board, checkPiece.coord);
        }
    }

    public void moveToLeft(Piece actualPiece, boolean isActualPiece) {
        Coordinates newXY = actualPiece.copyCoord(actualPiece.coord);
        newXY.updateCoord(0, -1);
        if (!actualPiece.checkCollision(this.board, newXY)) {
            removePiece(actualPiece, this.board);
            actualPiece.moveCoord(0, -1);
            shadowPiece(actualPiece, isActualPiece);
            addPiece(actualPiece, this.board);
        }
    }

    public void moveToRight(Piece actualPiece, boolean isActualPiece) {
        Coordinates newXY = actualPiece.copyCoord(actualPiece.coord);
        newXY.updateCoord(0, 1);
        if (!actualPiece.checkCollision(this.board, newXY)) {
            removePiece(actualPiece, this.board);
            actualPiece.moveCoord(0, 1);
            shadowPiece(actualPiece, isActualPiece);
            addPiece(actualPiece, this.board);
        }
    }

    public boolean moveOneDown(Piece actualPiece, boolean isActualPiece) {
        Coordinates newXY = actualPiece.copyCoord(actualPiece.coord);
        newXY.updateCoord(1, 0);
        if (!actualPiece.checkCollision(this.board, newXY)) {
            removePiece(actualPiece, this.board);
            actualPiece.moveCoord(1, 0);
            shadowPiece(actualPiece, isActualPiece);
            addPiece(actualPiece, this.board);
            return true;
        }
        return false;
    }

    public void moveDown(Piece shadowPiece) {
        Piece newPosition = new Piece(shadowPiece);
        newPosition.coord = shadowPiece.copyCoord(shadowPiece.coord);
        newPosition.moveCoord(1, 0);
        while (!shadowPiece.checkCollision(this.board, newPosition.coord)) {
            removePiece(shadowPiece, this.board);
            addPiece(newPosition, this.board);
            newPosition.coord.updateCoord(1, 0);
            shadowPiece.moveCoord(1, 0);
        }
    }

    public void fastFall(Piece piece, boolean isActualPiece) {
        if (!getShadowActualPiece().coord.equals(new Coordinates()) || !getShadowActualPiece().equals(new Coordinates())) {
            removePiece(piece, this.board);
            if (isActualPiece) {
                piece.coord = piece.copyCoord(getShadowActualPiece().coord);
                getShadowActualPiece().coord = new Coordinates();
            } else {
                piece.coord = piece.copyCoord(getShadowRandomPiece().coord);
                getShadowRandomPiece().coord = new Coordinates();
            }
            addPiece(piece, this.board);
        }
    }

    public void shadowPiece(Piece actualPiece, boolean isActualPiece) {
        if (isActualPiece) {
            removePiece(shadowActualPiece, this.board);
            shadowActualPiece.coord = actualPiece.copyCoord(actualPiece.coord);
            moveDown(shadowActualPiece);
            addPiece(shadowActualPiece, this.board);
            if (shadowActualPiece.coord.equals(actualPiece.coord)) {
                shadowActualPiece.coord = new Coordinates();
            }
        } else {
            removePiece(shadowRandomPiece, this.board);
            shadowRandomPiece.coord = actualPiece.copyCoord(actualPiece.coord);
            moveDown(shadowRandomPiece);
            addPiece(shadowRandomPiece, this.board);
            if (shadowRandomPiece.coord.equals(actualPiece.coord)) {
                shadowRandomPiece.coord = new Coordinates();
            }
        }
    }

    public void reduceBoard(Piece randomPiece) {

        Piece actualPiece = this.getActualPiece();
        boolean firstRows = false;
        boolean firstRowsRandom = false;

        this.removePiece(actualPiece, this.board);

        Coordinates newXY = actualPiece.copyCoord(actualPiece.coord);
        newXY.updateCoord(2, 0);

        //Pieza en la fila 0 del tablero
        if (actualPiece.getCoord1().x == 0 || actualPiece.getCoord2().x == 0 || actualPiece.getCoord3().x == 0 || actualPiece.getCoord4().x == 0) {
            if (!actualPiece.checkCollision(this.board, newXY)) {
                actualPiece.moveCoord(1,0);
            }
        }

        //Pieza en la fila 1 del tablero
        if (actualPiece.getCoord1().x == 1 || actualPiece.getCoord2().x == 1 || actualPiece.getCoord3().x == 1 || actualPiece.getCoord4().x == 1) {
            if (!actualPiece.checkCollision(this.board, newXY)) {
                actualPiece.moveCoord(1,0);
                firstRows = true;
            }
        }

        //Hacemos lo mismo para la pieza random en caso de que se encuentre en el tablero
        if (randomPiece != null) {

            this.removePiece(randomPiece, this.board);

            Coordinates randomXY = randomPiece.copyCoord(randomPiece.coord);
            randomXY.updateCoord(2, 0);

            //Pieza en la fila 0 del tablero
            if (randomPiece.getCoord1().x == 0 || randomPiece.getCoord2().x == 0 || randomPiece.getCoord3().x == 0 || randomPiece.getCoord4().x == 0) {
                if (!randomPiece.checkCollision(this.board, randomXY)) {
                    randomPiece.moveCoord(1,0);
                }
            }

            //Pieza en la fila 1 del tablero
            if (randomPiece.getCoord1().x == 1 || randomPiece.getCoord2().x == 1 || randomPiece.getCoord3().x == 1 || randomPiece.getCoord4().x == 1) {
                if (!actualPiece.checkCollision(this.board, randomXY)) {
                    randomPiece.moveCoord(1,0);
                    firstRowsRandom = true;
                }
            }
        }

        shadowActualPiece.coord = new Coordinates();
        shadowRandomPiece.coord = new Coordinates();

        //Reducimos las filas del tablero en 2
        this.actualRows = this.actualRows - 2;

        //Proceso para actualizar el tablero con dos filas menos
        int [][] newBoard = new int[actualRows][BOARD_NUM_COLS];

        for (int row = 2; row < this.board.length; row++) {
            System.arraycopy(this.board[row], 0, newBoard[row - 2], 0,this.board[row].length);
        }

        this.board = newBoard;
        /*
        Una vez tenemos el tablero actualizado, si teniamos la(s) pieza(s) al principio del tablero, como
        las piezas han sido desplazadas dos filas abajo antes de quitar las dos filas ya no lo tenemos que mover.
         */
        actualPiece.moveCoord(-2, 0);

        if (firstRows) {
            this.addPiece(actualPiece, this.board);
            return;
        }

        this.moveOneDown(actualPiece, true);
        this.moveOneDown(actualPiece, true);

        //Hacemos lo mismo para el caso de la pieza aleatoria
        if (randomPiece != null) {
            randomPiece.moveCoord(-2,0);

            if (firstRowsRandom) {
                this.addPiece(randomPiece, this.board);
                return;
            }

            this.moveOneDown(randomPiece, false);
            this.moveOneDown(randomPiece, false);
        }

    }
}
