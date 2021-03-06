package com.wangalangg.tetris.gamemechanics.ui;

import com.wangalangg.tetris.gamemechanics.blocks.BlockManager;
import com.wangalangg.tetris.gamemechanics.gamemodes.GameMode;
import com.wangalangg.tetris.gamemechanics.matrix.Matrix;
import com.wangalangg.tetris.gamemechanics.matrix.VisualMatrix;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Shows color and updates images for the player.
 */
public class UIHandler {

	private GridPane tetrisGrid;
	private ImageView holdBlock, gameOverMask;
	private ImageView[] nextBlocks;
	private ImageLoader imageLoader;
	private VisualMatrix matrix;
	private BlockManager blockManager;
	private GameMode gameMode;
	private boolean isMainPlayer;

	// Constructor for other player matrix UI's
	public UIHandler(GridPane tetrisGrid, ImageView gameOverMask, VisualMatrix matrix) {
		this(tetrisGrid, null, null, null, matrix, gameOverMask, null, null);
		isMainPlayer = false;
	}

	// Constructor for main player ui (UIPackage version)
	public UIHandler(UIPackage uiPackage, VisualMatrix matrix, BlockManager blockManager,
					 GameMode gameMode) {
		this(uiPackage.p1Grid, uiPackage.holdBlockImage, uiPackage.images,
				uiPackage.nextBlocksImages, matrix, uiPackage.p1GameOverMask, blockManager, gameMode);
	}

	// Constructor for main player ui. (UI with the hold block image previews and such)
	public UIHandler(GridPane tetrisGrid, ImageView holdBlock, ImageLoader imageLoader,
					 ImageView[] nextBlocks, VisualMatrix matrix, ImageView gameOverMask,
					 BlockManager blockManager, GameMode gameMode) {
		this.tetrisGrid = tetrisGrid;
		this.holdBlock = holdBlock;
		this.nextBlocks = nextBlocks;
		this.imageLoader = imageLoader;
		this.gameOverMask = gameOverMask;
		this.matrix = matrix;
		this.blockManager = blockManager;
		this.gameMode = gameMode;
		isMainPlayer = true;

		// Create Tetris area
		for (int row = 0; row < Matrix.HEIGHT; row++) {
			for (int col = 0; col < Matrix.WIDTH; col++) {
				tetrisGrid.add(new Rectangle(30, 30), col, row);
			}
		}
	}

	public void update() {
		if (isMainPlayer) {
			updateImageViews();
			gameMode.update();
		}
		updateGridColors();
	}

	public void updateFromSocket() {
		updateGridColorsFromSocket();
	}

	public void gameOver() {
		if (gameOverMask != null) {
			gameOverMask.setVisible(true);
		}
	}

	private Color getColorFromInt(int color) {
		switch (color) {
			case -1:
				return Color.DARKGRAY;
			case 0:
				return Color.LIGHTGRAY;
			case 1:
				return Color.DEEPSKYBLUE;
			case 2:
				return Color.DARKBLUE;
			case 3:
				return Color.ORANGE;
			case 4:
				return Color.YELLOW;
			case 5:
				return Color.GREEN;
			case 6:
				return Color.PURPLE;
			case 7:
				return Color.ORANGERED;
			default:
				throw new IllegalStateException("Controller: Weird color number - " + color);
		}
	}

	private void updateImageViews() {
		if (blockManager.getHoldBlock() != null) {
			holdBlock.setImage(imageLoader.getImage(blockManager.getHoldBlock()));
		} else {
			holdBlock.setImage(null);
		}
		for (int i = 0; i < 5; i++) {
			nextBlocks[i].setImage(imageLoader.getImage(blockManager.getQueuedBlock(i)));
		}
	}

	private void updateGridColors() {
		for (int row = 0; row < Matrix.HEIGHT; row++) {
			for (int col = 0; col < Matrix.WIDTH; col++) {
				int color = matrix.getVisualActiveValue(row, col);
				Rectangle rectangle = getRectangleFromGrid(col, row);
				if (rectangle != null) {
					rectangle.setFill(getColorFromInt(color));
				}
			}
		}
	}

	private void updateGridColorsFromSocket() {
		for (int row = 0; row < Matrix.HEIGHT; row++) {
			for (int col = 0; col < Matrix.WIDTH; col++) {
				int color = matrix.getVisualActiveValue(row, col);
				Rectangle rectangle = getRectangleFromGrid(col, row);
				if (rectangle != null) {
					Platform.runLater(() -> {
						rectangle.setFill(getColorFromInt(color));
					});
				}
			}
		}
	}

	private Rectangle getRectangleFromGrid(int col, int row) {
		for (Node node : tetrisGrid.getChildren()) {
			if (node instanceof Rectangle &&
					GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return (Rectangle) node;
			}
		}
		return null;
	}
}
