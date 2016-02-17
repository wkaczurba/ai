package exampleGeneticPolynom1;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleGeneticPolynom1Wrapper extends Example {
	@Override
	public String getName() {
		return "GeneticPolynom1";
	}

	@Override
	public String getInfo() {

		return ""
                   + "The algorithm is supposed to find polynomial approximation of sine function\n"
				   + "( f(x) = sin(x) for x within range: <6.28318 … 12.58>. ).\n"
				   + "\n"
				   + "The fitness expresses error between polynomial approximation and sine.\n"
				   + "The error is calculated for 63 values -> { 6.28, 6.38, 6.48 … 12.48 12.58 } in\n"
				   + "the following fashion: ( error = abs ( f(x) - est(x) ), where est(x) is the\n"
				   + "polynomial (A*x^7 + B*x^6 + C*x^5 + D*x^4 + E*x^3 + F*x^2 + G*x + H.)\n"
				   + "\n"
				   + "********************\n"
				   + "** Representaiton **\n"
				   + "********************\n"
				   + "\n"
				   + "The polynomial is is written as A*x^7 + B*x^6 + C*x^5 + D*x^4 + E*x^3 + F*x^2 + G*x + H.\n"
				   + "Values A...G are expressed as 32-bit long float. \n"
				   + "Float is represented as 16-characters long string containinig values within\n"
				   + "range 0...3 for each of the characters. These characters are the genetic code.\n"
				   + "\n"
				   + "  | 0 3 2 1 3 2 4 2 3 0 2 1 3 0 2 3 | 3 2 1 0 0 2 3 ... ... ...\n"
				   + "Float representation:\n"
				   + "  | 0 3 2 1 3 2 4 2 3 0 2 1 3 0 2 3 |\n"
				   + "\n"
				   + "Each of the values (range [0...3]) (in the cells) are called “Duals” (two-bits) for simplicity.\n"
				   + "Conversion of \"Duals\" <-> float values are handled by Duals.java file.\n"
				   + "\n"
				   + "**************\n"
				   + "** Results: ** \n"
				   + "**************\n"
				   + "\n"
				   + "The genetic algorithm takes significant amount of time for calculation.\n"
				   + "During the initial code generation - only 0.6% of initially generated\n"
				   + "values have value of absolute error less than 0.6%.\n"
				   + "90% of initially generated population have absolute error larger than\n"
				   + "1E20 (ceiling of the error). This suggests that there is a very narrow\n"
				   + "subset of values that are within any valid range.\n"
				   + "\n"
				   + "The representation has following flaws:\n"
				   + "- very narrow subset of genes that are within valid range may cause tournament\n"
				   + "  selection to converge too quickly.\n"
				   + "- random mutation are more likely to end up at the ceiling of error (1E20) with\n"
				   + "  this representation. \n"
				   + "\n"
				   + "The reason for high occurance of highly erroenous polyonmial (i.e. polynomial\n"
				   + "estimator that has a very high error measured against estimated sin(x) function )\n"
				   + "is the representaiton of float. Floats have exponent that are “easy” to hit with\n"
				   + "randomization:\n"
				   + "\n"
				   + "Bits: (31) (30-23)  (22-0)\n"
				   + "\n"
				   + "       1  10000011  01010010100000000000000\n"
				   + "\n"
				   + "      /         |                \\n"
				   + " Sign=-1   Exponent = 4  Mantissa=1.322265625\n"
				   + "\n"
				   + "** Changing of representation: **\n"
				   + "\n"
				   + "Duals.java allows to change the representation. \n"
				   + "The exponent instead of being within range of 2^-127 … 2^127 can be limited to 2^-16 ...  2^16.\n"
				   + "\n"
				   + "In order to do that - boolean includeRangeModulo should be changed from false to true.\n\n"
				   + "**********************************************************************************\n"
				   + "** Results after changing the representation (exponent limited to 2^-16...2^16) **\n"
				   + "***********************************************************************************\n\n"
				   + "Initially it appeared that changing representation should improve the algorithm.\n"
				   + "Interestingly this was not the case: limiting the exponent does not lead to generation of better results.\n"
				   + "\n"
				   + "This is likely linked to the fact that: polynomial:\n"
				   + "  A*x^7 + B*x^6 + C*x^5 + D*x^4 + E*x^3 + F*x^2 + G*x + H.\n"
				   + "appears to be progressing towards a linear function Gx + H.\n"
				   + "Limiting exponent range to 2^-16...2^16 could potentially \n"
				   + "prevent from getting values A... F close to 0 (i.e.setting exponent towards 2^-127).\n"
				   + "\n"
				   + "*******************************************\n"
				   + "** Increasing reference function sin(x). **\n"
				   + "*******************************************\n"
				   + "\n"
				   + "The problem with linearization of function can be solved by increasing amplitude\n"
				   + "of reference sine wave or level of error feedback. Instead of using f(x) = sin(x) as a\n"
				   + "reference function - it is better to use f(x) = 100*sin(x). With f(x) =100*sin(x) polynomial\n"
				   + "is less likely to be estimated as a linear function f(x)=ax + b\n"
				   + "\n";
	}
		
	public void run() {
		if (panel == null)
			return;
		
		emulateConsoleAndRedirectStream();

		
		new Main();

		// REFRESH+RESTORE:
		this.refreshPanel();
		this.restoreSystemOutPrintStream();
	}
}
