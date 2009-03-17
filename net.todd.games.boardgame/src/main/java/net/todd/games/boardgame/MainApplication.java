package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MainApplication {
	private final JPanel mainPanel;

	public MainApplication(String title) {
		JFrame frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(1000, 800);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public void createGame(Canvas3D canvas3D, SimpleUniverse su, GameEngine gameEngine) {
		mainPanel.add(canvas3D, BorderLayout.CENTER);

		gameEngine.createScene(su, canvas3D);
		gameEngine.createCamera(su, canvas3D);
	}
}
