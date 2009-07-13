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

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MainApplication {
	private final IGameLauncher gameLauncher;
	private final String title;

	private final Canvas3D canvas3D;
	private final IUniverse universe;

	private JFrame frame;

	public MainApplication(String title, IGameLauncher gameLauncher) {
		this.title = title;
		this.gameLauncher = gameLauncher;

		canvas3D = createCanvas();
		universe = new UniverseFactory(canvas3D).createUniverse();

		createFullScreenFrame();
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

		canvas3D.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					createWindowFrame();
				}
			}

		});
		frame.getContentPane().add(canvas3D, BorderLayout.CENTER);

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

		canvas3D.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 27) {
					createFullScreenFrame();
				}
			}

		});
		frame.getContentPane().add(canvas3D, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	private void closePreviousFrame() {
		if (frame != null) {
			for (WindowListener windowListener : frame.getWindowListeners()) {
				frame.removeWindowListener(windowListener);
			}
			for (KeyListener keyListener : canvas3D.getKeyListeners()) {
				canvas3D.removeKeyListener(keyListener);
			}
			frame.setVisible(false);
			frame.dispose();
		}
	}

	private Canvas3D createCanvas() {
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		return canvas3D;
	}

	public void start() {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameEngineFactory gameEngineFactory = new GameEngineFactory();
		gameLauncher.launchGame(universe, branchGroupFactory, gameEngineFactory);
	}
}
