package edu.vt.cs5044;

import static org.junit.Assert.*;
import edu.vt.cs5044.tetris.AI;
import edu.vt.cs5044.tetris.Board;
import edu.vt.cs5044.tetris.Placement;
import edu.vt.cs5044.tetris.Rotation;
import edu.vt.cs5044.tetris.Shape;
import org.junit.Before;
import org.junit.Test;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author Elan
 *  @version Jul 9, 2017
 */
@SuppressWarnings("javadoc")

public class TetrisAITest
{
    private AI ai;
    
    @Before
    public void setUp()
       throws Exception
    {
        ai = new TetrisAI();
    }
    
    
    @Test
    public void testTotalBlockCountEmptyBoard()
    {
        Board board = new Board();
        
        assertEquals(0, ai.getTotalBlockCount(board));
        assertEquals(0, ai.getTotalGapCount(board));
        assertEquals(0, ai.getMaximumBlockHeight(board));
        assertEquals(0, ai.getColumnHeightVariability(board));
    }
    
    @Test
    public void testTotalBlockCountSimpleBoard()
    {
        Board board = new Board(
            "#####  ###",
            "### ### ##");
        
        assertEquals(16, ai.getTotalBlockCount(board));
        assertEquals(2, ai.getTotalGapCount(board));
        assertEquals(2, ai.getMaximumBlockHeight(board));
        assertEquals(2, ai.getColumnHeightVariability(board));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 0));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 1));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 2));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 3));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 4));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 5));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 6));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 7));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 8));
        assertEquals(2, ((TetrisAI)ai).getHeightAtColumn(board, 9));
    }
    
    @Test
    public void testTotalBlockCountSimplerBoard()
    {
        Board board = new Board(
            "##########");
        
        assertEquals(10, ai.getTotalBlockCount(board));
        assertEquals(0, ai.getTotalGapCount(board));
        assertEquals(1, ai.getMaximumBlockHeight(board));
        assertEquals(0, ai.getColumnHeightVariability(board));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 0));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 1));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 2));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 3));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 4));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 5));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 6));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 7));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 8));
        assertEquals(1, ((TetrisAI)ai).getHeightAtColumn(board, 9));
    }
    
        @Test
    public void testGapCount()
    {
        Board board = new Board(
            " ###      ",
            "# #       ",
            "   ##     "
            );
        
        
        assertEquals(7, ai.getTotalBlockCount(board));
        assertEquals(5, ai.getTotalGapCount(board));
        assertEquals(3, ai.getMaximumBlockHeight(board));
        assertEquals(4, ai.getColumnHeightVariability(board));
        assertEquals(1, ((TetrisAI)ai).getGapsAtColumn(board, 0));
        assertEquals(2, ((TetrisAI)ai).getGapsAtColumn(board, 1));
        assertEquals(1, ((TetrisAI)ai).getGapsAtColumn(board, 2));
        assertEquals(1, ((TetrisAI)ai).getGapsAtColumn(board, 3));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 4));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 5));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 6));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 7));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 8));
        assertEquals(0, ((TetrisAI)ai).getGapsAtColumn(board, 9));
        
    }
        @Test
        public void testSimpleBoardPlacement()
        {
            Board expectedResultBoard = new Board(
                "### ######"
            );
            Placement expectedPlace = new Placement(Rotation.CCW_180, 2);
            Shape shape = Shape.T;
            assertEquals(expectedPlace, ai.findBestPlacement(expectedResultBoard, shape));

            Board testBoard = new Board();
            Placement bestPlace = ai.findBestPlacement(testBoard, Shape.T);
            Board actualResultBoard = testBoard.getResultBoard(Shape.T, bestPlace);
            assertEquals(2, ai.getMaximumBlockHeight(actualResultBoard)); 
            assertEquals(4, ai.getTotalBlockCount(actualResultBoard));
            assertEquals(3, ai.getColumnHeightVariability(actualResultBoard)); 
            assertEquals(0, ai.getTotalGapCount(actualResultBoard)); 
            //assertEquals(expectedResultBoard, actualResultBoard);
            
        }
        
        @Test
        public void testBoard1()
        {
            Board board = new Board(
                "###       ",
                "######    ",
                "######    ",
                "######  # ",
                "###### ###",
                "######### "
                );
            Shape shape = Shape.T;
            Placement expectedPlace = new Placement(Rotation.CCW_270, 6); 
            
            assertEquals(40, ai.getTotalBlockCount(board));
            assertEquals(1, ai.getTotalGapCount(board));
            assertEquals(6, ai.getMaximumBlockHeight(board));
            assertEquals(8, ai.getColumnHeightVariability(board));
            assertEquals(expectedPlace, ai.findBestPlacement(board, shape));
            
        }
        
        @Test
        public void testBoard2()
        {
            Board board = new Board(
                " ##       ",
                " #####    ",
                " #####    ",
                " #########",
                " #########",
                " ######## "
                );
            Shape shape = Shape.I;
            Placement expectedPlace = new Placement(Rotation.NONE, 0); 
            
            assertEquals(38, ai.getTotalBlockCount(board));
            assertEquals(1, ai.getTotalGapCount(board));
            assertEquals(6, ai.getMaximumBlockHeight(board));
            assertEquals(9, ai.getColumnHeightVariability(board));
            assertEquals(expectedPlace, ai.findBestPlacement(board, shape));
            
        }
        
        @Test
        public void testBoard3()
        {
            Board board = new Board(
                "          ",
                "  #       ",
                "  #       ",
                "  #       ",
                "  #       ",
                "  #       ",
                "  #       ",
                " ##   #   ",
                "# #   #   ",
                "# ### #   ",
                "# ##  #   ",
                "##########"
                );
            Shape shape = Shape.O;
            Placement expectedPlace = new Placement(Rotation.NONE, 3); 
            
            assertEquals(31, ai.getTotalBlockCount(board));
            assertEquals(4, ai.getTotalGapCount(board));
            assertEquals(11, ai.getMaximumBlockHeight(board));
            assertEquals(25, ai.getColumnHeightVariability(board));
            assertEquals(expectedPlace, ai.findBestPlacement(board, shape));
            
        }
        
        @Test
        public void testBoard4()
        {
            Board board = new Board(
                "          ",
                "          ",
                "  #       ",
                "  #    ## ",
                "  #    ## ",
                "  #    ## ",
                "  #   ####",
                "  ##  ####",
                " ###  ####",
                "# ##  ####",
                "# ### ####",
                "# ##  ####",
                "##########"
                );
            Shape shape = Shape.J;
            Placement expectedPlace = new Placement(Rotation.CCW_180, 0); 
            
            assertEquals(60, ai.getTotalBlockCount(board));
            assertEquals(4, ai.getTotalGapCount(board));
            assertEquals(11, ai.getMaximumBlockHeight(board));
            assertEquals(29, ai.getColumnHeightVariability(board));
            assertEquals(expectedPlace, ai.findBestPlacement(board, shape));
            
        }
        
        @Test
        public void testBoard5()
        {
            Board board = new Board(
                "##        ",
                "##        ",
                "##        ",
                "##        ",
                "##        ",
                "##        ",
                "## # #    ",
                "## ### #  ",
                "## # # ## ",
                "## ### ###",
                "## # # ## "
                );
            Shape shape = Shape.S;
            Placement expectedPlace = new Placement(Rotation.CCW_90, 8); 
            
            assertEquals(42, ai.getTotalBlockCount(board));
            assertEquals(3, ai.getTotalGapCount(board));
            assertEquals(11, ai.getMaximumBlockHeight(board));
            assertEquals(29, ai.getColumnHeightVariability(board));
            assertEquals(expectedPlace, ai.findBestPlacement(board, shape));
            
        }
}
