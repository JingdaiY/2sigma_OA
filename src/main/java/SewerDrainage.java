package main.java;

import java.util.*;

public class SewerDrainage {

    public static int drainagePartition(List<Integer> parent, List<Integer> inputs) {

        //build a tree using hashmap since we already have parent id
        int len = parent.size();

        Map<Integer, List<Integer>> childrens = new HashMap<>();

        //build the tree
        for (int i = 0; i < len; i++) {

            //root node can be skipped
            if (parent.get(i) == -1) continue;

            List<Integer> children = childrens.getOrDefault(parent.get(i), new ArrayList<>());

            children.add(i);

            childrens.put(parent.get(i), children);

        }

        //build another map to record the sum of water that flows through the node
        Map<Integer, Integer> sumWater = new HashMap<>();

        //go through all nodes using dfs and summarize the water
        int totalSum = dfs(sumWater, childrens, inputs, 0);

        int rs = Integer.MAX_VALUE;

        //go through all nodes
        //cutting branches of the code
        //one: sumWater of childNode
        //the other one: totalSum - sumWater of childNode
        //diff = the one - the other one (absolute vale of the diff)
        //compare all and get the minimum
        for (int index : sumWater.keySet()) {

            //root need to be skipped
            if (index == 0) continue;

            rs = Math.min(rs, Math.abs(totalSum - sumWater.get(index) * 2));

        }

        return rs;

    }

    private static int dfs(Map<Integer, Integer> sumWater, Map<Integer, List<Integer>> childrens, List<Integer> inputs, int index) {
        //start with water that not flows into it which comes from itself
        int sum = inputs.get(index);

        //leaf ndoe
        if (!childrens.containsKey(index)) {

            sumWater.put(index, sum);

            return sum;

        }

        //summize all water that flows into the node
        for (int child : childrens.get(index)) {

            sum += dfs(sumWater, childrens, inputs, child);

        }

        sumWater.put(index, sum);

        return sum;

    }

}
