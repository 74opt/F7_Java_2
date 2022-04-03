package F7.when.god.forsakes.you.tell.god.to.take.the.lemons.back;

import java.util.*;

public class SoTrueBestie extends RubricsAreDumb {
    String string;

    public SoTrueBestie() {
        super();
        string = "";
    }

    public static void method() {
        RubricsAreDumb[] rubricsAreDumbArray = {new SoTrueBestie()};
        ArrayList<RubricsAreDumb> rubricsAreDumbArrayList = new ArrayList<>(Arrays.asList(rubricsAreDumbArray));

        for (int i = rubricsAreDumbArrayList.size() - 1; i >= 0; i--) {
            System.out.println(rubricsAreDumbArrayList.remove(i));
        }

        ArrayList<Integer> ints = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        // insertion sort
        int insCount = 0;

        for (int i = 1; i < ints.size(); i++) {
            int temp = ints.get(i);
            int j = i;

            while (j > 0 && ints.get(j - 1)> temp) {
                insCount++;
                ints.set(j, ints.get(j - 1));
                j--;
            }

            ints.set(j, temp);
        }

        // selection sort
        int selCount = 0;

        for (int i = 0; i < ints.size() - 1; i++) {
            int min = i;

            // TODO: do i need the count here?
            for (int j = i + 1; j < ints.size(); j++) {
                if (ints.get(j) < ints.get(min)) {
                    min = j;
                }
            }

            if (min != i) {
                selCount++;

                int temp = ints.get(i);
                ints.set(i, ints.get(min));
                ints.set(min, temp);
            }
        }
    }
}