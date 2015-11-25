/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;

/**
 *
 * @author philip
 */
public class ReadPdf {
	private final File pdfToRead;
	ReadPdf(String filename) throws FileNotFound, FileNotReadable, Exception {
		pdfToRead = new File(filename);
		System.out.println("Filename "+pdfToRead.getAbsolutePath());
		if (!pdfToRead.exists())
			throw new FileNotFound(filename);
		if (!pdfToRead.canRead()) throw new FileNotReadable(filename);
		        Document document = new Document();
        try {
            document.setFile(pdfToRead.getAbsolutePath());
        } catch (PDFException ex) {
            System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
            System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error file not found " + ex);
        } catch (IOException ex) {
            System.out.println("Error handling PDF document " + ex);
        }
        try {
            // Get text from the first page of the document, assuming that there
            // is text to extract.
            for (int pageNumber = 0, max = document.getNumberOfPages();
                 pageNumber < max; pageNumber++) {
                PageProcessor processor= new PageProcessor(
						filename, document, pageNumber);
				processor.process();
			}

        } catch (InterruptedException ex) {
            System.out.println("Error paring page " + ex);
        }

        // clean up resources
        document.dispose();


	}

}
