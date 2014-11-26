package io.github.celebes.sudoku.history;

public class Move {
	private int number;
	private int column;
	private int row;
	private int value;
	
	public Move(int column, int row, int value) {
		super();
		this.column = column;
		this.row = row;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "value [" + value + "] at [" + column + "," + row + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		
		final Move other = (Move)obj;
		
		if(this.column != other.column) {
			return false;
		}
		
		if(this.row != other.row) {
			return false;
		}
		
		if(this.value != other.value) {
			return false;
		}
		
		return true;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
