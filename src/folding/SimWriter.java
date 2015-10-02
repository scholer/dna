/**
 * Copyright (c) 2015 FRITS DANNENBERG 
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package folding;

import writers.JsonParser;
import writers.ListWriter;

public class SimWriter
{
	Design design;
	int id, count;
	JsonParser jsonParser;
	ListWriter wList;
	boolean toggle;
	boolean isClosed;

	public SimWriter(Design design2, int id2)
	{
		design = design2;
		id = id2;
		jsonParser = new JsonParser(design);
		wList = new ListWriter(design.outDir + "/json");

		wList.addWriter("/combined-path-" + id + ".json");
		wList.writeToFile("\n [", 0);

		count = 0;

		toggle = false;
		isClosed = false;
	}

	public void write(State state, double temp)
	{
		if (toggle) {
			wList.writeToFile(",", 0);
		} else {
			toggle = true;
		}

		String output = jsonParser.writeJsonSequential(state, (temp - 273.15));
		wList.writeToFile(output + "\n ", 0);
		
		count++;
	}

	public void finalize()
	{
		// System.out.println("SimWriter: finalize() called...");
		// There is a potential problem where finalize could be called multiple times,
		// first in simulator.run() and then during deconstruction.
		// I've added a check to make sure we only add the closing bracket once.
		if (!isClosed) {
			// System.out.println("SimWriter: finalize() - writing closing bracket to file...");
			wList.writeToFile("\n ]", 0);
			isClosed = true;
		}
		//System.out.println("SimWriter: finalize() done");
	}

}
