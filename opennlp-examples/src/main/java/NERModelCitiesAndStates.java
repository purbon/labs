import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NERModelCitiesAndStates {

    public static String TRAIN_DATASET = "states.dict";
    public static String GEN_MODEL = "ner-locations.bin";

    public static void main(String[] args) throws Exception {

        Dictionary dict = new Dictionary();

        try (BufferedReader br = new BufferedReader(new FileReader(TRAIN_DATASET))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.put(new StringList(line.split(" ")));
            }
        }

        Dictionary dict0 = new Dictionary();
        dict0.put(new StringList("Berlin"));
        dict0.put(new StringList("London"));
        dict0.put(new StringList("Barcelona"));
        dict0.put(new StringList("A", "Pobra", "do", "Brollo"));

        DictionaryNameFinder dnf = new DictionaryNameFinder(dict0, "Cities");
        DictionaryNameFinder dnfStates = new DictionaryNameFinder(dict, "State");

        ArrayList<DictionaryNameFinder> finders = new ArrayList<DictionaryNameFinder>();
        finders.add(dnf);
        finders.add(dnfStates);

        String[][] inputs = new String[][] {
                new String[] { "Berlin",  "Germany" },
                new String[] { "London",  "United Kingdom" },
                new String[] { "Ball" },
                new String[]{"Who", "is", "in", "The", "Matrix", "?"},
                new String[] { "barcelona",  "germayn" },

        };

        for (String[] input : inputs) {

            for(DictionaryNameFinder finder : finders) {
                Span[] spans = finder.find(input);

                for (int i = 0; i < spans.length; i++) {
                    for (int j = spans[i].getStart(); j < spans[i].getEnd(); j++)
                        System.out.printf("%s ", input[j]);
                    System.out.printf("%s\n", spans[i].toString());
                }
            }
        }

    }

}


