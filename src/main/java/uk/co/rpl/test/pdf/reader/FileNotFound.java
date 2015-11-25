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
public class FileNotFound extends Exception {
	public final String filename;
	public FileNotFound(String filename) {
		super(filename+" NOT FOUND");
		this.filename=filename;
	}
}
