import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessWrapper refereeProcessW = new ProcessWrapper(getProcess(new String[]{"java", "Referee"}), "referee", true);
        ProcessWrapper bot1ProcessW = new ProcessWrapper(getProcess(new String[]{"java", "bot.Player"}), "bot1", true);


        refereeProcessW.toStdIn("Hiii");
        final List<String> refereeInitOut = refereeProcessW.fromStdOut();
        refereeInitOut.removeIf((s) -> s.contains("###"));
//        int entityCount = Integer.valueOf(refereeInitOut.get(1));
        refereeInitOut.forEach(bot1ProcessW::toStdIn);

        refereeProcessW.toStdIn(bot1ProcessW.fromStdOut(1).get(0));

        refereeProcessW.fromStdOut().forEach(System.out::println);

    }

    private static Process getProcess(String[] ss) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(ss);
        inheritEnv(processBuilder.environment());
        setDirectory(processBuilder, ss[1]);
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
