package edu.vt.cs5044;

import edu.vt.cs5044.tetris.AI;
import edu.vt.cs5044.tetris.Board;
import edu.vt.cs5044.tetris.Placement;
import edu.vt.cs5044.tetris.Rotation;
import edu.vt.cs5044.tetris.Shape;

/**
 *  This class implements the AI for Tetris.
 *  The AI is used to determine best placement of pieces on the board in order to attain a higher 
 *  score in the game Tetris.
 *  It iterates through each shape and placement combination and uses cost and weights to 
 *  determine the best placement.
 * 
 *  @author Elan
 *  @version Jun 28, 2017
 */
public class TetrisAI
    implements AI
{

    /**
     * {@inheritDoc}
     */
    @Override
    public Placement findBestPlacement(Board currentBoard, Shape shape)
    {
        double bestCost = Double.MAX_VALUE;
        int col = 0;
        Placement bestPlacement = null;
        
        for (Rotation rotation : shape.getRotationSet())

        {
            for (col = 0; col <= Board.WIDTH - shape.getWidth(rotation); col++)
            {
                Placement place = new Placement(rotation, col);
                Board resultBoard = currentBoard.getResultBoard(shape, place);
                double cost = calculateCost(resultBoard);
                if (cost < bestCost)
                {
                    bestPlacement = place;
                    bestCost = cost; 
                }
            }
        
              
        }
        return bestPlacement;
    }    


    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnHeightVariability(Board board)
    {
        int col = 0;
        int var = 0;
     
        for (col = 0; col < Board.WIDTH - 1; col++)
        {
            var +=  Math.abs(getHeightAtColumn(board, col) - getHeightAtColumn(board, col + 1));
        }
            
        return var;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumBlockHeight(Board board)
    {
        int maxHeight = -1;
        int col = 0;
        for (col = 0; col < Board.WIDTH; col++)
        {
            int colHeight = getHeightAtColumn(board, col);
            if (colHeight > maxHeight)
            {
                maxHeight = colHeight;
            }
        }
        return maxHeight;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalBlockCount(Board board)
    {
        int numberofblocks = 0;
        int row = 0;
        for (int col = 0; col < Board.WIDTH; col++)
        {
            for (row = 0; row < Board.HEIGHT; row++)
            {
                if (board.isBlockAt(col, row))
                {
                    numberofblocks++;
                }
            }
        }
        return numberofblocks;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalGapCount(Board board)
    {
        int count = 0;
        int col = 0;
        for (col = 0; col < Board.WIDTH; col++)
        {     // iterate through columns from left
            count += getGapsAtColumn(board, col);
        }
        return count;
    }

    /**
     * This method returns the highest block for a given column, it iterates starting from the left
     * of the board and finishes on the last column on the board.
     * @param board current board
     * @param col current column
     * @return highest highest block
     */
    public int getHeightAtColumn(Board board, int col)
    {
        int row = 0;
        int highest = 0;
        for (row = 0; row < Board.HEIGHT; row++)
        {
            if (board.isBlockAt(col, row))
            {
                highest = row + 1;
            }
        }
        return highest;
    }
    
    /**
     * This method returns the number of gaps for a given column, it iterates starting from the left
     * of the board and finishes on the last column of the board.
     * @param board the current board
     * @param col the current column
     * @return count the count of gaps in current column
     */
    public int getGapsAtColumn(Board board, int col)
    {
        int row = 0;
        int count = 0;
        boolean hasBlock = false;
        for (row = Board.HEIGHT - 1; row > 0; row--)
        {
            if (board.isBlockAt(col, row)) 
            {
                hasBlock = true;
                
            }
            if (hasBlock && !board.isBlockAt(col, row - 1))
            {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Calculates the current cost of a move using the 4 cost methods that have been developed 
     * multiplied by a respective weight for each cost.
     * @param board the current board
     * @return costTotal the total cost of a given move
     */
    public double calculateCost(Board board)
    {
        double maxHeight = 1 * getMaximumBlockHeight(board);
        double totalBlocks = 1.5 * getTotalBlockCount(board); 
        double totalGaps = 10 * getTotalGapCount(board);
        double heightVar = 2.5 * getColumnHeightVariability(board);
        double costTotal =
            maxHeight + totalBlocks + totalGaps + heightVar;
        return costTotal;
    }

}
