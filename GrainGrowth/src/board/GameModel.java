package board;

import java.util.ArrayList;
import java.util.Random;

public class GameModel
{
    private BoardGrid board;

    public GameModel(BoardGrid board)
    {
        this.board = board;
    }

    public BoardGrid getBoard()
    {
        return board;
    }


    public void setBoard(BoardGrid board)
    {
        this.board = board;
    }

    public void oneIteration()
    {
        for (BoardGrid.BoardCell cell: board.getAllCells()) {
            ArrayList<BoardGrid.BoardCell> activeNeighbors = this.getActiveCellsFromCollection(
                    board.getCellNeighbors(cell.getRow(), cell.getCol())
            );
            BoardButton button = cell.getButton();

            if (button.isActive()) {
                if (activeNeighbors.size() > 3) {
                    button.setState(BoardButton.STATE_INACTIVE); // żywa komórka, która posiada więcej niż 3 sąsiadów umiera z natłoku
                } else if (activeNeighbors.size() < 2) {
                    button.setState(BoardButton.STATE_INACTIVE); // żywa komórka, która posiada mniej niż 2 sąsiadów umiera z samotności
                }
            } else if (activeNeighbors.size() == 3) {
                button.setState(BoardButton.STATE_ACTIVE); // martwa komórka która posiada 3 sąsiadów zostaje ożywiona
            }
        }
    }

//pobiera aktywne pola
    public ArrayList<BoardGrid.BoardCell> getActiveCellsFromCollection(ArrayList<BoardGrid.BoardCell> cells)
    {
        ArrayList<BoardGrid.BoardCell> collection = new ArrayList<BoardGrid.BoardCell>();

        for (BoardGrid.BoardCell cell: cells) {
            if (cell.getButton().isActive()) {
                collection.add(cell);
            }
        }

        return collection;
    }

    public void randomLiveCells(int amount)
    {
        Random rand = new Random();
        ArrayList<BoardGrid.BoardCell> collection = new ArrayList<BoardGrid.BoardCell>();

        for (BoardGrid.BoardCell cell: board.getAllCells()) {
            collection.add(cell);
        }

        for(int i=0; i<amount; i++) {
            int index = rand.nextInt(collection.size());
            BoardGrid.BoardCell grain = collection.get(index);
            grain.getButton().setColors();
            grain.getButton().setColorStyle(i);
            grain.getButton().setState(BoardButton.STATE_COLORED);


        }
    }

    public void clearBoard()
    {
        board.setSize(board.getSizeX(), board.getSizeY());
    }


}
