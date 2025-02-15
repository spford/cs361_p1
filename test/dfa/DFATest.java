package test.dfa;

//import org.junit.*;

import java.util.Set;



import fa.dfa.DFA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DFATest {
	
	
	//------------------- dfa1 tests ----------------------//
	private DFA dfa1() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));
		
		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("c"));
		assertFalse(dfa.setFinal("c"));
		
		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "a", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));
		
		assertFalse(dfa.addTransition("c", "b", '1'));
		assertFalse(dfa.addTransition("a", "c", '1'));
		assertFalse(dfa.addTransition("a", "b", '2'));
		
		return dfa;
	}
	
	@Test
	public void test1_1() {
		DFA dfa = dfa1();
		System.out.println("dfa1 instantiation pass");
	}

	@Test
	public void test1_2() {
		DFA dfa = dfa1();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa1 correctness pass");
	}
	
	@Test
	public void test1_3() {
		DFA dfa = dfa1();
		
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
		
		System.out.println("dfa1 accept pass");
	}
	
	@Test
	public void test1_4() {
		DFA dfa = dfa1();
		
		String dfaStr = dfa.toString();
		String expStr = " Q = { a b }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	a	b\n"
				+ "q0 = a\n"
				+ "F = { b }";
		
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		
		System.out.println("dfa1 toString pass");
	}
	
	
	
	@Test
	public void test1_5() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		
		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));
		
		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
	
		System.out.println("dfa1Swap instantiation pass");
	}
	
	@Test
	public void test1_6() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("0"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("e"));
		
		System.out.println("dfa1Swap accept pass");
	}

//------------------- dfa2 tests ----------------------//
	private DFA dfa2() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("3"));
		assertTrue(dfa.setFinal("3"));
		
		assertTrue(dfa.addState("0"));
		assertTrue(dfa.setStart("0"));
		
		assertTrue(dfa.addState("1"));
		assertTrue(dfa.addState("2"));
		
		
		assertFalse(dfa.setFinal("c"));
		assertFalse(dfa.setStart("a"));
		assertFalse(dfa.addState("2"));
		
		assertTrue(dfa.addTransition("0", "1", '0'));
		assertTrue(dfa.addTransition("0", "0", '1'));
		assertTrue(dfa.addTransition("1", "3", '0'));
		assertTrue(dfa.addTransition("1", "2", '1'));
		assertTrue(dfa.addTransition("2", "1", '0'));
		assertTrue(dfa.addTransition("2", "1", '1'));
		assertTrue(dfa.addTransition("3", "3", '0'));
		assertTrue(dfa.addTransition("3", "3", '1'));
		
		assertFalse(dfa.addTransition("3", "a", '1'));
		assertFalse(dfa.addTransition("c", "a", '1'));
		assertFalse(dfa.addTransition("3", "a", '2'));
		
		return dfa;
	}
	
	@Test
	public void test2_1() {
		DFA dfa = dfa2();
		System.out.println("dfa2 instantiation pass");
	}

	@Test
	public void test2_2() {
		DFA dfa = dfa2();
		assertNotNull(dfa.getState("0"));
		assertEquals(dfa.getState("1").getName(),"1");
		assertTrue(dfa.isStart("0"));
		assertNotNull(dfa.getState("3"));
		assertEquals(dfa.getState("3").getName(),"3");
		assertTrue(dfa.isFinal("3"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa2 correctness pass");
	}
	
	@Test
	public void test2_3() {
		DFA dfa = dfa2();
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
	
		System.out.println("dfa2 accept pass");
	}
	
	@Test
	public void test2_4() {
		DFA dfa = dfa2();
		
		String dfaStr = dfa.toString();
		String expStr = "Q={3 0 1 2}\n"
				+ "Sigma = {0 1}\n"
				+ "delta =\n"
				+ "	0	1\n"
				+ "3	3	3\n"
				+ "0	1	0\n"
				+ "1	3	2\n"
				+ "2	1	1\n"
				+ "q0 = 0\n"
				+ "F={3}\n";
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa2 toString pass");
	}
	
	
	
	@Test
	public void test2_5() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		//different DFA states
		assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
		assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
		assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
		assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
		assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));
		
		//ensure that the transitions of the original DFA don't change
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
		
		System.out.println("dfa2Swap instantiation pass");
	}
	
	@Test
	public void test2_6() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("101"));
		assertTrue(dfaSwap.accepts("11"));
		assertFalse(dfaSwap.accepts("010"));
		assertTrue(dfaSwap.accepts("000100000000001"));
		assertFalse(dfaSwap.accepts("0001000000000101"));
		System.out.println("dfa2Swap accept pass");
	}	

