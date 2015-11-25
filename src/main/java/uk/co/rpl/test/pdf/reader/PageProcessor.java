/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.PDimension;
import org.icepdf.core.pobjects.graphics.text.LineText;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.icepdf.core.pobjects.graphics.text.WordText;

/**
 *
 * @author philip
 */
public class PageProcessor {

	private static final int WIDE=1000;
	private static final int HIGH=900;
	private final Document document;
	private final int pageNumber;
	private final Canvas c;
	private final JFrame jf;
	private double scale;
	private double pageHeight;
	private double pageWidth;

	public PageProcessor(String filename, Document document, int pageNumber) {
		this.document = document;
		this.pageNumber = pageNumber;
		jf = new JFrame("Page "+pageNumber);
		jf.setSize(WIDE, HIGH);
		jf.getContentPane().setLayout(new BorderLayout());
		c = new Canvas();
		jf.getContentPane().add(c, BorderLayout.CENTER);
		jf.setEnabled(true);
		jf.setVisible(true);
		jf.setTitle(filename+" "+(pageNumber+1));
	}

	void process() throws InterruptedException, Exception {
		FieldStructure struct = new FieldStructure(10);
		PDimension dim = document.getPageDimension(pageNumber, 0.0F);
		pageWidth = dim.getWidth();
		pageHeight = dim.getHeight();
		double scWide = (c.getBounds().width-10)/pageWidth;
		double scHeigh = (c.getBounds().height-10)/pageHeight;
		//scale = Math.min(scWide, scHeigh);
		scale = scWide;

		Graphics2D g = (Graphics2D)c.getGraphics();
		g.setColor(Color.black);
		g.drawRect(5, 5, (int)(pageWidth*scale), (int)(pageHeight*scale));
		g.setColor(Color.white);
		g.fillRect(5, 5, (int)(pageWidth*scale), (int)(pageHeight*scale));
		PageText pageText = document.getPageText(pageNumber);
        System.out.println("Extracting page text: " + pageNumber);
		boolean spaces = true;
        if (pageText != null && pageText.getPageLines() != null) {
			for (LineText txt : pageText.getPageLines()){
				if (txt != null && txt.getWords() != null){
					drawBox(c, txt.getBounds(), scale, Color.green, "", 3);
					for (WordText wt: txt.getWords()){
						System.out.println("Processing word "+
								wt.getText()+" @ "+wt.getBounds());

						Rectangle2D.Float bnds = wt.getBounds();
						
						if (!wt.getText().matches("^\\s*$")){
							drawBox(c, bnds, scale, Color.blue, wt.getText(), 1);
							struct.add(new DisplayedField(bnds.x*scale, 
									scale*(pageHeight-bnds.y-bnds.height),
									bnds.width*scale, bnds.height*scale,
									wt.getText().trim()));
						} else {
							String w = wt.getText();
							String wtm = w.trim();
							if (!spaces){
								spaces = true;
							}
							if (wtm.length()>0){
								spaces = false;
								if (w.endsWith(" ")){
									spaces = true;
								}
							}
						}
					}
				}
			}
        }
		for (Sequence seq: struct.getNumericIsolatedSequences()){
			System.out.println("draw a numeric seq "+seq);
			double left = seq.getMin();
			double width = seq.getWidth();
			double base=-1;
			double top=-1;
			for (DisplayedField df: seq.list()){
				if (base<0)base = df.getBottom();
				top = df.getTop();
			}
			Graphics2D gg = (Graphics2D)c.getGraphics();
			gg.setColor(Color.red);
			gg.drawRect((int)left, (int)base, (int)width, (int)(top-base));
		}
    }

	
	private void drawBox(Canvas c, Rectangle2D.Float bnds, double scale, 
			Color colour, String text, int size) {
		Graphics2D g = (Graphics2D)c.getGraphics();
		g.setFont(c.getFont().deriveFont(9.0F));
		g.setColor(colour);
		double xr = bnds.x;
		double yr = pageHeight-bnds.y-bnds.height;
		int x = (int)(xr*scale);
		int y = (int)(yr*scale);
		int w = (int)(bnds.width*scale);
		int h = (int)(bnds.height*scale);
		for (int i=0; i<size; i++){
			g.drawRect(x-i, y-i, w+i+i, h+i+i);
		}
		
		g.drawString(text, x+size+1, y+h-size-1);
		
	}
}
