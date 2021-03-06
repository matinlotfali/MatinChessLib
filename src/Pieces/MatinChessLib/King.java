package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class King extends Piece {
    private boolean _rookMoved = false;
    private Movement _rookMovement;

    King(final ChessSquare location, final PieceColor color, final Board board) {
        super(location, color, board);
        if (color == PieceColor.White)
            board.WhiteKing = this;
        else
            board.BlackKing = this;
        this.myKing = this;
    }

    @Override
    char GetChar() {
        return color == White ? 'k' : 'K';
    }

    private boolean IsCheckMate() {  return GetThreatCount() > 0 && board.GetNextMovesCount() == 0;  }

    @Override
    int GetScore() {
        return GetScore(true);
    }
    int GetScore(final boolean nextMoves) {
        if (IsCheckMate())
            return Integer.MIN_VALUE;

        int score = super.GetScore(nextMoves);
        //if(IsInThreat())
        //    score -= 50;
        //score += GetNextMovesCount()*5;

        return score;
    }

    @Override
    void MovePiece(final ChessSquare square) {
        _rookMoved = false;
        if(GetMoveCount() == 0)
        {
            Piece piece;
            if(square.file == Board.G || square.file == Board.C)
            {
                _rookMoved = true;
                if(square.file == Board.G)
                {
                    _rookMovement = new Movement(
                            board.squares[Board.H][GetLocation().rank],
                            board.squares[Board.F][GetLocation().rank]);
                }
                else
                {
                    _rookMovement = new Movement(
                            board.squares[Board.A][GetLocation().rank],
                            board.squares[Board.D][GetLocation().rank]);
                }
                piece = board.squares[_rookMovement.from.file][_rookMovement.from.rank].piece;
                //if(piece)
                piece.MovePiece(board.squares[_rookMovement.to.file][_rookMovement.to.rank]);
            }
        }
        super.MovePiece(square);
    }

    @Override
    void MoveBack(final ChessSquare square) {
        _rookMoved = false;
        if(GetMoveCount() == 1)
        {
            Piece piece;
            switch(GetLocation().file)
            {
                case Board.G:
                    piece = board.squares[Board.F][GetLocation().rank].piece;
                    //if(piece)
                    piece.MoveBack(board.squares[Board.H][GetLocation().rank]);
                    break;
                case Board.C:
                    piece = board.squares[Board.D][GetLocation().rank].piece;
                    //if(piece)
                    piece.MoveBack(board.squares[Board.A][GetLocation().rank]);
                    break;
                default:
                    break;
            }
        }
        super.MoveBack(square);
    }

    boolean GetRookMoved() {
        return _rookMoved;
    }

    Movement GetRookMovement() {
        return _rookMovement;
    }

    @Override
    List<ChessSquare> GetNextMoves(final boolean checkKing)
    {
        List<ChessSquare> nextMoves = new ArrayList<>(10);

        final byte x = GetLocation().file;
        final byte y = GetLocation().rank;

        if(x<7)
            AppendSquare(x+1,y,nextMoves,checkKing);

        if(y<7)
            AppendSquare(x,y+1,nextMoves,checkKing);

        if(x>0)
            AppendSquare(x-1,y,nextMoves,checkKing);

        if(y>0)
            AppendSquare(x,y-1,nextMoves,checkKing);

        if(x<7 && y<7)
            AppendSquare(x+1,y+1,nextMoves,checkKing);

        if(x<7 && y>0)
            AppendSquare(x+1,y-1,nextMoves,checkKing);

        if(x>0 && y>0)
            AppendSquare(x-1,y-1,nextMoves,checkKing);

        if(x>0 && y<7)
            AppendSquare(x-1,y+1,nextMoves,checkKing);

        if(GetMoveCount() == 0 && GetThreatCount() == 0)
        {
            if(board.squares[x+1][y].piece == null && board.GetThreatCount(board.squares[x+1][y],color, false)== 0
                && board.squares[x+2][y].piece == null && board.GetThreatCount(board.squares[x+2][y],color, false)== 0)
            {
                Piece piece = board.squares[x+3][y].piece;
                if(piece != null && piece instanceof Rook && piece.GetMoveCount()==0 && piece.GetThreatCount() == 0)
                    nextMoves.add(board.squares[x+2][y]);
            }

            if(board.squares[x-1][y].piece == null && board.GetThreatCount(board.squares[x-1][y],color, false) == 0
                && board.squares[x-2][y].piece == null && board.GetThreatCount(board.squares[x-2][y],color, false) == 0
                && board.squares[x-3][y].piece == null && board.GetThreatCount(board.squares[x-3][y],color, false) == 0)
            {
                Piece piece = board.squares[x-4][y].piece;
                if(piece != null && piece instanceof Rook && piece.GetMoveCount()==0 && piece.GetThreatCount()==0)
                    nextMoves.add(board.squares[x-2][y]);
            }
        }

        return nextMoves;
    }
}
