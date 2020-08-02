package ru.eugenpolytechnic.tictactoe;

import java.util.*;

public final class TicTacToe {

    public enum CellState {
        EMPTY('.'), CROSS('X'), ZERO('O');

        private char view;

        CellState(char symbol) {
            view = symbol;
        }

        public char getView() {
            return view;
        }
    }

    private enum Route {
        horizontal(1, 0), vertical(0, 1), leftDiagonal(1, 1), rightDiagonal(1, -1);

        private int x, y;

        Route(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final int size;

    private CellState[][] field;

    private int maxCross = 0;
    private int maxZero = 0;

    public TicTacToe(int size) {
        if (size >= 3)
            this.size = size;
         else
            throw new IllegalArgumentException("Размер поля должен быть, как минимум, 3х3");
        field = new CellState[this.size][this.size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.field[i][j] = CellState.EMPTY;
    }

    public TicTacToe() {
        this(3);
    }

    private boolean inside(int x, int y) {
        return x < size && y < size && x >= 0 && y >= 0;
    }

    private void changeMaximum(CellState symbol, int currentMax) {
        switch (symbol) {
            case CROSS:
                if (currentMax > maxCross)
                    maxCross = currentMax;
                break;
            case ZERO:
                if (currentMax > maxZero)
                    maxZero = currentMax;
                break;
        }
    }

    /**
     * Метод ниже проверяет направление, заданное в параметре,
     * на количество подряд идущих символов.
     *
     * @param route указывает маршрут для проверки
     * @param symbol указывает символ, последовательность которых ищется
     * @param x задаёт координату х начальной для проверки клетки
     * @param y задаёт координату y начальной для проверки клетки
     * @param direction указывает направление проверки(положительное или отрицательное)
     * @return наибольшую последовательность подряд идущих символов(количество)
     */
    private int checkRoute(Route route, CellState symbol, int x, int y, boolean direction) {
        int currentMax = 0;
        int currentX = x;
        int currentY = y;
        while (inside(currentX, currentY) && field[currentX][currentY] == symbol) {
            currentMax++;
            if (direction) {
                currentX += route.x;
                currentY += route.y;
            } else {
                currentX -= route.x;
                currentY -= route.y;
            }
        }
        if (!direction)
            return currentMax;
        return currentMax + checkRoute(route, symbol, x, y, false) - 1; // -1, так как клетку [x][y] считали 2 раза
    }

    /**
     * Метод ниже проверяет 4 направления, идущие от клетки, в которую поставили новый символ, на наличие появления
     * новой максимальной последовательности этих символов. Проход только в 4х направлениях, в отличие от проверки
     * всего поля, позволяет сэкономить время.
     *
     * @param x задаёт координату х начальной для проверки клетки
     * @param y ззадаёт координату y начальной для проверки клетки
     * @param symbol указывает символ, последовательность которых ищется
     */
    private void lengthCheck(int x, int y, CellState symbol) {
        for (Route route: Route.values())
            changeMaximum(symbol, checkRoute(route, symbol, x, y, true));
    }

    private boolean setSymbolAt(int x, int y, CellState symbol) {
        if (inside(x, y) && field[x][y] == CellState.EMPTY)
            field[x][y] = symbol;
        else
            return false;
        this.lengthCheck(x, y, symbol);
        return true;
    }

    public boolean setCrossAt(int x, int y) {
        return this.setSymbolAt(x, y, CellState.CROSS);
    }

    public boolean setZeroAt(int x, int y) {
        return this.setSymbolAt(x, y, CellState.ZERO);
    }

    /**
     * Метод ниже проверяет все вертикальные или горизонтальные направления для поиска
     * наибольшей последовательности символов.
     *
     * @param route указывает маршрут для проверки(вертикальнаый или диагональный)
     * @param symbol указывает символ, последовательность которых ищется
     */
    private void checkAllLinesAt(Route route, CellState symbol) {
        int currentMax = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (route) {
                    case horizontal:
                        if (field[i][j] == symbol) {
                            currentMax++;
                        } else {
                            changeMaximum(symbol, currentMax);
                            currentMax = 0;
                        }
                        break;
                    case vertical:
                        if (field[j][i] == symbol) {
                            currentMax++;
                        } else {
                            changeMaximum(symbol, currentMax);
                            currentMax = 0;
                        }
                        break;
                }
            }
            changeMaximum(symbol, currentMax);
            currentMax = 0;
        }
    }

    /**
     * Метод ниже проверяет все диагонали(правые или левые) для поиска
     * наибольшей последовательности символов.
     *
     * @param route указывает маршрут для проверки(правая или левая диагонали)
     * @param symbol указывает символ, последовательность которых ищется
     * @param isTop указывает, какая половина из множества диагоналей проверяется
     */
    private void checkAllDiagonals(Route route, CellState symbol, boolean isTop) {
        int currentMax = 0;
        for (int i = 1; i < size; i++) {
            int currentX = i;
            int currentY = 0;
            if ((route == Route.leftDiagonal) == isTop) //route == leftDiagonal && isTop || route == rightDiagonal && !isTop
                currentY = size - 1;
            while (inside(currentX, currentY)) {
                if (field[currentX][currentY] == symbol) {
                    currentMax++;
                } else {
                    changeMaximum(symbol, currentMax);
                    currentMax = 0;
                }
                if (isTop) {
                    currentX--;
                    if (route == Route.leftDiagonal)
                        currentY--;
                    else //route == Route.rightDiagonal
                        currentY++;
                } else {
                    currentX++;
                    if (route == Route.leftDiagonal)
                        currentY++;
                    else //route == Route.rightDiagonal
                        currentY--;
                }
            }
            changeMaximum(symbol, currentMax);
            currentMax = 0;
        }
        if (isTop)
            checkAllDiagonals(route, symbol, false);
    }

    /**
     * Методом ниже проверяется всё поле, для поиска наибольшей последовательности символов.
     * Он необходим для обновления данных после очистки какой-либо клетки от символа.
     *
     * @param symbol указывает символ, последовательность которых ищется
     */
    private void checkMaximum(CellState symbol) {
        switch (symbol) {
            case CROSS:
                maxCross = 0;
                break;
            case ZERO:
                maxZero = 0;
                break;
        }
        checkAllLinesAt(Route.horizontal, symbol);
        checkAllLinesAt(Route.vertical, symbol);
        checkAllDiagonals(Route.leftDiagonal, symbol, true);
        checkAllDiagonals(Route.rightDiagonal, symbol, true);
    }

    public boolean clearFieldAt(int x, int y) {
        CellState symbol;
        if (inside(x, y) && field[x][y] != CellState.EMPTY) {
            symbol = field[x][y];
            field[x][y] = CellState.EMPTY;
        } else
            return false;
        checkMaximum(symbol);
        return true;
    }

    public CellState getFieldAt(int x, int y) {
        if (inside(x, y))
            return field[x][y];
        else
            throw new IllegalArgumentException("Клетка не находится внутри поля");
    }

    public int longestCrossSequence() {
        return maxCross;
    }

    public int longestZeroSequence() {
        return maxZero;
    }

    public void clearField() {
        maxCross = 0;
        maxZero = 0;
        for (int i =0; i < size; i++) {
            for (int j = 0; j < size; j++)
                field[i][j] = CellState.EMPTY;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(field);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            result.append(field[i][j].getView());
            result.append("   ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof TicTacToe) {
            TicTacToe other = (TicTacToe) obj;
            return (this.size == other.size) && Arrays.deepEquals(this.field, other.field);
        }
        return false;
    }
}
