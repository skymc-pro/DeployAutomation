package pro.skymc.deployautomation.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public abstract class FolderMonitorTask extends TimerTask {

    private File directory;
    private String filter = ".jar";
    private Map<String, Long> lastModifiedMap;

    public FolderMonitorTask(File directory) {
        this.directory = directory;
        this.lastModifiedMap = new HashMap<String, Long>();
        Arrays.stream(this.directory.listFiles())
                .filter(f -> f.getName().endsWith(filter))
                .forEach(f -> lastModifiedMap.put(f.getName(), f.lastModified()));
    }

    @Override
    public void run() {
        Arrays.stream(this.directory.listFiles())
                .filter(f -> f.getName().endsWith(filter))
                .forEach(f -> {
                    Long time = lastModifiedMap.get(f.getName());
                    if(time == null) time = 0L;

                    if(time < (f.lastModified())){
                        if(isFileClosed(f)) {
                            lastModifiedMap.put(f.getName(), f.lastModified());
                            onChange(f);
                        }
                    }
                });
    }

    public static boolean isFileClosed(File file) {
        try {
            Process process = new ProcessBuilder(new String[]{"lsof", "|", "grep", file.getAbsolutePath()}).start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s = bufferedReader.readLine()) !=null) {
                if(s.contains(file.getAbsolutePath())) {
                    bufferedReader.close();
                    process.destroy();
                    return false;
                }
            }
            bufferedReader.close();
            process.destroy();
        } catch(Exception ex) {}
        return true;
    }

    public abstract void onChange(File file);
}
