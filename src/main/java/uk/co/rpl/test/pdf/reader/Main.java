/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


/**
 *
 * @author philip
 */
public class Main {
	public static void main(String[] argv) throws TesseractException, Exception{

//		File jpg = new File("pdf-test-jpg-image.jpg");
//		ITesseract tess = new Tesseract();
//		System.out.println(tess.doOCR(jpg));
//		if (argv.length<1){
//			System.out.println("Usage: java -jar test-pdf-reader.jar pdfFilename");
//			System.exit(1);
//		}
		try {
			for (int i=0; i<argv.length; i++)
			 new ReadPdf(argv[i]);
		} catch (FileNotFound ex) {
			System.out.println("File Not Found - "+ex.getMessage());
			ex.printStackTrace();
		} catch (FileNotReadable ex) {
			System.out.println("File Not Readable - "+ex.getMessage());
			ex.printStackTrace();
		}
	}
}
