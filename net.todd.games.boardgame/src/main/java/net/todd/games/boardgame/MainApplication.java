package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MainApplication {
	private final IUniverse universe;
	private final JFrame frame;
	private final IGameLauncher gameLauncher;

	public MainApplication(String title, IGameLauncher gameLauncher) {
		this.gameLauncher = gameLauncher;
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

		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		mainPanel.add(canvas3D, BorderLayout.CENTER);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		universe = new UniverseFactory(canvas3D).createUniverse();
	}

	public void start() {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameEngineFactory gameEngineFactory = new GameEngineFactory();
		gameLauncher.launchGame(universe, branchGroupFactory, gameEngineFactory);
		frame.setVisible(true);
	}
}
