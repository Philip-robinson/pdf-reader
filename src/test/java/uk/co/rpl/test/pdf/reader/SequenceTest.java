/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author philip
 */
public class SequenceTest {
	
	Sequence inst;
	DisplayedField CMAAA;
	DisplayedField CMBBB;
	DisplayedField CM999;
	DisplayedField CM888;
	
	DisplayedField CN777;
	DisplayedField CN666;
	DisplayedField CN999;
	DisplayedField CN888;
	public SequenceTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() throws FieldFormatException {
		CMAAA=new DisplayedField(100, 100, 50, 50, "AAA");
		CMBBB=new DisplayedField(100, 200, 50, 50, "BBB");
		CM999=new DisplayedField(100, 300, 50, 50, "999");
		CM888=new DisplayedField(100, 400, 50, 50, "888");

		CN999=new DisplayedField(200, 250, 50, 50, "999");
		CN888=new DisplayedField(200, 350, 50, 50, "888");
		CN777=new DisplayedField(200, 450, 50, 50, "777");
		CN666=new DisplayedField(200, 550, 50, 50, "666");

		inst = new Sequence(d->{System.out.println("get min"); 
								return d.getLeft();}, 
				            d->{System.out.println("get width");
							    return d.getRight()-d.getLeft();}, 
				            d->{System.out.println("get bot");
							    return d.getBottom();}, 
		            		d->{System.out.println("get height");
							    return d.getTop()-d.getBottom();});
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of put method, of class Sequence.
	 */
	@Test
	public void testPut() {
		System.out.println("put");
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CN777.getBottom(), CN777);
		inst.put(CN666.getBottom(), CN666);

		assertEquals(8, inst.size());
		assertEquals(CMAAA, inst.list().get(0));
		assertEquals(CN666, inst.list().get(7));
		assertEquals(CN777, inst.list().get(6));
	}

	/**
	 * Test of merge method, of class Sequence.
	 */
	@Test
	public void testMerge() {
		System.out.println("merge");
		Sequence inst2 = inst.getNumeric();
		
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CM888.getBottom(), CM888);
		inst2.put(CN999.getBottom(), CN999);
		inst2.put(CN888.getBottom(), CN888);
		inst2.put(CN777.getBottom(), CN777);
		inst2.put(CN666.getBottom(), CN666);

		assertEquals(4, inst.size());
		assertEquals(4, inst2.size());
		assertEquals(CMAAA, inst.list().get(0));
		assertEquals(CN666, inst2.list().get(3));
		assertEquals(CN777, inst2.list().get(2));
		inst.merge(inst2);
		assertEquals(8, inst.size());
		assertEquals(CMAAA, inst.list().get(0));
		assertEquals(CN666, inst.list().get(7));
		assertEquals(CN777, inst.list().get(6));
	}

	/**
	 * Test of getMin method, of class Sequence.
	 */
	@Test
	public void testGetMin() {
		System.out.println("getMin");
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CN777.getBottom(), CN777);
		inst.put(CN666.getBottom(), CN666);

		assertEquals(100.0, inst.getMin(), 0.0001);
	}

	/**
	 * Test of getWidth method, of class Sequence.
	 */
	@Test
	public void testGetWidth() {
		System.out.println("getWidth");
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CN777.getBottom(), CN777);
		inst.put(CN666.getBottom(), CN666);

		assertEquals(150.0, inst.getWidth(), 0.0001);
	}

	/**
	 * Test of getMax method, of class Sequence.
	 */
	@Test
	public void testGetMax() {
		System.out.println("getMax");
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CN777.getBottom(), CN777);
		inst.put(CN666.getBottom(), CN666);

		assertEquals(250.0, inst.getMax(), 0.0001);
	}

	/**
	 * Test of getNumeric method, of class Sequence.
	 */
	@Test
	public void testGetNumeric() {
		System.out.println("getNumeric");
		inst.put(CN666.getBottom(), CN666);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN777.getBottom(), CN777);

		Sequence num = inst.getNumeric();
		assertEquals(6, num.size());
		assertEquals(250, (int)num.list().get(0).getBottom());
		assertEquals(300, (int)num.list().get(1).getBottom());
		assertEquals(350, (int)num.list().get(2).getBottom());
		assertEquals(400, (int)num.list().get(3).getBottom());
		assertEquals(450, (int)num.list().get(4).getBottom());
		assertEquals(550, (int)num.list().get(5).getBottom());
	}

	/**
	 * Test of list method, of class Sequence.
	 */
	@Test
	public void testList() {
		System.out.println("list");
		inst.put(CN666.getBottom(), CN666);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN777.getBottom(), CN777);

		assertEquals(8, inst.size());
		assertEquals(100, (int)inst.list().get(0).getBottom());
		assertEquals(200, (int)inst.list().get(1).getBottom());
		assertEquals(250, (int)inst.list().get(2).getBottom());
		assertEquals(300, (int)inst.list().get(3).getBottom());
		assertEquals(350, (int)inst.list().get(4).getBottom());
		assertEquals(400, (int)inst.list().get(5).getBottom());
		assertEquals(450, (int)inst.list().get(6).getBottom());
		assertEquals(550, (int)inst.list().get(7).getBottom());
	}

	/**
	 * Test of getBase method, of class Sequence.
	 */
	@Test
	public void testGetBase() {
		System.out.println("getBase");
		inst.put(CN666.getBottom(), CN666);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN777.getBottom(), CN777);

		assertEquals(100.0, inst.getBase(), 0.0001);
	}

	/**
	 * Test of getHeight method, of class Sequence.
	 */
	@Test
	public void testGetHeight() {
		System.out.println("getHeight");
		inst.put(CN666.getBottom(), CN666);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN777.getBottom(), CN777);

		assertEquals(500.0, inst.getHeight(), 0.0001);
	}

	/**
	 * Test of size method, of class Sequence.
	 */
	@Test
	public void testSize() {
		System.out.println("size");
		inst.put(CN666.getBottom(), CN666);
		inst.put(CMBBB.getBottom(), CMBBB);
		inst.put(CN888.getBottom(), CN888);
		inst.put(CM999.getBottom(), CM999);
		inst.put(CMAAA.getBottom(), CMAAA);
		inst.put(CM888.getBottom(), CM888);
		inst.put(CN999.getBottom(), CN999);
		inst.put(CN777.getBottom(), CN777);

		assertEquals(8, inst.size());
	}
}
