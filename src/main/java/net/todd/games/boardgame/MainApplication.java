package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class MainApplication {
	private final IGameLauncher gameLauncher;
	private final String title;

	private JFrame frame;
	private final IUniverse universe;

	public MainApplication(String title, IGameLauncher gameLauncher) {
		this.title = title;
		this.gameLauncher = gameLauncher;
		universe = new UniverseProvider().createUniverse();
	}

	private void createFullScreenFrame() {
		closePreviousFrame();

		frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setUndecorated(true);

		universe.getCanvas().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					createWindowFrame();
				}
			}

		});
		frame.getContentPane().add(universe.getCanvas(), BorderLayout.CENTER);

		frame.setVisible(true);
	}

	private void createWindowFrame() {
		closePreviousFrame();

		frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.setSize(1000, 800);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int topLeftXCoordinate = (screenSize.width / 2) - (1000 / 2);
		int topLeftYCoordinate = (screenSize.height / 2) - (800 / 2);
		frame.setLocation(topLeftXCoordinate, topLeftYCoordinate);

		universe.getCanvas().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					createFullScreenFrame();
				}
			}

		});
		frame.getContentPane().add(universe.getCanvas(), BorderLayout.CENTER);

		frame.setVisible(true);
	}

	private void closePreviousFrame() {
		if (frame != null) {
			for (WindowListener windowListener : frame.getWindowListeners()) {
				frame.removeWindowListener(windowListener);
			}
			for (KeyListener keyListener : universe.getCanvas().getKeyListeners()) {
				universe.getCanvas().removeKeyListener(keyListener);
			}
			frame.setVisible(false);
			frame.dispose();
		}
	}

	public void start() {
		createWindowFrame();
		gameLauncher.launchGame();
	}
}
