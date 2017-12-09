import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class NERModelOrgs {


    public static String MODEL = "en-ner-organization.bin";

    public static void main(String[] args) throws Exception {


        InputStream modelIn = new FileInputStream(MODEL);
        TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
        NameFinderME nameFinder = new NameFinderME(model);

        String[][] inputs = new String[][] {
                new String[] { "E.O.", "Wilson", "Biodiversity", "Foundation" },
                new String[] { "London",  "United Kingdom" },
                new String[] { "Creighton", "University", "School", "of", "Medicine" },
                new String[]{"Who", "is", "in", "The", "Matrix", "?"},
                new String[] { "barcelona", "Technical", "University", "of", "Berlin" },

        };

        ArrayList<TokenNameFinder> finders = new ArrayList<TokenNameFinder>();
        finders.add(nameFinder);

        for (String[] input : inputs) {

            for(TokenNameFinder finder : finders) {
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


