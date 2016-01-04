/*
Name : Hisham Yahya Garout
ID # : 201193610
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
public class MemoryGame extends JFrame implements ActionListener, Runnable
{
    private ImageIcon backImage = new ImageIcon(getClass().getResource("Colors/backImage.png"));
    private ArrayList<Card> cards = new ArrayList<Card>(16);
    private ArrayList<Integer> openCardIndices = new ArrayList<Integer>(16);
    private int clicks =0;
    
    public MemoryGame()
    {
        super("Memory Game");
        setSize(533,878);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        setLayout(new GridLayout(4,4));

        for(int i=0;i<16;i++)
        {
            cards.add(new Card(backImage));
            add(cards.get(i));
            cards.get(i).addActionListener(this);
        }
        Collections.shuffle(cards,new Random());
        new Card().setColor(cards);
    }

    public void actionPerformed(ActionEvent e)
    {
            clicks +=1;
            Card clicked = (Card)e.getSource();
            clicked.removeActionListener(this);
            clicked.changeColor();

            for(int i=0;i<16;i++)
                if(clicked == cards.get(i))
                    openCardIndices.add(i);


             if(clicks==2)
             {
            	if(!cards.get(openCardIndices.get(openCardIndices.size()-1)).equals(cards.get(openCardIndices.get(openCardIndices.size()-2))))
            	{
            		(cards.get(openCardIndices.get(openCardIndices.size()-1))).addActionListener(this);
            		(cards.get(openCardIndices.get(openCardIndices.size()-2))).addActionListener(this);
             		new Thread(this).start();
            		openCardIndices.remove(openCardIndices.size()-1);
            		openCardIndices.remove(openCardIndices.size()-1);
            	}
            	clicks = 0;
             }

            if(openCardIndices.size() == 16)
            	 new JOptionPane().showMessageDialog(null, "Congratulations, you've won the game!");
    }

    public void run()
    {
    	lockCards();
    	sleep(1000);
    	unlockCards();
    }

    public void lockCards()
    {
    	for(int i=0;i<16;i++)
			if(!cards.get(i).open)
				cards.get(i).removeActionListener(this);
    }

    public void unlockCards()
    {
    	for(int i=0;i<16;i++)
    	{
			if(!cards.get(i).open)
				cards.get(i).addActionListener(this);
			else if(!openCardIndices.contains(i))
				cards.get(i).setIcon(backImage);
    	}
    }

    public void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(Exception e)
        {
            new JOptionPane().showMessageDialog(null, "Sorry, the program had an error and is forced to exit, please try again");
        }
    }
    public static void main(String[] args)
    {
        new MemoryGame().setVisible(true);
    }
}

class Card extends JButton
{
    private ImageIcon a;
    public boolean open = false;

    private ImageIcon Red = new ImageIcon(getClass().getResource("Colors/Red.png"));
    private ImageIcon Orange = new ImageIcon(getClass().getResource("Colors/Orange.png"));
    private ImageIcon Yellow = new ImageIcon(getClass().getResource("Colors/Yellow.png"));
    private ImageIcon Green = new ImageIcon(getClass().getResource("Colors/Green.png"));
    private ImageIcon Blue = new ImageIcon(getClass().getResource("Colors/Blue.png"));
    private ImageIcon Purple = new ImageIcon(getClass().getResource("Colors/Purple.png"));
    private ImageIcon Navy = new ImageIcon(getClass().getResource("Colors/Navy.png"));
    private ImageIcon Pink = new ImageIcon(getClass().getResource("Colors/Pink.png"));
    private ImageIcon [] colors = {Purple,Pink,Red,Orange,Yellow,Green,Blue,Navy,Purple,Pink,Red,Orange,Yellow,Green,Blue,Navy};
    
    public Card(ImageIcon x)
    {
    	super(x);
    }
    
    public Card()
    {
    }

    public void setColor(ArrayList<Card> a)
    {
        for(int i=0;i<a.size();i++)
            a.get(i).a = colors[i];
    }

    public void changeColor()
    {
        setIcon(a);
        open = true;
    }

    public boolean equals(Card other)
    {
        return this.a == other.a;
    }
}