package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MainApplication {
	private final SimpleUniverse universe;
	private final Canvas3D canvas3D;
	private final JFrame frame;

	public MainApplication(String title, UniverseGenerator universeGenerator) {
		frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(1000, 800);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		canvas3D = new Canvas3D(config);
		universe = universeGenerator.generateUniverse(canvas3D);

		mainPanel.add(canvas3D, BorderLayout.CENTER);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	public void start() {
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		frame.setVisible(true);
	}

	public void createGame(IGameEngine gameEngine) {
		gameEngine.createScene(canvas3D);
		gameEngine.createCamera(universe, canvas3D);

		universe.addBranchGraph(gameEngine.getBranchGroup());
	}
}