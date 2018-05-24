import java.util.concurrent.ThreadLocalRandom;

public class board {
  int rows;
  int cols;
  int free;           // number of free positions on the field
  int score;
  tile[][] field;


  // Constructor for new board
  public board(int rows, int cols) {
    this.score = 0;
    this.rows = rows;
    this.cols = cols;
    this.free = cols * rows;

    tile[][] field;
    field = new tile[rows][];
    for (int i = 0; i < rows; ++i) {
      field[i] = new tile[cols];
      for (int j = 0; j < cols; ++j) {
        field[i][j] = new tile();
      }
    }

    this.field = field;

    // Add two tiles
    this.addTile();
    this.addTile();
  }


  // Return number of rows
  public int getRows() {
    return this.rows;
  }


  // Return number of columns
  public int getCols() {
    return this.cols;
  }


  // Returns the tile at position (row,col)
  public tile getTile(int row, int col) {
    return this.field[row][col];
  }


  // Returns value at position (i,j)
  public int getValue(int row, int col) {
    return this.field[row][col].getValue();
  }


  // Returns true if position (row,col) is full, false if empty
  public boolean getFull(int row, int col) {
    return this.field[row][col].getFull();
  }


  // Sets the value at position (row,col) to val
  public void setValue(int row, int col, int val){
    this.field[row][col].setValue(val);
  }


  // Marks the position (row,col) as full (true) or empty (false)
  public void setFull(int row, int col, boolean bool){
    this.field[row][col].setFull(bool);
  }


  // Adds a new tile with value 2 or 4 at a free position
  // Does nothing if board is full
  public void addTile() {
    if (this.free == 0) {
      return;
    }

    // Generate a random integer in [0, this.free)
    int n = ThreadLocalRandom.current().nextInt(0, this.free);

    // Find position (i,j) of the correct tile
    int current = 0;
    int x = 0;
    int y = 0;
    for (int i = 0; i < this.rows && current <= n; ++i) {
      for (int j = 0; j < this.cols && current <= n; ++j) {
        // Increase counter if current tile is empty
        if ( !(this.getFull(i, j)) ) {
          current += 1;
          x = i;
          y = j;
        }
      }
    }

    // if n != 0 then i gets increased
    // in the last iteration of the loops


    // Generate a random integer k in {1,2} set n = 2^k
    n = 1 << ThreadLocalRandom.current().nextInt(1, 3);

    // Set value of tile (i,j) to n
    this.setValue(x, y, n);
    this.setFull(x, y, true);

    // Decrease free counter
    this.free -= 1;
  }


  // Print current state of the board
  public void print() {
    System.out.print("\033\143");     // Clear screen
    System.out.print("Current score: " + this.score + "\n\n");

    String h = " ------";
    String v = "|      ";

    for (int j = 0; j < this.rows; ++j) {
      for (int i = 0; i < this.cols; ++i) {
        System.out.print(h);
      }
      System.out.print("\n");


      for (int i = 0; i < this.cols; ++i) {
        System.out.print(v);
      }
      System.out.print("|\n");


      for (int i = 0; i < this.cols; ++i) {
        // If tile is full print value, else empty space
        String s;
        boolean b = this.field[j][i].getFull();
        if (b) {
          s = " " + field[j][i].getValue() + " ";
        }
        else {
          s = " ";
        }
        while ( s.length() < 6 ) {
          s = " " + s;
        }
        System.out.print("|" + s);
      }
      System.out.print("|\n");


      for (int i = 0; i < this.cols; ++i) {
        System.out.print(v);
      }
      System.out.print("|\n");
    }

    for (int i = 0; i < this.cols; ++i) {
      System.out.print(h);
    }

    System.out.println( "\n\nControls:" );
    System.out.println( "  q   (quit)\n" );
    System.out.println( "  w   (up)" );
    System.out.println( "  a   (left)" );
    System.out.println( "  s   (down)" );
    System.out.println( "  d   (right)\n" );
  }


  // Moves the tile in position (x1, y1) to position (x2, y2)
  public void moveTile(int x1, int y1, int x2, int y2) {
    int val = getValue(x1, y1);
    boolean full = getFull(x1, y1);

    setValue(x2, y2, val);
    setFull(x2, y2, full);

    setValue(x1, y1, 0);
    setFull(x1, y1, false);
  }


  // Merges tiles in position (x1, y1) and (x2, y2)
  // Produces merged tile at (x1,y1) and empty tile at (x2,y2)
  public void mergeTiles(int x1, int y1, int x2, int y2) {
    int val1 = getValue(x1, y1);
    boolean full1 = getFull(x1, y1);

    int val2 = getValue(x2, y2);
    boolean full2 = getFull(x2, y2);

    if ( !(val1 == val2 && full1 == true && full2 == true) ) {
      System.err.println("Could not merge tiles!");
      System.exit(1);
    }

    setValue(x1, y1, 2*val1);

    setValue(x2, y2, 0);
    setFull(x2, y2, false);

    this.score += 2*val1;
    this.free += 1;
  }


  // Perform movement of tile t from (x,y) to (x+dx, y+dy) if possible
  // Handle merging, borders, no move possible
  // return 1 if it moved tiles, 0 otherwise
  public int move(int x, int y, int dx, int dy) {
    // If tile is empty, do nothing
    if (!getFull(x, y)) {
      return 0;
    }

    // Tile is at border of grid
    boolean b = (dx == -1 && x == 0) || (dx == 1 && x == this.rows - 1)
              || (dy == -1 && y == 0) || (dy == 1 && y == this.cols - 1);
    if (b) {
        return 0;
    }

    // Not at border
    if (!getFull(x + dx, y + dy)) {
      // recursively move tile up as far as possible
      moveTile(x, y, x + dx, y +dy);
      move(x + dx, y + dy, dx, dy);
      return 1;
    }
    if ( getValue(x, y) == getValue(x + dx, y +dy) ) {
      mergeTiles(x + dx, y + dy, x, y);
      return 1;
    }
    // Should not reach this point
    return 0;
  }


  // returns 0 if no tiles moves, otherwise positive integer
  public int moveUp(){
    int movecounter = 0;
    for (int i = 1; i < this.rows; ++i) {
      for (int j = 0; j < this.cols; ++j) {
        movecounter += move(i, j, -1, 0);
      }
    }
    return movecounter;
  }


  // returns 0 if no tiles moves, otherwise positive integer
  public int moveDown(){
    int movecounter = 0;
    for (int i = this.rows - 1; i >= 0; --i) {
      for (int j = 0; j < this.cols; ++j) {
        movecounter += move(i, j, 1, 0);
      }
    }
    return movecounter;
  }


  // returns 0 if no tiles moves, otherwise positive integer
  public int moveLeft(){
    int movecounter = 0;
    for (int j = 1; j < this.cols; ++j) {
      for (int i = 0; i < this.rows; ++i) {
        movecounter += move(i, j, 0, -1);
      }
    }
    return movecounter;
  }


  // returns 0 if no tiles moves, otherwise positive integer
  public int moveRight(){
    int movecounter = 0;
    for (int j = this.cols - 1; j >= 0; --j) {
      for (int i = 0; i < this.rows; ++i) {
        movecounter += move(i, j, 0, 1);
      }
    }
    return movecounter;
  }


  // returns 0 if no tiles moves, otherwise positive integer
  public void moveQuit(){
    System.out.println("Quitting.\n");
    System.exit(0);
  }
}
