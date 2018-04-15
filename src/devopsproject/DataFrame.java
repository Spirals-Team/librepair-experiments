package devopsproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataFrame {

    private int linesNumber; // Stocke la taille de la plus grande colonne pour l'affichage du Dataframe
    private List<String> orderedLabels; // Stocke l'ordre des labels pour afficher les colonnes du Dataframe dans le même ordre que celui donné lors de la construction
    private HashMap<String, Integer> indexLabels; // Permet de retrouver la position d'un label/d'une colonne
    private TreeMap<String, List> data; // Table d'association Label -> données

    // Comparator pour ordonner les labels selon leur position donnée à la construction
    private class DataComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return indexLabels.get(o1) < indexLabels.get(o2) ? -1
                    : indexLabels.get(o1) > indexLabels.get(o2) ? 1 : 0;
        }
    }

    public DataFrame() {
        this.linesNumber = 0;
        this.orderedLabels = new ArrayList<>();
        this.indexLabels = new HashMap<>();
        this.data = new TreeMap<>(new DataComparator());
    }

    public DataFrame(String[] labels, List<List> elements) {
        this();
        for (int i = 0; i < elements.size(); i++) {
            this.linesNumber = Math.max(linesNumber, elements.get(i).size());
            this.orderedLabels.add(labels[i]);
            this.indexLabels.put(labels[i], i);
            this.data.put(labels[i], elements.get(i));
        }
    }

    public DataFrame(String nameFile, String separator) throws IOException {
        this();
        FileReader fr = null;
        BufferedReader br;
        String extension;
        List donnees;
        String[] values;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
        Matcher matcher;
        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new IOException("L'extension du fichier est incorrecte");
        } else {
            try {
                fr = new FileReader(nameFile);
                br = new BufferedReader(fr);
                String linea;
                linea = br.readLine();
                String[] labels = linea.split(separator, -1);
                for (int j = 0; j < labels.length; j++) {
                    donnees = new ArrayList<>();
                    this.orderedLabels.add(labels[j]);
                    this.indexLabels.put(labels[j], j);
                    this.data.put(labels[j], donnees);
                }

                // Stockage de la première ligne et inférence de classe (ou du type) de chacun de ses éléments
                String lineaType = br.readLine();
                String[] firstElement = lineaType.split(separator, -1);
                String elementString;

                for (int j = 0; j < firstElement.length; j++) {
                    matcher = pattern.matcher(firstElement[j]);
                    if (!matcher.find()) {
                        throw new NumberFormatException("La premiere ligne ne peut pas avoir des valeurs nuls");
                    }

                    try {
                        int op1 = Integer.parseInt(firstElement[j]);
                        donnees = this.data.get(labels[j]);
                        donnees.add(op1);

                    } catch (NumberFormatException e1) {
                        try {
                            float op2 = Float.parseFloat(firstElement[j]);
                            donnees = this.data.get(labels[j]);
                            donnees.add(op2);
                        } catch (NumberFormatException e2) {
                            elementString = firstElement[j];
                            donnees = this.data.get(labels[j]);
                            donnees.add(elementString);
                        }
                    }
                }
                // Stockage des lignes restantes, inférence des classes de chaun de leus éléments et comparaison des classes avec la classe du premier élément
                while ((linea = br.readLine()) != null) {
                    values = linea.split(separator, -1);
                    for (int i = 0; i < labels.length; i++) {
                        donnees = this.data.get(labels[i]);

                        try {
                            Integer op1 = Integer.parseInt(values[i]);
                            if (op1.getClass().equals(donnees.get(0).getClass())) {
                                donnees.add(op1);
                            } else {
                                throw new IllegalArgumentException("Les donnees dans le label " + "'" + labels[i] + "'" + " ne sont pas bien type. Linea : " + linea + " Donnee : " + op1);
                            }

                        } catch (NumberFormatException e1) {
                            try {
                                Float op2 = Float.parseFloat(values[i]);
                                if (op2.getClass().equals(donnees.get(0).getClass())) {
                                    donnees.add(op2);
                                } else {
                                    throw new IllegalArgumentException("Les donnees dans le label " + "'" + labels[i] + "'" + " ne sont pas bien type. Linea : " + linea + " Donnee : " + op2);
                                }
                            } catch (NumberFormatException e2) {
                                String op3 = values[i];

                                matcher = pattern.matcher(values[i]);

                                if (matcher.find()) {
                                    if (op3.getClass().equals((donnees.get(0).getClass()))) {
                                        donnees.add(op3);
                                    } else {
                                        throw new IllegalArgumentException("Les donnees dans le label " + "'" + labels[i] + "'" + " ne sont pas bien type. Linea : " + linea + " Donnee : " + op3);
                                    }

                                } else {

                                    donnees.add(op3);
                                }

                            }
                        }
                    }
                }

                // Stockage de la taille de la plus grande colonne
                for (Map.Entry<String, List> entry : data.entrySet()) {

                    linesNumber = Math.max(linesNumber, entry.getValue().size());
                }

                fr.close();

            } catch (FileNotFoundException e) {
                System.out.println("Error: Fichier pas trouve");
                System.out.println(e.getMessage());
            } catch (IOException type) {
                System.err.print(type);
            }
        }
    }

    private void print(int deb, int n) {
        int l = deb;
        showLabels();
        while (l < n) {
            String values = "";
            for (Map.Entry<String, List> entry : data.entrySet()) {
                values = values + " | "
                        + (l < entry.getValue().size() && entry.getValue().get(l) != null
                        ? entry.getValue().get(l).toString() : " ");
            }
            values = l + " " + values;
            System.out.println(values);
            l++;
        }
    }

    public void head(int n) {
        print(0, checkingLinesNumber(n, PrintingType.HEAD));
    }

    public void tail(int n) {
        print(checkingLinesNumber(n, PrintingType.TAIL), linesNumber);
    }

    private enum PrintingType {
        HEAD, TAIL
    };

    private int checkingLinesNumber(int n, PrintingType type) {
        if (linesNumber - n < 0) {
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Number of lines < 0 !");
        }
        return type == PrintingType.HEAD ? n : linesNumber - n;
    }

    public void show() {
        print(0, linesNumber);
    }

    public void head(String label, int n) {
        column(label);
        System.out.println(label + " : " + data.get(label).subList(0, checkingLinesNumber(n, PrintingType.HEAD)));
    }

    public void tail(String label, int n) {
        column(label);
        System.out.println(label + " : " + data.get(label).subList(checkingLinesNumber(n, PrintingType.TAIL), data.get(label).size()));
    }

    public void showLabels() {
        String lab = " ";
        for (String orderedLabel : orderedLabels) {
            lab = lab + " | " + orderedLabel;
        }
        System.out.println(lab);
    }

    public int size() {
        int size = 0;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            for (Object object : entry.getValue()) {
                if (object != null) {
                    size++;
                }
            }
        }
        return size;
    }

    private List column(String label) {
        try {
            data.containsKey(label);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return data.get(label);
    }

    public DataFrame loc(String label) {
        List<List> elements = new ArrayList<>();
        elements.add(new ArrayList<>(column(label)));
        String[] labels = {label};
        return new DataFrame(labels, elements);
    }

    public DataFrame loc(List<String> labels) {
        List<List> elements = new ArrayList<>(labels.size());
        for (String label : labels) {
            elements.add(new ArrayList<>(column(label)));
        }
        String[] labelsArray = new String[labels.size()];
        return new DataFrame(labels.toArray(labelsArray), elements);
    }

    public DataFrame loc(String labelInf, String labelSup) {

        column(labelInf);
        column(labelSup);

        int inf = indexLabels.get(labelInf), sup = indexLabels.get(labelSup);

        int size = Math.abs(sup - inf) + 1;
        List<List> elements = new ArrayList<>(size);
        String[] labels = new String[size];

        if (inf > sup) {
            int tmp = inf;
            inf = sup;
            sup = tmp;
        }
        for (int i = inf, j = 0; i <= sup; i++, j++) {
            labels[j] = this.orderedLabels.get(i);
            elements.add(data.get(this.orderedLabels.get(i)));
        }

        return new DataFrame(labels, elements);

    }

    public DataFrame loc(String... labels) {
        List<String> labelsList = new ArrayList<>(labels.length);
        labelsList.addAll(Arrays.asList(labels));
        return loc(labelsList);
    }

    private DataFrame initDataFrameBeforeSelectingLines(int linesNumber) {
        DataFrame df = new DataFrame();
        df.linesNumber = linesNumber;
        df.orderedLabels = new ArrayList<>(orderedLabels);
        df.indexLabels = new HashMap<>(indexLabels);
        df.data = new TreeMap<>(data);
        return df;
    }

    private void checkingIndex(int index) {
        if (index > linesNumber) {
            throw new IllegalArgumentException("index > Number of lines of DataFrame !");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index < 0 !");
        }
    }

    public DataFrame iloc(int index) {
        checkingIndex(index);
        DataFrame df = initDataFrameBeforeSelectingLines(1);
        for (String label : orderedLabels) {
            df.data.replace(label, new ArrayList(Arrays.asList(data.get(label).get(index))));
        }
        return df;
    }

    // Pour chaque index de indexes est associé l'élement de elements correspondant
    private List indexToElement(List<Integer> indexes, List elements) {
        return indexes.stream().map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer index) {
                return elements.get(index);
            }
        }).collect(Collectors.toList());
    }

    public DataFrame iloc(List<Integer> indexes) {
        for (Integer index : indexes) {
            checkingIndex(index);
        }
        DataFrame df = initDataFrameBeforeSelectingLines(indexes.size());
        for (String label : orderedLabels) {
            df.data.replace(label, indexToElement(indexes, data.get(label)));
        }
        return df;
    }

    public DataFrame iloc(Integer... indexes) {
        for (Integer index : indexes) {
            checkingIndex(index);
        }
        DataFrame df = initDataFrameBeforeSelectingLines(indexes.length);
        for (String label : orderedLabels) {
            df.data.replace(label, indexToElement(Arrays.asList(indexes), data.get(label)));
        }
        return df;
    }

    public DataFrame iloc(int indexInf, int indexSup) {
        int inf = indexInf, sup = indexSup;
        if (inf > sup) {
            int tmp = inf;
            inf = sup;
            sup = tmp;
        }
        checkingIndex(inf);
        checkingIndex(sup);
        DataFrame df = initDataFrameBeforeSelectingLines(sup - inf + 1);
        for (String label : orderedLabels) {
            df.data.replace(label, IntStream.range(inf, sup + 1).boxed().map(new Function<Integer, Object>() {

                @Override
                public Object apply(Integer index) {
                    return data.get(label).get(index);
                }
            }).collect(Collectors.toList()));
        }
        return df;
    }

    private void checkingNumberFormat(String label) {
        try {
            if (!(data.get(label).get(0) instanceof Number)) {
                throw new IllegalArgumentException("Column at Label " + label + " is not Numeric !");
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
    }

    private Class<?> checkingComparable(String label) {
        try {
            if (!(data.get(label).get(0) instanceof Comparable)) {
                throw new IllegalArgumentException("Column at Label " + label + " is not Comparable !");
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return data.get(label).get(0).getClass();
    }

    public void showStatitic(String label) {
        System.out.println("Label : " + label);
        System.out.println("Mean : " + meanColumn(label));
        System.out.println("Minimum : " + minColumn(label));
        System.out.println("Maximum : " + maxColumn(label));
    }

    public Float meanColumn(String label) {
        column(label);
        checkingNumberFormat(label);
        List donnee;
        float mean = 0;
        Float num;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey())) {
                donnee = entry.getValue();
                for (int i = 0; i < donnee.size(); i++) {
                    if (!donnee.get(i).toString().equals("") && !donnee.get(i).toString().equals(" ")) {
                        num = new Float(donnee.get(i).toString());
                        mean = mean + num;
                    }
                }
                mean = mean / donnee.size();
            }
        }
        return mean;
    }

    public Comparable minColumn(String label) {
        column(label);
        Class<?> classe = checkingComparable(label);
        Comparable min = (Comparable) data.get(label).get(0);
        for (int i = 1; i < data.get(label).size(); i++) {
            if (!(data.get(label).get(i).getClass().equals(classe))) {
                throw new IllegalArgumentException(data.get(label).get(i) + "is not Comparable !");
            }
            Comparable currentElt = (Comparable) data.get(label).get(i);
            min = currentElt.compareTo(min) == -1 ? currentElt : min;
        }
        return min;
    }

    public Comparable maxColumn(String label) {
        column(label);
        Class<?> classe = checkingComparable(label);
        Comparable max = (Comparable) data.get(label).get(0);
        for (int i = 1; i < data.get(label).size(); i++) {
            if (!(data.get(label).get(i).getClass().equals(classe))) {
                throw new IllegalArgumentException(data.get(label).get(i) + "is not Comparable !");
            }
            Comparable currentElt = (Comparable) data.get(label).get(i);
            max = currentElt.compareTo(max) == 1 ? currentElt : max;
        }
        return max;
    }

    public void orderBy(String label) {
        column(label);
        checkingComparable(label);
    }

    public Integer getMaxColumnSize() {
        return linesNumber;
    }
}
