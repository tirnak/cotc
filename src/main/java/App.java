import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessWrapper refereeProcessW = new ProcessWrapper(getProcess("Referee"), "referee", true);
        ProcessWrapper bot1ProcessW = new ProcessWrapper(getProcess("bot.Player"), "bot1", true);
        ProcessWrapper bot2ProcessW = new ProcessWrapper(getProcess("bot.Player"), "bot1", true);
        ProcessWrapper[] bots = new ProcessWrapper[]{bot1ProcessW, bot2ProcessW};

        refereeProcessW.toStdIn("Hiii");
        refereeProcessW.fromStdOut(2); //skip ###Input
        while (true) {
            for (ProcessWrapper bot : bots) {
                refereeProcessW.fromStdOut(1); //skip ###Input

                List<String> first2Lines = refereeProcessW.fromStdOut(2);
                int shipNum = Integer.valueOf(first2Lines.get(0));
                int entitiesNum = Integer.valueOf(first2Lines.get(1));
                first2Lines.forEach(bot::toStdIn);

                refereeProcessW.fromStdOut(entitiesNum).forEach(bot::toStdIn);

                refereeProcessW.fromStdOut(1);

                bot.fromStdOut(shipNum).forEach(refereeProcessW::toStdIn);

            }
        }
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
