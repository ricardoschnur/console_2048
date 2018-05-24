public class tile {
  int val;
  boolean full;

  // Constructor for empty tile
  public tile() {
    this.val = 0;
    this.full = false;
  }

  // Constructor for non-empty tile
  public tile(int val) {
    this.val = val;
    this.full = true;
  }

  // Constructor for arbitrary tile
  public tile(int val, boolean full) {
    this.val = val;
    this.full = full;
  }

  // Returns vale of the tile
  public int getValue() {
    return this.val;
  }

  // Returns true if tile is full, false otherwise
  public boolean getFull() {
    return this.full;
  }

  // Sets vale of the tile
  public void setValue(int val) {
    this.val = val;
  }

  // Sets boolean full of the tile to bool
  public void setFull(boolean bool) {
    this.full = bool;
  }
}
