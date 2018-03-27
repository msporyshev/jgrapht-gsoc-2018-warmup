package ms;

import org.jgrapht.io.ImportException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, ImportException
    {
        DotFamilyTreeImporter importer = new DotFamilyTreeImporter();
        FamilyTree tree = new FamilyTree();
        importer.importGraph(tree, new FileReader(args[0]));

        Set<String> ancestors = tree.findLcas(args[1], args[2]);
        if (ancestors.isEmpty()) {
            System.out.printf("There are no common ancestors for %s and %s \n", args[1], args[2]);
            return;
        }

        System.out.printf( "Least common ancestors of %s and %s are:\n", args[1], args[2]);
        for (String ancestor : ancestors) {
            System.out.println(ancestor);
        }
    }
}
