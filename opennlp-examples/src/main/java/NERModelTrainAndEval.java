
import opennlp.tools.namefind.*;
import opennlp.tools.util.*;
import opennlp.tools.util.eval.FMeasure;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class NERModelTrainAndEval {

    public static String TRAIN_DATASET = "ner-loc.train";
    public static String GEN_MODEL = "ner-locations.bin";


    public NERModelTrainAndEval() {

    }

    public static void train() throws Exception {

        MarkableFileInputStreamFactory fileFactory =  new MarkableFileInputStreamFactory(new File(TRAIN_DATASET));
        ObjectStream<String> stream = new PlainTextByLineStream(fileFactory, StandardCharsets.UTF_8);

        TokenNameFinderModel model;
        TokenNameFinderFactory nameFinderFactory = new TokenNameFinderFactory();

        TrainingParameters trainingParameters = TrainingParameters.defaultParams();
        trainingParameters.put("Cutoff", 1);

        try (ObjectStream<NameSample> sampleStream = new NameSampleDataStream(stream)) {
            model = NameFinderME.train("en",
                    "state",
                    sampleStream,
                    trainingParameters,
                    nameFinderFactory);
        }


        try (BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream(GEN_MODEL))) {
            model.serialize(modelOut);
        }

    }

    public static void eval() throws  Exception {
        MarkableFileInputStreamFactory fileFactory =  new MarkableFileInputStreamFactory(new File(TRAIN_DATASET));
        PlainTextByLineStream stream = new PlainTextByLineStream(fileFactory, StandardCharsets.UTF_8);

        TokenNameFinderFactory nameFinderFactory = new TokenNameFinderFactory();

        TrainingParameters trainingParameters = TrainingParameters.defaultParams();
        trainingParameters.put("Cutoff", 1);

        ObjectStream<NameSample> sampleStream = new NameSampleDataStream(stream);
        TokenNameFinderCrossValidator evaluator = new TokenNameFinderCrossValidator("en",
                "state",
                trainingParameters,
                nameFinderFactory
        );

        evaluator.evaluate(sampleStream, 10);

        FMeasure result = evaluator.getFMeasure();

        System.out.println(result.toString());

    }

    public static void validate() throws  Exception {

        String documents[][] = new String[][] {
                new String[]{"Berlin"},
                new String[]{"Ball"},
                new String[]{"London","United Kingdom"}
        };

        try (InputStream modelIn = new FileInputStream(GEN_MODEL)){
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

                for (String[] sentence : documents) {
                    Span nameSpans[] = nameFinder.find(sentence);
                    for(Span span : nameSpans) {
                        System.out.println(span);
                    }
                    System.out.println("++++");
                }

                nameFinder.clearAdaptiveData();


        }
    }


    public static void main(String[] args) throws Exception {

       NERModelTrainAndEval.validate();

    }

}


