package gp;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import gp.func.AbstractNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BotGame extends GPProblem {

    static Path pathToBot = Paths.get("src/main/java/bot/GPBot.java");
    static String templateSeparator = "//## ##//";
    static {
        System.out.println(pathToBot.getFileName().toString());
    }
    static Random random = new Random();

    @Override
    public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
        if (ind.evaluated) { return; }// don't bother reevaluating

        GPIndividual gpInd = (GPIndividual) ind;
        GPNode gpNode = gpInd.trees[0].child;

        String body = ((AbstractNode)gpNode).eval();


        List<String> botTemplate = null;

        try {
            botTemplate = Files.readAllLines(pathToBot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> newFile = new ArrayList<>();
        for (String s : botTemplate) {
           if (s.contains(templateSeparator)) {
               newFile.add(body);
           } else {
               newFile.add(s);
           }

        }

        try {
            int randomInt = random.nextInt();
            String newBotPath = "tmp/GeneratedBot" + randomInt + ".java";
            BufferedWriter writer = new BufferedWriter(new FileWriter(newBotPath));
            for (String s : newFile) {
                writer.write(s);
                writer.write("\n");
            }
            writer.flush();
            writer.close();

            ProcessBuilder javac = new ProcessBuilder("javac", "-cp", "target", "-d", "target", newBotPath);
            javac.inheritIO();
            javac.start().waitFor();

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs/eval.log", true)));

            out.println("########### generate method ############");
            out.println(body);
            out.println("########### end new  method ############");

            int fitnessAcc = 0;
            int iters = 5;
            boolean[] done = new boolean[iters];
            CompletableFuture<Integer>[] promises = new CompletableFuture[iters];
            for (int i = 0; i < iters; i++) {
                promises[i] = CompletableFuture.supplyAsync(()-> {
                    try {
                        int res = runEmulation(out);
                        out.println("fitness " + res);
                        return res;
                    } catch (Exception e) {throw new RuntimeException(e);}
                });
            }

            int got = 0;
            while (got < iters) {
                for (int i = 0; i < iters; i++) {
                    if (promises[i].isDone() && !done[i]) {
                        fitnessAcc += promises[i].get();
                        got++;
                        done[i] = true;
                    }
                }
            }


            out.flush();

            KozaFitness f = ((KozaFitness)ind.fitness);
            f.setStandardizedFitness(state, fitnessAcc / iters);
            ind.evaluated = true;

            Files.delete(Paths.get(newBotPath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private int runEmulation(PrintWriter out) throws IOException, InterruptedException {
        ProcessBuilder gamePB = new ProcessBuilder("java", //"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999",
                "-cp", "target", "GameManager", "TemplateBot", "GPBot");

//            gamePB.inheritIO();

        Process gameP = gamePB.start();

        gameP.waitFor();

        BufferedReader gameStdOut = new BufferedReader(new InputStreamReader(gameP.getInputStream()));

        String result = gameStdOut.readLine();

        out.println(result);

        int fitnessRaw;
        String playerWon = result.split(" ")[0];
        int rounds = Integer.valueOf(result.split(" ")[1]);
        int upperBound = 1000;

        if (playerWon.equals("-")) {
            return upperBound / 2;
        } else {
            if (playerWon.equals("1")) {
                return rounds;
            } else {
                return upperBound - rounds;
            }
        }
    }
}
