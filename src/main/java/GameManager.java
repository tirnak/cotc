import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameManager {

    private static String winner;
//    private static List<String> validBotNames;
//    static {
//
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 2) { throw new RuntimeException("there must be 2 bot classes mentioned as args"); }
        ProcessWrapper refereeProcessW = new ProcessWrapper(getProcess("Referee"), "referee", true);
        ProcessWrapper bot0ProcessW = new ProcessWrapper(getProcess("bot."+args[0]), args[0]+"0", true);
        ProcessWrapper bot1ProcessW = new ProcessWrapper(getProcess("bot."+args[1]), args[1]+"1", true);
        ProcessWrapper[] bots = new ProcessWrapper[]{bot0ProcessW, bot1ProcessW};

        refereeProcessW.toStdIn("Hiii");
        refereeProcessW.fromStdOut(2); //skip ###Input
        int rounds = 0;
        outer: while (true) {
            for (ProcessWrapper bot : bots) {
                String cmdFromReferee = refereeProcessW.fromStdOut(1).get(0);
                if (validateCmd(cmdFromReferee)) {break outer;}

                List<String> first2Lines = refereeProcessW.fromStdOut(2);
                int shipNum = Integer.valueOf(first2Lines.get(0));
                int entitiesNum = Integer.valueOf(first2Lines.get(1));
                first2Lines.forEach(bot::toStdIn);

                refereeProcessW.fromStdOut(entitiesNum).forEach(bot::toStdIn);

                refereeProcessW.fromStdOut(1);

                bot.fromStdOut(shipNum).forEach(refereeProcessW::toStdIn);

            }
            rounds++;
        }
        System.out.println(winner + " " + rounds);
    }

    private static boolean validateCmd(String cmdFromReferee) {
        if (cmdFromReferee.contains("End")) {
            String[] split = cmdFromReferee.split(" ");
            for (String s : split) {
                System.out.println("split " + s);
            }
            if (split.length == 2) { winner = "-"; }
            // beware - inverse logic
            // ###End 0 1 means that bot0 won
            else if (split[1].equals("1")) { winner = "1"; }
            else { winner = "0"; }
            return true;
        }
        if (cmdFromReferee.contains("Error")) {
            int playerLostId = Integer.valueOf(cmdFromReferee.split(" ")[1]);
            winner = playerLostId == 0 ? "1" : "0";
            return true;
        }
        return false;
    }

    private static Process getProcess(String className) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
            new String[]{"java", "-cp", ".", className});
        inheritEnv(processBuilder.environment());
        setDirectory(processBuilder, className);
//        processBuilder.redirectErrorStream(true);
//        processBuilder.inheritIO();
        return processBuilder.start();
    }

    private static void inheritEnv(Map<String, String> inheritorEnv) {
        Map<String, String> curEnv = System.getenv();
        for (Map.Entry<String, String> var : curEnv.entrySet())
            inheritorEnv.put(var.getKey(), var.getValue());
    }

    private static void setDirectory(ProcessBuilder pb, String targetClassName) {
        String current = ".";
        List<String> lsed = ls(current);
        if (lsed.contains(targetClassName)) return;
        String targetDir = "target";
//        if (lsed.contains(targetDir) && contains(ls(targetDir), targetClassName)) {
            pb.directory(new File(targetDir));
//        }
    }

    private static boolean contains(List<String> hay, String needle) {
        for (String s : hay) {
            if (s.contains(needle)) return true;
        }
        return false;
    }

    private static List<String> ls(String path) {
        System.out.println("path: " + path);
        System.out.println("files: " + Arrays.toString(new File(path).listFiles()));

        return Arrays.asList(new File( path ).listFiles()).stream().map(File::getName).collect(Collectors.toList());
    }
}
