package Pieces;

import Structures.Board;
import Structures.Square;

import java.util.ArrayList;
import java.util.List;

import static Structures.PieceColor.White;

public class BlackPawn extends Pawn {
    public BlackPawn(final Square location, final Board board)
    {
        super(location, White,board);
    }

    @Override
    public int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) +  _location.rank*20 + 80;
    }

    @Override
    public List<Square> GetNextMoves(boolean checkKing) {
        List<Square> nextMoves = new ArrayList<Square>(4);

        //TODO
        //if(Game::GetInstance()->GetTurn() != color)
        //return nextMoves;

        byte x = _location.file;
        byte y = _location.rank;

        Piece piece;

        if(y+1 < 8)
        {
            piece = board.squares[x][y+1].piece;
            if(piece != null)
            {
                Append(board.squares[x][y+1],nextMoves,checkKing);

                if(y == 1)
                {
                    piece = board.squares[x][3].piece;
                    if(piece != null)
                        Append(board.squares[x][3],nextMoves,checkKing);
                }
            }

            if(x<7)
            {
                piece = board.squares[x+1][y+1].piece;
                if(piece != null && piece.color == White)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 4)
                {
                    piece = board.squares[x+1][4].piece;
                    if(piece != null && piece.color == White && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                        Append(board.squares[x+1][5],nextMoves,checkKing);
                    }
                }
            }

            if(x>0)
            {
                piece = board.squares[x-1][y+1].piece;
                if(piece != null && piece.color == White)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 4)
                {
                    piece = board.squares[x-1][4].piece;
                    if(piece != null && piece.color == White && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                        Append(board.squares[x-1][5],nextMoves,checkKing);
                    }
                }
            }
        }

        return nextMoves;
    }
}
