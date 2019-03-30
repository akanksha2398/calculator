 import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
class Calc extends JFrame
{
	JPanel ptop,pbottom,pmemory;
	JLabel lblhist,lblres;
	JButton btnmem[]=new JButton[5];
	JButton btnothers[]=new JButton[24];
	String smem[]={"MC","MR","M+","M-","MS"},strothers[]={"%","sqrt","sqr"," 1/x","CE","C","\u232b","/","7","8","9","*","4","5","6","-","1","2","3","+","\u2213","0",".","="};
	int i;
	double currentNumber,memory,lastNumber=0;
	boolean flagconcat=false;
	char currentOperator;
	Calc()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		ptop=new JPanel();
		ptop.setLayout(new GridLayout(3,1));
		pbottom=new JPanel();
		pbottom.setLayout(new GridLayout(6,4,5,5));
		pmemory=new JPanel();
		pmemory.setLayout(new GridLayout(1,5));
		lblhist=new JLabel("");
		lblhist.setHorizontalAlignment(SwingConstants.RIGHT);
		lblhist.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
		lblres=new JLabel("0");
		lblres.setHorizontalAlignment(SwingConstants.RIGHT);
		lblres.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		ptop.add(lblhist);
		ptop.add(lblres);
		for(int i=0;i<btnmem.length;i++)
		{
			btnmem[i]=new JButton(smem[i]);
			btnmem[i].setBorderPainted(false);
			btnmem[i].setBackground(Color.LIGHT_GRAY);
		//	btnmem[i].addActionListener(this);
			btnmem[i].addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent me)
				{
					JButton b=(JButton)me.getSource();
					b.setBackground( new Color(51,153,255));
				}
				public void mouseExited(MouseEvent me)
				{
					JButton b=(JButton)me.getSource();
					b.setBackground(Color.LIGHT_GRAY);
				}
			});
			btnmem[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					JButton b1=(JButton)ae.getSource();
					String str=b1.getText();
					if(str.equalsIgnoreCase("MC"))
						memory=0;
					else if(str.equalsIgnoreCase("MR"))
						lblres.setText(formatValue(memory));
					else if(str.equalsIgnoreCase("M+"))
						memory+=Double.parseDouble(lblres.getText());
					else if(str.equalsIgnoreCase("M-"))
						memory-=Double.parseDouble(lblres.getText());
					else if(str.equalsIgnoreCase("MS"))
						memory=Double.parseDouble(lblres.getText());
					flagconcat=false;
				}
			});
			
			pmemory.add(btnmem[i]);
			
		}
		ptop.add(pmemory);
		for(int i=0;i<btnothers.length;i++)
		{
			btnothers[i]=new JButton(strothers[i]);
			btnothers[i].setBorderPainted(false);
			if(Character.isDigit(strothers[i].charAt(0)))
				btnothers[i].setBackground(Color.white);
			else
				btnothers[i].setBackground(Color.LIGHT_GRAY);
			btnothers[i].addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent me)
				{
					JButton b=(JButton)me.getSource();		
			 		if(b.getBackground()==Color.white)
				 		b.setBackground(Color.LIGHT_GRAY);
				 	else if(isOperator(b.getText().charAt(0)))
				 		b.setBackground( new Color(51,153,255));
					else
						b.setBackground(new Color(71,107,107));
				}
				public void mouseExited(MouseEvent me)
				{
					JButton b=(JButton)me.getSource();
					if(b.getBackground()==Color.LIGHT_GRAY)
						b.setBackground(Color.white);
					else
						b.setBackground(Color.LIGHT_GRAY);	
				}
			});
			btnothers[i].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				JButton b1=(JButton)ae.getSource();
				char ch=b1.getText().charAt(0);
				String s1=lblres.getText();
				String str=b1.getText();
				currentNumber=Double.parseDouble(lblres.getText());
				if(Character.isDigit(ch))
				{
					if(flagconcat==false)
					{
						lblres.setText(ch+"");
						flagconcat=true;
					}
					else
					{
						s1+=ch;
						lblres.setText(s1);
						
					}
				}	
				
				else if(isOperator(ch))
				{
					flagconcat=false;
					if(currentOperator!='\u0000')
					{
						switch(currentOperator){
							case '+':
								lastNumber+=currentNumber;
								break;
							case '-':
								lastNumber-=currentNumber;
								break;
							case '*':
								lastNumber*=currentNumber;
								break;
							case '/':
								lastNumber/=currentNumber;
								break;
							default:
								break;
						}
						lblres.setText(formatValue(lastNumber)+"");
					}
					currentOperator=ch;
					lastNumber=Double.parseDouble(lblres.getText());
					lblhist.setText(lblhist.getText()+formatValue(currentNumber)+currentOperator);
				}
				else if(str.equalsIgnoreCase("%"))
				{
					currentNumber=(lastNumber*currentNumber)/100;
					lblres.setText(formatValue(currentNumber)+"");	
				}
				else if(str.equalsIgnoreCase("sqrt"))
				{
					currentNumber=Math.sqrt(currentNumber);
					lblres.setText(formatValue(currentNumber)+"");
					flagconcat=false;
				}
				else if(str.equalsIgnoreCase("sqr"))
				{
					currentNumber*=currentNumber;
					lblres.setText(formatValue(currentNumber)+"");
					flagconcat=false;
				}
				else if(str.equalsIgnoreCase(" 1/x"))
				{
					currentNumber=1/currentNumber;
					lblres.setText(formatValue(currentNumber)+"");
				}
				else if(str.equalsIgnoreCase("ce"))
				{
					lblres.setText("0");
					flagconcat=false;
				}
				else if(str.equalsIgnoreCase("c"))
				{
					lblres.setText("0");
					currentOperator='\u0000';
					lblhist.setText("");
					lastNumber=0;
					currentNumber=0;
					flagconcat=false;
				}
				else if(str.equalsIgnoreCase("\u232b"))
				{
					String str1=lblres.getText();
					lblres.setText(str1.substring(0,str1.length()-1));
					if(lblres.getText().equals(""))
						lblres.setText("0");
					flagconcat=false;
					
				}
				else if(str.equalsIgnoreCase("\u2213"))
				{
					currentNumber=-currentNumber;
					lblres.setText(formatValue(currentNumber)+"");
					flagconcat=false;
				}
				else if(str.equalsIgnoreCase("."))
				{
					int pos=s1.indexOf(".");
					if(pos==-1)
						lblres.setText(s1+".");
				}
				else if(str.equalsIgnoreCase("="))
				{
					switch(currentOperator) {
							case '+':
								lastNumber=lastNumber+currentNumber;
								break;
							case '-':
								lastNumber=lastNumber-currentNumber;
								break;
							case '*':
								lastNumber=lastNumber*currentNumber;
								break;
							case '/':
								lastNumber=lastNumber/currentNumber;
								break;
							default:
								break;
							}
							if(lastNumber==(int)lastNumber)
								lastNumber=(int)lastNumber;
							lblres.setText(formatValue(lastNumber)+"");
							lblhist.setText("");
							currentOperator='\u0000';
							lastNumber=0.0;
							flagconcat=false;	
				}
			}	
			});
			pbottom.add(btnothers[i]);
				
		}
		add(ptop,"North");
		add(pbottom);
		setSize(400,500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String args[])
	{
		new Calc();
	}
	public boolean isOperator(char a)
	{
		if(a=='+'||a=='-'||a=='*'||a=='/')
			return true;
		else
			return false;
		
	}
	String formatValue(double d)
	{
		if(d==(int)d)
			return (int)d+"";
		else
			return d+"";
	}
}
