package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MainApplication {
	private final IUniverse universe;
	private final Canvas3D canvas3D;
	private final JFrame frame;

	public MainApplication(String title) {
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
		universe = new UniverseFactory(canvas3D).createUniverse();

		mainPanel.add(canvas3D, BorderLayout.CENTER);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	public void start() {
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		frame.setVisible(true);
	}

	public void createGame() {
		BranchGroup branchGroup = new BranchGroup();
		IBranchGroup bg = new BranchGroupAdapter(branchGroup);
		ISceneGenerator gameGridGenerator = new GameGridGenerator();
		IPieceGenerator pieceGenerator = new PieceGenerator();
		ICameraGenerator cameraGenerator = new CameraGenerator();

		GameEngine gameEngine = new GameEngine(bg, gameGridGenerator, pieceGenerator,
				cameraGenerator);

		IPicker picker = new Picker(universe, branchGroup);

		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		universe.addBranchGraph(bg);
	}
}
