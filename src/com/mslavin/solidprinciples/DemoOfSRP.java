package com.mslavin.solidprinciples;

//Single Responsibility Principle (SRP)
//one responsibililty

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class JournalSRP {
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }
    public void removeEntry(int index) {
        entries.remove(index);
    }
    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    //breaks SRP as Journal should not have to be responsible for anything other than being a journal
    public void save(String filename) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(filename)) {
            out.println(toString());
        }
    }

    public void load(String filename) {}
    public void load(URL url) {}
}

class PersistenceSRP {
    public void saveToFile(JournalSRP journal,
                           String filename,
                           boolean overwrite) throws FileNotFoundException
    {
        if (overwrite || new File(filename).exists()) {
            try (PrintStream out = new PrintStream(filename)){
                out.println(toString());
            }
        }
    }
}

class DemoSRP {
    public static void main(String[] args) throws Exception {
        JournalSRP j = new JournalSRP();
        j.addEntry("I cried today");
        j.addEntry("I ate a bug");
        System.out.println(j);

        //SRP
        PersistenceSRP p = new PersistenceSRP();
        String filename = "c:\\temp\\journal.txt";
        p.saveToFile(j, filename, true);

        Runtime.getRuntime().exec("notepad.exe " + filename);

    }
}