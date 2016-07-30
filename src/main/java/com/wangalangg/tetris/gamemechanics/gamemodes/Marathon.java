package com.wangalangg.tetris.gamemechanics.gamemodes;

import javafx.scene.text.Text;

public class Marathon implements GameMode {

	private int points, level, linesLeft;
	private Text pointsDisplay, levelDisplay, linesLeftDisplay;

	public Marathon(Text pointsDisplay, Text levelDisplay, Text linesLeftDisplay) {
		this.pointsDisplay = pointsDisplay;
		this.levelDisplay = levelDisplay;
		this.linesLeftDisplay = linesLeftDisplay;
		initValues();
	}

	private void initValues() {
		points = 0;
		level = 1;
		linesLeft = 5;
	}

	public void update() {
		linesLeftDisplay.setText(Integer.toString(linesLeft));
		pointsDisplay.setText(Integer.toString(points));
		levelDisplay.setText(Integer.toString(level));
	}

	public int getLevel() {
		return level;
	}

	public void softDrop() {
		points++;
	}

	public void hardDrop(int lines) {
		points += lines * 2;
	}

	public void single() {
		linesLeft--;
		points += 100 * level;
		checkLevelUp();
	}

	public void duhble() {
		linesLeft -= 3;
		points += 300 * level;
		checkLevelUp();
	}

	public void triple() {
		linesLeft -= 4;
		points += 500 * level;
		checkLevelUp();
	}

	public void tetris() {
		linesLeft -= 5;
		points += 800 * level;
		checkLevelUp();
	}

	// Tspin that clears no lines
	public void tSpin() {
		linesLeft -= 4;
		points += 400 * level;
		checkLevelUp();
	}

	public void tSpinSingle() {
		linesLeft -= 8;
		points += 800 * level;
		checkLevelUp();
	}

	public void tSpinDouble() {
		linesLeft -= 12;
		points += 1200 * level;
		checkLevelUp();
	}

	public void tSpinTriple() {
		linesLeft -= 16;
		points += 1600 * level;
		checkLevelUp();
	}

	public void tSpinMini() {
		linesLeft -= 1;
		points += 100 * level;
		checkLevelUp();
	}

	public void tSpinMiniSingle() {
		linesLeft -= 2;
		points += 200 * level;
		checkLevelUp();
	}

	public void backToBack() {
		// todo stub
	}

	private void checkLevelUp() {
		if (linesLeft <= 0) {
			level++;
			linesLeft = level * 5;
		}
	}

	public void reset() {
		initValues();
		update();
	}

	public static class MarathonFactory {

		private Text pointsDisplay, levelDisplay, linesLeftDisplay;

		public MarathonFactory() {
		}

		public MarathonFactory points(Text points) {
			pointsDisplay = points;
			return this;
		}

		public MarathonFactory level(Text level) {
			levelDisplay = level;
			return this;
		}

		public MarathonFactory linesLeft(Text linesLeft) {
			linesLeftDisplay = linesLeft;
			return this;
		}

		public Marathon build() {
			return new Marathon(pointsDisplay, levelDisplay, linesLeftDisplay);
		}
	}
}