//------------------- dfa3 tests ----------------------//
	private DFA dfa3() {
		DFA dfa = new DFA();
		dfa.addSigma('2');
		dfa.addSigma('1');

		assertTrue(dfa.addState("G"));
		assertTrue(dfa.addState("D"));

		assertTrue(dfa.setFinal("G"));
		assertTrue(dfa.setFinal("D"));

		assertTrue(dfa.addState("A"));
		assertTrue(dfa.setStart("D"));
		assertTrue(dfa.setStart("A"));

		assertTrue(dfa.addState("B"));
		assertTrue(dfa.addState("C"));
		assertTrue(dfa.addState("E"));
		assertTrue(dfa.addState("F"));

		assertFalse(dfa.addState("A"));
		assertFalse(dfa.setFinal("K"));
		assertFalse(dfa.setStart("BK"));

		assertTrue(dfa.addTransition("A", "B", '1'));
		assertTrue(dfa.addTransition("A", "C", '2'));

		assertTrue(dfa.addTransition("B", "D", '1'));
		assertTrue(dfa.addTransition("B", "E", '2'));

		assertTrue(dfa.addTransition("C", "F", '1'));
		assertTrue(dfa.addTransition("C", "G", '2'));

		assertTrue(dfa.addTransition("C", "F", '1'));
		assertTrue(dfa.addTransition("C", "G", '2'));

		assertTrue(dfa.addTransition("D", "D", '1'));
		assertTrue(dfa.addTransition("D", "E", '2'));

		assertTrue(dfa.addTransition("E", "D", '1'));
		assertTrue(dfa.addTransition("E", "E", '2'));

		assertTrue(dfa.addTransition("F", "F", '1'));
		assertTrue(dfa.addTransition("F", "G", '2'));

		assertTrue(dfa.addTransition("G", "F", '1'));
		assertTrue(dfa.addTransition("G", "G", '2'));


		assertFalse(dfa.addTransition("FF", "F", '1'));
		assertFalse(dfa.addTransition("F", "GG", '2'));

		assertFalse(dfa.addTransition("G", "F", 'K'));
		assertFalse(dfa.addTransition("A", "K", '7'));

		return dfa;
	}

	@Test
	public void test3_1() {
		DFA dfa = dfa3();

		System.out.println("dfa3 instantiation pass");
	}

	@Test
	public void test3_2() {
		DFA dfa = dfa3();
		assertNotNull(dfa.getState("A"));
		assertNull(dfa.getState("K"));
		assertEquals(dfa.getState("C").getName(),"C");
		assertTrue(dfa.isStart("A"));
		assertFalse(dfa.isStart("D"));
		assertNotNull(dfa.getState("G"));
		assertEquals(dfa.getState("E").getName(),"E");
		assertTrue(dfa.isFinal("G"));
		assertFalse(dfa.isFinal("B"));
		assertEquals(dfa.getSigma(), Set.of('2','1'));

		System.out.println("dfa3 correctness pass");
	}

	@Test
	public void test3_3() {
		DFA dfa = dfa3();
		assertTrue(dfa.accepts("121212121"));
		assertTrue(dfa.accepts("12221212121"));
		assertFalse(dfa.accepts("12"));
		assertFalse(dfa.accepts("2"));
		assertFalse(dfa.accepts("1212"));

		System.out.println("dfa3 accept pass");
	}

	@Test
	public void test3_4() {
		DFA dfa = dfa3();

		String dfaStr = dfa.toString();
		String expStr = "Q={GDABCEF}\n"
				+ "Sigma = {2 1}\n"
				+ "delta =\n"
				+ "	2	1\n"
				+ "G	G	F\n"
				+ "D	E	D\n"
				+ "A	C	B\n"
				+ "B	E	D\n"
				+ "C	G	F\n"
				+ "E	E	D\n"
				+ "F	G	F\n"
				+ "q0 = A\n"
				+ "F = {G D}\n";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa3 toString pass");
	}



	@Test
	public void test3_5() {
		DFA dfa = dfa3();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfa != dfaSwap);
		assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
		assertTrue(dfa.getState("G") != dfaSwap.getState("G"));
		assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
		assertEquals(dfa.isStart("D"), dfaSwap.isStart("D"));
		assertEquals(dfa.isFinal("A"), dfaSwap.isFinal("A"));

		//transitions of the original dfa should not change
		assertTrue(dfa.accepts("121212121"));
		assertTrue(dfa.accepts("12221212121"));
		assertFalse(dfa.accepts("12"));
		assertFalse(dfa.accepts("2"));
		assertFalse(dfa.accepts("1212"));

		System.out.println("df31Swap instantiation pass");
	}

	@Test
	public void test3_6() {
		DFA dfa = dfa3();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfaSwap.accepts("212121212"));
		assertTrue(dfaSwap.accepts("21112121212"));
		assertFalse(dfaSwap.accepts("21"));
		assertFalse(dfaSwap.accepts("1"));
		assertFalse(dfaSwap.accepts("2121"));

		System.out.println("dfa3Swap accept pass");
	}

	//------------------- dfa4 tests ----------------------//
	private DFA dfa4() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		dfa.addSigma('2');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.addState("d"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("d"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.addState("d"));
		assertFalse(dfa.setStart("z"));
		assertFalse(dfa.setFinal("x"));

		assertTrue(dfa.addTransition("a", "b", '0'));
		assertTrue(dfa.addTransition("a", "a", '1'));
		assertTrue(dfa.addTransition("a", "a", '2'));
		assertTrue(dfa.addTransition("b", "b", '0'));
		assertTrue(dfa.addTransition("b", "c", '1'));
		assertTrue(dfa.addTransition("b", "a", '2'));
		assertTrue(dfa.addTransition("c", "d", '0'));
		assertTrue(dfa.addTransition("c", "a", '1'));
		assertTrue(dfa.addTransition("c", "a", '2'));
		assertTrue(dfa.addTransition("d", "d", '0'));
		assertTrue(dfa.addTransition("d", "d", '1'));
		assertTrue(dfa.addTransition("d", "d", '2'));

		assertFalse(dfa.addTransition("a", "a", '9'));
		assertFalse(dfa.addTransition("b", "z", '2'));
		assertFalse(dfa.addTransition("z", "d", '0'));

		return dfa;
	}

	@Test
	public void test4_1() {
		DFA dfa = dfa4();
		System.out.println("dfa4 instantiation pass");
	}

	@Test
	public void test4_2() {
		DFA dfa = dfa4();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("d"));
		assertEquals(dfa.getSigma(), Set.of('0','1', '2'));

		System.out.println("dfa4 correctness pass");
	}

	@Test
	public void test4_3() {
		DFA dfa = dfa4();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("010"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("020120102"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa4 accept pass");
	}

	@Test
	public void test4_4() {
		DFA dfa = dfa4();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c d }\n"
				+ "Sigma = { 0 1 2 }\n"
				+ "delta =\n"
				+ "		0	1	2\n"
				+ "	a	b	a	a\n"
				+ "	b	b	c	a\n"
				+ "	c	d	a	a\n"
				+ "	d	d	d	d\n"
				+ "q0 = a\n"
				+ "F = { d }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa4 toString pass");
	}



	@Test
	public void test4_5() {
		DFA dfa = dfa4();
		DFA dfaSwap = dfa.swap('2', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("010"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("020120102"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa4Swap instantiation pass");
	}

	@Test
	public void test4_6() {
		DFA dfa = dfa4();
		DFA dfaSwap = dfa.swap('2', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("212"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("11120002221200"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa4Swap accept pass");
	}

	//------------------- dfa5 tests ----------------------//
	private DFA dfa5() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		dfa.addSigma('2');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.addState("d"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("d"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.addState("d"));
		assertFalse(dfa.setStart("z"));
		assertFalse(dfa.setFinal("x"));

		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("a", "a", '2'));
		assertTrue(dfa.addTransition("b", "c", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));
		assertTrue(dfa.addTransition("b", "a", '2'));
		assertTrue(dfa.addTransition("c", "a", '0'));
		assertTrue(dfa.addTransition("c", "d", '1'));
		assertTrue(dfa.addTransition("c", "a", '2'));
		assertTrue(dfa.addTransition("d", "d", '0'));
		assertTrue(dfa.addTransition("d", "d", '1'));
		assertTrue(dfa.addTransition("d", "d", '2'));

		assertFalse(dfa.addTransition("a", "a", '9'));
		assertFalse(dfa.addTransition("b", "z", '2'));
		assertFalse(dfa.addTransition("z", "d", '0'));

		return dfa;
	}

	@Test
	public void test5_1() {
		DFA dfa = dfa5();
		System.out.println("dfa5 instantiation pass");
	}

	@Test
	public void test5_2() {
		DFA dfa = dfa5();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("d"));
		assertEquals(dfa.getSigma(), Set.of('0','1', '2'));

		System.out.println("dfa5 correctness pass");
	}

	@Test
	public void test5_3() {
		DFA dfa = dfa5();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("0011221102101210"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa5 accept pass");
	}

	@Test
	public void test5_4() {
		DFA dfa = dfa5();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c d }\n"
				+ "Sigma = { 0 1 2 }\n"
				+ "delta =\n"
				+ "		0	1	2\n"
				+ "	a	a	b	a\n"
				+ "	b	c	b	a\n"
				+ "	c	a	d	a\n"
				+ "	d	d	d	d\n"
				+ "q0 = a\n"
				+ "F = { d }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa5 toString pass");
	}



	@Test
	public void test5_5() {
		DFA dfa = dfa5();
		DFA dfaSwap = dfa.swap('2', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("0011221102101210"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa5Swap instantiation pass");
	}

	@Test
	public void test5_6() {
		DFA dfa = dfa5();
		DFA dfaSwap = dfa.swap('2', '0');
		assertFalse(dfaSwap.accepts("0"));
		assertTrue(dfaSwap.accepts("121"));
		assertFalse(dfaSwap.accepts("00"));
		assertTrue(dfaSwap.accepts("2211001120121012"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa5Swap accept pass");
	}

	//------------------- dfa6 tests ----------------------//
	private DFA dfa6() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		dfa.addSigma('2');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.addState("d"));
		assertTrue(dfa.addState("e"));
		assertTrue(dfa.addState("f"));
		assertTrue(dfa.addState("g"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("g"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.addState("d"));
		assertFalse(dfa.setStart("z"));
		assertFalse(dfa.setFinal("x"));

		assertTrue(dfa.addTransition("a", "b", '0'));
		assertTrue(dfa.addTransition("a", "a", '1'));
		assertTrue(dfa.addTransition("a", "a", '2'));
		assertTrue(dfa.addTransition("b", "b", '0'));
		assertTrue(dfa.addTransition("b", "c", '1'));
		assertTrue(dfa.addTransition("b", "a", '2'));
		assertTrue(dfa.addTransition("c", "d", '0'));
		assertTrue(dfa.addTransition("c", "a", '1'));
		assertTrue(dfa.addTransition("c", "a", '2'));
		assertTrue(dfa.addTransition("d", "d", '0'));
		assertTrue(dfa.addTransition("d", "e", '1'));
		assertTrue(dfa.addTransition("d", "d", '2'));
		assertTrue(dfa.addTransition("e", "f", '0'));
		assertTrue(dfa.addTransition("e", "e", '1'));
		assertTrue(dfa.addTransition("e", "d", '2'));
		assertTrue(dfa.addTransition("f", "d", '0'));
		assertTrue(dfa.addTransition("f", "g", '1'));
		assertTrue(dfa.addTransition("f", "d", '2'));
		assertTrue(dfa.addTransition("g", "g", '0'));
		assertTrue(dfa.addTransition("g", "g", '1'));
		assertTrue(dfa.addTransition("g", "g", '2'));


		assertFalse(dfa.addTransition("a", "a", '9'));
		assertFalse(dfa.addTransition("b", "z", '2'));
		assertFalse(dfa.addTransition("z", "d", '0'));

		return dfa;
	}

	@Test
	public void test6_1() {
		DFA dfa = dfa6();
		System.out.println("dfa6 instantiation pass");
	}

	@Test
	public void test6_2() {
		DFA dfa = dfa6();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("g"));
		assertEquals(dfa.getSigma(), Set.of('0','1', '2'));

		System.out.println("dfa6 correctness pass");
	}

	@Test
	public void test6_3() {
		DFA dfa = dfa6();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("010101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("0201201020011221102101210"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa6 accept pass");
	}

	@Test
	public void test6_4() {
		DFA dfa = dfa6();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c d e f g }\n"
				+ "Sigma = { 0 1 2 }\n"
				+ "delta =\n"
				+ "		0	1	2\n"
				+ "	a	b	a	a\n"
				+ "	b	b	c	a\n"
				+ "	c	d	a	a\n"
				+ "	d	d	e	d\n"
				+ "	e	f	e	d\n"
				+ "	f	d	g	d\n"
				+ "	g	g	g	g\n"
				+ "q0 = a\n"
				+ "F = { g }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa6 toString pass");
	}



	@Test
	public void test6_5() {
		DFA dfa = dfa6();
		DFA dfaSwap = dfa.swap('2', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("010101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("0201201020011221102101210"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa6Swap instantiation pass");
	}

	@Test
	public void test6_6() {
		DFA dfa = dfa6();
		DFA dfaSwap = dfa.swap('2', '0');
		assertFalse(dfaSwap.accepts("0"));
		assertTrue(dfaSwap.accepts("212121"));
		assertFalse(dfaSwap.accepts("00"));
		assertTrue(dfaSwap.accepts("2021021202211001120121012"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa6Swap accept pass");
	}

	//------------------- dfa7 tests ----------------------//
	private DFA dfa7() {
		DFA dfa = new DFA();
		dfa.addSigma('1');
		dfa.addSigma('0');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("c"));
		assertFalse(dfa.setFinal("c"));

		assertTrue(dfa.addTransition("a", "b", '0'));
		assertTrue(dfa.addTransition("a", "a", '1'));
		assertTrue(dfa.addTransition("b", "b", '0'));
		assertTrue(dfa.addTransition("b", "a", '1'));

		assertFalse(dfa.addTransition("c", "b", '1'));
		assertFalse(dfa.addTransition("a", "c", '1'));
		assertFalse(dfa.addTransition("a", "b", '2'));

		return dfa;
	}

	@Test
	public void test7_1() {
		DFA dfa = dfa7();
		System.out.println("dfa7 instantiation pass");
	}

	@Test
	public void test7_2() {
		DFA dfa = dfa7();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa7 correctness pass");
	}

	@Test
	public void test7_3() {
		DFA dfa = dfa7();

		assertFalse(dfa.accepts("1"));
		assertTrue(dfa.accepts("0"));
		assertFalse(dfa.accepts("11"));
		assertTrue(dfa.accepts("010"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa7 accept pass");
	}

	@Test
	public void test7_4() {
		DFA dfa = dfa7();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b }\n"
				+ "Sigma = { 1 0 }\n"
				+ "delta =\n"
				+ "		1	0\n"
				+ "	a	a	b\n"
				+ "	b	a	b\n"
				+ "q0 = a\n"
				+ "F = { b }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa7 toString pass");
	}



	@Test
	public void test7_5() {
		DFA dfa = dfa7();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("1"));
		assertTrue(dfa.accepts("0"));
		assertFalse(dfa.accepts("11"));
		assertTrue(dfa.accepts("010"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa7Swap instantiation pass");
	}

	@Test
	public void test7_6() {
		DFA dfa = dfa7();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("0"));
		assertTrue(dfaSwap.accepts("1"));
		assertFalse(dfaSwap.accepts("00"));
		assertTrue(dfaSwap.accepts("101"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa7Swap accept pass");
	}

	//------------------- dfa8 tests ----------------------//
	private DFA dfa8() {
		DFA dfa = new DFA();
		dfa.addSigma('1');
		dfa.addSigma('0');

		assertTrue(dfa.addState("3"));
		assertTrue(dfa.setFinal("3"));

		assertTrue(dfa.addState("0"));
		assertTrue(dfa.setStart("0"));

		assertTrue(dfa.addState("1"));
		assertTrue(dfa.addState("2"));


		assertFalse(dfa.setFinal("c"));
		assertFalse(dfa.setStart("a"));
		assertFalse(dfa.addState("2"));

		assertTrue(dfa.addTransition("0", "1", '1'));
		assertTrue(dfa.addTransition("0", "0", '0'));
		assertTrue(dfa.addTransition("1", "3", '1'));
		assertTrue(dfa.addTransition("1", "2", '0'));
		assertTrue(dfa.addTransition("2", "1", '1'));
		assertTrue(dfa.addTransition("2", "1", '0'));
		assertTrue(dfa.addTransition("3", "3", '1'));
		assertTrue(dfa.addTransition("3", "3", '0'));

		assertFalse(dfa.addTransition("3", "a", '1'));
		assertFalse(dfa.addTransition("c", "a", '1'));
		assertFalse(dfa.addTransition("3", "a", '2'));

		return dfa;
	}

	@Test
	public void test8_1() {
		DFA dfa = dfa8();
		System.out.println("dfa8 instantiation pass");
	}

	@Test
	public void test8_2() {
		DFA dfa = dfa8();
		assertNotNull(dfa.getState("0"));
		assertEquals(dfa.getState("1").getName(),"1");
		assertTrue(dfa.isStart("0"));
		assertNotNull(dfa.getState("3"));
		assertEquals(dfa.getState("3").getName(),"3");
		assertTrue(dfa.isFinal("3"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa8 correctness pass");
	}

	@Test
	public void test8_3() {
		DFA dfa = dfa8();
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("11"));
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("000100000000001"));
		assertFalse(dfa.accepts("0001000000000101"));

		System.out.println("dfa8 accept pass");
	}

	@Test
	public void test8_4() {
		DFA dfa = dfa8();

		String dfaStr = dfa.toString();
		String expStr = "Q={3 0 1 2}\n"
				+ "Sigma = {1 0}\n"
				+ "delta =\n"
				+ "		1	0\n"
				+ "3	3	3\n"
				+ "0	1	0\n"
				+ "1	3	2\n"
				+ "2	1	1\n"
				+ "q0 = 0\n"
				+ "F={3}\n";
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa8 toString pass");
	}



	@Test
	public void test8_5() {
		DFA dfa = dfa8();
		DFA dfaSwap = dfa.swap('1', '0');
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		//different DFA states
		assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
		assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
		assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
		assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
		assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));

		//ensure that the transitions of the original DFA don't change
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("11"));
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("000100000000001"));
		assertFalse(dfa.accepts("0001000000000101"));

		System.out.println("dfa8Swap instantiation pass");
	}

	@Test
	public void test8_6() {
		DFA dfa = dfa8();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("010"));
		assertTrue(dfaSwap.accepts("00"));
		assertFalse(dfaSwap.accepts("101"));
		assertTrue(dfaSwap.accepts("111011111111110"));
		assertFalse(dfaSwap.accepts("1110111111111010"));
		System.out.println("dfa8Swap accept pass");
	}

	//------------------- dfa9 tests ----------------------//
	private DFA dfa9() {
		DFA dfa = new DFA();
		dfa.addSigma('1');
		dfa.addSigma('2');

		assertTrue(dfa.addState("G"));
		assertTrue(dfa.addState("D"));

		assertTrue(dfa.setFinal("G"));
		assertTrue(dfa.setFinal("D"));

		assertTrue(dfa.addState("A"));
		assertTrue(dfa.setStart("D"));
		assertTrue(dfa.setStart("A"));

		assertTrue(dfa.addState("B"));
		assertTrue(dfa.addState("C"));
		assertTrue(dfa.addState("E"));
		assertTrue(dfa.addState("F"));

		assertFalse(dfa.addState("A"));
		assertFalse(dfa.setFinal("K"));
		assertFalse(dfa.setStart("BK"));

		assertTrue(dfa.addTransition("A", "B", '2'));
		assertTrue(dfa.addTransition("A", "C", '1'));

		assertTrue(dfa.addTransition("B", "D", '2'));
		assertTrue(dfa.addTransition("B", "E", '1'));

		assertTrue(dfa.addTransition("C", "F", '2'));
		assertTrue(dfa.addTransition("C", "G", '1'));

		assertTrue(dfa.addTransition("C", "F", '2'));
		assertTrue(dfa.addTransition("C", "G", '1'));

		assertTrue(dfa.addTransition("D", "D", '2'));
		assertTrue(dfa.addTransition("D", "E", '1'));

		assertTrue(dfa.addTransition("E", "D", '2'));
		assertTrue(dfa.addTransition("E", "E", '1'));

		assertTrue(dfa.addTransition("F", "F", '2'));
		assertTrue(dfa.addTransition("F", "G", '1'));

		assertTrue(dfa.addTransition("G", "F", '2'));
		assertTrue(dfa.addTransition("G", "G", '1'));


		assertFalse(dfa.addTransition("FF", "F", '1'));
		assertFalse(dfa.addTransition("F", "GG", '2'));

		assertFalse(dfa.addTransition("G", "F", 'K'));
		assertFalse(dfa.addTransition("A", "K", '7'));

		return dfa;
	}

	@Test
	public void test9_1() {
		DFA dfa = dfa9();

		System.out.println("dfa9 instantiation pass");
	}

	@Test
	public void test9_2() {
		DFA dfa = dfa9();
		assertNotNull(dfa.getState("A"));
		assertNull(dfa.getState("K"));
		assertEquals(dfa.getState("C").getName(),"C");
		assertTrue(dfa.isStart("A"));
		assertFalse(dfa.isStart("D"));
		assertNotNull(dfa.getState("G"));
		assertEquals(dfa.getState("E").getName(),"E");
		assertTrue(dfa.isFinal("G"));
		assertFalse(dfa.isFinal("B"));
		assertEquals(dfa.getSigma(), Set.of('2','1'));

		System.out.println("dfa9 correctness pass");
	}

	@Test
	public void test9_3() {
		DFA dfa = dfa9();
		assertTrue(dfa.accepts("212121212"));
		assertTrue(dfa.accepts("21112121212"));
		assertFalse(dfa.accepts("21"));
		assertFalse(dfa.accepts("1"));
		assertFalse(dfa.accepts("2121"));

		System.out.println("dfa9 accept pass");
	}

	@Test
	public void test9_4() {
		DFA dfa = dfa9();

		String dfaStr = dfa.toString();
		String expStr = "Q={GDABCEF}\n"
				+ "Sigma = {1 2}\n"
				+ "delta =\n"
				+ "		1	2\n"
				+ "G	G	F\n"
				+ "D	E	D\n"
				+ "A	C	B\n"
				+ "B	E	D\n"
				+ "C	G	F\n"
				+ "E	E	D\n"
				+ "F	G	F\n"
				+ "q0 = A\n"
				+ "F = {G D}\n";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa9 toString pass");
	}



	@Test
	public void test9_5() {
		DFA dfa = dfa9();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfa != dfaSwap);
		assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
		assertTrue(dfa.getState("G") != dfaSwap.getState("G"));
		assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
		assertEquals(dfa.isStart("D"), dfaSwap.isStart("D"));
		assertEquals(dfa.isFinal("A"), dfaSwap.isFinal("A"));

		//transitions of the original dfa should not change
		assertTrue(dfa.accepts("212121212"));
		assertTrue(dfa.accepts("21112121212"));
		assertFalse(dfa.accepts("21"));
		assertFalse(dfa.accepts("1"));
		assertFalse(dfa.accepts("2121"));

		System.out.println("dfa9Swap instantiation pass");
	}

	@Test
	public void test9_6() {
		DFA dfa = dfa9();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfaSwap.accepts("121212121"));
		assertTrue(dfaSwap.accepts("12221212121"));
		assertFalse(dfaSwap.accepts("12"));
		assertFalse(dfaSwap.accepts("2"));
		assertFalse(dfaSwap.accepts("1212"));

		System.out.println("dfa9Swap accept pass");
	}

	//------------------- dfa10 tests ----------------------//
	private DFA dfa10() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("c"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("d"));
		assertFalse(dfa.setFinal("d"));

		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "c", '0'));
		assertTrue(dfa.addTransition("b", "a", '1'));
		assertTrue(dfa.addTransition("c", "b", '0'));
		assertTrue(dfa.addTransition("c", "c", '1'));

		assertFalse(dfa.addTransition("c", "b", '9'));
		assertFalse(dfa.addTransition("a", "z", '1'));
		assertFalse(dfa.addTransition("z", "b", '2'));

		return dfa;
	}

	@Test
	public void test10_1() {
		DFA dfa = dfa10();
		System.out.println("dfa10 instantiation pass");
	}

	@Test
	public void test10_2() {
		DFA dfa = dfa10();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("c"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa10 correctness pass");
	}

	@Test
	public void test10_3() {
		DFA dfa = dfa10();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("10"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa10 accept pass");
	}

	@Test
	public void test10_4() {
		DFA dfa = dfa10();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	c	a\n"
				+ "	c	b	c\n"
				+ "q0 = a\n"
				+ "F = { c }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa10 toString pass");
	}

	@Test
	public void test10_5() {
		DFA dfa = dfa10();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("10"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa10Swap instantiation pass");
	}

	@Test
	public void test10_6() {
		DFA dfa = dfa10();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("01"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa10Swap accept pass");
	}

	//------------------- dfa11 tests ----------------------//
	private DFA dfa11() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("d"));
		assertFalse(dfa.setFinal("d"));

		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "c", '0'));
		assertTrue(dfa.addTransition("b", "a", '1'));
		assertTrue(dfa.addTransition("c", "c", '0'));
		assertTrue(dfa.addTransition("c", "b", '1'));

		assertFalse(dfa.addTransition("c", "b", '9'));
		assertFalse(dfa.addTransition("a", "z", '1'));
		assertFalse(dfa.addTransition("z", "b", '2'));

		return dfa;
	}

	@Test
	public void test11_1() {
		DFA dfa = dfa11();
		System.out.println("dfa11 instantiation pass");
	}

	@Test
	public void test11_2() {
		DFA dfa = dfa11();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa11 correctness pass");
	}

	@Test
	public void test11_3() {
		DFA dfa = dfa11();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("1001"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa11 accept pass");
	}

	@Test
	public void test11_4() {
		DFA dfa = dfa11();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	c	a\n"
				+ "	c	c	b\n"
				+ "q0 = a\n"
				+ "F = { b }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa11 toString pass");
	}

	@Test
	public void test11_5() {
		DFA dfa = dfa11();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("1001"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa11Swap instantiation pass");
	}

	@Test
	public void test11_6() {
		DFA dfa = dfa11();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("0110"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa11Swap accept pass");
	}

	//------------------- dfa12 tests ----------------------//
	private DFA dfa12() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("a"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("d"));
		assertFalse(dfa.setFinal("d"));

		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "c", '0'));
		assertTrue(dfa.addTransition("b", "a", '1'));
		assertTrue(dfa.addTransition("c", "c", '0'));
		assertTrue(dfa.addTransition("c", "b", '1'));

		assertFalse(dfa.addTransition("c", "b", '9'));
		assertFalse(dfa.addTransition("a", "z", '1'));
		assertFalse(dfa.addTransition("z", "b", '2'));

		return dfa;
	}

	@Test
	public void test12_1() {
		DFA dfa = dfa12();
		System.out.println("dfa12 instantiation pass");
	}

	@Test
	public void test12_2() {
		DFA dfa = dfa12();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("a"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa12 correctness pass");
	}

	@Test
	public void test12_3() {
		DFA dfa = dfa12();

		assertFalse(dfa.accepts("1"));
		assertTrue(dfa.accepts(""));
		assertFalse(dfa.accepts("10"));
		assertTrue(dfa.accepts("1011"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa12 accept pass");
	}

	@Test
	public void test12_4() {
		DFA dfa = dfa12();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	c	a\n"
				+ "	c	c	b\n"
				+ "q0 = a\n"
				+ "F = { a }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa12 toString pass");
	}

	@Test
	public void test12_5() {
		DFA dfa = dfa12();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("1"));
		assertTrue(dfa.accepts(""));
		assertFalse(dfa.accepts("10"));
		assertTrue(dfa.accepts("1011"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa12Swap instantiation pass");
	}

	@Test
	public void test12_6() {
		DFA dfa = dfa12();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("0"));
		assertTrue(dfaSwap.accepts(""));
		assertFalse(dfaSwap.accepts("01"));
		assertTrue(dfaSwap.accepts("0100"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa12Swap accept pass");
	}

	//------------------- dfa13 tests ----------------------//
	private DFA dfa13() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.addState("d"));
		assertTrue(dfa.addState("e"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("e"));
		assertTrue(dfa.setFinal("c"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("f"));
		assertFalse(dfa.setFinal("f"));

		assertTrue(dfa.addTransition("a", "b", '0'));
		assertTrue(dfa.addTransition("a", "d", '1'));
		assertTrue(dfa.addTransition("b", "c", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));
		assertTrue(dfa.addTransition("c", "c", '0'));
		assertTrue(dfa.addTransition("c", "c", '1'));
		assertTrue(dfa.addTransition("d", "d", '0'));
		assertTrue(dfa.addTransition("d", "e", '1'));
		assertTrue(dfa.addTransition("e", "e", '0'));
		assertTrue(dfa.addTransition("e", "e", '1'));

		assertFalse(dfa.addTransition("c", "b", '9'));
		assertFalse(dfa.addTransition("a", "z", '1'));
		assertFalse(dfa.addTransition("z", "b", '2'));

		return dfa;
	}

	@Test
	public void test13_1() {
		DFA dfa = dfa13();
		System.out.println("dfa13 instantiation pass");
	}

	@Test
	public void test13_2() {
		DFA dfa = dfa13();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("c"));
		assertTrue(dfa.isFinal("e"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa13 correctness pass");
	}

	@Test
	public void test13_3() {
		DFA dfa = dfa13();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("00"));
		assertTrue(dfa.accepts("11"));
		assertFalse(dfa.accepts("10"));
		assertTrue(dfa.accepts("0101"));
		assertTrue(dfa.accepts("1010"));
		assertFalse(dfa.accepts("f"));

		System.out.println("dfa13 accept pass");
	}

	@Test
	public void test13_4() {
		DFA dfa = dfa13();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c d e }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	b	d\n"
				+ "	b	c	b\n"
				+ "	c	c	c\n"
				+ "	d	d	e\n"
				+ "	e	e	e\n"
				+ "q0 = a\n"
				+ "F = { e c }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa13 toString pass");
	}

	@Test
	public void test13_5() {
		DFA dfa = dfa13();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("00"));
		assertTrue(dfa.accepts("11"));
		assertFalse(dfa.accepts("10"));
		assertTrue(dfa.accepts("0101"));
		assertTrue(dfa.accepts("1010"));
		assertFalse(dfa.accepts("f"));

		System.out.println("dfa13Swap instantiation pass");
	}

	@Test
	public void test13_6() {
		DFA dfa = dfa13();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("00"));
		assertFalse(dfaSwap.accepts("01"));
		assertTrue(dfaSwap.accepts("1010"));
		assertTrue(dfaSwap.accepts("0101"));
		assertFalse(dfaSwap.accepts("f"));

		System.out.println("dfa13Swap accept pass");
	}

	//------------------- dfa14 tests ----------------------//
	private DFA dfa14() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.addState("c"));
		assertTrue(dfa.addState("d"));
		assertTrue(dfa.addState("e"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("e"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("f"));
		assertFalse(dfa.setFinal("f"));

		assertTrue(dfa.addTransition("a", "b", '0'));
		assertTrue(dfa.addTransition("a", "c", '1'));
		assertTrue(dfa.addTransition("b", "a", '0'));
		assertTrue(dfa.addTransition("b", "a", '1'));
		assertTrue(dfa.addTransition("c", "e", '0'));
		assertTrue(dfa.addTransition("c", "d", '1'));
		assertTrue(dfa.addTransition("d", "c", '0'));
		assertTrue(dfa.addTransition("d", "c", '1'));
		assertTrue(dfa.addTransition("e", "e", '0'));
		assertTrue(dfa.addTransition("e", "e", '1'));

		assertFalse(dfa.addTransition("c", "b", '9'));
		assertFalse(dfa.addTransition("a", "z", '1'));
		assertFalse(dfa.addTransition("z", "b", '2'));

		return dfa;
	}

	@Test
	public void test14_1() {
		DFA dfa = dfa14();
		System.out.println("dfa14 instantiation pass");
	}

	@Test
	public void test14_2() {
		DFA dfa = dfa14();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("e"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));

		System.out.println("dfa14 correctness pass");
	}

	@Test
	public void test14_3() {
		DFA dfa = dfa14();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("10"));
		assertFalse(dfa.accepts("01"));
		assertTrue(dfa.accepts("100001"));
		assertTrue(dfa.accepts("1100"));
		assertFalse(dfa.accepts("f"));

		System.out.println("dfa14 accept pass");
	}

	@Test
	public void test14_4() {
		DFA dfa = dfa14();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b c d e }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	b	c\n"
				+ "	b	a	a\n"
				+ "	c	e	d\n"
				+ "	d	c	c\n"
				+ "	e	e	e\n"
				+ "q0 = a\n"
				+ "F = { e }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa14 toString pass");
	}

	@Test
	public void test14_5() {
		DFA dfa = dfa14();
		DFA dfaSwap = dfa.swap('1', '0');

		//different DFA objects
		assertTrue(dfa != dfaSwap);

		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("10"));
		assertFalse(dfa.accepts("01"));
		assertTrue(dfa.accepts("100001"));
		assertTrue(dfa.accepts("1100"));
		assertFalse(dfa.accepts("f"));

		System.out.println("dfa14Swap instantiation pass");
	}

	@Test
	public void test14_6() {
		DFA dfa = dfa14();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("01"));
		assertFalse(dfaSwap.accepts("10"));
		assertTrue(dfaSwap.accepts("0111110"));
		assertTrue(dfaSwap.accepts("0011"));
		assertFalse(dfaSwap.accepts("f"));

		System.out.println("dfa14Swap accept pass");
	}

}

