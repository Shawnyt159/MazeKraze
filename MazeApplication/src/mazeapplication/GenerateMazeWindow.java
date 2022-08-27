package mazeapplication;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GenerateMazeWindow {

	private JFrame frmMazekrazeMazeGenerator;
	private JLabel selectedDifficulty = null;
	private boolean canGenerateMaze = false;
	private JComboBox<String> comboBox;
	
	public GenerateMazeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMazekrazeMazeGenerator = new JFrame();
		frmMazekrazeMazeGenerator.setResizable(false);
		frmMazekrazeMazeGenerator.setIconImage(Toolkit.getDefaultToolkit().getImage(GenerateMazeWindow.class.getResource("/images/logo.png")));
		frmMazekrazeMazeGenerator.setTitle("Maze Generator");
		frmMazekrazeMazeGenerator.setBounds(100, 100, 450, 261);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmMazekrazeMazeGenerator.setLocation(dim.width / 2 - frmMazekrazeMazeGenerator.getSize().width / 2, dim.height / 2 - frmMazekrazeMazeGenerator.getSize().height / 2);
		frmMazekrazeMazeGenerator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMazekrazeMazeGenerator.getContentPane().setLayout(null);
		frmMazekrazeMazeGenerator.getContentPane().setBackground(new Color(120,177,199));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		titlePanel.setBackground(new Color(65, 105, 225));
		titlePanel.setBounds(0, 0, 438, 55);
		frmMazekrazeMazeGenerator.getContentPane().add(titlePanel);
		titlePanel.setLayout(null);
		
		JLabel titleLabel = new JLabel("Generate a Maze");
		titleLabel.setBounds(135, 11, 169, 25);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titlePanel.add(titleLabel);
		
		BevelBorder raisedBorder = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
		BevelBorder loweredBorder = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
		
		JLabel easyLabelButton = new JLabel("Easy");
		easyLabelButton.setBorder(raisedBorder);
		easyLabelButton.setOpaque(true);
		easyLabelButton.setBackground(new Color(255, 215, 0));
		easyLabelButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		easyLabelButton.setHorizontalAlignment(SwingConstants.CENTER);
		easyLabelButton.setBounds(10, 66, 90, 44);
		frmMazekrazeMazeGenerator.getContentPane().add(easyLabelButton);
		
		JLabel mediumLabelButton = new JLabel("Medium");
		mediumLabelButton.setBorder(raisedBorder);
		mediumLabelButton.setOpaque(true);
		mediumLabelButton.setBackground(new Color(255, 215, 0));
		mediumLabelButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		mediumLabelButton.setHorizontalAlignment(SwingConstants.CENTER);
		mediumLabelButton.setBounds(176, 66, 90, 44);
		frmMazekrazeMazeGenerator.getContentPane().add(mediumLabelButton);
		
		JLabel hardLabelButton = new JLabel("Hard");
		hardLabelButton.setBorder(raisedBorder);
		hardLabelButton.setOpaque(true);
		hardLabelButton.setBackground(new Color(255, 215, 0));
		hardLabelButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		hardLabelButton.setHorizontalAlignment(SwingConstants.CENTER);
		hardLabelButton.setBounds(338, 66, 90, 44);
		frmMazekrazeMazeGenerator.getContentPane().add(hardLabelButton);
		
		JLabel[] mazeGenerationButtonArray = new JLabel[] {easyLabelButton, mediumLabelButton, hardLabelButton};
		
		
		MouseListener labelButtonListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel jLabelComponent = (JLabel) e.getComponent();
				for(int i = 0; i < mazeGenerationButtonArray.length; i++) {
					if(!jLabelComponent.equals(mazeGenerationButtonArray[i])) {
						mazeGenerationButtonArray[i].setBackground(new Color(255,215,0));
					}
				}
				selectedDifficulty = jLabelComponent;
				jLabelComponent.setBackground(Color.yellow);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JLabel jLabelComponent = (JLabel) e.getComponent();
				jLabelComponent.setBorder(loweredBorder);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JLabel jLabelComponent = (JLabel) e.getComponent();
				jLabelComponent.setBorder(raisedBorder);
			}
			
		};
		easyLabelButton.addMouseListener(labelButtonListener);
		mediumLabelButton.addMouseListener(labelButtonListener);
		hardLabelButton.addMouseListener(labelButtonListener);
		
		Color buttonColor = new Color(170, 204, 0);
		
		JButton generateMazeButton = new JButton("Generate");
		generateMazeButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		generateMazeButton.setBounds(308, 165, 120, 57);
		generateMazeButton.setBackground(buttonColor);
		frmMazekrazeMazeGenerator.getContentPane().add(generateMazeButton);
		
		JTextArea explinationArea = new JTextArea();
		explinationArea.setEditable(false);
		explinationArea.setText("The algorithm used to design these mazes\nallows for anywhere in the maze to be\naccessed by any other spot in the maze.\nOnce it is generated, place the start\nand end spots anywhere you\nlike and the maze is solvable.");
		explinationArea.setBounds(0, 0, 246, 101);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 122, 232, 100);
		frmMazekrazeMazeGenerator.getContentPane().add(scrollPane);
		scrollPane.add(explinationArea);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Black", "Red", "Yellow", "Orange",
				"Pink", "Purple", "Blue", "Gray", "Dark Gray", "Green", "Brown", "Lavender", "Navy Blue", "Army Green",
				"Plum", "Turquoise", "Maroon", "Storm", "Salmon"}));
		comboBox.setBounds(324, 132, 100, 22);
		frmMazekrazeMazeGenerator.getContentPane().add(comboBox);
		
		JLabel lineColorLabel = new JLabel("Line Color:");
		lineColorLabel.setBounds(248, 136, 66, 14);
		frmMazekrazeMazeGenerator.getContentPane().add(lineColorLabel);
		generateMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedDifficulty == null) {
					JOptionPane.showMessageDialog(null, "No difficulty selected!");
				}
				else {
					canGenerateMaze = false;
					MazeDesignMainGUI.ClearCurrentMaze();
					if(canGenerateMaze == true) {
						if(selectedDifficulty.equals(easyLabelButton)) {
							GenerateMaze.GenerateEasyMaze(getColorFromColorComboBox());
						}
						else if(selectedDifficulty.equals(mediumLabelButton)) {
							GenerateMaze.GenerateMediumMaze(getColorFromColorComboBox());
						}
						else if(selectedDifficulty.equals(hardLabelButton)) {
							GenerateMaze.GenerateHardMaze(getColorFromColorComboBox());
						}
					}
				}
			}
		});
		
		frmMazekrazeMazeGenerator.setVisible(true);
	}
	
	public void setVisible(boolean visible) {
		frmMazekrazeMazeGenerator.setVisible(visible);
	}
	
	public void setCanGenerateMaze(boolean canGenerateMaze) {
		this.canGenerateMaze = canGenerateMaze;
	}
	
	private Color getColorFromColorComboBox() {
		String color = (String) comboBox.getSelectedItem();
		if (color.equals("Black")) {
			return Color.black;
		} else if (color.equals("Red")) {
			return Color.red;
		} else if (color.equals("Yellow")) {
			return Color.yellow;
		} else if (color.equals("Orange")) {
			return Color.orange;
		} else if (color.equals("Pink")) {
			return Color.pink;
		} else if (color.equals("Purple")) {
			return new Color(128, 0, 128);
		} else if (color.equals("Blue")) {
			return Color.blue;
		} else if (color.equals("Gray")) {
			return Color.gray;
		} else if (color.equals("Green")) {
			return Color.green;
		} else if (color.equals("Brown")) {
			return new Color(139, 69, 19);
		} else if (color.equals("Lavender")) {
			return new Color(204, 204, 255);
		} else if (color.equals("Navy Blue")) {
			return new Color(0, 0, 153);
		} else if (color.equals("Army Green")) {
			return new Color(0, 102, 0);
		} else if (color.equals("Plum")) {
			return new Color(153, 51, 153);
		} else if (color.equals("Turquoise")) {
			return new Color(102, 255, 255);
		} else if (color.equals("Maroon")) {
			return new Color(153, 0, 51);
		} else if (color.equals("Storm")) {
			return new Color(102, 102, 153);
		} else if (color.equals("Salmon")) {
			return new Color(250, 128, 114);
		} else if (color.equals("Dark Gray")) {
			return Color.darkGray;
		} else {
			return Color.black;
		}
	}
}
