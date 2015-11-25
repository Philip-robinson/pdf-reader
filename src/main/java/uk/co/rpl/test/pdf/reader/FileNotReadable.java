/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

/**
 *
 * @author philip
 */
class FileNotReadable extends Exception {
	public final String filename;
	public FileNotReadable(String filename) {
		super(filename+" CANNOT BE READ");
		this.filename=filename;
	}
}
