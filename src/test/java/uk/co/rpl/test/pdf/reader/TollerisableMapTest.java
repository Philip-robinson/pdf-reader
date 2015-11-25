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
public class TollerisableMapTest {
	
	TollerisableMap inst;
	
	DisplayedField CMAAA;
	DisplayedField CMBBB;
	DisplayedField CM999;
	DisplayedField CM888;
	
	DisplayedField CXAAA;
	DisplayedField CXBBB;
	DisplayedField CX999;
	DisplayedField CX888;
	
	DisplayedField CYAAA;
	DisplayedField CYBBB;
	DisplayedField CY999;
	DisplayedField CY888;
	
	DisplayedField CN777;
	DisplayedField CN666;
	DisplayedField CN999;
	DisplayedField CN888;

	public TollerisableMapTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() throws FieldFormatException {
		inst= new TollerisableMap(10,
							d->{System.out.println("get min"); 
								return d.getLeft();}, 
				            d->{System.out.println("get width");
							    return d.getRight()-d.getLeft();}, 
				            d->{System.out.println("get bot");
							    return d.getBottom();}, 
		            		d->{System.out.println("get height");
							    return d.getTop()-d.getBottom();});
		CMAAA=new DisplayedField(100, 100, 50, 50, "AAA");
		CMBBB=new DisplayedField(100, 200, 50, 50, "BBB");
		CM999=new DisplayedField(100, 300, 50, 50, "999");
		CM888=new DisplayedField(100, 400, 50, 50, "888");

		CXAAA=new DisplayedField(130, 105, 50, 50, "XAAA");
		CXBBB=new DisplayedField(130, 205, 50, 50, "XBBB");
		CX999=new DisplayedField(130, 305, 50, 50, "X999");
		CX888=new DisplayedField(130, 405, 50, 50, "X888");

		CYAAA=new DisplayedField(160, 115, 50, 50, "YAAA");
		CYBBB=new DisplayedField(160, 215, 50, 50, "YBBB");
		CY999=new DisplayedField(160, 315, 50, 50, "Y999");
		CY888=new DisplayedField(160, 415, 50, 50, "Y888");

		CN999=new DisplayedField(200, 250, 50, 50, "999");
		CN888=new DisplayedField(200, 350, 50, 50, "888");
		CN777=new DisplayedField(200, 450, 50, 50, "777");
		CN666=new DisplayedField(200, 550, 50, 50, "666");
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of add method, of class TollerisableMap.
	 */
	@Test
	public void testAdd() {
		System.out.println("add");
		inst.add(CN777.getLeft(), CN777.getBottom(), CN777);
		inst.add(CMBBB.getLeft(), CMBBB.getBottom(), CMBBB);
		inst.add(CM888.getLeft(), CM888.getBottom(), CM888);
		inst.add(CMAAA.getLeft(), CMAAA.getBottom(), CMAAA);
		inst.add(CN999.getLeft(), CN999.getBottom(), CN999);
		inst.add(CN888.getLeft(), CN888.getBottom(), CN888);
		inst.add(CM999.getLeft(), CM999.getBottom(), CM999);
		inst.add(CN666.getLeft(), CN666.getBottom(), CN666);

		inst.add(CXAAA.getLeft(), CXAAA.getBottom(), CXAAA);
		inst.add(CYBBB.getLeft(), CYBBB.getBottom(), CYBBB);
		inst.add(CY888.getLeft(), CY888.getBottom(), CY888);
		inst.add(CYAAA.getLeft(), CYAAA.getBottom(), CYAAA);
		inst.add(CX999.getLeft(), CX999.getBottom(), CX999);
		inst.add(CX888.getLeft(), CX888.getBottom(), CX888);
		inst.add(CY999.getLeft(), CY999.getBottom(), CY999);
		inst.add(CXBBB.getLeft(), CXBBB.getBottom(), CXBBB);

		assertEquals(4, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());
		assertEquals(4, inst.getSequences().get(2).size());
		assertEquals(4, inst.getSequences().get(3).size());
	}

	/**
	 * Test of tidy method, of class TollerisableMap.
	 */
	@Test
	public void testTidyNothingToDo() {
		System.out.println("testTidyNothingToDo");
		inst.add(CN777.getLeft(), CN777.getBottom(), CN777);
		inst.add(CMBBB.getLeft(), CMBBB.getBottom(), CMBBB);
		inst.add(CM888.getLeft(), CM888.getBottom(), CM888);
		inst.add(CMAAA.getLeft(), CMAAA.getBottom(), CMAAA);
		inst.add(CN999.getLeft(), CN999.getBottom(), CN999);
		inst.add(CN888.getLeft(), CN888.getBottom(), CN888);
		inst.add(CM999.getLeft(), CM999.getBottom(), CM999);
		inst.add(CN666.getLeft(), CN666.getBottom(), CN666);

		assertEquals(2, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());
		inst.tidy();
		assertEquals(2, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());
	}

	/**
	 * Test of tidy method, of class TollerisableMap.
	 */
	@Test
	public void testTidySomthingToDo() {
		System.out.println("testTidySomthingToDo");
		inst.add(CN777.getLeft(), CN777.getBottom(), CN777);
		inst.add(CMBBB.getLeft(), CMBBB.getBottom(), CMBBB);
		inst.add(CM888.getLeft(), CM888.getBottom(), CM888);
		inst.add(CMAAA.getLeft(), CMAAA.getBottom(), CMAAA);
		inst.add(CN999.getLeft(), CN999.getBottom(), CN999);
		inst.add(CN888.getLeft(), CN888.getBottom(), CN888);
		inst.add(CM999.getLeft(), CM999.getBottom(), CM999);
		inst.add(CN666.getLeft(), CN666.getBottom(), CN666);

		inst.add(CXAAA.getLeft(), CXAAA.getBottom(), CXAAA);
		inst.add(CYBBB.getLeft(), CYBBB.getBottom(), CYBBB);
		inst.add(CY888.getLeft(), CY888.getBottom(), CY888);
		inst.add(CYAAA.getLeft(), CYAAA.getBottom(), CYAAA);
		inst.add(CX999.getLeft(), CX999.getBottom(), CX999);
		inst.add(CX888.getLeft(), CX888.getBottom(), CX888);
		inst.add(CY999.getLeft(), CY999.getBottom(), CY999);
		inst.add(CXBBB.getLeft(), CXBBB.getBottom(), CXBBB);

		assertEquals(4, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());
		assertEquals(4, inst.getSequences().get(2).size());
		assertEquals(4, inst.getSequences().get(3).size());
		inst.tidy();
		assertEquals(1, inst.getSequences().size());
		assertEquals(16, inst.getSequences().get(0).size());
	}

	/**
	 * Test of getNumericNoneOverlappingSequences method, of class TollerisableMap.
	 */
	@Test
	public void testGetNumericNoneOverlappingSequences() {
		System.out.println("getNumericNoneOverlappingSequences");
		inst.add(CN777.getLeft(), CN777.getBottom(), CN777);
		inst.add(CMBBB.getLeft(), CMBBB.getBottom(), CMBBB);
		inst.add(CM888.getLeft(), CM888.getBottom(), CM888);
		inst.add(CMAAA.getLeft(), CMAAA.getBottom(), CMAAA);
		inst.add(CN999.getLeft(), CN999.getBottom(), CN999);
		inst.add(CN888.getLeft(), CN888.getBottom(), CN888);
		inst.add(CM999.getLeft(), CM999.getBottom(), CM999);
		inst.add(CN666.getLeft(), CN666.getBottom(), CN666);

//		inst.add(CXAAA.getLeft(), CXAAA.getBottom(), CXAAA);
//		inst.add(CYBBB.getLeft(), CYBBB.getBottom(), CYBBB);
//		inst.add(CY888.getLeft(), CY888.getBottom(), CY888);
//		inst.add(CYAAA.getLeft(), CYAAA.getBottom(), CYAAA);
//		inst.add(CX999.getLeft(), CX999.getBottom(), CX999);
//		inst.add(CX888.getLeft(), CX888.getBottom(), CX888);
//		inst.add(CY999.getLeft(), CY999.getBottom(), CY999);
//		inst.add(CXBBB.getLeft(), CXBBB.getBottom(), CXBBB);

		assertEquals(2, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());

		assertEquals(2, inst.getNumericNoneOverlappingSequences().size());
		assertEquals(2, inst.getNumericNoneOverlappingSequences().get(0).size());
		assertEquals(4, inst.getNumericNoneOverlappingSequences().get(1).size());
		inst.tidy();
		assertEquals(2, inst.getNumericNoneOverlappingSequences().size());
		assertEquals(2, inst.getNumericNoneOverlappingSequences().get(0).size());
		assertEquals(4, inst.getNumericNoneOverlappingSequences().get(1).size());
	}

	/**
	 * Test of getNumericNoneOverlappingSequences method, of class TollerisableMap.
	 */
	@Test
	public void testGetNumericOverlappingSequences() {
		System.out.println("getNumericOverlappingSequences");
		inst.add(CN777.getLeft(), CN777.getBottom(), CN777);
		inst.add(CMBBB.getLeft(), CMBBB.getBottom(), CMBBB);
		inst.add(CM888.getLeft(), CM888.getBottom(), CM888);
		inst.add(CMAAA.getLeft(), CMAAA.getBottom(), CMAAA);
		inst.add(CN999.getLeft(), CN999.getBottom(), CN999);
		inst.add(CN888.getLeft(), CN888.getBottom(), CN888);
		inst.add(CM999.getLeft(), CM999.getBottom(), CM999);
		inst.add(CN666.getLeft(), CN666.getBottom(), CN666);

		inst.add(CXAAA.getLeft(), CXAAA.getBottom(), CXAAA);
		inst.add(CYBBB.getLeft(), CYBBB.getBottom(), CYBBB);
		inst.add(CY888.getLeft(), CY888.getBottom(), CY888);
		inst.add(CYAAA.getLeft(), CYAAA.getBottom(), CYAAA);
		inst.add(CX999.getLeft(), CX999.getBottom(), CX999);
		inst.add(CX888.getLeft(), CX888.getBottom(), CX888);
		inst.add(CY999.getLeft(), CY999.getBottom(), CY999);
		inst.add(CXBBB.getLeft(), CXBBB.getBottom(), CXBBB);

		assertEquals(4, inst.getSequences().size());
		assertEquals(4, inst.getSequences().get(0).size());
		assertEquals(4, inst.getSequences().get(1).size());
		assertEquals(4, inst.getSequences().get(2).size());
		assertEquals(4, inst.getSequences().get(3).size());

		assertEquals(2, inst.getNumericNoneOverlappingSequences().size());
		assertEquals(2, inst.getNumericNoneOverlappingSequences().get(0).size());
		assertEquals(4, inst.getNumericNoneOverlappingSequences().get(1).size());
		inst.tidy();
		assertEquals(2, inst.getNumericNoneOverlappingSequences().size());
		assertEquals(2, inst.getNumericNoneOverlappingSequences().get(0).size());
		assertEquals(4, inst.getNumericNoneOverlappingSequences().get(1).size());
	}
}
