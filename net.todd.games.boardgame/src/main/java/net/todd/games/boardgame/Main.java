package net.todd.games.boardgame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		JFrame frame = new JFrame();
		Container c = new Container();
		c.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(new Label("This is the beginning..."), BorderLayout.CENTER);
		c.add(mainPanel, BorderLayout.CENTER);
		frame.getContentPane().add(c);
		frame.pack();
		frame.setVisible(true);
	}
}
