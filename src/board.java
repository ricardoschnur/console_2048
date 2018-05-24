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
    System.out.print("\n");
  }
}
