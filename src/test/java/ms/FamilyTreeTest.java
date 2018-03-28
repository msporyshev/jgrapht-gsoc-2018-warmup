package ms;

import org.jgrapht.alg.cycle.DirectedSimpleCycles;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ImportException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FamilyTreeTest {
    private FamilyTree tree;

    public void assertAllPairsHaveLcas(List<String> siblings, List<String> lcas) {
        Set<String> expectLcas = new TreeSet<>(lcas);
        for (String first : siblings) {
            for (String second : siblings) {
                if (!first.equals(second)) {
                    Set<String> gotLcas = tree.findLcas(first, second);
                    assertEquals(expectLcas, gotLcas);
                }
            }
        }
    }

    public void assertHaveLcas(String first, String second, String ... lcas) {
        Set<String> expectLcas = new TreeSet<>(Arrays.asList(lcas));
        assertEquals(expectLcas, tree.findLcas(first, second));
    }

    @Before
    public void createTree() throws FileNotFoundException, ImportException {
        DotFamilyTreeImporter importer = new DotFamilyTreeImporter();
        tree = new FamilyTree();
        importer.importGraph(tree, new FileReader("got.dot"));
    }

    @Test
    public void familyTreeIsTree() {
        DirectedSimpleCycles<String, DefaultEdge> cyclesFinder = new HawickJamesSimpleCycles<>(tree.getGraph());
        assertTrue(cyclesFinder.findSimpleCycles().size() == 0);
    }

    @Test
    public void commonAncestorEddard() {
        assertAllPairsHaveLcas(Arrays.asList("Robb", "Sansa", "Arya", "Rickon", "Brandon", "Jon"), Arrays.asList("Eddard"));
    }

    @Test
    public void commonAncestorRickard1() {
        assertAllPairsHaveLcas(Arrays.asList("Eddard", "Benjen", "Lyanna"), Arrays.asList("Rickard"));
    }

    @Test
    public void commonAncestorSteffon() {
        assertAllPairsHaveLcas(Arrays.asList("Shireen", "Robert", "Renly"), Arrays.asList("Steffon"));
    }

    @Test
    public void commonAncestorRickard2() {
        assertHaveLcas("Robb", "Benjen", "Rickard");
        assertHaveLcas("Robb", "Lyanna", "Rickard");
        assertHaveLcas("Brandon", "Benjen", "Rickard");
        assertHaveLcas("Brandon", "Lyanna", "Rickard");
    }

    @Test
    public void haveMultipleLcas() {
        assertHaveLcas("Joffrey", "Tommen", "Jaime", "Cersei", "Robert");
        assertHaveLcas("Joffrey", "Myrcellar", "Jaime", "Cersei", "Robert");
        assertHaveLcas("Tommen", "Myrcellar", "Jaime", "Cersei", "Robert");
        assertHaveLcas("Jaime", "Tyrion", "Tywin", "Joanna");
    }
    @Test
    public void differentHousesLca() {
        assertHaveLcas("Jon", "Aegon", "Rhaegar");
        assertHaveLcas("Tommen", "Aegon", "Aegon_V");
        assertHaveLcas("Jon", "Shireen", "Aegon_V");
        assertHaveLcas("Joffrey", "Tyrion", "Tywin", "Joanna");
    }
}
