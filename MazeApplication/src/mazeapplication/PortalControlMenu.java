package mazeapplication;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.ScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;

public class PortalControlMenu {

	private JFrame frmLinkControls;
	private Panel portalsPanel;
	private LineBorder linkLabelBorder;
	private MouseListener portalMouseListener;
	private JComboBox <Character> startSpotExitPointComboBox;
	private JComboBox<Character> endSpotExitPointComboBox;
	private JButton startPortalButton;
	private JButton endPortalButton;
	private ImageIcon optionImageIcon;

	/**
	 * Create the application.
	 */
	public PortalControlMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLinkControls = new JFrame();
		frmLinkControls.setIconImage(Toolkit.getDefaultToolkit().getImage(PortalControlMenu.class.getResource("/images/logo.png")));
		frmLinkControls.setTitle("Portal Controls");
		frmLinkControls.setBounds(100, 100, 380, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmLinkControls.setLocation(dim.width / 2 - frmLinkControls.getSize().width / 2, dim.height / 2 - frmLinkControls.getSize().height / 2);
		frmLinkControls.getContentPane().setLayout(null);
		frmLinkControls.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLinkControls.setResizable(false);
		frmLinkControls.getContentPane().setBackground(new Color(120,177,199));
		frmLinkControls.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
				for(int i = 0; i < portalList.size(); i++) {
					PortalNode currentNode = portalList.get(i);
					if(currentNode.getLinkStart() != null) {
						currentNode.getLinkStart().setBorder(null);
					}
					if(currentNode.getLinkEnd() != null) {
						currentNode.getLinkEnd().setBorder(null);
					}
				}
				setDefaultForegroundColorOfPortalButtons();
				MazeDesignMainGUI.SetSelected(0);
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		/*
		 * Image Icon that is used for the menu items.
		 */
		BufferedImage optionImage = null;
		try {
			optionImage = ImageIO.read(getClass().getResource("/images/logo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image optionImg = optionImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		optionImageIcon = new ImageIcon(optionImg);
		
		Color buttonColor = new Color(170,204,0);
		linkLabelBorder = new LineBorder(Color.black, 1);
		LineBorder selectedLineBorder = new LineBorder(Color.red, 2);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 62, 169, 232);
		frmLinkControls.getContentPane().add(scrollPane);
		
		portalsPanel = new Panel();
		portalsPanel.setBounds(0, 0, 169, 232);
		scrollPane.add(portalsPanel);
		portalsPanel.setLayout(new BoxLayout(portalsPanel, BoxLayout.Y_AXIS));
		
		JLabel portalsLabel = new JLabel("Your Portals");
		portalsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		portalsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portalsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		portalsLabel.setBounds(10, 11, 169, 45);
		frmLinkControls.getContentPane().add(portalsLabel);
		
		JButton deletePortalButton = new JButton("Delete Portal");
		deletePortalButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		deletePortalButton.setBounds(10, 331, 169, 23);
		deletePortalButton.setBackground(buttonColor);
		frmLinkControls.getContentPane().add(deletePortalButton);
		
		JButton addPortalButton = new JButton("Add Portal");
		addPortalButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addPortalButton.setBounds(10, 300, 169, 23);
		addPortalButton.setBackground(buttonColor);
		frmLinkControls.getContentPane().add(addPortalButton);
		
		JPanel portalControlsPanel = new JPanel();
		portalControlsPanel.setBounds(185, 62, 169, 232);
		portalControlsPanel.setBackground(new Color(120,177,199));
		frmLinkControls.getContentPane().add(portalControlsPanel);
		portalControlsPanel.setLayout(null);
		
		endSpotExitPointComboBox = new JComboBox<Character>();
		endSpotExitPointComboBox.setEnabled(false);
		endSpotExitPointComboBox.setBounds(100, 136, 59, 22);
		endSpotExitPointComboBox.setModel(new DefaultComboBoxModel<Character>(new Character[] {
				'N', 'S', 'E', 'W'
		}));
		portalControlsPanel.add(endSpotExitPointComboBox);
		
		startSpotExitPointComboBox = new JComboBox<Character>();
		startSpotExitPointComboBox.setEnabled(false);
		startSpotExitPointComboBox.setBounds(100, 45, 59, 22);
		startSpotExitPointComboBox.setModel(new DefaultComboBoxModel<Character>(new Character[] {
				'N', 'S', 'E', 'W'
		}));
		portalControlsPanel.add(startSpotExitPointComboBox);
		
		startPortalButton = new JButton("Start Portal");
		startPortalButton.setEnabled(false);
		startPortalButton.setBounds(0, 11, 159, 23);
		startPortalButton.setBackground(buttonColor);
		portalControlsPanel.add(startPortalButton);
		
		endPortalButton = new JButton("End Portal");
		endPortalButton.setEnabled(false);
		endPortalButton.setBounds(0, 102, 159, 23);
		endPortalButton.setBackground(buttonColor);
		portalControlsPanel.add(endPortalButton);
		
		JPanel examplePanel = new JPanel();
		examplePanel.setBounds(100, 167, 50, 50);
		examplePanel.setBackground(new Color(120,177,199));
		portalControlsPanel.add(examplePanel);
		examplePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel northLabel = new JLabel("N");
		northLabel.setHorizontalAlignment(SwingConstants.CENTER);
		examplePanel.add(northLabel, BorderLayout.NORTH);
		
		JLabel westLabel = new JLabel("W");
		westLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		westLabel.setHorizontalAlignment(SwingConstants.CENTER);
		examplePanel.add(westLabel, BorderLayout.WEST);
		
		JLabel southLabel = new JLabel("S");
		southLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		southLabel.setHorizontalAlignment(SwingConstants.CENTER);
		examplePanel.add(southLabel, BorderLayout.SOUTH);
		
		JLabel eastLabel = new JLabel("E");
		eastLabel.setHorizontalAlignment(SwingConstants.CENTER);
		examplePanel.add(eastLabel, BorderLayout.EAST);
		
		JLabel startPortalExitDirectionLabel = new JLabel("Exit Direction:");
		startPortalExitDirectionLabel.setBounds(0, 45, 91, 22);
		portalControlsPanel.add(startPortalExitDirectionLabel);
		
		JLabel endPortalExitDirectionLabel = new JLabel("Exit Direction:");
		endPortalExitDirectionLabel.setBounds(0, 136, 91, 22);
		portalControlsPanel.add(endPortalExitDirectionLabel);
		
		JLabel portalControlsLabel = new JLabel("Portal Controls");
		portalControlsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		portalControlsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portalControlsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		portalControlsLabel.setBounds(189, 11, 165, 45);
		frmLinkControls.getContentPane().add(portalControlsLabel);
		
		JCheckBox portalConnectionsCheckBox = new JCheckBox("See Portal Connections");
		portalConnectionsCheckBox.setBounds(185, 301, 169, 23);
		portalConnectionsCheckBox.setBackground(buttonColor);
		frmLinkControls.getContentPane().add(portalConnectionsCheckBox);
		
		portalMouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Component c : portalsPanel.getComponents()) {
				    if (c instanceof JLabel) { 
				    	JLabel currentLabel = (JLabel) c;
				    	currentLabel.setBorder(new LineBorder(Color.black));
				    }
				}
				JLabel currentLabel = (JLabel) e.getComponent();
				currentLabel.setBorder(selectedLineBorder);
				
				startPortalButton.setEnabled(true);
				endPortalButton.setEnabled(true);
				startSpotExitPointComboBox.setEnabled(true);
				endSpotExitPointComboBox.setEnabled(true);
				
				PortalNode currentPortal = getCorrectPortalNode(((JLabel) e.getComponent()).getText());
				startSpotExitPointComboBox.setSelectedItem(currentPortal.getLinkStartExitSide());
				endSpotExitPointComboBox.setSelectedItem(currentPortal.getLinkEndExitSide());
				if(currentPortal.getLinkStart() != null) {
					currentPortal.getLinkStart().setBorder(new LineBorder(Color.green, 2));
				}
				if(currentPortal.getLinkEnd() != null) {
					currentPortal.getLinkEnd().setBorder(new LineBorder(Color.red, 2));
				}
				LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
				for(int i = 0; i < portalList.size(); i++) {
					if(!portalList.get(i).equals(currentPortal)) {
						PortalNode portal = portalList.get(i);
						if(portal.getLinkStart() != null) {
							portal.getLinkStart().setBorder(null);
						}
						if(portal.getLinkEnd() != null) {
							portal.getLinkEnd().setBorder(null);
						}
					}
				}
				MazeDesignMainGUI.GetMazePanel().repaint();
				MazeDesignMainGUI.setCurrentPortal(currentPortal);
				
				portalsPanel.revalidate();
				portalsPanel.repaint();
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
			
		};
		
		addPortalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
				if(portalList.size() >= 25) {
					JOptionPane.showMessageDialog(portalsPanel, "You have the maximum number of portals available.", "Maximum Portals", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
				}
				else {
					String portalName = JOptionPane.showInputDialog(portalsPanel, "What would you like to name your portal?");
					if(portalName != null) {
						String copyPortalName = new String(portalName);
						if(copyPortalName == null || copyPortalName.equals("") || copyPortalName.length() > 15 || copyPortalName.replace(" ", "").equals("")) {
							JOptionPane.showMessageDialog(portalsPanel, "illegal name, must be less than 15 characters");
						}
						else {
							LinkedList<PortalNode> portalNodeList = ButtonFunctions.getPortalNodeList();
							boolean usableName = true;
							for(int i = 0; i < portalNodeList.size(); i++) {
								if(portalName.equals(portalNodeList.get(i).getLinkName())) {
									usableName = false;
								}
							}
							if(usableName == true) {
								PortalNode newLink = new PortalNode(portalName, 'N', 'N');
								portalNodeList.add(newLink);
								JLabel newLinkLabel = new JLabel(portalName);
								newLinkLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
								newLinkLabel.setBorder(linkLabelBorder);
								newLinkLabel.addMouseListener(portalMouseListener);
								portalsPanel.add(newLinkLabel);
								portalsPanel.revalidate();
								portalsPanel.repaint();
							}
							else {
								JOptionPane.showMessageDialog(portalsPanel, "Portal not created, that name is already in use.", "Name Already Used", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
							}
						}
					}
				}
			}
			
		});
		
		deletePortalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean removed = false;
				for(Component c : portalsPanel.getComponents()) {
					if(c instanceof JLabel) {
						if(((JLabel) c).getBorder().equals(selectedLineBorder)) {
							int answer = JOptionPane.showConfirmDialog(portalsPanel, "Are you sure you want to remove \"" + ((JLabel) c).getText() + "\"?", "Delte Portal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
							if(answer == JOptionPane.YES_OPTION) {
								LinkedList<PortalNode> linkNodeList = ButtonFunctions.getPortalNodeList();
								for(int i = 0; i < linkNodeList.size(); i++) {
									if(linkNodeList.get(i).getLinkName().equals(((JLabel) c).getText())) {
										PortalNode currentPortal = linkNodeList.get(i);
										if(currentPortal.getLinkStart() != null) {
											MazeDesignMainGUI.GetMazePanel().remove(currentPortal.getLinkStart());
										}
										if(currentPortal.getLinkEnd() != null) {
											MazeDesignMainGUI.GetMazePanel().remove(currentPortal.getLinkEnd());
										}
										MazeDesignMainGUI.GetMazePanel().repaint();
										linkNodeList.remove(i);
										break;
									}
								}
								MazeDesignMainGUI.SetSelected(0);
								portalsPanel.remove(c);
								portalsPanel.revalidate();
								portalsPanel.repaint();
								removed = true;
							}
							else {
								return;
							}
						}
					}
				}
				if(removed == false) {
					JOptionPane.showMessageDialog(portalsPanel, "No Portal Selected", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
				}
				startPortalButton.setEnabled(false);
				endPortalButton.setEnabled(false);
				startSpotExitPointComboBox.setEnabled(false);
				endSpotExitPointComboBox.setEnabled(false);
				MazeDesignMainGUI.GetMazePanel().repaint();
			}
			
		});
		
		startSpotExitPointComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PortalNode currentPortal = getCorrectPortalNode(portalsPanel, selectedLineBorder);
				if(currentPortal != null) {
					currentPortal.setLinkStartExitSide((char) startSpotExitPointComboBox.getSelectedItem());
				}
			}
		});
		
		endSpotExitPointComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PortalNode currentPortal = getCorrectPortalNode(portalsPanel, selectedLineBorder);
				if(currentPortal != null) {
					currentPortal.setLinkEndExitSide((char) endSpotExitPointComboBox.getSelectedItem());
				}
			}
			
		});
		
		startPortalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startPortalButton.setForeground(Color.red);
				endPortalButton.setForeground(Color.black);
				MazeDesignMainGUI.SetSelected(13);
				MazeDesignMainGUI.setCurrentPortal(getCorrectPortalNode(portalsPanel, selectedLineBorder));
			}
		});
		
		endPortalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endPortalButton.setForeground(Color.red);
				startPortalButton.setForeground(Color.black);
				MazeDesignMainGUI.SetSelected(14);
				MazeDesignMainGUI.setCurrentPortal(getCorrectPortalNode(portalsPanel, selectedLineBorder));
			}
		});
		
		portalConnectionsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(portalConnectionsCheckBox.isSelected() == true) {
					((MazePanel) MazeDesignMainGUI.GetMazePanel()).setPortalConnectionsActive(true);
					MazeDesignMainGUI.GetMazePanel().repaint();
				}
				else {
					((MazePanel) MazeDesignMainGUI.GetMazePanel()).setPortalConnectionsActive(false);
					MazeDesignMainGUI.GetMazePanel().repaint();
				}
				
			}
			
		});
		
		LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
		for(int i = 0; i < portalList.size(); i++) {
			PortalNode currentPortal = portalList.get(i);
			JLabel newLinkLabel = new JLabel(currentPortal.getLinkName());
			newLinkLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			newLinkLabel.setBorder(linkLabelBorder);
			newLinkLabel.addMouseListener(portalMouseListener);
			portalsPanel.add(newLinkLabel);
			portalsPanel.revalidate();
			portalsPanel.repaint();
		}
		frmLinkControls.setVisible(true);
	}
	
	public PortalNode getCorrectPortalNode(Panel portalsPanel, LineBorder selectedLineBorder) {
		for(Component c : portalsPanel.getComponents()) {
			if(c instanceof JLabel) {
				if(((JLabel) c).getBorder().equals(selectedLineBorder)) {
					return getCorrectPortalNode(((JLabel) c).getText());
				}
			}
		}
		return null;
	}
	
	public PortalNode getCorrectPortalNode(String portalName) {
		LinkedList<PortalNode> portalLinkedList = ButtonFunctions.getPortalNodeList();
		for(int i = 0; i < portalLinkedList.size(); i++) {
			if(portalLinkedList.get(i).getLinkName().equals(portalName)) {
				return portalLinkedList.get(i);
			}
		}
		return null;
	}
	
	public void LoadPortalsToPortalControlPanel() {
		portalsPanel.removeAll();
		LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
		for(int i = 0; i < portalList.size(); i++) {
			PortalNode currentPortal = portalList.get(i);
			JLabel newLinkLabel = new JLabel(currentPortal.getLinkName());
			newLinkLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			newLinkLabel.setBorder(linkLabelBorder);
			newLinkLabel.addMouseListener(portalMouseListener);
			portalsPanel.add(newLinkLabel);
			portalsPanel.revalidate();
			portalsPanel.repaint();
		}
	}
	
	public void setVisible(boolean visible) {
		frmLinkControls.setVisible(visible);
		if(visible == false) {
			setDefaultForegroundColorOfPortalButtons();
		}
	}
	
	private void setDefaultForegroundColorOfPortalButtons() {
		startPortalButton.setForeground(Color.black);
		endPortalButton.setForeground(Color.black);
	}
	
	public boolean isVisible() {
		return frmLinkControls.isVisible();
	}
	
	public void resetPortalControls() {
		startSpotExitPointComboBox.setEnabled(false);
		startSpotExitPointComboBox.setSelectedIndex(0);
		endSpotExitPointComboBox.setEnabled(false);
		endSpotExitPointComboBox.setSelectedIndex(0);
		startPortalButton.setEnabled(false);
		startPortalButton.setForeground(Color.black);
		endPortalButton.setEnabled(false);
		endPortalButton.setForeground(Color.black);
		portalsPanel.revalidate();
		portalsPanel.repaint();
	}
}
