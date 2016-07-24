package com.wangalangg.tetris.controllers;

import com.wangalangg.tetris.gamemechanics.SPGame;
import com.wangalangg.tetris.gamemechanics.ui.ImageLoader;
import com.wangalangg.tetris.ui.UIManager;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SPController implements ScreenChangeable {

	public static final boolean DEBUG = false;
	protected SPGame SPGame;
	protected UIManager uiManager;
	@FXML protected GridPane tetrisGrid;
	@FXML protected ImageView holdBlock, block1, block2, block3, block4, block5;
	@FXML protected Text points, level, linesLeft;

	public SPController() {
	}

	@FXML
	public void initialize() {
		ImageView[] blocks = {block1, block2, block3, block4, block5};

		SPGame = new SPGame(tetrisGrid, holdBlock, new ImageLoader(), blocks, points, level, linesLeft);
	}

	@Override
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	public void setupKeyboardInput(Scene scene) {
		scene.setOnKeyPressed(evt -> {
			if (DEBUG) {
				System.out.println(evt.getCode());
				//printMatrices();
			}
			SPGame.onPressed(evt.getCode());
		});

		scene.setOnKeyReleased(event -> {
			SPGame.onReleased(event.getCode());
		});
	}
}