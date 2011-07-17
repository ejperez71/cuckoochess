/*
    DroidFish - An Android chess program.
    Copyright (C) 2011  Peter Österlund, peterosterlund2@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.petero.droidfish.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.petero.droidfish.BookOptions;
import org.petero.droidfish.engine.DroidBook.BookEntry;
import org.petero.droidfish.gamelogic.ChessParseError;
import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.Piece;
import org.petero.droidfish.gamelogic.Position;
import org.petero.droidfish.gamelogic.TextIO;
import org.petero.droidfish.gamelogic.UndoInfo;

public class InternalBook implements IOpeningBook {
    private BookOptions options = new BookOptions();

    private static Map<Long, List<BookEntry>> bookMap;
    private static int numBookMoves = -1;

    InternalBook() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                initInternalBook();
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    
    static boolean canHandle(String filename) {
        return filename.length() == 0;
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public List<BookEntry> getBookEntries(Position pos) {
        initInternalBook();
        List<BookEntry> ents = bookMap.get(pos.zobristHash());
        List<BookEntry> ret = new ArrayList<BookEntry>();
        for (BookEntry be : ents) {
            BookEntry be2 = new BookEntry(be.move);
            double w = be.weight;
            switch (options.randomness) {
            case BookOptions.RANDOM_LOW:
                break;
            case BookOptions.RANDOM_MEDIUM:
                w = Math.sqrt(w) * 100 + 1;
                break;
            case BookOptions.RANDOM_HIGH:
                w = 1;
                break;
            }
            be2.weight = w;
            ret.add(be2);
        }
        return ret;
    }

    @Override
    public void setOptions(BookOptions options) {
        this.options = new BookOptions(options);
    }

    private synchronized final void initInternalBook() {
        if (numBookMoves >= 0)
            return;
        long t0 = System.currentTimeMillis();
        bookMap = new HashMap<Long, List<BookEntry>>();
        numBookMoves = 0;
        try {
            InputStream inStream = getClass().getResourceAsStream("/book.bin");
            List<Byte> buf = new ArrayList<Byte>(8192);
            byte[] tmpBuf = new byte[1024];
            while (true) {
                int len = inStream.read(tmpBuf);
                if (len <= 0) break;
                for (int i = 0; i < len; i++)
                    buf.add(tmpBuf[i]);
            }
            inStream.close();
            Position startPos = TextIO.readFEN(TextIO.startPosFEN);
            Position pos = new Position(startPos);
            UndoInfo ui = new UndoInfo();
            int len = buf.size();
            for (int i = 0; i < len; i += 2) {
                int b0 = buf.get(i); if (b0 < 0) b0 += 256;
                int b1 = buf.get(i+1); if (b1 < 0) b1 += 256;
                int move = (b0 << 8) + b1;
                if (move == 0) {
                    pos = new Position(startPos);
                } else {
                    boolean bad = ((move >> 15) & 1) != 0;
                    int prom = (move >> 12) & 7;
                    Move m = new Move(move & 63, (move >> 6) & 63,
                                      promToPiece(prom, pos.whiteMove));
                    if (!bad)
                        addToBook(pos, m);
                    pos.makeMove(m, ui);
                }
            }
        } catch (ChessParseError ex) {
            throw new RuntimeException();
        } catch (IOException ex) {
            System.out.println("Can't read opening book resource");
            throw new RuntimeException();
        }
        {
            long t1 = System.currentTimeMillis();
            System.out.printf("Book moves:%d (parse time:%.3f)%n", numBookMoves,
                    (t1 - t0) / 1000.0);
        }
    }

    
    /** Add a move to a position in the opening book. */
    private final void addToBook(Position pos, Move moveToAdd) {
        List<BookEntry> ent = bookMap.get(pos.zobristHash());
        if (ent == null) {
            ent = new ArrayList<BookEntry>();
            bookMap.put(pos.zobristHash(), ent);
        }
        for (int i = 0; i < ent.size(); i++) {
            BookEntry be = ent.get(i);
            if (be.move.equals(moveToAdd)) {
                be.weight++;
                return;
            }
        }
        BookEntry be = new BookEntry(moveToAdd);
        ent.add(be);
        numBookMoves++;
    }

    private static int promToPiece(int prom, boolean whiteMove) {
        switch (prom) {
        case 1: return whiteMove ? Piece.WQUEEN : Piece.BQUEEN;
        case 2: return whiteMove ? Piece.WROOK  : Piece.BROOK;
        case 3: return whiteMove ? Piece.WBISHOP : Piece.BBISHOP;
        case 4: return whiteMove ? Piece.WKNIGHT : Piece.BKNIGHT;
        default: return Piece.EMPTY;
        }
    }
}
