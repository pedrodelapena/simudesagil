package br.pro.hashi.ensino.desagil.lucianogic.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.net.URL;

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
	public boolean[] alavancas;

	private Switch[] switches;
	private Gate gate;
	public int x, y,x1,y1,x2,y2,rectwidth,rectheight;
	public Ellipse2D[] oval;


	public GateView(Gate gate) {
		super(205, 180);

		this.gate = gate;

		image = loadImage(gate.toString());

		int size = gate.getSize();

		inBoxes = new JCheckBox[size];

		switches = new Switch[size];
		alavancas = new boolean[size];

		for(int i = 0; i < size; i++) {
			boolean alavanca = false;
			alavancas[i]= alavanca;
			inBoxes[i] = new JCheckBox();

			inBoxes[i].addItemListener(this);

			switches[i] = new Switch();

			gate.connect(switches[i], i);
		}

		outBox = new JCheckBox();

		outBox.setEnabled(false);

//		if(size == 1) {
//			add(inBoxes[0], 0, 60, 20, 20);			
//		}
//		else {
//			for(int i = 0; i < size; i++) {
//				add(inBoxes[i], 0, (i + 1) * 40, 20, 20);			
//			}			
//		}

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
		x=10;
		y=10;
		x1=5;
		y1=5;
		x2=x1+30;
		y2=y1;
		rectwidth =30;
		rectheight=30;
		int size = gate.getSize();
		oval = new Ellipse2D[size];
		Graphics2D g2 = (Graphics2D) g;
		if(size == 1) {
			oval[0] = new Ellipse2D.Double(0, 60, rectwidth, rectheight);
			g2.draw(oval[0]);	
			if (alavancas[0] == true){
				g2.draw(new Line2D.Double(x, y, x1, y1));
				}else{
					g2.draw(new Line2D.Double(x, y, x2, y2));
					alavancas[0]=false;
				}
		}
		else {
			for(int i = 0; i < size; i++) {
				oval[i] = new Ellipse2D.Double(0, (i + 1) * 40, rectwidth, rectheight);
				g2.draw(oval[i]);
				if (alavancas[i] == true){
					g2.draw(new Line2D.Double(0, (i + 1) * 40, x2, ((i + 1) * 40)+10));
					}else{
						g2.draw(new Line2D.Double(0, (i + 1) * 40, x1, ((i + 1) * 40)+10));
						alavancas[i]=false;
					}
			}			
		}
		g.drawImage(image, 10, 20, 184, 140, null);

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
				alavancas[i] = !alavancas[i];
					switches[(i)].setOn(alavancas[i]);
					outBox.setSelected(gate.read());
				}
                repaint();
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
		

		// Evita bugs visuais em alguns sistemas operacionais.
		getToolkit().sync();
    }}