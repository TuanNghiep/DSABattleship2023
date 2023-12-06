package main.ui;

import java.util.LinkedList;
import java.util.Random;
import java.io.Serializable;

public class Map implements Serializable {
    public static final int DIM_MAP = 10;
    private final char NULL = '0', OCTOPUS = 'X', ACQUA = 'A', HIT = 'H';
    private char[][] map;
    LinkedList<OctPos> list;

    public Map() {
        list = new LinkedList<OctPos>();
        map = new char[DIM_MAP][DIM_MAP];
        for (int i = 0; i < DIM_MAP; i++)
            for (int j = 0; j < DIM_MAP; j++)
                map[i][j] = NULL;
    }

    public void initializeRandomMap() {
        clear();
        Random r = new Random();
        insertRandom(r, 4);
        insertRandom(r, 3);
        insertRandom(r, 3);
        insertRandom(r, 2);
        insertRandom(r, 2);
        insertRandom(r, 2);
        insertRandom(r, 1);
        insertRandom(r, 1);
        insertRandom(r, 1);
        insertRandom(r, 1);
    }

    private void clear() {
        for (int i = 0; i < DIM_MAP; i++)
            for (int j = 0; j < DIM_MAP; j++)
                map[i][j] = NULL;

    }

    public boolean insert(int x, int y, int dim, int dir) {
        if (dir == 1 && x + dim > DIM_MAP) {
            return false;
        } // vertical
        if (dir == 0 && y + dim > DIM_MAP) {
            return false;
        } // horizontal
        boolean insert;

        if (dir == 0)
            insert = checkHorizontal(x, y, dim);
        else
            insert = checkVertical(x, y, dim);

        if (!insert)
            return false;
        if (dir == 0) {
            OctPos n = new OctPos(x, y, x, y + dim - 1);
            list.add(n);
        } else {
            OctPos n = new OctPos(x, y, x + dim - 1, y);
            list.add(n);
        }
        for (int i = 0; i < dim; i++) {
            if (dir == 0) {
                map[x][y + i] = OCTOPUS;
            } else
                map[x + i][y] = OCTOPUS;
        }
        return true;
    }

    public int[] insertRandom(Random random, int dimention) {
        boolean insert;
        int[] dati = new int[4];
        int direction, row, column;
        do {
            insert = true;
            direction = random.nextInt(2); // 0=Horizontal, 1=Vertical
            if (direction == 0) {
                column = random.nextInt(DIM_MAP - dimention + 1);
                row = random.nextInt(DIM_MAP);
            } else {
                column = random.nextInt(DIM_MAP);
                row = random.nextInt(DIM_MAP - dimention + 1);
            }
            if (direction == 0)
                insert = checkHorizontal(row, column, dimention);
            else
                insert = checkVertical(row, column, dimention);
        } while (!insert);
        if (direction == 0) {
            OctPos n = new OctPos(row, column, row, column + dimention - 1);
            list.add(n);
        } else {
            OctPos n = new OctPos(row, column, row + dimention - 1, column);
            list.add(n);
        }
        for (int i = 0; i < dimention; i++) {
            if (direction == 0) {
                map[row][column + i] = OCTOPUS;
            } else
                map[row + i][column] = OCTOPUS;
        }
        dati[0] = row;
        dati[1] = column;
        dati[2] = dimention;
        dati[3] = direction;
        return dati;
    }

    public boolean checkVertical(int row, int column, int dimention) {
        if (row != 0)
            if (map[row - 1][column] == OCTOPUS)
                return false;
        if (row != DIM_MAP - dimention)// finish place octopus
            if (map[row + dimention][column] == OCTOPUS)
                return false;
        for (int i = 0; i < dimention; i++) {
            if (column != 0)
                if (map[row + i][column - 1] == OCTOPUS)
                    return false;
            if (column != DIM_MAP - 1)
                if (map[row + i][column + 1] == OCTOPUS)
                    return false;
            if (map[row + i][column] == OCTOPUS)
                return false;
        }
        return true;
    }

    public boolean checkHorizontal(int row, int column, int dimention) {
        if (column != 0)
            if (map[row][column - 1] == OCTOPUS)
                return false;
        if (column != DIM_MAP - dimention)
            if (map[row][column + dimention] == OCTOPUS)
                return false;
        for (int i = 0; i < dimention; i++) {
            if (row != 0)
                if (map[row - 1][column + i] == OCTOPUS)
                    return false;
            if (row != DIM_MAP - 1)
                if (map[row + 1][column + i] == OCTOPUS)
                    return false;
            if (map[row][column + i] == OCTOPUS)
                return false;
        }
        return true;
    }

    public boolean hitt(Position p) {
        int row = p.getCoordX();
        int column = p.getCoordY();
        if (map[row][column] == OCTOPUS) {
            map[row][column] = HIT;
            return true;
        }
        map[row][column] = ACQUA;
        return false;
    }

    public OctPos sunk(Position p) {
        int row = p.getCoordX();
        int col = p.getCoordY();
        OctPos ship = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).checkCoord(row, col)) {
                ship = list.get(i);
                break;
            }
        }
        for (int i = ship.getXin(); i <= ship.getXfin(); i++) {
            for (int j = ship.getYin(); j <= ship.getYfin(); j++) {
                if (map[i][j] != HIT) {
                    return null;
                }
            }
        }
        list.remove(ship);
        return ship;
    }

    public void setAcqua(Position p) {
        map[p.getCoordX()][p.getCoordY()] = ACQUA;
    }

    public boolean acqua(Position p) {
        return map[p.getCoordX()][p.getCoordY()] == ACQUA;
    }

    public boolean hit(Position p) {
        return map[p.getCoordX()][p.getCoordY()] == HIT;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIM_MAP; i++) {
            for (int j = 0; j < DIM_MAP; j++) {
                sb.append(map[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setAdvOctopus(LinkedList<int[]> advOctopus) {
        list.clear();
        for (int[] a : advOctopus) {
            insert(a[0], a[1], a[2], a[3]);
            System.out.println("Insert Octopus" + a[0] + a[1] + a[2] + a[3]);
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                System.out.print(map[i][j]);
            System.out.println("");
        }
    }

    public LinkedList<int[]> get(LinkedList<int[]> playerOctopus) {
        return null;
    }
}