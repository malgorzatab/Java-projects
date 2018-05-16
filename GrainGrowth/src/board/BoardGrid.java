package board;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardGrid extends GridPane
{
    private BoardCell[][] cells;        //pola na planszy
    private int sizeX;
    private int sizeY;
    private boolean blocked;


    public class BoardCell
    {
        private BoardButton button;     //pojedynczy przycisk
        private int col;
        private int row;

        public BoardCell(BoardButton button, int row, int col)
        {
            this.button = button;
            this.row = row;
            this.col = col;
        }

        public int getRow()
        {
            return row;
        }

        public int getCol()
        {
            return col;
        }

        public BoardButton getButton()
        {
            return button;
        }
    }

    public BoardGrid()
    {
        this.setSize(1,1);
        this.blocked = false;
    }

    public BoardGrid(int sizeX, int sizeY)
    {
        this.setSize(sizeX, sizeY);
        this.blocked = false;
    }

    public int getSizeX()
    {
        return sizeX;
    }

    public int getSizeY()
    {
        return sizeY;
    }


    public void setSize(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.cells = new BoardCell[sizeY][sizeX];
        this.generate();
    }

    //wszystkie pola
    public ArrayList<BoardCell> getAllCells()
    {
        ArrayList<BoardCell> collection = new ArrayList<BoardCell>();

        for (int row = 0; row < this.getSizeX(); ++row) {
            collection.addAll(Arrays.asList(cells[row]));
        }

        return collection;
    }

    public boolean isCellExists(int row, int col)
    {
        if (row >= 0 && row < this.getSizeX() && col >= 0 && col < this.getSizeY()) {
            return true;
        } else {
            return false;
        }
    }

    public void setCell(int row, int col, BoardButton button)
    {
        this.cells[row][col] = new BoardCell(button, row, col);
        this.add(button, col, row);
    }

    public BoardCell getCell(int row, int col)
    {
        return this.cells[row][col];
    }

    private void generate()
    {
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        for (int i = 0; i < this.getSizeY(); ++i) {
            this.getColumnConstraints().add(new ColumnConstraints());
        }

        for (int i = 0; i < this.getSizeX(); ++i) {
            this.getRowConstraints().add(new RowConstraints());
        }

        for (int row = 0; row < this.getSizeX(); ++row) {
            for (int col = 0; col < this.getSizeY(); ++col) {
                BoardButton button = new BoardButton();
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!isBlocked()) {
                            if (button.isActive()) {
                                button.setState(BoardButton.STATE_INACTIVE);
                            } else {
                                button.setState(BoardButton.STATE_ACTIVE);
                            }
                        }
                    }
                });

                this.setCell(row, col, button);
            }
        }
    }


    //sąsiedzi komórki
    public ArrayList<BoardCell> getCellNeighbors(int row, int col)
    {
        ArrayList<BoardCell> list = new ArrayList<BoardCell>();

        for (int i = col - 1; i < col + 2; i++) {
            if (this.isCellExists(row - 1, i)) {
                list.add(this.getCell(row - 1, i));
            }

            if (this.isCellExists(row + 1, i)) {
                list.add(this.getCell(row + 1, i));
            }
        }

        if (this.isCellExists(row, col - 1)) {
            list.add(this.getCell(row, col - 1));
        }

        if (this.isCellExists(row, col + 1)) {
            list.add(this.getCell(row, col + 1));
        }

        return list;
    }


    public boolean isBlocked()
    {
        return blocked;
    }

    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }
}
