package br.pro.hashi.ensino.desagil.lucianogic.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import br.pro.hashi.ensino.desagil.lucianogic.model.Gate;
import br.pro.hashi.ensino.desagil.lucianogic.model.Switch;


public class GateView extends FixedPanel implements ItemListener {

	// Necessario para serializar objetos desta classe.
	private static final long serialVersionUID = 1L;

	private Image image;

	private JCheckBox[] inBoxes;
	private JCheckBox outBox;

	private Switch[] switches;
	private Gate gate;
	public int x,y,xa,ya,xb,yb;
	public int largura,altura;
	public Ellipse2D[] oval;
	
	public boolean[] levers;

	public GateView(Gate gate) {
		super(205, 180);

		this.gate = gate;

		image = loadImage(gate.toString());

		int size = gate.getSize();

		inBoxes = new JCheckBox[size];

		switches = new Switch[size];
		
		levers = new boolean[size];

		for(int i = 0; i < size; i++) {
		
			inBoxes[i] = new JCheckBox();

			inBoxes[i].addItemListener(this);

			switches[i] = new Switch();
			
			boolean lever = false;
			
			levers[i]= lever;
			
			gate.connect(switches[i], i);
			
		}

		outBox = new JCheckBox();

		outBox.setEnabled(false);

		add(outBox, 184, 60, 20, 20);

		outBox.setSelected(gate.read());
	}


	@Override
	public void itemStateChanged(ItemEvent event) {
		int i;
		for(i = 0; i < inBoxes.length; i++) {
			if(inBoxes[i] == event.getSource()) {
				break;
			}
		}

		switches[i].setOn(inBoxes[i].isSelected());

		outBox.setSelected(gate.read());
	}


	// Necessario para carregar os arquivos de imagem.
	private Image loadImage(String filename) {
		URL url = getClass().getResource("/img/" + filename + ".png");
		ImageIcon icon = new ImageIcon(url);
		return icon.getImage();
	}


	@Override
	public void paintComponent(Graphics g) {
		// Evita bugs visuais em alguns sistemas operacionais.
		super.paintComponent(g);

		g.drawImage(image, 10, 20, 184, 130, null);
		Graphics2D g2 = (Graphics2D) g;
		x=15;
		xa=3;
		xb=xa+20;
		y=20;
		ya=3;
		yb=ya;
		
		largura =30;
		altura=30;
		
		int size = gate.getSize();
		oval = new Ellipse2D[size];
		if(size == 1) {
			oval[0] = new Ellipse2D.Double(0, 60, largura, altura);
			g2.draw(oval[0]);	
			if (levers[0] == true){
				g2.draw(new Line2D.Double(x, y, xa, ya));
				}else{
					g2.draw(new Line2D.Double(x, y, xb, yb));
					levers[0]=false;
				}
		}
		else {
			for(int i = 0; i < size; i++) {
				oval[i] = new Ellipse2D.Double(0, (i + 1) * 30, largura, altura);
				g2.draw(oval[i]);
				if (levers[i] == true){
					g2.draw(new Line2D.Double(0, (i + 1) * 30, xb, ((i + 1) * 30)+10));
					
					}
				
				else{
						g2.draw(new Line2D.Double(0, (i + 1) * 30, xa, ((i + 1) * 30)+10));
						levers[i]=false;
					}
			}			
		}
		g.drawImage(image, 10, 20, 184, 130, null);

		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				int i=0;
				for(i = 0; i < size; i++) {
                if (oval[i].contains(e.getPoint())) {
//                	System.out.println(i);
                    break;
                }  
                }
				if (i != 2){
				System.out.println(i);
				levers[i] = !levers[i];
					switches[(i)].setOn(levers[i]);
					outBox.setSelected(gate.read());
				}
                repaint();
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
		});
	}
}