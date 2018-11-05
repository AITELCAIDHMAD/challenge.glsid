package metier;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Movement implements MouseListener, MouseMotionListener{
	
	private int X, Y;
	
	public Movement(Component... components) {
		for (Component component : components) {
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		event.getComponent()
		.setLocation(event.getX()+event.getComponent().getX()-X
					, event.getY()+event.getComponent().getY()-Y);		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) {
		this.X=e.getX();
		this.Y=e.getY();		
	}
	
	
	
	

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
