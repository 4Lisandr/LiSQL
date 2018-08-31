package ua.com.juja.lisql.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
class Table {
    int[] maxLength;
    String[][] content;

    public static final int LEFT = -1;
    public static final int CENTER = 0;
    public static final int RIGHT = 1;
    public static final int MAX_WIDTH = 180;

    Table(List<List<?>> data) {
        if (data == null || data.size() == 0)
            return;

        int rows = data.size();
        int col = data.get(0).size();
        maxLength = new int[col];

        content = new String[rows][col];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < col; x++) {
                String s = data.get(y).get(x).toString();
                content[y][x] = s;

                if (maxLength[x] >= s.length()) {
                    continue;
                }
                maxLength[x] = s.length();
            }
        }
    }

    public String makeRows() {
        return makeRows(LEFT);
    }

    public String makeRows(int alignment) {
        if (content.length ==0)
                return "No content in this table!";


        String header = makeRow(0, CENTER);
        String horizontal = Line.horizontal(header.length());

        header = Line.concat(2, Line.SEPARATOR,
                horizontal,
                header,
                horizontal);

        StringBuilder sb = new StringBuilder(header);
        for (int y = 1; y < content.length; y++) {
            sb.append(makeRow(y, alignment)).append(Line.SEPARATOR);
        }

        return sb.append(horizontal).toString();
    }

    //Line.concat(2, "|", tableColumns)
    private String makeRow(int row, int alignment) {
        StringBuilder line = new StringBuilder("|");
        for (int x = 0; x < maxLength.length; x++) {
            line.append(alignCell(content[row][x], x, alignment));
            line.append("|");
        }
        return line.toString();
    }

    /**
     * @int alignment -1 for left margin, 1 right and 0 for center alignment
     */
    private String alignCell(String s, int x, int alignment) {
        StringBuilder builder = new StringBuilder();
        int diff = maxLength[x] - s.length();
        int countToCenter = alignment == CENTER ?
                countToCenter = diff / 2 : 0;
        if (alignment == LEFT)
            builder.append(s);

        fill(builder, countToCenter);
        if (alignment == CENTER)
            builder.append(s);
        fill(builder, diff - countToCenter);

        if (alignment == RIGHT)
            builder.append(s);

        return builder.toString();
    }

    private void fill(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
    }

    public static void main(String[] args) {
        String[][] pocketArray = {{"STATUS CHANGERS", "RUNES", "KEY ITEMS"},
                {"POTION", "SUPER POTION", "FULL HEAL"}, {"ARMOR+", "ATTACK+", "EXP+"},
                {"QUEST ITEMS", "STORY ITEMS", "JOURNAL"}};

        List<List<?>> pocketList = new ArrayList<>(pocketArray.length);
        for (String[] pocket : pocketArray) {
            List<String> currentSubList = new ArrayList<>(pocket.length);
            Collections.addAll(currentSubList, pocket);
            pocketList.add(currentSubList);
        }

        System.out.println(new Table(pocketList).makeRows(LEFT));
    }
}
